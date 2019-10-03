
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// trier le moins fréquent par le plus fréquent.
// idée : black list (commune)
// - 2 char
// - fréquent caractère

// en black liste : tous les mots avec 2 car
// caractère fréquent : (a, etc) : articl indéfini.
// tou sles mots sup à une certaines fréquence : check par un humain.
public class Indexing {

    public static class Index {
        ArrayList<TextPosition> coordonnees;
        String mot;
        int frequence;

        public Index(String mot, int frequence) {
            this.mot = mot;
            this.frequence = frequence;
            coordonnees = new ArrayList<TextPosition>();
        }

        public Index(String mot, int ligne, int colonne) {
            this.mot = mot;
            frequence = 1;
            coordonnees = new ArrayList<TextPosition>();
            coordonnees.add(new TextPosition(ligne, colonne));
        }

        public Index(String mot, int ligne, int colonne, int frequence) {
            this.mot = mot;
            frequence = 1;
            coordonnees = new ArrayList<TextPosition>();
            coordonnees.add(new TextPosition(ligne, colonne));
            this.frequence = frequence;
        }

    }

    public static String createIndex(String path, ArrayList<String> text) {
        String indexPath = path.substring(0, path.lastIndexOf('.'));
        indexPath = path.substring(path.lastIndexOf('/') + 1, indexPath.length());
        String textPath = indexPath;

        textPath = "./index/" + textPath + ".txt";
        indexPath = "./index/" + indexPath + ".index";

        File f = new File(indexPath);
        if (!f.isFile()) {
            try {
                Runtime.getRuntime().exec("./script.bash " + path + " " + textPath).waitFor();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ArrayList<String> txt = null;
                txt = new ArrayList<>(Files.readAllLines(Paths.get(textPath)));
                ArrayList<String> index = new ArrayList<>();

                for (String line : txt) {
                    String[] columns = line.split("\\s+");

                    if (columns.length > 2 && Integer.parseInt(columns[1]) < 50) {

                        String s = columns[2];
                        ArrayList<TextPosition> occ = KMP.kmp(text, s.toCharArray());

                        for (TextPosition tp : occ) {
                            s += " (" + tp.ligne + "," + tp.pos + ") ";
                        }

                        index.add(s);
                    } else {
                        break;
                    }
                }

                Path file = Paths.get(indexPath);
                Files.write(file, index, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return indexPath;

    }

    public static ArrayList<IndexList> loadIndexList(String indexPath) {
        ArrayList<IndexList> indexList = new ArrayList<>();
        try {
            ArrayList<String> txt = new ArrayList<>(Files.readAllLines(Paths.get(indexPath)));

            for (String line : txt) {
                String[] columns = line.split("\\s+");
                IndexList index = new IndexList(columns[0]);

                for (int i = 1; i < columns.length; i++) {
                    String[] value = columns[i].split("[(,)]");
                    index.addPos(new TextPosition(Integer.parseInt(value[1]), Integer.parseInt(value[2])));
                }

                indexList.add(index);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexList;
    }

}
