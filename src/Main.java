
import wiko.Wiko;


/**
 * Main class to examine how the parser works.
 */
public class Main {
    /**
     * @param args The command line arguments - No such.
     */
    public static void main(String[] args) {
        Wiko wiki = new Wiko();
        Writer wrt = new Writer();
        String HTML = wiki.process(TestStrings.TEST);
        wrt.write(HTML);
        wrt.close();
    }
}

