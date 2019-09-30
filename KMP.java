/**
 * KMP
 */
public class KMP {

    public static int KMP(String text, char[] factor, int[] rest) {
        int pos = 0;
        int i = 0;

        for (char c : text.toCharArray()) {
            if (c == factor[i]) {
                i++;
                if (i == factor.length) {
                    return pos - factor.length;
                }
            } else {
                i = rest[i];
                if (i == -1) {
                    i = 0;
                }
            }
            pos++;
        }

        return -1;
    }

    public static int[] retenue(char[] factor) {
        int[] ret = new int[factor.length + 1];

        int len = 0;
        int i = 1;
        ret[0] = 0; // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        while (i < factor.length) {
            if (factor[i] == factor[len]) {
                len++;
                ret[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = ret[len - 1];

                } else {
                    ret[i] = len;
                    i++;
                }
            }

        }

        return ret;

    }

}