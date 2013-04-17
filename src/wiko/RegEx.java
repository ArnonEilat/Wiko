package wiko;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The <tt>RegEx</tt> class contains couple of
 * {@link  java.util.regex.Pattern Patterns} and one
 * {@link RegEx#find(java.util.regex.Pattern, java.lang.String) find} method
 * which are all static.<br>
 * The purpose of this class is to be used as utility class for{@link Wiki}
 */
public final class RegEx {
    /*
     * Don't let anyone instantiate this class.
     */
    private RegEx() {
    }
    /**
     * Regular Expression to match <tt>;</tt> (once or more) at the start of
     * line.<br>
     * Can be precede by <tt>:</tt> <br>
     * Note:<br>
     * <blockquote>
     * To achieve full and more accurate(too much accurate) match should use:
     * (\;+)([^:]*)(:+).
     * </blockquote>
     */
    public static final Pattern DEFINITION_LIST = Pattern.compile("(:*)(;+)", Pattern.UNICODE_CASE);
    /**
     * Regular Expression to match <tt>;</tt> Or <tt>:</tt> at least one time at
     * the start of line.<br>
     */
    public static final Pattern NEST_UNDER_DEFINITION_LIST = Pattern.compile("^(:+?|;+?)", Pattern.UNICODE_CASE);
    /**
     * Regular Expression to match <tt>:</tt> (once or more) at the start of
     * line.
     */
    public static final Pattern INDENT = Pattern.compile("^(:+)", Pattern.UNICODE_CASE);
    /**
     * Regular Expression to match <tt>*</tt> (once or more) at the start of
     * line.
     */
    public static final Pattern BULLET = Pattern.compile("(\\*+)", Pattern.UNICODE_CASE);
    /**
     * Regular Expression to match <tt>#</tt> (once or more) at the start of
     * line.
     */
    public static final Pattern ORDERED_LIST = Pattern.compile("(#+ )", Pattern.UNICODE_CASE);
    /**
     * Regular Expression to match <tt>#</tt> Or <tt>**</tt> Or <tt>:</tt> (once
     * or more) at the start of line.
     */
    public static final Pattern LIST_SIMBOL = Pattern.compile("(#+|(\\*\\*)+):?", Pattern.UNICODE_CASE);
    /**
     * Regular Expression to match <tt>----</tt> (four dashes or more) at the
     * start of line.
     */
    public static final Pattern LINE = Pattern.compile("-{4,}", Pattern.UNICODE_CASE);

    /**
     * Attempts to find the subsequence of the input sequence that matches the
     * <tt>pattern</tt>.
     * @param pattern The expression to be compiled
     * @param input   The character sequence to be matched
     * @return <tt>true</tt> if, and only if, a subsequence of the input
     * sequence matches the <tt>pattern</tt>.
     */
    public static boolean find(Pattern pattern, String input) {
        Pattern p = pattern;
        Matcher m = p.matcher(input);
        return m.find();
    }
}

