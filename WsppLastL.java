import java.io.*;
import java.util.Map;
import java.util.LinkedHashMap;
    

public class WsppLastL {

    public static class Parser implements AbstractParser {
        public boolean delimiter(char i) {
            return !(Character.isLetter(i) || i == '\'' || Character.getType(i) == Character.DASH_PUNCTUATION);
        }
    }


    public static void main(String[] args) {
        try {
            AbstractParser parser = new Parser();
            int pointer = 1;
            Map<String, IntList> words = new LinkedHashMap<>();
            Scanner sc = new Scanner(new File(args[0]), parser);
            int lineNo = 0;
            try {
                while (sc.hasNextWord()) {
                    if (!(lineNo == sc.curLineNo())) {
                        pointer = 1;
                        lineNo = sc.curLineNo();
                    } 
                    String tmp = sc.nextWord().toLowerCase();
                    IntList arr = words.getOrDefault(tmp, new IntList());  
                    if (arr.size() == 0) {
                        arr.add(0);
                        arr.add(pointer);
                        arr.add(lineNo);
                    } else if (arr.get(arr.size() - 1) == lineNo) {
                        arr.set(arr.size() - 2, pointer);
                    } else {
                        arr.set(arr.size() - 1, pointer);
                        arr.add(lineNo);
                    }
                    arr.set(0, arr.get(0) + 1);
                    words.put(tmp, arr);
                    pointer++;
                    //first_element == word_frequency;
                    //last_element == current_line_number;                       
                }
            } catch (FileNotFoundException e) {
                System.out.println("InputFile not found:" + e.getMessage());
            } finally {
                sc.close();
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF8"));
            try {
                for (Map.Entry<String, IntList> entry : words.entrySet()){
                    String key = entry.getKey();
                    IntList arr = entry.getValue();
                    writer.write(key + " " + arr.get(0));
                    for (int i = 1; i < arr.size() - 1; i++) {
                        writer.write(" " + arr.get(i));
                    }
                    writer.newLine();
                }
            } catch (FileNotFoundException e) {
                System.out.println("OutputFile not found:" + e.getMessage());
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}