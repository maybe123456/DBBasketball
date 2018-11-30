package com.miracle.sport.onetwo.frag;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.sport.onetwo.adapter.DoubleColorListAdapter;
import com.miracle.sport.onetwo.adapter.RandBallAdapter;
import com.miracle.sport.onetwo.entity.RandBallEntity;
import com.miracle.sport.onetwo.netbean.LotteryCatListItem;
import com.miracle.sport.onetwo.util.RBRandNum;
import com.nightonke.boommenu.Animation.BoomEnum;
import com.nightonke.boommenu.Animation.OrderEnum;
import com.nightonke.boommenu.BoomButtons.CustomBitTextCButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;
import com.miracle.databinding.FragmentRandNumBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

//FragmentRandNumBinding
public class RandomNumFragment extends HandleFragment<FragmentRandNumBinding> {
    Random random;
    BoomMenuButton bmb;

    List<Integer> redNumList;
    List<Integer> blueNumList;
    List<RandBallEntity> redBallList;
    List<RandBallEntity> blueBallList;

    RandBallAdapter redBallAdapter;
    RandBallAdapter blueBallAdapter;

    int readMaxNum = 33;
    int blueMaxNum = 16;
    int redNum = 6;
    int blueNum = 1;
    int radius = Util.dp2px(14);
    int radius_popup = Util.dp2px(14);
    int colums = 6;
    Point point;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    DoubleColorListAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.fragment_rand_num;
    }

    @Override
    public void initView() {
        showTitle();
        setTitle("机选选号");
        random = new Random();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        radius = (int) (getResources().getDimension(R.dimen.ball_size) / 2);
        radius_popup = radius + 4;

        bmb = (BoomMenuButton) binding.getRoot().findViewById(R.id.bmb4);
        bmb.setShowDelay(200);
//        bmb.setShowEaseEnum(EaseEnum.);
        bmb.setBoomEnum(BoomEnum.RANDOM);
        bmb.setOrderEnum(OrderEnum.RANDOM);
//        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_7_1);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_7_1);
//        bmb.setButtonPlaceAlignmentEnum(ButtonPlaceAlignmentEnum.);

        redBallList = new ArrayList<>();
        blueBallList = new ArrayList<>();
        for(int i = 0; i < readMaxNum; i++){
            redBallList.add(new RandBallEntity(i+1));
        }
        for(int i = 0;i < blueMaxNum; i++){
            blueBallList.add(new RandBallEntity(i+1));
        }
        redBallAdapter = new RandBallAdapter();
        blueBallAdapter = new RandBallAdapter();
        initballList(redBallAdapter, getResources().getColor(R.color.red_ball), redBallList, binding.redRv);
        initballList(blueBallAdapter,  getResources().getColor(R.color.blue_ball), blueBallList, binding.blueRv);

        bmb.setUserocl(new BoomMenuButton.OnClickListener() {
            long lastClick;
            @Override
            public boolean onClick(View v) {
                if(lastClick > System.currentTimeMillis() - 1000)
                    return true;
                lastClick = System.currentTimeMillis();
                binding.getRoot().findViewById(R.id.scroll_view).scrollTo(0,0);
                randNewNum();
                bmb.post(new Runnable() {
                    @Override
                    public void run() {
                        bmb.toLayout();
                        bmb.calculateStartPositions(true);
                        bmb.boom();
                    }
                });
                return true;
            }
        });


        mAdapter = new DoubleColorListAdapter(mContext);
        binding.recordRv.setAdapter(mAdapter);
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setReverseLayout(true);
        binding.recordRv.setLayoutManager(lm);
    }

    private void randNewNum(){
        bmb.clearBuilders();
        bmb.getCustomPiecePlacePositions().clear();
        bmb.getCustomButtonPlacePositions().clear();
//        bmb.toLayout();
//        bmb.calculateStartPositions(true);

        point = bmb.getCoordinateOrigin();

        redNumList = RBRandNum.randRed();
        fillBall(getResources().getColor(R.color.red_ball_dark), binding.redRv , radius_popup, redNumList);
        Log.i("TAG", "randNewNum: red " + redNumList.toString());

        blueNumList = RBRandNum.randBlue();
        fillBall(getResources().getColor(R.color.blue_ball_dark), binding.blueRv , radius_popup, blueNumList);
        Log.i("TAG", "randNewNum: blue " + blueNumList.toString());

        bmb.toLayout();
        bmb.calculateStartPositions(true);

        addNumToList();
    }

    private void addNumToList() {
        LotteryCatListItem lotteryCatListItem = new LotteryCatListItem();
        String numStr = "";
        for(int nu : redNumList)
        {
            numStr = numStr + nu + " ";
        }
        lotteryCatListItem.setHost_num(numStr);
        numStr = "";
        for(int nu : blueNumList)
        {
            numStr = numStr + nu + " ";
        }

        lotteryCatListItem.setFirst_num(numStr);
        lotteryCatListItem.setOpen_time(sdf.format(new Date()));
        mAdapter.addData(lotteryCatListItem);
        mAdapter.notifyDataSetChanged();
    }

    private List<RandBallEntity> conver(List<Integer> inList){
        List<RandBallEntity> out = new ArrayList<>();
        for(int i : inList)
        {
            out.add(new RandBallEntity(i));
        }
        return out;
    }

    private void initballList(RandBallAdapter adapter, int color, List<RandBallEntity> list, RecyclerView recyclerView){
        adapter.setButtonDim(radius);
        adapter.setNormalColor(color);
        adapter.addData(list);
        recyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2,  StaggeredGridLayoutManager.VERTICAL);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, colums);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void fillBall(int color, RecyclerView rv, int radius, List<Integer> numList) {
        for (int i = 0; i < numList.size(); i++) {
            CustomBitTextCButton.Builder b = new CustomBitTextCButton.Builder();
            String ballText = "" + ((numList.get(i) / 10 == 0) ? "0"+numList.get(i) : numList.get(i));
            b.normalText(ballText);
            b.setUnable(false);
            b.imageRect(new Rect(0,0,0,0));
            b.imagePadding(new Rect(0,0,0,0));
            b.normalColor(color);
            b.textGravity(Gravity.CENTER);
            b.goneImg(true);
            b.buttonRadius(radius_popup);
            bmb.addBuilder(b);

//            int bmWidth = b.button().getMeasuredWidth();
//            bmb.getCustomPiecePlacePositions().add(new PointF(0,0));
            PointF pointF = numToPointF(numList.get(i), rv);
            pointF.x += radius_popup;
            pointF.y += radius_popup;
            bmb.getCustomButtonPlacePositions().add(pointF);
        }
    }

    public static boolean test = true;
    private PointF numToPointF(int num, RecyclerView rv){
        PointF pointF = new PointF();
        View item = rv.findViewWithTag(num);
        if(item == null)
            return pointF;
        int location[] = new int[2];
        int locations[] = new int[2];
        View btn = item.findViewById(R.id.button);
        btn.getLocationInWindow(location);
        btn.getLocationOnScreen(locations);
        Point tmpP = new Point(point.x, point.y - (Math.abs(locations[1] - location[1])));//honor10'bug
        pointF.x = location[0] - (tmpP.x / 2);
        pointF.y = location[1] - (tmpP.y / 2);
//        Point parentSize = new Point(background.getLayoutParams().width,
//                background.getLayoutParams().height);
//        pointF.x = 0;
//        pointF.y = -(tmpP.y / 2);
        return pointF;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onHandleMessage(Message msg) {
        if(msg.what == MSG_WHAT_SET_TITLE){
            setTitle((String) msg.obj);
        }
    }
}
