import java.io.*;

import java.util.Map;
import java.util.TreeMap;
    

public class WordStatWordsPrefix {
    public static void main(String[] args) {
        try {
            Map<String, Integer> words = new TreeMap<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF8"));
            try {
                char[] buffer = new char[1024];
                int read = reader.read(buffer);
                StringBuilder subStr = new StringBuilder();
                while (read >= 0) { 
                    for (int i = 0; i < read; i++) {
                        if (Character.isLetter(buffer[i]) ||
                         buffer[i] == '\'' ||
                          Character.getType(buffer[i]) == Character.DASH_PUNCTUATION) {
                            if (subStr.length() < 3){
                                subStr.append(buffer[i]);
                            }
                            continue;
                        }
                        if (!subStr.isEmpty()) {
                            String word = subStr.toString().toLowerCase();
                            words.put(word, words.getOrDefault(word, 0) + 1);
                            subStr = new StringBuilder();
                        }
                    }
                    read = reader.read(buffer);
                }
            } catch (FileNotFoundException e) {
                System.out.println("InputFile not found:" + e.getMessage());
            } finally {
                reader.close();
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF8"));
            try {
                for (Map.Entry entry : words.entrySet()){
                    writer.write(entry.getKey() + " " + entry.getValue());
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