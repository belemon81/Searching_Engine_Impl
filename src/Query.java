package a1_2001040208;

import java.util.ArrayList;
import java.util.List;

public class Query {
    private List<Word> keywords;

    /**
     * Receives the raw search phrase from user, then extract only
     * keywords from it.
     */
    public Query(String searchPhrase) {
        keywords = new ArrayList<>();
        String[] rawText = searchPhrase.split(" ");
        for (String text : rawText) {
            Word word = Word.createWord(text);
            if (word != null) {
                if (word.isKeyword()) {
                    keywords.add(word);
                }
            }
        }
    }

    public List<Word> getKeywords() {
        return keywords;
    }

    /**
     * Returns a list of matches against the input document. Sort matches by position
     * where the keyword first appears in the document.
     */
    public List<Match> matchAgainst(Doc d) {
        List<Match> matches = new ArrayList<>();
        List<Word> title = d.getTitle();
        List<Word> body = d.getBody();
        for (Word key : keywords) {
            int freq = 0;
            int firstIndex = -1;
            for (int i = 0; i < title.size(); i++) {
                if (title.get(i).equals(key)) {
                    if (firstIndex == -1) firstIndex = i;
                    freq++;
                }
            }
            for (int i = 0; i < body.size(); i++) {
                if (body.get(i).equals(key)) {
                    if (firstIndex == -1) firstIndex = i + title.size();
                    freq++;
                }
            }
            if (freq != 0) matches.add(new Match(d, key, freq, firstIndex));
        }
        matches.sort(Match::compareTo);
        return matches;
    }
}
