package wiko;


import java.util.regex.Pattern;

/**
 * Static utility class to be used by {@link Wiki} class.
 */
public final class Utill {
    /**
     * Don't let anyone instantiate this class.
     */
    private Utill() {
    }

    /**
     * Count the colons in the starts of <tt>str</tt>.<br>
     * E.g:
     * <blockquote>
     * <tt>Utill.countColons(":::someText:")</tt> return 3
     * </blockquote>
     */
    public static int countColons(String str) {
        int amount = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ':') {
                amount++;
            } else {
                return amount;
            }
        }
        return -1;
    }

    /**
     * Count list signs(ordered and unordered) in the starts of
     * <tt>str</tt>.<br>
     * List signs are: <tt>#</tt> , <tt>*</tt><br>
     * E.g:
     * <blockquote>
     * <tt>Utill.countListIndet("##*someText#")</tt> return 3
     * </blockquote>
     */
    public static int countListIndet(String str) {
        int level = 0;

        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case '#':
                case '*':
                    level++;
                    break;
                default:
                    return level;
            }
        }
        return -1;
    }

    /**
     * Count list and indent signs in the starts of <tt>str</tt>.<br>
     * Counted characters are: <tt>#</tt> , <tt>*</tt> , <tt>:</tt><br>
     * e.g:
     * <blockquote>
     * <tt>Utill.countAllIndent("#:*someText#")</tt> return 3
     * </blockquote>
     */
    public static int countAllIndent(String str) {
        int level = 0;

        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case ':':
                case '#':
                case '*':
                    level++;
                    break;
                default:
                    return level;
            }
        }
        return -1;
    }

    /**
     * Returns a copy of <tt>str</tt>, with leading colons omitted.
     * @param str String to trim.
     * @return A copy of <tt>str</tt> without leading colons, or <tt>str</tt> if
     * it has no leading colons.
     */
    public static String trimColons(String str) {
        int i = 0;
        do {
            i++;
        } while (i != str.length() && str.charAt(i) == ':');
        if (i == str.length()) {
            return null;
        }
        return str.substring(i);
    }

    /**
     * Returns a string containing the string representation of each of lines
     * starting at <tt>start</tt> index until <tt>end</tt> index inclusive.<br>
     * The connection is done by using the <tt>separator</tt> between each line.
     * @param lines     to connect.
     * @param separator to connect the lines with.
     * @param start     index to connect from.
     * @param end       index to connect to.
     */
    public static String join(String[] lines, String separator, int start, int end) {
        StringBuilder sb = new StringBuilder("");
        for (int i = start; i < end; i++) {
            sb.append(lines[i]);
            sb.append(separator);
        }
        return sb.toString();
    }

    /**
     * Returns true if and only if all the {@link Pattern}s in the {@link RegEx}
     * class not matches.
     * @param text to match against all the patterns in the {@link RegEx} class.
     */
    public static boolean isNormalText(String text) {
        if (text.matches("^={2}[^=]*={2}") == true) {
            return false;
        }
        if (RegEx.find(RegEx.DEFINITION_LIST, text) == true) {
            return false;
        }
        if (RegEx.find(RegEx.INDENT, text) == true) {
            return false;
        }
        if (RegEx.find(RegEx.ORDERED_LIST, text) == true) {
            return false;
        }
        if (RegEx.find(RegEx.LINE, text) == true) {
            return false;
        }
        if (text.isEmpty() == true) {
            return false;
        }
        return true;
    }
}

