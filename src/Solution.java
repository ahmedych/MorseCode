import java.io.*;
import java.util.*;

public class Solution {
    private static Map<String, String> alphabet = new HashMap<>();

    public static void main(String[] args) {
        File propFile = new File("src\\resources\\Morse.properties");
        File source = new File("src\\resources\\sample.txt");
        File outputMorse = new File("src\\resources\\output.txt");
        File outputText = new File("src\\resources\\toText.txt");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(propFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String letter : properties.stringPropertyNames()) {
            alphabet.put(letter, properties.getProperty(letter));
        }
        String content = null;
        try {
            content = new Scanner(source).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writeToFile(outputMorse, convertToMorse(content));
        List<String> textAgain = convertMorseToEngFromFile(outputMorse);
        for (String s : textAgain) {
            System.out.print(s);
        }
        writeToFile(outputText, textAgain);
    }

    private static ArrayList<String> convertToMorse(String words) {
        ArrayList<String> listOfWordsInMorse = new ArrayList<>();
        char[] charArrayOfWord = words.toCharArray();
        for (int i = 0; i < charArrayOfWord.length; i++) {
            listOfWordsInMorse.add(alphabet.get(String.valueOf(charArrayOfWord[i]).toUpperCase()) + " ");
        }
        return listOfWordsInMorse;
    }

    private static void writeToFile(File output, List<String> list) {
        try (FileWriter writer = new FileWriter(output)) {
            for (String s : list) {
                writer.write(s);
                writer.append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> convertMorseToEngFromFile(File morseFile) {
        ArrayList<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(morseFile)) {
            String wholeMorseTextFromFile = scanner.useDelimiter("\\Z").next();
            String[] morseWords = wholeMorseTextFromFile.split("\\s\\s");
            StringBuilder morseLetter = new StringBuilder();
            for (int i = 0; i < morseWords.length; i++) {
                String[] morseLetters = morseWords[i].split("\\s");
                for (int j = 0; j < morseLetters.length; j++) {
                    morseLetter.append(morseLetters[j]);
                }
                Set<Map.Entry<String, String>> entrySet = alphabet.entrySet();
                String soughtValue = new String(morseLetter);
                for (Map.Entry<String, String> pair : entrySet) {
                    if (soughtValue.equals(pair.getValue()))
                        list.add(pair.getKey());
                }
                morseLetter = new StringBuilder();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
