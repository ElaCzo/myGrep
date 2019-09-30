package myGrep;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// trier le moins fréquent par le plus fréquent.
// idée : black list (commune)
// - 2 char
// - fréquent caractère

// en black liste : tous les mots avec 2 car
// caractère fréquent : (a, etc) : articl indéfini.
// tou sles mots sup à une certaines fréquence : check par un humain.
public class Indexing {
    public class Coordonnee {
        public int ligne;
        public int colonne;

        public Coordonnee(int l, int c){
            ligne = l;
            colonne = c;
        }
    }

    public class Index{
        ArrayList<Coordonnee> coordonnees;
        String mot;
        int frequence;

        public Index(String mot, int ligne, int colonne){
            this.mot = mot;
            frequence=1;
            coordonnees = new ArrayList<Coordonnee>();
            coordonnees.add(new Coordonnee(ligne, colonne));
        }
    }

    public static void main(String[] argv) throws IOException
    {
        BufferedReader lecteurAvecBuffer = null;
        String ligne;

        try
        {
            lecteurAvecBuffer = new BufferedReader(new FileReader(argv[0]));
        }
        catch(FileNotFoundException exc)
        {
            System.out.println("Erreur d'ouverture");
        }
        while ((ligne = lecteurAvecBuffer.readLine()) != null) {
            System.out.println(ligne);
        }


        lecteurAvecBuffer.close();
    }
}
