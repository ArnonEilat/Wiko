package wiko;


/**
 * Class with methods to convert one line in Wiki markup to HTML.<br>
 */
public final class Processors {
    /**
     * Supported Internet protocols.
     */
    private static String[] WEB_PROTOCOLS = {"http", "ftp", "news"};

    /**
     * Don't let anyone instantiate this class.
     */
    private Processors() {
    }

    /**
     * Get one line in a Wiki markup and return it as HTML.<br>
     * Supported syntax:
     * <blockquote>
     * <pre>
     * Image         [[File:path/to/image/file.jpg]]
     * Video         [[Video:url/to/video/file]]
     * URL           [http://www.link.com linkName].
     * bold text     '''bold Text'''
     * Italic text   ''italic Text''
     * </pre>
     * </blockquote>
     * @param wikiText to convert to HTML text.
     * @return String with HTML representation of wiki markup.
     */
    public static String processLine(String wikiText) {

        // Image
        int indexOfFile = wikiText.indexOf("[[File:");
        int endIndexOfFile = wikiText.indexOf("]]", indexOfFile + 7);
        while (indexOfFile != -1 && endIndexOfFile != -1) {
            wikiText =
                    wikiText.substring(0, indexOfFile) +// Removes the "[[File:"
                    processImage(wikiText.substring(indexOfFile + 7, endIndexOfFile))
                    + wikiText.substring(endIndexOfFile + 2);// Removes the "]]"

            indexOfFile = // Gets the indexes of next image
                    wikiText.indexOf("[[File:", indexOfFile + 7);
            endIndexOfFile = wikiText.indexOf("]]", indexOfFile + 7);
        }

        // Video
        int indexOfVideo = wikiText.indexOf("[[Video:");
        int endIndexOfVideo = wikiText.indexOf("]]", indexOfVideo + 8);
        while (indexOfVideo != -1 && endIndexOfVideo != -1) {
            wikiText = wikiText.substring(0, indexOfVideo)
                    + processVideo(wikiText.substring(indexOfVideo + 8, endIndexOfVideo))
                    + wikiText.substring(endIndexOfVideo + 2);
            indexOfVideo = wikiText.indexOf("[[Video:");
            endIndexOfVideo = wikiText.indexOf("]]", indexOfVideo + 8);
        }

        // URL 
        for (int i = 0; i < WEB_PROTOCOLS.length; i++) {
            int index = wikiText.indexOf("[" + WEB_PROTOCOLS[i] + "://");
            int endIndex = wikiText.indexOf("]", index + 1);
            while (index != -1 && endIndex != -1) {
                wikiText = wikiText.substring(0, index)
                        + processURL(wikiText.substring(index + 1, endIndex))
                        + wikiText.substring(endIndex + 1);
                index = wikiText.indexOf("[" + WEB_PROTOCOLS[i] + "://", endIndex + 1);
                endIndex = wikiText.indexOf("]", index + 1);
            }
        }


        int countBold = 0;
        int indexOfApostrophe = // Use for both:Bold and Italic
                wikiText.indexOf("'''");
        while (indexOfApostrophe != -1) {
            if ((countBold % 2) == 0) {
                wikiText = wikiText.replaceFirst("'''", "<b>");
            } else {
                wikiText = wikiText.replaceFirst("'''", "</b>");
            }
            countBold++;
            indexOfApostrophe = wikiText.indexOf("'''", indexOfApostrophe);
        }

        int countItalic = 0;
        indexOfApostrophe = wikiText.indexOf("''");
        while (indexOfApostrophe > -1) {
            if ((countItalic % 2) == 0) {
                wikiText = wikiText.replaceFirst("''", "<i>");
            } else {
                wikiText = wikiText.replaceFirst("''", "</i>");
            }
            countItalic++;
            indexOfApostrophe = wikiText.indexOf("''", indexOfApostrophe);
        }

        wikiText = wikiText.replace("<\\/b><\\/i>", "</i></b>");
        return wikiText;
    }

    /**
     * Gets string with URL to image and alternate text attribute(optional)
     * separated with vertical bar (|) and convert it to HTML image tag.<br>
     * E.i:
     * <blockquote><pre>
     *[[File:path/to/image/file.jpg|alt=Alternative Text]]
     * </blockquote></pre> will be:
     * <blockquote><pre>
     * {@literal <}img src="path/to/image/file.jpg" alt="Alternative Text"/>";
     * </blockquote></pre>
     * @param txt Wiki markup image
     */
    private static String processImage(String txt) {
        String url;
        String label = "";
        String arr[] = txt.split("\\|");

        url = arr[0] + "\"";
        if (arr[1].startsWith("alt=")) {
            label = " alt=\"" + arr[1].substring(4) + "\" ";
        }

        return "<img src=\"" + url + label + " />";
    }

    /**
     * Gets string with video URL from one of the supported sites, and convert
     * it to HTML <tt>iframe</tt> tag which embed it.<br>
     * Supported web sites:
     * <blockquote>
     * <pre>
     * YouTube
     * Vimeo
     * DailyMotion
     * </pre>
     * </blockquote>
     */
    private static String processVideo(String url) {
        String id;
        int startOfId;

        if (url.contains("youtube")) {
            startOfId = url.indexOf("youtube.com/");
            id = url.substring(startOfId + 11);
            url = "http://www.youtube.com/" + id;
        } else if (url.contains("vimeo")) {
            startOfId = url.indexOf("vimeo.com/");
            id = url.substring(startOfId + 10);
            url = "http://player.vimeo.com/" + id;
        } else if (url.contains("dailymotion")) {
            startOfId = url.indexOf("dailymotion.com/");
            id = url.substring(startOfId + 16);
            url = "http://www.dailymotion.com/" + id;
        } else {
            return " Can only handle YouTube, DailyMotion and Vimeo URLs ";
        }

        return "<iframe width=\"480\" height=\"390\" src=\"" + url + "\" frameborder=\"0\" allowfullscreen></iframe>";
    }

    /**
     * Gets string with URL and text(optional) separated with space and convert
     * it to HTML a tag.<br>
     * E.g:
     * <blockquote><pre>
     *  http://www.google.com Google
     * will be:
     * {@literal <}a href="http://www.google.com">Google{@literal <}/a>
     * </pre></blockquote>
     * @param txt Wiki markup image
     */
    private static String processURL(String txt) {
        int index = txt.indexOf(" ");
        String url = txt;
        String label = txt;

        if (index != -1) {
            url = txt.substring(0, index);
            label = txt.substring(index + 1);
        }
        return "<a href=\"" + url + "\">" + label + "</a>";
    }
}

