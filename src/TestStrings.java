
/**
 * Class with string to examine how the parser works.
 */
public class TestStrings {
    /**
     * String containing all kinds of supported mediaWiki syntax.
     */
    public final static String TEST 
            = "==Level 2==\n"
            + "===Level 3===\n"
            + "====Level 4====\n"
            + "=====Level 5=====\n"
            + "======Level 6======\n"
            + "----\n"
            + "Some content\n"
            + "I would like to add another line\n"
            + "This is '''bold''' , '''''bold and italic''''', and just ''italic''\n"
            + "\n"
            + ";Term : Definition\n"
            + ";Term2 : Definition2\n"
            + ":under Term2 \n"
            + "Some more content\n"
            + "Some more lines\n"
            + ";Term with no definition\n"
            + ";Term with one definition : The Definition itself\n"
            + "\n"
            + ":A line with indent\n"
            + ":: A 2-indented line\n"
            + ":: more\n"
            + ":back to 1-indented line\n"
            + "\n"
            + "This is a cow : \n"
            + "[[File:testPicture.png|alt=cow]]\n"
            + "Text after image.\n"
            + "\n"
            + "This is a empty link:  [http://www.google.com].\n"
            + "This is a link: [http://www.google.com Google].\n"
            + "This is a bold link: '''[http://www.google.com Google]'''.\n"
            + "This is a bold-italic link:'''''[http://www.google.com Google]'''''.\n"
            + "\n"
            + "# First\n"
            + "# second\n"
            + "## Second-First\n"
            + "*** First Point\n"
            + "*** Second Point\n"
            + "#### z\n"
            + "#### y\n"
            + "#### x\n"
            + "*** Third Point\n"
            + "## Second-Second [ftp://www.facebook.com Facebook]\n"
            + "## Second-Third [http://www.google.com Google Here] \n"
            + "# Third\n"
            + "[[Video:http://www.youtube.com/embed/ExWfh6sGyso]]\n"
            + "[[Video:http://player.vimeo.com/video/64056533]]\n"
            + "[[Video:http://www.dailymotion.com/embed/video/xyol25]]\n";
}

