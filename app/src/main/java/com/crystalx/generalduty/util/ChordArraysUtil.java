package com.crystalx.generalduty.util;

import java.util.Arrays;
import java.util.stream.IntStream;

public interface ChordArraysUtil {
    static int[] omit (int[] original,int[] addition) {
        return Arrays.stream(original).filter(v -> v != addition[0]).sorted().toArray();
    }

    static int[] extra (int[] original,int[] addition) {
        IntStream.Builder builder = IntStream.builder();
        for (int i : original) builder.add(i);
        for (int j : addition) builder.add(j);
        int[] res = builder.build().sorted().toArray();
        int[] res_out = new int[res.length - 1];
        if (res[0] == res[1]) System.arraycopy(res, 1, res_out, 0, res.length - 1);
        return res_out;
    }

    static int[] transpose (int[] original,int[] addition) {
        boolean token = false;
        for (int i = 0; i < original.length; i++) {
            if (original[i] == addition[0]) {
                original[i] += 12;
            }else if (original[i] < addition[0]){
                original[i] += 12;
            }else {
                for (int k = 0; k < original.length; k++) {
                    if (original[k] < addition[0]) original[k] += 12;
                    token = true;
                }
            }
        }
        if (token) original[original.length - 1] = addition[0];
        return Arrays.stream(original).sorted().toArray();
    }

    enum AdditionType {
        Transpose,Omit,Extra;
    }

    static int[] combine(int[] a,int[] b,AdditionType rule) {
        int[] res = new int[10];
        if (rule == AdditionType.Transpose) res = transpose(a,b);
        if (rule == AdditionType.Omit) res = omit(a,b);
        if (rule == AdditionType.Extra) res = extra(a,b);
        return res;
    }
}
