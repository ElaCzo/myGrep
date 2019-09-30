import java.util.ArrayList;

public class Automate {
    int[][] states;
    boolean[] debut;
    boolean[] fin;
    ArrayList<Integer>[] epsilon;

    public Automate(int n) {
        this.states = new int[n][256];
        this.debut = new boolean[n];
        this.fin = new boolean[n];
        this.epsilon = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            epsilon[i] = new ArrayList<>();
            fin[i] = false;
            debut[i] = false;
            for (int j = 0; j < 256; j++) {
                this.states[i][j] = -1;
            }
        }
    }

    public Automate(char a) {
        this(2);
        debut[0] = true;
        fin[1] = true;
        states[0][(int) a] = 1;
    }

    public Automate concat(Automate A1, Automate A2) {
        Automate sortie = new Automate(A1.nbStates() + A2.nbStates());

        for (int i = 0; i < A1.nbStates(); i++) {
            for (int j = 0; j < 256; j++) {
                if (A1.states[i][j] != -1) {
                    sortie.states[i][j] = A1.states[i][j];
                }
            }
            sortie.debut[i] = A1.debut[i];
            sortie.epsilon[i].addAll(A1.epsilon[i]);

            if (A1.fin[i] == true) {
                for (int k = 0; k < A2.nbStates(); k++) {
                    if (A2.debut[k] == true) {
                        sortie.epsilon[i].add(k + A1.nbStates());
                    }
                }
            }
        }

        for (int i = 0; i < A2.nbStates(); i++) {
            for (int j = 0; j < 256; j++) {
                if (A2.states[i][j] != -1) {

                    sortie.states[i + A1.nbStates()][j] = A2.states[i][j] + A1.nbStates();
                }
            }
            sortie.fin[i + A1.nbStates()] = A2.fin[i];
            sortie.epsilon[i + A1.nbStates()].addAll(A2.epsilon[i]);
        }

        return sortie;
    }

    public Automate union(Automate A1, Automate A2) {
        Automate sortie = new Automate(A1.nbStates() + A2.nbStates() + 2);

        for (int i = 0; i < A1.nbStates(); i++) {
            for (int j = 0; j < 256; j++) {
                sortie.states[i][j] = A1.states[i][j];
            }
            sortie.epsilon[i].addAll(A1.epsilon[i]);

        }

        for (int i = 0; i < A2.nbStates(); i++) {
            for (int j = 0; j < 256; j++) {
                if (A2.states[i][j] != -1) {

                    sortie.states[i + A1.nbStates()][j] = A2.states[i][j] + A1.nbStates();
                }
            }
            sortie.epsilon[i + A1.nbStates()].addAll(A2.epsilon[i]);
        }

        sortie.debut[sortie.nbStates() - 2] = true;
        sortie.fin[sortie.nbStates() - 1] = true;

        for (int i = 0; i < A1.nbStates(); i++) {

            if (A1.debut[i] == true) {
                sortie.epsilon[sortie.nbStates() - 2].add(i);
            }

            if (A1.fin[i] == true) {
                sortie.epsilon[i].add(sortie.nbStates() - 1);
            }

        }

        for (int i = 0; i < A2.nbStates(); i++) {

            if (A1.debut[i] == true) {
                sortie.epsilon[sortie.nbStates() - 2].add(i + A1.nbStates());
            }

            if (A1.fin[i] == true) {
                sortie.epsilon[i + A1.nbStates()].add(sortie.nbStates() - 1);
            }
        }

        return sortie;
    }

    public Automate closure(Automate a) {
        Automate sortie = new Automate(a.nbStates() + 2);

        for (int i = 0; i < a.nbStates(); i++) {
            for (int j = 0; j < 256; j++) {
                sortie.states[i][j] = a.states[i][j];
            }
            sortie.epsilon[i].addAll(a.epsilon[i]);
        }

        sortie.debut[sortie.nbStates() - 2] = true;
        sortie.fin[sortie.nbStates() - 1] = true;
        sortie.epsilon[sortie.nbStates() - 2].add(sortie.nbStates() - 1);

        for (int i = 0; i < a.nbStates(); i++) {

            if (a.debut[i] == true) {
                sortie.epsilon[sortie.nbStates() - 2].add(i);
            }

            if (a.fin[i] == true) {
                sortie.epsilon[i].add(sortie.nbStates() - 1);
                for (int j = 0; j < a.nbStates(); j++) {
                    if (a.debut[j] == true) {
                        sortie.epsilon[j].add(i);
                    }
                }
            }

        }

        return sortie;
    }

    public Automate fromTree(RegExTree t) {

        if (t.root == RegEx.CONCAT)
            return concat(fromTree(t.subTrees.get(0)), fromTree(t.subTrees.get(1)));
        if (t.root == RegEx.ETOILE)
            return closure(fromTree(t.subTrees.get(0)));
        if (t.root == RegEx.ALTERN)
            return union(fromTree(t.subTrees.get(0)), fromTree(t.subTrees.get(1)));
        if (t.root == RegEx.DOT)
            return concat(fromTree(t.subTrees.get(0)), fromTree(t.subTrees.get(1)));
        else {
            return new Automate((char) t.root);
        }

    }

    public static Automate determinate(Automate a) {
        ArrayList<Integer>[] etats = new ArrayList[100];
        
        return null;

    }

    public int nbStates() {
        return epsilon.length;
    }

    public String toString() {
        String s = "";

        for (int i = 0; i < this.nbStates(); i++) {
            s += i + " | ";
            for (int j = 0; j < 256; j++) {
                if (this.states[i][j] != -1) {
                    s += " [ " + (char) j + " : " + this.states[i][j] + " ] ";
                }
            }
            s += "| epsilons :" + epsilon[i].toString();
            if (this.debut[i] == true) {
                s += "| deb";
            }

            if (this.fin[i] == true) {
                s += "| fin";
            }

            s += "\n";
        }

        return s;
    }

}