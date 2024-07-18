package a1_2001040208;

import java.util.List;

public class Result implements Comparable<Result> {
    private Doc d;
    private List<Match> matches;

    /**
     * Initialize a Result object with the related document and the list
     * of matches.
     */
    public Result(Doc d, List<Match> matches) {
        this.d = d;
        this.matches = matches;
    }

    public Doc getDoc() {
        return d;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public int getTotalFrequency() {
        int totalFrequency = 0;
        for (Match match : getMatches()) {
            totalFrequency += match.getFreq();
        }
        return totalFrequency;
    }

    public double getAverageFirstIndex() {
        double totalFirstIndex = 0.0;
        for (Match match : getMatches()) {
            totalFirstIndex += match.getFirstIndex();
        }
        return totalFirstIndex / getMatches().size();
    }

    /**
     * Highlight the matched words in the document using HTML markups. For a
     * matched word in the document’s title, put the tag <u> and </u> around the
     * word’s text part (the <u> tag should not affect the word’s prefix and suffix). For a
     * matched word in the document’s body, surround the word’s text part with the tag
     * <b> and </b>.
     */
    public String htmlHighlight() {
        List<Word> title = d.getTitle();
        List<Word> body = d.getBody();
        StringBuilder sb = new StringBuilder();
        sb.append("<h3>");
        for (int i = 0; i < title.size(); i++) {
            boolean isMatch = false;
            for (Match match : matches) {
                Word word = match.getWord();
                if (word.equals(title.get(i))) {
                    String hightlightPrefix = title.get(i).getPrefix() + "<u>";
                    String hightlightSuffix = "</u>" + title.get(i).getSuffix();
                    Word newWord = new Word(hightlightPrefix, title.get(i).getText(), hightlightSuffix);
                    if (i == 0) {
                        sb.append(newWord.toString());
                    } else {
                        sb.append(" ").append(newWord.toString());
                    }
                    isMatch = true;
                }
            }
            if (!isMatch) {
                if (i == 0) {
                    sb.append(title.get(i).toString());
                } else {
                    sb.append(" ").append(title.get(i).toString());
                }
            }
        }
        sb.append("</h3>");
        sb.append("<p>");
        for (int i = 0; i < body.size(); i++) {
            boolean isMatch = false;
            for (Match match : matches) {
                Word word = match.getWord();
                if (word.equals(body.get(i))) {
                    String hightlightPrefix = body.get(i).getPrefix() + "<b>";
                    String hightlightSuffix = "</b>" + body.get(i).getSuffix();
                    Word newWord = new Word(hightlightPrefix, body.get(i).getText(), hightlightSuffix);
                    if (i == 0) {
                        sb.append(newWord.toString());
                    } else {
                        sb.append(" ").append(newWord.toString());
                    }
                    isMatch = true;
                }
            }
            if (!isMatch) {
                if (i == 0) {
                    sb.append(body.get(i).toString());
                } else {
                    sb.append(" ").append(body.get(i).toString());
                }
            }
        }
        sb.append("</p>\n");
        return sb.toString();
    }

    /**
     * These are criteria to determine if Result A is greater than Result B
     * (in descending order of priority):
     * 1. A has greater match count than B
     * 2. A has greater total frequency than B
     * 3. A has lower average first index than B
     */
    @Override
    public int compareTo(Result o) {
        if (getMatches().size() > o.getMatches().size()) {
            return 1;
        } else if (getMatches().size() < o.getMatches().size()) {
            return -1;
        } else {
            if (getTotalFrequency() > o.getTotalFrequency()) {
                return 1;
            } else if (getTotalFrequency() < o.getTotalFrequency()) {
                return -1;
            } else {
                return Double.compare(o.getAverageFirstIndex(), getAverageFirstIndex());
            }
        }
    }
}
