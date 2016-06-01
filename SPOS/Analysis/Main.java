//import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileIn = "in.txt";
        Analysis file = new Analysis(fileIn);
        String content = file.coloring();
        String fileOut = "out.html";
        writeToFile(content, fileOut);
        /*String fileIn = "in.txt";
        Transforming file = new Transforming(fileIn);
        String content = file.analyse();
        String fileOut = "out.html";
        writeToFile(content, fileOut); */
    }

    /*public static File writeToFile(final String content, final String fileName) throws IOException {
        File file = new File(fileName);
        FileUtils.writeStringToFile(file, content);
        return file;
    } */

    public static File writeToFile(final String content, final String fileName) throws IOException {
        File file = new File(fileName);

        if (file.exists()) {
            if (!file.delete()) {
                throw new IOException("Can't delete file " + file.getAbsolutePath());
            }
        }
        if (!file.createNewFile()) {
            throw new IOException("Can't create file " + file.getAbsolutePath());
        }

        PrintWriter pw = new PrintWriter(new FileOutputStream(file));
        pw.write(content);
        pw.flush();
        pw.close();

        return file;
    }


}









