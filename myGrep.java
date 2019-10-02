import java.util.Scanner;

import java.io.IOException;
import java.lang.Exception;
import java.nio.file.Files;
import java.nio.file.Paths;
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

        try {
            text = new ArrayList<>(Files.readAllLines(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<TextPosition> pos = null;

        if (indexable(regEx)) {

        }
        if (pos == null && kmpable(regEx)) {
            pos = KMP.kmp(text, regEx.toCharArray());
        } else {

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