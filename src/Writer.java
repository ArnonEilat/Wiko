
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * Writes text to a given file.
 */
public class Writer {
    private File file;
    FileOutputStream fileOutputStream;
    OutputStreamWriter outputStreamWriter;
    private BufferedWriter bufferWriter;

    /**
     * Constructs a Writer object.
     */
    public Writer() {

        try {
            file = new File("test.html");

            if (file.exists() == false) {// If file doesn’t exists, then create it
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, Charset.forName("UTF8"));
            bufferWriter = new BufferedWriter(outputStreamWriter);

        } catch (IOException ex) {
            System.out.println(ex);
            for (StackTraceElement el : ex.getStackTrace()) {
                System.out.println(el);
            }
        }
        String startOfFile =
                "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "       <title>HTML Output</title>\n"
                + "       <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "       <link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"all\">"
                + "    </head>\n"
                + "    <body>\n";
        this.write(startOfFile);
    }

    /**
     * Append data to the file.
     * @param data to append.
     */
    public final void write(String data) {
        try {
            bufferWriter.write(data);
        } catch (IOException ex) {
            System.out.println(ex);
            for (StackTraceElement el : ex.getStackTrace()) {
                System.out.println(el);
            }
        }
    }

    /**
     * Close the <tt>writer</tt>.
     */
    public void close() {
        String end = "</body>\n</html>";
        this.write(end);

        try {
            bufferWriter.close();
        } catch (IOException ex) {
            System.out.println(ex);
            for (StackTraceElement el : ex.getStackTrace()) {
                System.out.println(el);
            }
        }
    }
}

