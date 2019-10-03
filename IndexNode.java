import java.util.ArrayList;

public class IndexNode {

    public String mot;
    public ArrayList<TextPosition> occurences;

    public IndexNode(String mot) {
        this.mot = mot;
        occurences = new ArrayList<>();
    }

    public void addPos(TextPosition tp){
        occurences.add(tp);
    }

}