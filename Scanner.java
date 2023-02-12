import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;


public class Scanner {

    private static class Parser implements AbstractParser {
        public boolean delimiter(char i) {
            return Character.isWhitespace(i);
        }
    }

    private static final String LINE_FEED = System.lineSeparator();
    private final BufferedReader reader;
    private final char[] buffer = new char[1024];
    private final StringBuilder nextToken = new StringBuilder();
    private int curLineNo = 0;
    private int pointer = 0;
    private char lastChar = '1';
    private int read;
    private AbstractParser parser = new Parser();


    public Scanner(InputStream source) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(source, StandardCharsets.UTF_8));
        nextBuffer();
    }


    public Scanner(File source) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(source), StandardCharsets.UTF_8));
        nextBuffer();
    }


    public Scanner(InputStream source, AbstractParser ap) throws IOException {
        this.parser = ap;
        this.reader = new BufferedReader(new InputStreamReader(source, StandardCharsets.UTF_8));
        nextBuffer();
    }


    public Scanner(File source, AbstractParser ap) throws IOException {
        this.parser = ap;
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(source), StandardCharsets.UTF_8));
        nextBuffer();
    }

    public Scanner(String source) throws IOException {
        this.reader = new BufferedReader(new StringReader(source));
        nextBuffer();
    }


    private void nextBuffer() throws IOException {
        read = reader.read(buffer);
        pointer = 0;
    }


    private int parseNextInt() {
        char lastChar = nextToken.charAt(nextToken.length() - 1);
        int nextInt;
        if (lastChar == 'o' || lastChar == 'O') {
            nextToken.deleteCharAt(nextToken.length() - 1);
            nextInt = Integer.parseUnsignedInt(nextToken.toString(), 8);
        } else {
            nextInt = Integer.parseInt(nextToken.toString());
        }
        nextToken.setLength(0); 
        return nextInt;
    }


    private String parseNextWord() {
        String nextWord = nextToken.toString();
        nextToken.setLength(0);
        return nextWord;
    }


    private boolean hasNextToken() throws IOException {
        if (!nextToken.isEmpty()) {
            return true;
        }
        while(read >= 0){
            for (; pointer < read; pointer++) {
                if (parser.delimiter(buffer[pointer])) {
                    if (!nextToken.isEmpty()) {
                        return true;
                    }
                    if (LINE_FEED.length() == 1) {
                        if (buffer[pointer] == LINE_FEED.charAt(0)) {
                            curLineNo++;
                        }
                    } else {
                        if (LINE_FEED.charAt(0) == lastChar && LINE_FEED.charAt(1) == buffer[pointer]) {
                            curLineNo++;
                        }
                    }
                } else {
                    nextToken.append(buffer[pointer]);
                }
                lastChar = buffer[pointer];
            }
            nextBuffer();
        }
        return !nextToken.isEmpty();
    }


    public boolean hasNextInt() throws IOException {
        return hasNextToken();
    }


    public boolean hasNextWord() throws IOException {
        return hasNextToken();
    }


    public int nextInt() throws IOException {
        if (!nextToken.isEmpty() || hasNextToken()) {
            return parseNextInt();
        } else {
            throw new NoSuchElementException();
        }
    }


    public String nextWord() throws IOException {
        if (!nextToken.isEmpty() || hasNextToken()) {
            return parseNextWord(); 
        } else {
            throw new NoSuchElementException();
        }
    }


    public int curLineNo() {
        return curLineNo;
    }


    public void close() throws IOException {
        reader.close();
    }
}