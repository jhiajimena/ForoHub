package lescano.forohub.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }

    private static final Set<String> ACRONYMS = new HashSet<>(Arrays.asList("CSS",
            "HTML","JS","API","URL","SQL","AI"));
    public static String normalizeName(String name) {
        if (name == null) {
            return "";
        }
        String[] words = name.trim().split("\\s+");  // Separa las palabras por espacio
        StringBuilder normalized = new StringBuilder();

        for (String word : words) {
           word = word.trim();
           if(word.isEmpty()) continue;

           if(ACRONYMS.contains(word.toUpperCase())){
               normalized.append(word.toUpperCase()).append(" ");
           }else {
               normalized.append(capitalizeFirstLetter(word)).append(" ");
           }
        }
        return normalized.toString().trim();
    }
    private static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
