package com.miracle.sport.onetwo.util;

import java.util.ArrayList;
import java.util.List;

public class RBRandNum {
    public static List<Integer> randRed(){
        return randChose(6,33);
    }

    public static List<Integer> randBlue(){
        return randChose(1,16);
    }

    public static List<Integer> randChose(int chose, int numbers){
        List<Integer> list = new ArrayList<>();
        List<Integer> numberPool = new ArrayList<>();
        for(int i = 0;i < numbers; i++){
            numberPool.add(i+1);
        }

        Integer chosedArr[] = new Integer[chose];
        while(chose > 0){
            Integer chosedArr1[] = new Integer[numberPool.size()];
            chosedArr1 = numberPool.toArray(chosedArr1);
            Integer num = RandUtils.rand(chosedArr1);
            numberPool.remove((Object) num);
            list.add(num);
            chose--;
        }
        return list;
    }
}
