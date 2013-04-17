package wiko;

/**
 * Convert Wiki markup to HTML.<br>
 * Supported Syntax:
 * <li>Heading (Levels 6 to 2): <tt>== Heading ==</tt> .
 * <li>Links: <tt>[http://www.url.com|Name of URLs]</tt>
 * <li>images: <tt>[[File:http://www.url.com/image.png|alt=Alternative
 * Text]]</tt>
 * <li>Horizontal line: <tt>----</tt>
 * <li>Indentation: <tt>:</tt>
 * <li>Ordered bullet point: <tt>#</tt>
 * <li>Unordered bullet point: <tt>*</tt>
 * <li>Definition List: <tt>;:</tt>
 */
public class Wiko {
    private String[] lines;
    /* Indicate whether the element creat block by defult.
     * https://developer.mozilla.org/en-US/docs/CSS/display
     */
    private boolean isABlockElement = false;

    /**
     * Convert Wiki markup to HTML.<br>
     * @param wikiText to convert to HTML.
     */
    public String process(String wikiText) {
        lines = wikiText.split("\\n");
        int start;
        StringBuilder html = new StringBuilder("");

        for (int i = 0; i < lines.length; i++) {// Run through all the lines.
            String line = lines[i];
            /*
             * Section headings:
             * Levels 2 to 6
             */
            if (line.matches("^={6}[^=]*={6}") == true) {
                html.append("<h6>");
                html.append(line.substring(6, line.length() - 6));
                html.append("</h6>");
                isABlockElement = true;
            } else if (line.matches("^={5}[^=]*={5}") == true) {
                html.append("<h5>");
                html.append(line.substring(5, line.length() - 5));
                html.append("</h5>");
                isABlockElement = true;
            } else if (line.matches("^={4}[^=]*={4}") == true) {
                html.append("<h4>");
                html.append(line.substring(4, line.length() - 4));
                html.append("</h4>");
                isABlockElement = true;
            } else if (line.matches("^={3}[^=]*={3}") == true) {
                html.append("<h3>");
                html.append(line.substring(3, line.length() - 3));
                html.append("</h3>");
                isABlockElement = true;
            } else if (line.matches("^={2}[^=]*={2}") == true) {
                html.append("<h2>");
                html.append(line.substring(2, line.length() - 2));
                html.append("</h2>");
                isABlockElement = true;
            } /*
             * Definition List.
             */ else if (RegEx.find(RegEx.DEFINITION_LIST, line) == true) {
                // Find start line and ending line.
                start = i;
                while (i < lines.length
                        && RegEx.find(RegEx.NEST_UNDER_DEFINITION_LIST, lines[i]) == true) {
                    i++;
                }
                i--;
                String tmpStr = this.processDefinitionList(lines, start, i);
                html.append(tmpStr);
                isABlockElement = true;
            } /*
             * Lists: 
             *  Indent text
             *  Ordered lists
             *  Unordered lists
             */ else if (RegEx.find(RegEx.INDENT, line) == true) {
                // Find start line and ending line.
                start = i;
                while (i < lines.length && RegEx.find(RegEx.INDENT, lines[i]) == true) {
                    i++;
                }
                i--;

                html.append(this.processIndent(start, i));
                isABlockElement = true;
            } else if (RegEx.find(RegEx.ORDERED_LIST, line) == true) {
                // Find start line and ending line.
                start = i;
                while (i < lines.length && RegEx.find(RegEx.LIST_SIMBOL, lines[i]) == true) {
                    i++;
                }
                i--;
                html.append(this.processBulletPoint(start, i));
                isABlockElement = true;
            } /*
             * Horizontal line.
             */ else if (RegEx.find(RegEx.LINE, line) == true) {
                html.append("<hr/>");
                isABlockElement = true;
            } else { // It a free text.
                if (line.isEmpty() == false) {
                    int endOfFreeText = i;
                    while (endOfFreeText < lines.length
                            && Utill.isNormalText(lines[endOfFreeText]) == true) {
                        endOfFreeText++;
                    }
                    String freeTxt = "<p>" + Utill.join(lines, "<br>\n", i, endOfFreeText) + "</p>";
                    html.append(Processors.processLine(freeTxt));
                    isABlockElement = true;
                    i += (endOfFreeText - 1 - i);
                } else {
                    isABlockElement = true;
                }
            }
            if (isABlockElement == true) {
                html.append("\n");
            } else {
                html.append("<br>\n");
            }
            isABlockElement = false;
        }
        return html.toString();
    }

    /**
     * Process indent.
     * @param start index in <tt>lines</tt> to process.
     * @param end   index in <tt>lines</tt> to process.
     */
    private String processIndent(int start, int end) {
        StringBuilder htmlOutput = new StringBuilder("<dl>");

        for (int i = start; i <= end; i++) {
            htmlOutput.append("<dd>");

            int currentCount = Utill.countColons(lines[i]);

            htmlOutput.append(Processors.processLine(lines[i].substring(currentCount)));

            int nestedEnd = i;
            for (int j = i + 1; j <= end; j++) {
                int nestedCount = Utill.countColons(lines[j]);
                if (nestedCount <= currentCount) {
                    break;
                } else {
                    nestedEnd = j;
                }
            }

            if (nestedEnd > i) {
                htmlOutput.append(this.processIndent(i + 1, nestedEnd));
                i = nestedEnd;
            }
            htmlOutput.append("</dd>\n");
        }
        htmlOutput.append("</dl>\n");
        return htmlOutput.toString();
    }

    /**
     * Process bullet point.
     * @param start index in <tt>lines</tt> to process.
     * @param end   index in <tt>lines</tt> to process.
     */
    private String processBulletPoint(int start, int end) {
        StringBuilder htmlOutput;
        if (lines[start].charAt(0) == '*') {
            htmlOutput = new StringBuilder("<ul>"); // Unordered (bulleted) list.
        } else {
            htmlOutput = new StringBuilder("<ol>"); // Ordered list.
        }
        htmlOutput.append('\n'); // More readable source.

        for (int i = start; i <= end; i++) {

            htmlOutput.append("<li>");

            int currentCount = Utill.countListIndet(lines[i]);

            htmlOutput.append(Processors.processLine(lines[i].substring(currentCount + 1)));

            // Continue previous with #:
            int nestedEnd = i;
            for (int j = i + 1; j <= end; j++) {
                int nestedCount = Utill.countAllIndent(lines[j]);

                if (nestedCount < currentCount) {
                    break;
                } else {
                    if (lines[j].charAt(nestedCount) == ':') {
                        htmlOutput.append("<br/>");
                        htmlOutput.append(Processors.processLine(lines[j].substring(nestedCount + 2)));
                        nestedEnd = j;
                    } else {
                        break;
                    }
                }
            }
            i = nestedEnd;

            // Nested bullet point
            int nestedBPointEnd = i;
            for (int j = i + 1; j <= end; j++) {
                int nestedCount = Utill.countAllIndent(lines[j]);
                if (nestedCount <= currentCount) {
                    break;
                } else {
                    nestedBPointEnd = j;
                }
            }

            if (nestedBPointEnd > i) {
                htmlOutput.append(this.processBulletPoint(i + 1, nestedBPointEnd));
                i = nestedBPointEnd;
            }

            // Continue previous with #:
            int nestedPreviousEnd = i;
            for (int j = i + 1; j <= end; j++) {
                int nestedCount = Utill.countAllIndent(lines[j]);
                if (nestedCount < currentCount) {
                    break;
                } else {
                    if (lines[j].charAt(nestedCount) == ':') {
                        htmlOutput.append(Processors.processLine(lines[j].substring(nestedCount + 2)));
                        nestedPreviousEnd = j;
                    } else {
                        break;
                    }
                }
            }
            i = nestedPreviousEnd;
        }
        htmlOutput.append("</li>\n");

        if (lines[start].charAt(0) == '*') {
            htmlOutput.append("</ul>");
        } else {
            htmlOutput.append("</ol>");
        }
        htmlOutput.append('\n');
        return htmlOutput.toString();
    }

    /**
     * Creates a definition list ({@literal <}dl>) from all the lines strting at
     * <tt>start</tt> through <tt>end</tt> inclusive.
     *
     * @param start index of the definition list inside <tt>lines</tt>.
     * @param end   index of the definition list inside <tt>lines</tt>.
     * @param lines to convert from Wiki markup definition list to HTML
     *              definition list.
     *
     * TODO: Add support to definition list with indent.
     */
    private String processDefinitionList(String[] lines, int start, int end) {
        StringBuilder htmlOutput = new StringBuilder("<dl>");

        do {
            htmlOutput.append("<dt>");// a new term.

            int colonsCount = Utill.countColons(lines[start]);
            int startOfTerm = lines[start].indexOf(';', colonsCount) + 1;
            int endOfTerm = lines[start].indexOf(':', startOfTerm);

            String term;
            if (endOfTerm != -1) {
                term = lines[start].substring(startOfTerm, endOfTerm);
            } else {
                term = lines[start].substring(startOfTerm);
            }
            htmlOutput.append(Processors.processLine(term));
            htmlOutput.append("</dt>");

            if (endOfTerm != -1) {// Remove the term from lines[start]
                lines[start] = //    but keep the definition (if exist).
                        lines[start].substring(endOfTerm);
            } else {// There is no definition - Just term.
                if (start < end) {
                    start++;  // process next line.
                    continue;
                } else {
                    break; // We are at the last line - stop the loop.
                }
            }

            if (start == end) // We are at the last definition:
            {                 // append it, close all the tags and return.
                htmlOutput.append("<dd>");
                lines[start] = Utill.trimColons(lines[start]);
                htmlOutput.append(Processors.processLine(lines[start]));
                htmlOutput.append("</dd>\n</dl>");
                return htmlOutput.toString();
            } else {
                int // indicat where is the last definition for this term. 
                        lastDefinition = start;

                while // find the index of last definition.
                        (lastDefinition < lines.length
                        && lines[lastDefinition].charAt(0) == ':') {
                    lastDefinition++;
                }
                for (; start < lastDefinition; start++)//Add all the definitions to htmlOutput. 
                {
                    htmlOutput.append("<dd>");
                    lines[start] = Utill.trimColons(lines[start]);
                    htmlOutput.append(Processors.processLine(lines[start]));
                    htmlOutput.append("</dd>");
                }
            }
        } while (start <= end);

        htmlOutput.append("</dl>");
        return htmlOutput.toString();
    }
}