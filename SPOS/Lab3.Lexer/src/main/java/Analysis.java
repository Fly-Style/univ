import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Analysis {
    private String filename;
    StringBuilder builder = new StringBuilder();
    private Boolean inComment = false;
    private Boolean inCommentLine = false;
    private Boolean inString = false;
    private Boolean inDirective = true;

    public Analysis (String filename){
        this.filename = filename;
    }

    public String coloring() throws FileNotFoundException {
        Scanner file = new Scanner(new FileReader(filename));
        builder.append("<!DOCTYPE html>");
        builder.append("<html lang=\"en\">");
        builder.append("<head><meta charset=\"utf-8\"> <title>Title</title></head>");
        builder.append("<body>");
        while (file.hasNextLine()) {
            checking(file.nextLine());
        }
        builder.append("</body>");
        builder.append("</html>");
        return builder.toString();
    }

    private void checking(String line) {
        String[] words = line.split("[\\t]|[\\s]");
        if (inDirective && (words[0].equals("#include") || words[0].equals("#define"))) {
            builder.append("<font color = #A52A2A>").append(line).append("</font>").append("<br>"); //wine brown

            return;
        }

        inDirective = false;
        for (String word : words) {
            StringBuilder current = new StringBuilder();
            for (int iter = 0; iter < word.length(); iter++) {
                if ((inString && word.charAt(iter) != '"') || inCommentLine || (inComment && (iter == word.length() - 1))
                        || (inComment && !(iter == word.length() - 1) && !word.substring(iter, iter + 2).equals("*/")))
                    current.append(word.charAt(iter));
                else if (inString && word.charAt(iter) == '"') {
                    builder.append(current).append('"').append("</font>");
                    inString = false;
                    current.delete(0, current.length());
                }
                else if (!(iter == word.length() - 1) && inComment && word.substring(iter, iter + 2).equals("*/")) {
                    builder.append(current).append("*/").append("</font>");
                    inComment = false;
                    iter++;
                    current.delete(0, current.length());
                }
                else if ((iter < word.length() - 2) && (word.charAt(iter)=='\'') && (word.charAt(iter+2)=='\'')) {
                    builder.append("<font color = #B5B5B5>").append(word.substring(iter, iter + 3)).append("</font>"); //qrey
                    iter+=2;
                }
                else if ( !(isDelimiter(word.charAt(iter)) || isOperator(word.charAt(iter)) || (!(iter == word.length() - 1) && (isOperator(word.substring(iter, iter + 2))
                        || word.substring(iter, iter + 2).equals("//") || word.substring(iter, iter + 2).equals("/*") || word.substring(iter, iter + 2).equals("*/")))
                        || word.charAt(iter) == '"')) {
                    current.append(word.charAt(iter));
                } else {
                    if (!(current.length() == 0)) {
                        String cur = current.toString();
                        if (isReserved(cur)) outStyle(cur, 1);
                        else if (isIdentificator(cur)) outStyle(cur, 2);
                        else if (isNumber(cur)) outStyle(cur, 3);
                        //else if (isLiteral(cur)) outStyle(cur, 4);
                        else outStyle(cur, 5);
                        current.delete(0, current.length());
                    }

                    if (word.charAt(iter) == '"') {
                        inString = true;
                        current.append("<font color = #FF8C00>").append('"'); //sweet orange
                    }
                    else if (!(iter == word.length() - 1) && word.substring(iter, iter + 2).equals("//") && !inComment) {
                        inCommentLine = true;
                        builder.append("<font color = #B5B5B5>").append("//"); //qrey
                        iter++;
                    } else if (!(iter == word.length() - 1) && word.substring(iter, iter + 2).equals("/*") && !inCommentLine) {
                        inComment = true;
                        builder.append("<font color = #B5B5B5>").append("/*"); //qrey
                        iter++;
                    } else if (isDelimiter(word.charAt(iter))) outStyle(word.charAt(iter) + "", 6);
                    else if (isOperator(word.charAt(iter))) outStyle(word.charAt(iter) + "", 7);
                    else if (!(iter == word.length() - 1) && isOperator(word.substring(iter, iter + 2))) {
                        outStyle(word.substring(iter, iter + 2), 8);
                        iter++;
                    }
                }
            }

            if (current.length() != 0) {
                String cur = current.toString();
                if (inString) builder.append(cur);
                else if (inCommentLine || inComment) builder.append(cur);
                else if (isReserved(cur)) outStyle(cur, 1);
                else if (isIdentificator(cur)) outStyle(cur, 2);
                else if (isNumber(cur)) outStyle(cur, 3);
                //else if (isLiteral(cur)) outStyle(cur, 4);
                else outStyle(cur, 5);
                current.delete(0, current.length());
            }
            builder.append("&nbsp;");
        }

        inCommentLine = false;
        builder.append("<br>");
    }

    private boolean isNumber (String word) {
        if (isHex(word) || isOct(word) || isExp(word)) {
            return true;
        }
        boolean flag = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == '.') {
                if (flag) {
                    return false;
                }
                flag = true;
            } else if ((word.charAt(i) < '0') || (word.charAt(i) > '9')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isExp(String word) {
        boolean dot = false;
        for (int iter = 0; iter < word.length(); iter++) {
            if (!(('0' <= word.charAt(iter)) && (word.charAt(iter) <= '9'))) {
                if (word.charAt(iter)== '.') {
                    if (dot) {
                        return false;
                    }
                    dot = true;
                }
                else if (!(iter == word.length() -1) && (word.charAt(iter) == 'e' || word.charAt(iter) == 'E') && (word.charAt(iter+1)== '-' || word.charAt(iter+1)== '+')) {
                    iter+=1;
                }
                else if (!(word.charAt(iter) == 'e' || word.charAt(iter) == 'E')) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isHex(String word) {
        if (word.length() <= 2) return false;
        if (!(word.substring(0,2).equals("0x") || word.substring(0,2).equals("0X"))) {
            return false;
        }

        for (int i = 2; i < word.length(); i++) {
            if (!((('0' <= word.charAt(i)) && (word.charAt(i) <= '9')) || (('A' <= word.charAt(i)) && (word.charAt(i) <= 'F'))))
                return false;
        }
        return true;
    }

    public static boolean isOct(String word) {
        if (!word.substring(0,1).equals("0")) {
            return false;
        }
        for (int i = 1; i < word.length(); i++) {
            if (!(('0' <= word.charAt(i)) && (word.charAt(i) <= '9')))
                return false;
        }
        return true;
    }

    private boolean isReserved (String word) {
        word = word.toLowerCase();
        return (word.equals("abstract") || word.equals("as") || word.equals("base")  || word.equals("break") || word.equals("case")
                || word.equals("catch") || word.equals("checked") || word.equals("continue") || word.equals("decimal") || word.equals("default")
                || word.equals("delegate") || word.equals("do") || word.equals("else") || word.equals("event") || word.equals("explicit")
                || word.equals("extern") || word.equals("false") || word.equals("finally") || word.equals("fixed") || word.equals("for")
                || word.equals("foreach") || word.equals("goto") || word.equals("if") || word.equals("implicit") || word.equals("in")
                || word.equals("interface") || word.equals("internal") || word.equals("is") || word.equals("lock") || word.equals("namespace")
                || word.equals("new") || word.equals("null") || word.equals("object") || word.equals("operator") || word.equals("out")
                || word.equals("override") || word.equals("params") || word.equals("private") || word.equals("protected") || word.equals("public")
                || word.equals("readonly") || word.equals("ref") || word.equals("return") || word.equals("sealed") || word.equals("sizeof")
                || word.equals("stackalloc") || word.equals("static") || word.equals("switch") || word.equals("this") || word.equals("throw")
                || word.equals("true") || word.equals("try") || word.equals("typeof") || word.equals("unchecked") || word.equals("unsafe")
                || word.equals("using") || word.equals("var") || word.equals("virtual") || word.equals("volatile") || word.equals("while")
                || word.equals("bool") || word.equals("byte") || word.equals("char") || word.equals("class") || word.equals("const") || word.equals("double")
                || word.equals("enum") || word.equals("float") || word.equals("int") || word.equals("list")|| word.equals("long") || word.equals("sbyte")
                || word.equals("short") || word.equals("string") || word.equals("struct") || word.equals("uint") || word.equals("ulong") || word.equals("ushort")
                || word.equals("void"));
    }

    public static boolean isIdentificator(String word) {
        /*word = word.toLowerCase();
        return (   word.equals("bool") || word.equals("byte") || word.equals("char") || word.equals("class") || word.equals("const") || word.equals("double")
                || word.equals("enum") || word.equals("float") || word.equals("int") || word.equals("list")|| word.equals("long") || word.equals("sbyte")
                || word.equals("short") || word.equals("string") || word.equals("struct") || word.equals("uint") || word.equals("ulong") || word.equals("ushort")
                || word.equals("void")
        ); */
        if (!(('a' <= word.charAt(0)) && (word.charAt(0) <= 'z') || ('A' <= word.charAt(0)) && (word.charAt(0) <= 'Z'))) {
            return false;
        }
        for (int i = 1; i < word.length(); i++) {
            if (!((('0' <= word.charAt(i)) && (word.charAt(i) <= '9')) || (('a' <= word.charAt(i)) && (word.charAt(i) <= 'z'))
                    || ('A' <= word.charAt(i)) && (word.charAt(i) <= 'Z') || (word.charAt(i) == '_'))) {
                return false;
            }
        }
        return true;
    }

    private boolean isOperator (Character word) {
        return word.equals('+') || word.equals('-') || word.equals('*') || word.equals('/') || word.equals('=') || word.equals(':') || word.equals('?')
                || word.equals('!') || word.equals('%') || word.equals('>') || word.equals('<') || word.equals('^') || word.equals('|') || word.equals('&');
    }

    private boolean isOperator (String word) {
        return word.equals("+=") || word.equals("-=") || word.equals("*=") || word.equals("/=") || word.equals(">>") || word.equals("<<")
                || word.equals("&&") || word.equals("||") || word.equals("==") || word.equals("!=") || word.equals("++") || word.equals("--")
                || word.equals(">=") || word.equals("<=") || word.equals("??") || word.equals("%=") || word.equals("&=") || word.equals("|=")
                || word.equals("^=") || word.equals("=>");
    }

    private boolean isDelimiter(Character word) {
        return word.equals('.') || word.equals(',') || word.equals(';') || word.equals('(') || word.equals(')') || word.equals('[')
                || word.equals(']') || word.equals('{') || word.equals('}');
    }

    /*private boolean isLiteral (String word) {
        if (!(('a' <= word.charAt(0)) && (word.charAt(0) <= 'z') || ('A' <= word.charAt(0)) && (word.charAt(0) <= 'Z'))) {
            return false;
        }
        for (int i = 1; i < word.length(); i++) {
            if (!((('0' <= word.charAt(i)) && (word.charAt(i) <= '9')) || (('a' <= word.charAt(i)) && (word.charAt(i) <= 'z'))
                    || ('A' <= word.charAt(i)) && (word.charAt(i) <= 'Z') || (word.charAt(i) == '_'))) {
                return false;
            }
        }
        return true;
    } */

    public void outStyle(String word, int typeOf) {
        if (typeOf == 1) {
            builder.append("<font style = \"color:#5B8DDE\">").append(word).append("</font>"); // reserved -> dark chocolate
        } else if (typeOf == 2) {
            builder.append("<font style = \"color:#CD661D\">").append(word).append("</font>"); // identificator -> dark orange red
        } else if (typeOf == 3) {
            builder.append("<font style = \"color:#FFA500\">").append(word).append("</font>"); // number -> orange
        } else if (typeOf == 4) {
            builder.append("<font style = \"color:#000000\">").append(word).append("</font>"); //
        } else if (typeOf == 5) {
            builder.append("<font style = \"color:#FF00FF\">").append(word).append("</font>"); // wtf
        } else if (typeOf == 6) {
            builder.append("<font style = \"color:#b100d1\">").append(word).append("</font>"); // delimiter -> violet
        } else if (typeOf == 7) {
            builder.append("<font style = \"color:#8B4726\">").append(word).append("</font>"); // operator -> sienna
        }

    }
}

