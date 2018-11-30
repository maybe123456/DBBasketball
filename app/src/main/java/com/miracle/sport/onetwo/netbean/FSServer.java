package com.miracle.sport.onetwo.netbean;

import com.miracle.base.network.ZResponse;
import com.miracle.sport.onetwo.entity.CpHall;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

//捕鱼
public interface FSServer {
    //登录
    @POST("api/user/login")
    Call<ZResponse> login(@Query("username") String username, @Query("password") String password);

    //注册
    @POST("api/user/register")
    Call<ZResponse> register(@Query("username") String username, @Query("password") String password, @Query("name") String name);

    //////////////////////////////

    //获取全国省份数据
    @GET("api/geograph/province")
    Call<ZResponse> province();

    //获取某个省份的城市数据
    @GET("api/geograph/citys")
    Call<ZResponse> citys();

    ////////////////////////////////

    //捕鱼分类接口
//    @Headers({"BaseUrl:zh"})
//    @POST("home/fish/index")
//    Call<ZResponse<List<LotteryCatTitleItem>>> lotteryCategory();

    @Headers({"BaseUrl:zh"})
    @POST("home/fish/type")
    Call<ZResponse<List<FishType>>> fishType();

    //捕鱼分类详情接口
    @Headers({"BaseUrl:zh"})
    @POST("home/fish/detail")
    Call<ZResponse<List<LotteryCatListItem>>> lotteryCategoryList(@Query("page") int page, @Query("pageSize") int limit, @Query("class_id") int class_id);

    //列表
    @Headers({"BaseUrl:zh"})
    @POST("home/index/cp_list")
    Call<ZResponse<List<CpTitleItem>>> cpTitleList();

    //博彩接口
    @Headers({"BaseUrl:zh"})
    @POST("home/index/index")
    Call<ZResponse<List<CpListItem>>> cpList(@Query("page") int page, @Query("pageSize") int limit, @Query("bigtype") String bigtype, @Query("smalltype") String smalltype);


    //大厅接口
    @Headers({"BaseUrl:zh"})
    @POST("home/fish/hall")
    Call<ZResponse<List<CpHall>>> cpHall();
    //////////////////


    ///////////////////////////////


}
