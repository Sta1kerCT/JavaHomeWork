package md2html;


import java.io.*;
import java.io.FileNotFoundException;
import java.util.*;

public class Md2Html {
    private static final Map<Character, String> AMPS = Map.of(
            '<', "&lt;",
            '>', "&gt;",
            '&', "&amp;"
    );


    private static final Map<Character, String> SERVICE_CHAR = Map.of(
            '*', "em",
            '_', "em",
            '`', "code",
            '-', "-"
    );


    public static int opener(StringBuilder source, StringBuilder out, int curIndex, Deque<String> deque) {
        int counter = 0;
        char curChar = source.charAt(curIndex);
        while (curChar == '#') {
            counter++;
            curChar = source.charAt(++curIndex);
        }
        if (counter == 0 || !Character.isWhitespace(curChar) || counter > 6) {
            out.append("<p>").append("#".repeat(counter));
            deque.push("p");
        } else {
            out.append("<").append("h").append(counter).append(">");
            deque.push("h" + counter);
            curIndex++;
        }
        return curIndex;
    }


    public static int selection(StringBuilder source, StringBuilder out, int curIndex, ArrayDeque<String> deque) {
        char curChar = source.charAt(curIndex);
        if (!((curIndex == 0 || Character.isWhitespace(source.charAt(curIndex - 1))) &&
                Character.isWhitespace(source.charAt(curIndex + 1)))) {
            String tag;
            if (source.charAt(curIndex + 1) == curChar) {
                curIndex++;
                tag = curChar == '-' ? "s" : "strong";
            } else if (curChar == '-') {
                out.append(curChar);
                return curIndex;
            } else {
                tag = SERVICE_CHAR.get(curChar);
            }
            if (Objects.equals(deque.peek(), tag)) {
                deque.pop();
                out.append("</").append(tag).append(">");
            } else {
                deque.push(tag);
                out.append("<").append(tag).append(">");
            }
        } else {
            out.append(curChar);
        }
        return curIndex;
    }


    public static int image(StringBuilder source, StringBuilder out, int curIndex) {
        StringBuilder tmp = new StringBuilder();
        curIndex += 2;
        char curChar = source.charAt(curIndex);
        while (curChar != ']') {
            if (curChar == '\\') {
                curChar = source.charAt(++curIndex);
            }
            tmp.append(curChar);
            curChar = source.charAt(++curIndex);
        }
        out.append("<img alt='").append(tmp).append("' ");
        tmp.setLength(0);
        curIndex += 2;
        curChar = source.charAt(curIndex);
        while (curChar != ')') {
            tmp.append(curChar);
            curChar = source.charAt(++curIndex);
        }
        out.append("src='").append(tmp).append("'>");
        return curIndex;
    }


    public static void main(String[] args) {
        final StringBuilder html = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(args[0]), "UTF8"))) {
            final ArrayDeque<String> deque = new ArrayDeque<>();
            String read = reader.readLine();
            while (read != null) {
                final StringBuilder mark = new StringBuilder();
                while (read != null && read.length() != 0) {
                    mark.append(read).append('\n');
                    read = reader.readLine();
                }
                for (int i = 0; i < mark.length(); i++) {
                    char curChar = mark.charAt(i);
                    if (i == 0) {
                        curChar = mark.charAt(i = opener(mark, html, i, deque));
                    }
                    if (curChar == '!' && mark.charAt(i + 1) == '[') {
                        i = image(mark, html, i);
                        continue;
                    }
                    if (SERVICE_CHAR.containsKey(curChar)) {
                        i = selection(mark, html, i, deque);
                        continue;
                    }
                    if (curChar == '\\') {
                        curChar = mark.charAt(++i);
                    }
                    if (AMPS.containsKey(curChar)) {
                        html.append(AMPS.get(curChar));
                    } else {
                        html.append(curChar);
                    }
                }
                if (!deque.isEmpty()) {
                    html.deleteCharAt(html.length() - 1);
                    html.append("</").append(deque.pop()).append(">\n");
                }
                read = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("InputFile not found:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Input exception:" + e.getMessage());
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF8"))) {
            writer.write(html.toString());
        } catch (FileNotFoundException e) {
            System.out.println("OutputFile not found:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Output exception:" + e.getMessage());
        }
    }
}