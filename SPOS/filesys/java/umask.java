/**
 * @Author is flystyle
 * Created on 30.05.16.
 */
public class umask {
    public static final String PROGRAM_NAME = "umask" ;


    public static String parseOct(String str) {
        return Integer.toOctalString(Integer.parseInt(str, 2));
    }

    public static String rev (String s) {
        StringBuilder str  = new StringBuilder();
        for(int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '0')
                str.append('1');
            else if (s.charAt(i) == '1')
                str.append('0');
            else return "";
        }
        return str.toString();
    }

    public static String AND (String a, String b) {
        StringBuilder builder = new StringBuilder();
        if (a.length() != b.length())
            return null;
        else {
            for (int i = 0; i < a.length(); ++i) {
                if (a.charAt(i) == '1' && b.charAt(i) == '1')
                    builder.append('1');
                else builder.append('0');
            }
        }
        return builder.toString();
    }

    public static void main (String [] args) throws Exception{

        /*
         * Example: java umask 1010101010
         */
        Kernel.initialize() ;

        if( args.length < 1 )
        {
            System.err.println( PROGRAM_NAME + ": too few arguments" ) ;
            Kernel.exit( 1 ) ;
        }

        String smask = args[0];
        String noSmask = rev(smask);
        String fullRoot = "110110110";    // 666
        String fullRootDir = "111111111"; // 777
        short mask = Short.parseShort(parseOct(smask));

        String reallyMask = AND(noSmask, fullRootDir);

        short grp = Kernel.umask(mask);
        System.out.println(grp);

        Kernel.exit( 0 ) ;
    }
}
