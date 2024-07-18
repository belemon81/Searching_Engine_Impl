package a1_2001040208;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Word {
    private String prefix;
    private String text;
    private String suffix;
    public static Set<String> stopWords;

    public Word(String prefix, String text, String suffix) {
        this.prefix = prefix;
        this.text = text;
        this.suffix = suffix;
    }

    /**
     * Construct and return a complete Word object from raw text.
     */
    public static Word createWord(String rawText) {
        if (rawText == null) return null;
        if (rawText.isEmpty()) return new Word("", "", "");
        int i = 0;
        int endPrefix = -1;
        do {
            char c = rawText.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                endPrefix = i;
                break;
            }
            i++;
        } while (i < rawText.length());
        if (endPrefix == -1) return new Word("", rawText, "");
        String prefix = rawText.substring(0, endPrefix);
        for (int t = 0; t < prefix.length(); t++) {
            char c = prefix.charAt(t);
            if ((!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')) && (
                    (c >= '0' && c <= '9') ||
                            (c != '!' && c != '"' && c != '\'' && c != '(' && c != ')' && c != ',' && c != '.' && c != ':' && c != ';' && c!='>' && c!='<' && c != '«' && c != '»' && c != '?' && c != '{' && c != '}'))) {
                return new Word("", rawText, "");
            }
        }

        int j = rawText.length() - 1;
        int startSuffix = -1;
        do {
            char c = rawText.charAt(j);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                startSuffix = j + 1;
                break;
            }
            j--;
        } while (j >= 0);
        if (startSuffix == -1) return new Word("", rawText, "");
        String text = rawText.substring(endPrefix, startSuffix);
        String suffix = rawText.substring(startSuffix);
        if (!(text.endsWith("'t") && !text.endsWith("'T")) && text.contains("'")) {
            suffix = text.substring(text.indexOf("'")) + suffix;
            text = text.substring(0, text.indexOf("'"));
        }
        for (int t = 0; t < text.length() - 1; t++) {
            char c = text.charAt(t);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '\'' || c == '-')) {
                return new Word("", rawText, "");
            }
        }
        for (int t = 0; t < suffix.length(); t++) {
            char c = suffix.charAt(t);
            if ((!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')) && (
                    (c >= '0' && c <= '9') ||
                            (c != '!' && c != '"' && c != '\'' && c != '(' && c != ')' && c != ',' && c != '.' && c != ':' && c != ';' && c!='>' && c!='<' && c != '«' && c != '»' && c != '?' && c != '{' && c != '}'))) {
                return new Word("", rawText, "");
            }
        }

        return new Word(prefix, text, suffix);
    }

    /**
     * Returns true if the word is a keyword.
     */
    public boolean isKeyword() {
        if (getText() == null) return false;
        if (getText().isEmpty()) return false;
        for (char c : getText().toCharArray()) {
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '\'' || c == '-')) return false;
        }
        for (String word : stopWords) {
            if (getText().equalsIgnoreCase(word)) {
                return false;
            }
        }
        return true;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getText() {
        return text;
    }

    /**
     * Two words are considered equal if their text parts are equal, case-insensitively.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word = (Word) o;
        return getText().equalsIgnoreCase(word.getText());
    }

    /**
     * Returns the raw text of the word.
     */
    @Override
    public String toString() {
        return getPrefix() + getText() + getSuffix();
    }

    /**
     * Load stop words into the set Word.stopWords from the text file whose name is
     * specified by fileName (which resides under the project’s root directory). This
     * method returns true if the task is completed successfully and false otherwise.
     */
    public static boolean loadStopWords(String fileName) {
        stopWords = new HashSet<>();
        try {
            File f = new File(fileName);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String stopWord;
            while ((stopWord = br.readLine()) != null) {
                stopWords.add(stopWord);
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}


