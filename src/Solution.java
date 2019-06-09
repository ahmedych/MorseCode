import java.io.*;
import java.util.*;

public class Solution {
    private static Map<String, String> alphabet = new HashMap<>();

    public static void main(String[] args) {
        File propFile = new File("C:\\Users\\A\\IdeaProjects\\MorseCode\\src\\resources\\Morse.properties");
        File source = new File("C:\\Users\\A\\IdeaProjects\\MorseCode\\src\\resources\\sample.txt");
        File output = new File("C:\\Users\\A\\IdeaProjects\\MorseCode\\src\\resources\\output.txt");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(propFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String letter : properties.stringPropertyNames()) {
            alphabet.put(letter, properties.getProperty(letter));
        }
        String[] words = null;
        try {
            String content = new Scanner(source).useDelimiter("\\Z").next();
            words = content.split("\\s");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writeToFile(output, convertToMorse(words));
        for (String s : convertMorseToEngFromFile(output)) {
            System.out.print(s);
        }

        System.out.println();
    }

    public static ArrayList<String> convertToMorse(String[] words) {
        ArrayList<String> listOfWordsInMorse = new ArrayList<>();
        for (String s : words) {
            char[] charArrayOfWord = s.toCharArray();
            for (int i = 0; i < charArrayOfWord.length; i++) {
                listOfWordsInMorse.add(alphabet.get(String.valueOf(charArrayOfWord[i]).toUpperCase()) + " ");
            }
        }
        return listOfWordsInMorse;
    }

    public static void writeToFile(File output, List<String> list) {
        try (FileWriter writer = new FileWriter(output)) {
            for (String s : list) {
                writer.write(s);
                writer.append(" ");
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<String> convertMorseToEngFromFile(File morseFile) {
        ArrayList<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(morseFile)) {
            String content = scanner.useDelimiter("\\Z").next();
            String[] morseWords = content.split("\\s\\s");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < morseWords.length; i++) {
                String[] oneMorseWord = morseWords[i].split("\\s");
                for (int j = 0; j < oneMorseWord.length; j++) {
                    sb.append(oneMorseWord[j]);
                }
                Set<Map.Entry<String, String>> entrySet = alphabet.entrySet();
                String soughtValue = new String(sb);
                for (Map.Entry<String, String> pair : entrySet) {
                    if (soughtValue.equals(pair.getValue()))
                        list.add(pair.getKey());
                }
                sb = new StringBuilder();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
