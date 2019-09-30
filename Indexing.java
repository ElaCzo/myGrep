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

        public boolean equals(Object obj) {
            if(obj instanceof String)
            return (mot.equals((String)obj));
            return false;
        }
    }

    public static void main(String[] argv) throws IOException
    {
        BufferedReader facteurs = null;
        BufferedReader texte = null;
        String ligne, mot, facteur_, facteur;
        String[] mots;
        int numeroLigne =0;

        try
        {
            facteurs = new BufferedReader(new FileReader(".index"));
             texte = new BufferedReader(new FileReader(argv[0]));
        }
        catch(FileNotFoundException exc)
        {
            System.out.println("Erreur d'ouverture");
        }

        ArrayList<Index> indexs = new ArrayList<Index>();
        while ((facteur_ = facteurs.readLine()) != null) {
            while((ligne = texte.readLine()) != null) {
                numeroLigne++;
                facteur=facteur_.split(" ")[1];
                mots = ligne.split(" ");
                for(int numeroColonne=0; numeroColonne<mots.length; numeroColonne++) {
                    if(!indexs.contains(facteur)) {
                        //indexs.add(new Index(facteur, numeroLigne, numeroColonne));
                        //mots[numeroColonne];
                    }
                }
            }
        }

        facteurs.close();
        texte.close();
    }
}
