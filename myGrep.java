package myGrep;

import java.util.Scanner;
import java.io.IOException;
import java.lang.Exception;
import java.nio.file.Files;
import java.nio.file.Paths;

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

        String text = "";
        try {

            text = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = KMP.KMP(text, regEx.toCharArray(), KMP.retenue(regEx.toCharArray()));

        if (i == -1) {
            System.out.println("motif non trouvé");
        }

        else {
            System.out.println("motif trouvé a la place : " + i);
        }
    }
}