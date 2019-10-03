import java.util.ArrayList;

public class IndexList {

    public String mot;
    public ArrayList<TextPosition> occurences;

    public IndexList(String mot) {
        this.mot = mot;
        occurences = new ArrayList<>();
    }

    public void addPos(TextPosition tp){
        occurences.add(tp);
    }

}