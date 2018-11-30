package com.miracle.sport.onetwo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandUtils {
    public static long FIXED_RAN_SEED = 589348934L;
    public static Random fixedRan = new Random(FIXED_RAN_SEED);
    public static Random random = new Random();
    public static Random hourRandom;

    public static void initFixedRan(long seed){
        RandUtils.FIXED_RAN_SEED = seed;
        RandUtils.fixedRan = new Random(RandUtils.FIXED_RAN_SEED);
    }

    public static int rand(int[] arr) {
        int index = random.nextInt(arr.length);
        return arr[index];
    }
    public static int rand(Integer[] arr) {
        int index = random.nextInt(arr.length);
        return arr[index];
    }

    public static String rand(String[] arr) {
        int index = random.nextInt(arr.length);
        return arr[index];
    }

    public static int coresidual(int[] arr, int div) {
        return arr[div % arr.length];
    }

    public static String coresidual(String[] arr, int div) {
        return arr[div % arr.length];
    }

    public static List<String> randImgsUri(Context context, int pickNum, String dir) throws IOException {
        List<String> result = new ArrayList<>();
        List<String> allList = new ArrayList<>();
        try {
            String files[] = context.getAssets().list(dir);
            allList.addAll(Arrays.asList(files));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> chosedIndex = RBRandNum.randChose(pickNum, allList.size());
        for(int i = 0 ; i < chosedIndex.size(); i++){
            String tmpfile = "file:///android_asset/"+dir+"/"+allList.get(chosedIndex.get(i)-1);
            result.add(tmpfile);
        }
        return result;
    }

    public static List<Bitmap> randImgs(Context context, int pickNum, String dir) throws IOException {
        List<Bitmap> bitmaps = new ArrayList<>();
        List<String> allList = new ArrayList<>();
        try {
            String files[] = context.getAssets().list(dir);
            allList.addAll(Arrays.asList(files));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> chosedIndex = RBRandNum.randChose(pickNum, allList.size());
        for(int i = 0 ; i < chosedIndex.size(); i++){
            String tmpfile = dir+"/"+allList.get(chosedIndex.get(i)-1);
            Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open(tmpfile));
            bitmaps.add(bitmap);
        }

        return bitmaps;
    }

    public static List<Bitmap> randImgs(Context context, int pickNum) throws IOException {
        return randImgs(context, pickNum, "imgs");
    }

    public static List<String> randUserName(Context context, int pickNum){
        List<String> res = new ArrayList<>();
        res.add("未来败给命运");
        res.add("读不懂你的悲伤づ");
        res.add("面临孤独、");
        res.add("≠自錠義");
        res.add("不想听谎言");
        res.add("讓莪嘸倁菿楿信渏迹");
        res.add("缠绵过后的陌路");
        res.add("斯文禽兽゜ ");
        res.add("晚安晚安晚晚难安");
        res.add("伤、却美╮ ");
        res.add("爱情，不可信");
        res.add("旧心酸。");
        res.add("掩饰了难过");
        res.add("伪装の、快乐");
        res.add("僤純兲眞");
        res.add("ツ混ㄖ孓←┈");
        res.add("作茧自缚-");
        res.add("不要靠近我。");
        res.add("我们的约定。");
        res.add("放过。自己");
        res.add("ミ洄忆dē“独奏");
        res.add("当做最后一次 ");
        res.add("叶落花凋零。");
        res.add("牵痛了手づ ");
        res.add("尾戒如泪坠");
        res.add("痛定思痛。 ");
        res.add("薄凉之人终不念安。");
        res.add("旧日情如醉﹌");
        res.add("夜夜醉っ");
        res.add("遍体鳞伤。");
        res.add("有多少爱可以重来");
        res.add("为你止不住心碎");
        res.add("躺在键盘上的烟灰…");
        res.add("节日与我无缘～");
        res.add("回想着同样的时光ヽ");


        List<String> resultList = new ArrayList<>();
        List<Integer> indexList = RBRandNum.randChose(pickNum,res.size());
        for(int i : indexList)
        {
            resultList.add(res.get(i-1));
        }
        return resultList;
    }
}
