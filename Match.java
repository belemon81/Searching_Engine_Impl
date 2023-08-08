package a1_2001040208;

public class Match implements Comparable<Match> {
    private Doc d;
    private Word w;
    private int freq;
    private int firstIndex;

    public Match(Doc d, Word w, int freq, int firstIndex) {
        this.d = d;
        this.w = w;
        this.freq = freq;
        this.firstIndex = firstIndex;
    }

    public int getFreq() {
        return freq;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public Word getWord() {
        return w;
    }

    /**
     * Compare this with another Match object by the first index. This method obeys
     * the standard behavior specified by Java. Match object A is greater than Match
     * object B if the first index of A is greater than the first index of B.
     */
    @Override
    public int compareTo(Match o) {
        return Integer.compare(getFirstIndex(), o.getFirstIndex());
    }
}
