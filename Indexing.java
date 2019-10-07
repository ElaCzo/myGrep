
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// trier le moins fréquent par le plus fréquent.
// idée : black list (commune)
// - 2 char
// - fréquent caractère

// en black liste : tous les mots avec 2 car
// caractère fréquent : (a, etc) : articl indéfini.
// tou sles mots sup à une certaines fréquence : check par un humain.
public class Indexing {

    public static String createIndex(String path, ArrayList<String> text) {
        String indexPath = path.substring(0, path.lastIndexOf('.'));
        indexPath = path.substring(path.lastIndexOf('/') + 1, indexPath.length());
        String textPath = indexPath;

        textPath = "./index/" + textPath + ".txt";
        indexPath = "./index/" + indexPath + ".index";

        File f = new File(indexPath);
        if (!f.isFile()) {
            try {
                Runtime.getRuntime().exec("./script.bash " + path + " " + textPath).waitFor();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ArrayList<String> txt = null;
                txt = new ArrayList<>(Files.readAllLines(Paths.get(textPath)));
                ArrayList<String> index = new ArrayList<>();

                for (String line : txt) {
                    String[] columns = line.split("\\s+");

                    if (columns.length > 2 && Integer.parseInt(columns[1]) < 100) {

                        String s = columns[2];
                        ArrayList<TextPosition> occ = KMP.kmp(text, s.toCharArray());

                        for (TextPosition tp : occ) {
                            s += " (" + tp.ligne + "," + tp.pos + ") ";
                        }

                        index.add(s);
                    } else {
                        break;
                    }
                }

                Path file = Paths.get(indexPath);
                Files.write(file, index, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return indexPath;

    }

    public static ArrayList<IndexNode> loadIndexList(String indexPath) {
        ArrayList<IndexNode> indexList = new ArrayList<>();
        try {
            ArrayList<String> txt = new ArrayList<>(Files.readAllLines(Paths.get(indexPath)));

            for (String line : txt) {
                String[] columns = line.split("\\s+");
                IndexNode index = new IndexNode(columns[0]);

                for (int i = 1; i < columns.length; i++) {
                    String[] value = columns[i].split("[(,)]");
                    index.addPos(new TextPosition(Integer.parseInt(value[1]), Integer.parseInt(value[2])));
                }

                indexList.add(index);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexList;
    }

    public static IndexTree loadIndexTree(String indexPath) {
        IndexTree indexTree = new IndexTree();

        try {
            Stream<String> stream = Files.lines(Paths.get(indexPath));

            for (String line : stream.collect(Collectors.toList())) {
                String[] columns = line.split("\\s+");
                String mot = columns[0];
                ArrayList<TextPosition> pos = new ArrayList<>();

                for (int i = 1; i < columns.length; i++) {
                    String[] value = columns[i].split("[(,)]");
                    pos.add(new TextPosition(Integer.parseInt(value[1]), Integer.parseInt(value[2])));
                }

                indexTree.inserer(mot, pos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return indexTree;
    }

}
