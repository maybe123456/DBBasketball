//package com.miracle.sport.schedule.fragment;
//
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v7.widget.LinearLayoutManager;
//import android.view.View;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.miracle.R;
//import com.miracle.base.BaseFragment;
//import com.miracle.base.network.PageLoadCallback;
//import com.miracle.base.network.ZCallback;
//import com.miracle.base.network.ZClient;
//import com.miracle.base.network.ZResponse;
//import com.miracle.databinding.FragClubeMainBinding;
//import com.miracle.sport.schedule.adapter.FootClubKeyHiveAdapter;
//import com.miracle.sport.schedule.bean.FootClubKey;
//import com.miracle.sport.schedule.bean.ClubeType;
//import com.miracle.sport.schedule.net.FootClubServer;
//
//import java.util.List;
//import java.util.Random;
//
////hive foot ball vs score
//public class FragFootClubKeyHive extends BaseFragment<FragClubeMainBinding> {
//
//    PageLoadCallback<ZResponse<List<ClubeType>>> callback;
//
//    private FootClubKeyHiveAdapter mAdapter;
//    Random random;
//    LinearLayoutManager layoutManager;
//    Handler uiHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            if(msg.what == 1){
//                if(msg.obj != null && msg.obj instanceof Bitmap){
//                    Bitmap bitmap = (Bitmap) msg.obj;
//                    //bitmap
//                    binding.getRoot().findViewById(R.id.frag_hive_fl).setBackground(new BitmapDrawable(bitmap));
//                }
//            }
//        }
//    };
//
//    BaseQuickAdapter.OnItemClickListener onItemClickListener;
//
//    public BaseQuickAdapter.OnItemClickListener getOnItemClickListener() {
//        return onItemClickListener;
//    }
//
//    public void setOnItemClickListener(BaseQuickAdapter.OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    //    List<FootClubKey> clubKeyList = new ArrayList<>();
////    TagAdapter mTagAdapter = new TagAdapter<FootClubKey>(clubKeyList) {
////        @Override
////        public View getView(FlowLayout parent, int position, FootClubKey clubKey) {
////            View v = getActivity().getLayoutInflater().inflate(R.layout.item_club_key, null);
////            TextView tv = v.findViewById(R.id.tvIndex);
////            tv.setText(clubKey.getName());
////            tv.setTag(clubKey);
////            v.setTag(clubKey);
////            View.OnClickListener onClickListener = new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    openSub((FootClubKey) v.getTag());
////                }
////            };
////            v.setOnClickListener(onClickListener);
////
////
////            ViewGroup.LayoutParams lp = tv.getLayoutParams();
////            if (lp == null)
////                lp = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
////            tv.measure(View.MeasureSpec.makeMeasureSpec(((1 << 30) - 1), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(((1 << 30) - 1), View.MeasureSpec.AT_MOST));
////            float scaleFactor = ((random.nextFloat() % 1.2f) + 1f);
////
////            int newWidth = (int) (tv.getMeasuredWidth() * scaleFactor);
////            int newHeight = (int) (tv.getMeasuredHeight() * scaleFactor);
////            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.getTextSize() * scaleFactor);
////            lp.width = newWidth;
////            lp.height = newHeight;
////            tv.setLayoutParams(lp);
////            tv.setOnClickListener(onClickListener);
////            return v;
////        }
////    };
//
//    @Override
//    public int getLayout() {
//        return R.layout.frag_clube_main;
//    }
//
//    @Override
//    public void initView() {
//        random = new Random();
////        UiUtils.rsBlur(mContext,BitmapFactory.decodeResource(getResources(),R.mipmap.dsdfkjs),10,0.5f, uiHandler);
//
//        mAdapter = new FootClubKeyHiveAdapter(mContext.getApplicationContext());
//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                openSub(mAdapter.getData().get(position));
//
////                if(onItemClickListener != null)
////                    onItemClickListener.onItemClick(adapter, view, position);
//            }
//        });
//
//        callback = new PageLoadCallback<ZResponse<List<ClubeType>>>(mAdapter, binding.recyclerView) {
//            @Override
//            public void requestAction(int page, int limit) {
//                ZClient.getService(FootClubServer.class).getFootClubTypes(page, limit).enqueue(callback);
//            }
//        };
//        callback.initSwipeRefreshLayout(binding.swipeRefreshLayout);
//
//        layoutManager = new LinearLayoutManager(mContext);
////        layoutManager.setGravity(HiveLayoutManager.ALIGN_LEFT);
////        layoutManager.setPadding(100, 100, 100, 100);
//        binding.recyclerView.setLayoutManager(layoutManager);
//        binding.recyclerView.setAdapter(mAdapter);
////        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_scaleup);
////        binding.recyclerView.setLayoutAnimation(animation);
//
//
////        binding.idFlowlayout.setAdapter(mTagAdapter);
////        binding.idFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
////            @Override
////            public boolean onTagClick(View view, int position, FlowLayout parent) {
////                openSub(clubKeyList.get(position));
////                return false;
////            }
////        });
//
//        reqData();
//    }
//
//
//    public void openSub(ClubeType clubKey) {
//        //TODO
//        //xxxxxxxxxx
//        //getFootClubItems
////        Intent intent = new Intent(mContext, ClubDataActivity.class);
////        intent.putExtra("clubkey", clubKey);
////        startActivity(intent);
//    }
//
//    private void reqData() {
////        mAdapter.getData().clear();
////        mAdapter.addData(data.getData());
////        mAdapter.notifyDataSetChanged();
//        callback.onRefresh();
//    }
//
//    @Override
//    public void initListener() {
////        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
////            @Override
////            public void onRefresh() {
////                reqData();
////            }
////        });
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
////    @Override
////    public void onResume() {
////        super.onResume();
////    }
////
////    @Override
////    public void onPause() {
////        super.onPause();
////    }
//}
