package task;


import java.util.ArrayList;
import java.util.List;

public class Permutation {
    private static Integer[] source = new Integer[]{1, 0};


    public static List<String> getVariations(int variationLength) {
        int srcLength = source.length;
        int permutations = (int) Math.pow(srcLength, variationLength);

        Integer[][] table = new Integer[permutations][variationLength];

        for (int i = 0; i < variationLength; i++) {
            int t2 = (int) Math.pow(srcLength, i);
            for (int p1 = 0; p1 < permutations;) {
                for (int al = 0; al < srcLength; al++) {
                    for (int p2 = 0; p2 < t2; p2++) {
                        table[p1][i] = source[al];
                        p1++;
                    }
                }
            }
        }

        List<String> list = new ArrayList<>();
        for (int i = 0; i<table.length; i++){
            String num = "";
            for (int j = 0; j<table[i].length; j++){
                num += String.valueOf(table[i][j]);
            }
            list.add(num);
        }
        return list;
    }

}