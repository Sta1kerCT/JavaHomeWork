import java.io.*;
import java.util.Map;
import java.util.LinkedHashMap;
    

public class Wspp {

    public static class Parser implements AbstractParser {
        public boolean delimiter(char i) {
            return !(Character.isLetter(i) || i == '\'' || Character.getType(i) == Character.DASH_PUNCTUATION);
        }
    }


    public static void main(String[] args) {
        AbstractParser parser = new Parser();
        try {
            int pointer = 1;
            Map<String, IntList> words = new LinkedHashMap<>();
            Scanner sc = new Scanner(new File(args[0]), parser);
            try {
                while (sc.hasNextWord()) {
                    String tmp = sc.nextWord().toLowerCase();
                    IntList arr = words.getOrDefault(tmp, new IntList());
                    arr.add(pointer);
                    words.put(tmp, arr);
                    pointer++;
                }
            } catch (FileNotFoundException e) {
                System.out.println("InputFile not found:" + e.getMessage());
            } finally {
                sc.close();
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF8"));
            try {
                for (String key : words.keySet()) {
                    writer.write(key);
                    IntList arr = words.get(key);
                    writer.write(" " + arr.size());
                    for (int i = 0; i < words.get(key).size(); i++) {
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