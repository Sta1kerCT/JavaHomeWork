import java.io.*;
import java.util.Map;
import java.util.LinkedHashMap;
	

public class WordStatInput {

	public static class Parser implements AbstractParser {
        public boolean delimiter(char i) {
            return !(Character.isLetter(i) || i == '\'' || Character.getType(i) == Character.DASH_PUNCTUATION);
        }
    }


	public static void main(String[] args) {
        AbstractParser parser = new Parser();
		try {
			LinkedHashMap<String, Integer> words = new LinkedHashMap<String, Integer>();
			Scanner sc = new Scanner(new File(args[0]), parser);
			try {
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF8"));
				try {
					while (sc.hasNextWord()) {
						String tmp = sc.nextWord().toLowerCase();
						if (words.containsKey(tmp)) {
							int value = words.get(tmp); 
							words.replace(tmp, ++value);
						} else {
							words.put(tmp, 1);
						}
					}
					for (Map.Entry entry : words.entrySet()){
						writer.write(entry.getKey() + " " + entry.getValue());
						writer.newLine();
					} 
				} catch (FileNotFoundException e) {
					System.out.println("OutputFileNotFoundException error:" + e.getMessage());
				} finally {
					writer.close();
				}
			} finally {
				sc.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("InputFileNotFoundException error:" + e.getMessage());
		} catch (IOException e){
			System.out.println("I/O error:" + e.getMessage());
		}
	}
}