package a1_2001040208;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doc {
    private final List<Word> title;
    private final List<Word> body;

    /**
     * receives the raw text of a document and extracts the title and body parts from that.
     * Documents are provided as text files (.txt) in the "docs" directory under the project’s
     * root directory. Each text file contain two lines. The first line is the title and the
     * second line is the body.
     */
    public Doc(String content) {
        this.title = new ArrayList<>();
        this.body = new ArrayList<>();
        if (!content.isEmpty() && content != null) {
            Scanner sc = new Scanner(content);
            String title = sc.nextLine();
            String[] titleWords = title.split(" ");
            for (String word : titleWords) {
                this.title.add(Word.createWord(word));
            }
            String body = sc.nextLine();
            String[] bodyWords = body.split(" ");
            for (String word : bodyWords) {
                this.body.add(Word.createWord(word));
            }
        }
    }

    public List<Word> getTitle() {
        return title;
    }

    public List<Word> getBody() {
        return body;
    }

    /**
     * Two Doc objects are equal if their titles and bodies contain the same words in the
     * same order. To determine if two words are equal, use the equals() method
     * from the Word class.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doc)) return false;
        Doc doc = (Doc) o;
        List<Word> title = doc.getTitle();
        if (title.size() != getTitle().size()) return false;
        for (int i = 0; i < title.size(); i++) {
            Word word = title.get(i);
            if (!word.equals(getTitle().get(i))) return false;
        }

        List<Word> body = doc.getBody();
        if (body.size() != getBody().size()) return false;
        for (int i = 0; i < body.size(); i++) {
            Word word = body.get(i);
            if (!word.equals(getBody().get(i))) return false;
        }
        return true;
    }
}
