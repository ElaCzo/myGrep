package myGrep;

import java.util.*;

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

    private Automate(Automate a, int n) {
        this.states = new int[n][256];
        this.debut = new boolean[n];
        this.fin = new boolean[n];
        this.epsilon = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            epsilon[i] = a.epsilon[i];
            fin[i] = a.fin[i];
            debut[i] = a.debut[i];
            for (int j = 0; j < 256; j++) {
                this.states[i][j] = a.states[i][j];
            }
        }
    }

    public static Automate concat(Automate A1, Automate A2) {
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

    public static Automate union(Automate A1, Automate A2) {
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

    public static Automate closure(Automate a) {
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

    public static Automate fromTree(RegExTree t) {

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

    private class SetOfStates extends ArrayList<Integer>{
        @Override
        public boolean equals(Object obj) {
            SetOfStates los;
            if(obj instanceof SetOfStates)
                los = (SetOfStates)((SetOfStates)(obj)).clone();
            else
                return false;

            Set<Integer> clone=new HashSet<>(this);
            return los.stream().allMatch(p->clone.remove(p.intValue())
                    && clone.isEmpty());
        }
    }

    private SetOfStates statesReachingLetter(int state, int letter){
        SetOfStates result = new SetOfStates();

        boolean marked[] = new boolean[nbStates()];

        for(int i=0; i<nbStates(); i++){
            marked[i]=false;
        }

        if(states[state][letter]!=-1)
            result.add(states[state][letter]);

        ArrayList<Integer> stack = new ArrayList<>();

        // Màj pile et marquage
        stack.addAll(epsilon[state]);
        for(int e : epsilon[state])
            marked[e]=true;

        int stateFromEpsilon=-1;

        // Tant qu'il reste des états à parcourir (venant de transitions epsilon)
        while(!stack.isEmpty()) {
            stateFromEpsilon = stack.remove(stack.size() - 1);
            assert (stateFromEpsilon != -1);

            // On ajoute l'état s'il exitste une transition pour la lettre
            if (states[state][letter] != -1)
                result.add(states[state][letter]);

            // On ajoute les états epsilon suivants si non marqués et marquage
            for(int e : epsilon[stateFromEpsilon]) {
                if(!marked[e])
                    stack.add(e);
                marked[e] = true;
            }
        }

        return result;
    }

    /* Il s'agit d'une déterminisation dans un cas précis : un état pour avoir plusieurs epsilon transitions
    et celles-ci peuvent mener à une transition possédant la même lettre. */
    public Automate determinate() {
        Automate result = new Automate(nbStates());

        int nbStatesResult = 0;

        HashMap<SetOfStates, Integer> statesNDAToDA = new HashMap<>();
        int[] statesDAToNDA = new int[nbStates()];
        for(int i=0; i<statesDAToNDA.length; i++) statesDAToNDA[i]=-1;

        // pour chaque état
        for (int i = 0; i < nbStates(); i++) {
            for(int l = 0; l < 256; l++) {
                SetOfStates statesNDA=statesReachingLetter(i, l);

                // vérifier si ça marche bien le contains.
                if(!statesNDAToDA.containsKey(statesNDA))
                    statesNDAToDA.put(statesNDA, nbStatesResult++); // on associe un nouvel état

                result.states[statesNDAToDA.get(statesNDA)][l]= statesNDAToDA.get(statesNDA);

                // On vérifie si c'est un état initial ou final
                for(Integer s : statesNDA){
                    if (debut[s])
                        result.debut[statesNDAToDA.get(statesNDA)]=true;
                    if (fin[s])
                        result.fin[statesNDAToDA.get(statesNDA)]=true;
                }
            }
        }

        Automate resultRightNumberOfStates = new Automate(result, nbStatesResult);

        return resultRightNumberOfStates;
    }

    private class Couple {
        SetOfStates set;
        int letter;

        public Couple(SetOfStates set, int letter) {
            this.set = set;
            this.letter = letter;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Couple))
                return false;
            return ((Couple) obj).set.equals(set) && ((Couple) obj).letter == letter;
        }
    }

    public Automate minimizate() {
        ArrayList<SetOfStates> partition = new ArrayList<>();

        // on crée et remplit 2 partition au hasard
        partition.set(0, new SetOfStates());
        partition.set(1, new SetOfStates());
        for (int i = 0; i < nbStates(); i++)
            if (fin[i])
                partition.get(0).add(i);
            else
                partition.get(1).add(i);


        ArrayList<Couple> W = new ArrayList<>();

        // On veut l'ensemble le plus petit
        SetOfStates smallerSet;
        if (partition.get(0).size() < partition.get(1).size())
            smallerSet = partition.get(0);
        else
            smallerSet = partition.get(1);

        // On remplit W
        for (int l = 0; l < 256; l++) {
            W.add(new Couple(smallerSet, l));
        }

        SetOfStates x1 = new SetOfStates();
        SetOfStates x2 = new SetOfStates();
        while (!W.isEmpty()) {
            Couple Za = W.remove(W.size() - 1);

            for (SetOfStates x : partition) {

                x1 = new SetOfStates();
                x2 = new SetOfStates();
                for (int s : x) {
                    if (Za.set.contains(states[s][Za.letter]))
                        x1.add(s);
                    else
                        x2.add(s);
                }

                // On met à jour la partition
                if (!x1.isEmpty() && !x2.isEmpty()) {
                    partition.remove(x);
                    partition.add(x1);
                    partition.add(x2);
                }

                // On met à jour W :
                Couple c;
                smallerSet = partition.get(0);
                for (SetOfStates set : partition)
                    if (set.size() < smallerSet.size())
                        smallerSet = set;

                for (int m = 0; m < 256; m++) {
                    c = new Couple(x, m);

                    if (W.contains(c)) {
                        W.remove(c);
                        W.add(new Couple(x1, m));
                        W.add(new Couple(x2, m));
                    } else {
                        W.add(new Couple(smallerSet, m));
                    }
                }
            }
        }

        Automate result = new Automate(partition.size());
        int iOfResult=-1;
        for(int i=0; i<nbStates(); i++){
            for (SetOfStates s : partition)
                if (s.contains(i)) {
                    iOfResult = partition.indexOf(s);
                    break;
                }
            if(debut[i])
                result.debut[iOfResult]=true;
            for(int l=0; l<256; l++){
                if (states[i][l] != -1) {
                    for (SetOfStates s : partition) {
                        if (s.contains(result.states[i][l])) {
                            result.states[iOfResult][l] = partition.indexOf(s);
                            break;
                        }
                    }
                }
            }
            if(fin[i])
                result.fin[iOfResult]=true;
        }

        return result;
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