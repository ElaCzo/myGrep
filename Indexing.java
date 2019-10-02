
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import sun.reflect.misc.FieldUtil;

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
            coordonnees.add(new Coordonnee(ligne, colonne));
        }

        public Index(String mot, int ligne, int colonne, int frequence) {
            this.mot = mot;
            frequence = 1;
            coordonnees = new ArrayList<Coordonnee>();
            coordonnees.add(new Coordonnee(ligne, colonne));
            this.frequence = frequence;
        }

        public static String createIndex(String path) {
            String indexPath = path.substring(0, path.lastIndexOf('.'));
            indexPath = path.substring(path.lastIndexOf('/', path.length()));

            indexPath = String("./index/" + indexPath + ".index");

            File f = new File(indexPath);
            if (!f.isFile()) {
                Runtime.getRuntime().exec("./script.bash " + path + " " + indexPath);
            }

            return indexPath;

        }

    }

}
