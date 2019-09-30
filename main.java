import java.util.Scanner;

import java.lang.Exception;

public class main {

    // MAIN
    public static void main(String arg[]) {
        String regEx;
        System.out.println("Welcome to Bogota, Mr. Thomas Anderson.");
        if (arg.length != 0) {
            regEx = arg[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("  >> Please enter a regEx: ");
            regEx = scanner.next();
            scanner.close();
        }
        System.out.println("  >> Parsing regEx \"" + regEx + "\".");
        System.out.println("  >> ...");

        if (regEx.length() < 1) {
            System.err.println("  >> ERROR: empty regEx.");
        } else {
            System.out.print("  >> ASCII codes: [" + (int) regEx.charAt(0));
            for (int i = 1; i < regEx.length(); i++)
                System.out.print("," + (int) regEx.charAt(i));
            System.out.println("].");
            try {
                RegExTree ret = new RegEx(regEx).parse();
                System.out.println("  >> Tree result: " + ret.toString() + ".");

                System.out.println("  >> automaton result etape 2");

                Automate e2 = new Automate(0);
                e2 = e2.fromTree(ret);

                System.out.println(e2.toString());
            } catch (Exception e) {
                System.err.println("  >> ERROR: syntax error for regEx \"" + regEx + "\".");
                e.printStackTrace();

            }
        }

        System.out.println("  >> ...");
        System.out.println("  >> Parsing completed.");
        System.out.println("Goodbye Mr. Anderson.");
    }

}