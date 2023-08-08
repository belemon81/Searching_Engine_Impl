package a1_2001040208;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Engine {
    private Doc[] docs;

    public Engine() {
    }

    /**
     * Loads the documents from the folder specified by dirname (which resides under
     * the project’s root folder) and returns the number of documents loaded.
     */
    public int loadDocs(String dirname) {
        int count = 0;
        File folder = new File(dirname);
        File[] filesList = folder.listFiles();
        if (filesList == null) return count;
        docs = new Doc[filesList.length];

        for (int i = 0; i < filesList.length; i++) {
            StringBuilder sb = new StringBuilder();
            if (filesList[i].isFile() && filesList[i].getName().endsWith(".txt")) {
                try {
                    FileReader fr = new FileReader(filesList[i]);
                    BufferedReader br = new BufferedReader(fr);
                    sb.append(br.readLine()).append("\n").append(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!sb.toString().isEmpty()) {
                Doc doc = new Doc(sb.toString());
                docs[i] = doc;
                count++;
            }
        }
        return count;
    }

    /**
     * Returns an array of documents in the original order.
     */
    public Doc[] getDocs() {
        return docs;
    }

    /**
     * Performs the search function of the engine. Returns a list of sorted search
     * results.
     */
    public List<Result> search(Query q) {
        List<Result> list = new ArrayList<>();
        for (Doc doc : getDocs()) {
            List<Match> matches = q.matchAgainst(doc);
            if (matches.size() > 0) {
                Result result = new Result(doc, matches);
                list.add(result);
            }
        }
        list.sort(Comparator.reverseOrder());
        return list;
    }

    /**
     * Converts a list of search results into HTML format. The output of this method is
     * the output of Result.htmlHighlight() combined (without any delimiter).
     * Refer to the 3rd line of the file testCases.html for a specific example.
     */
    public String htmlResult(List<Result> results) {
        StringBuilder sb = new StringBuilder();
        for (Result result : results) {
            String html = result.htmlHighlight();
            html = html.replaceAll("\n","");
            sb.append(html);
        }
        return sb.toString();
    }
}
