package myGrep;

import java.util.Scanner;

import java.io.IOException;
import java.lang.Exception;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

import java.util.ArrayList;

public class myGrep {

    // MAIN
    public static void main(String arg[]) {
        String regEx = "";
        String path = "";

        if (arg.length == 2) {
            regEx = arg[0];
            path = arg[1];

        } else {
            System.out.print(" Error ");

        }

        ArrayList<String> text = null;

        System.out.println("Chargement du texte");
        Instant now = Instant.now();
    
        try {
            text = new ArrayList<>(Files.readAllLines(Paths.get(path)));
            System.out.println(
                    "Chargement du texte a pris : " + Duration.between(now, Instant.now()).toMillis() + " ms");

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<TextPosition> pos = null;

        /*if (indexable(regEx)) {
            System.out.println("Methode Index");
            now = Instant.now();

            String indexPath = Indexing.createIndex(path, text);
            IndexTree itree = Indexing.loadIndexTree(indexPath);

            System.out.println(
                    "Chargement de l'index a pris : " + Duration.between(now, Instant.now()).toMillis() + " ms");

            now = Instant.now();

            pos = itree.getPositions(regEx);
            
            System.out.println(
                    "Recherche dans l'arbre a pris : " + Duration.between(now, Instant.now()).toNanos() + " µs\n");

        }

        if ((pos == null || pos.size() == 0) && kmpable(regEx)) {
            System.out.println("Methode KMP");

            now = Instant.now();
            pos = KMP.kmp(text, regEx.toCharArray());
            System.out.println(
                    "Recherche avec KMP a pris : " + Duration.between(now, Instant.now()).toMillis() + " ms\n");

        }*/
        if (pos == null || pos.size() == 0) {
            System.out.println("Methode Regex");

            try {
                Automate a =Automate.fromTree(new RegEx(regEx).parse());
                System.out.println(a);
                /*for(int i=0; i<a.nbStates(); i++) {
                    System.out.print(a.states[i]['S'] + " ");
                    System.out.print(a.states[i]['a'] + " ");
                    System.out.print(a.states[i]['g'] + " ");
                    System.out.print(a.states[i]['r'] + " ");
                    System.out.print(a.states[i]['o'] + " ");
                    System.out.print(a.states[i]['n'] + " ");
                    System.out.println();
                }*/
                a=a.determinate();
                //a.minimizate();
                System.out.println(a);
                /*for(int i=0; i<a.nbStates(); i++) {
                    System.out.print(a.states[i]['S'] + " ");
                    System.out.print(a.states[i]['a'] + " ");
                    System.out.print(a.states[i]['g'] + " ");
                    System.out.print(a.states[i]['r'] + " ");
                    System.out.print(a.states[i]['o'] + " ");
                    System.out.print(a.states[i]['n'] + " ");
                    System.out.println();
                }*/
                //System.out.println(a.determinate().minimizate());
            }
            catch(Exception e){}
        }

        if (pos == null || pos.size() == 0) {

            System.out.println("motif non trouvé");
        }

        else {
            System.out.println(pos.size() + " matchs trouvés : ");
            for (TextPosition p : pos) {
                System.out.println(text.get(p.ligne));
            }
        }
    }

    public static boolean indexable(String facteur) {

        for (char c : facteur.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    public static boolean kmpable(String facteur) {

        for (char c : facteur.toCharArray()) {
            if (c == '*' || c == '(' || c == ')' || c == '|') {
                return false;
            }
        }

        return true;

    }
}