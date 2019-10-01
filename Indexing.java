
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
    public static class Coordonnee {
        public int ligne;
        public int colonne;

        public Coordonnee(int l, int c){
            ligne = l;
            colonne = c;
        }
    }

    public static class Index{
        ArrayList<Coordonnee> coordonnees;
        String mot;
        int frequence;

        public Index(String mot, int frequence){
            this.mot = mot;
            this.frequence=frequence;
            coordonnees = new ArrayList<Coordonnee>();
        }

        public Index(String mot, int ligne, int colonne){
            this.mot = mot;
            frequence=1;
            coordonnees = new ArrayList<Coordonnee>();
            coordonnees.add(new Coordonnee(ligne, colonne));
        }

        public Index(String mot, int ligne, int colonne, int frequence){
            this.mot = mot;
            frequence=1;
            coordonnees = new ArrayList<Coordonnee>();
            coordonnees.add(new Coordonnee(ligne, colonne));
            this.frequence=frequence;
        }

        public boolean equals(Object obj) {
            if(obj instanceof String)
                return (mot.equals((String)obj));
            return false;
        }
    }

    // public static void main(String[] argv) throws IOException
    // {
    //     BufferedReader facteurs = null;
    //     BufferedReader texte = null;

    //     try
    //     {
    //         facteurs = new BufferedReader(new FileReader(".index"));
    //         texte = new BufferedReader(new FileReader(argv[0]));
    //     }
    //     catch(FileNotFoundException exc)
    //     {
    //         System.out.println("Erreur d'ouverture");
    //     }


    //     /* Créaction de la table d'indexation avec uniquement les mots et leur fréquence.
    //      * Il manque les coordonnées des mots. Elles seront mises à jour juste après. */
    //     ArrayList<Index> tableDIndexation = new ArrayList<Index>();

    //     String ligne, facteur;
    //     String[] facteurEtFrequence;
    //     int frequence;

    //     // on crée la table d'indexation à partir des mots trouvés rangés par fréquence :
    //     while ((ligne = facteurs.readLine()) != null) {
    //         // On récupère les données du fichiers :  la fréquence et le facteur.
    //         facteurEtFrequence = ligne.split(" ");
    //         frequence = Integer.valueOf(facteurEtFrequence[0]);

    //         /* si la fréquence du mot n'est pas trop élevée, on place le mot
    //          * et sa fréquence dans la table d'indexation */
    //         if(frequence < 800 /* /!\ A REGLER !!!! */) {
    //             facteur = facteurEtFrequence[1];

    //             Index i = new Index(facteur, frequence);
    //             tableDIndexation.add(i);
    //         }
    //     }


    //     /* Il reste la mise à jour des coordonnées : */
    //     String[] mots;
    //     int numeroLigne =0;
    //     // while((ligne = texte.readLine()) != null) {
    //     //     // A chaque changement de ligne, on augmente le numéro de la ligne
    //     //     numeroLigne++;

    //     //     // On récupère la liste de mots de la ligne
    //     //     mots = ligne.split(" ");

    //     //     for(int i=0 ; i<mots.length ; i++) {
    //     //         // On récupère l'indice de la première occurence de facteur dans texte
    //     //         int indice = KMP.kmp(ligne, mots[i].toCharArray(), KMP.retenue(mots[i].toCharArray()));

    //     //         /* si le mot existe sur la ligne,
    //     //         /* indice est un nombre positif représentant le numéro du caractère où le mot commence. */
    //     //         if (indice > -1) {
    //     //             // si c'est un mot qui mérite d'être dans la table d'indexation (pas trop fréquent)
    //     //             if (tableDIndexation.contains(mots[i]))
    //     //                 tableDIndexation.get(tableDIndexation.indexOf(mots[i])).coordonnees.add(new Coordonnee(numeroLigne, indice));
    //     //         }
    //     //     }
    //     // }

    //     facteurs.close();
    //     texte.close();
    // }
}
