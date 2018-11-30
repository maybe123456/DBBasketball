package com.miracle.sport.schedule.net;

import com.miracle.base.network.ZResponse;
import com.miracle.sport.schedule.bean.ClubeHotPost;
import com.miracle.sport.schedule.bean.ClubeItem;
import com.miracle.sport.schedule.bean.ClubeType;
import com.miracle.sport.schedule.bean.post.ClubePostJF;
import com.miracle.sport.schedule.bean.post.ClubePostSC;
import com.miracle.sport.schedule.bean.post.ClubePostSS;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FootClubServer {

    /**
     * 足球比分列表
     */
//
//    //比分类别
//    @GET("football/data")
//    Call<ZResponse<List<FootClubKey>>> getFootClubKeys();
//
//    //指定类别的比分列表
//    @POST("football")
//    Call<ZResponse<List<FootClubScore>>> getFootClubScores(@Query("type") String type);
//
//    //比分类别内容
//    @POST("footballRows?type=value")
//    Call<ZResponse<FootClubScorePost>> getFootClubScorePost(@Query("type") String type);

    /////////////////////


    @Headers({"BaseUrl:zh"})
    @POST("home/club/clubType")
    Call<ZResponse<List<ClubeType>>> getFootClubTypes(@Query("page") int page, @Query("pageSize") int pageSize);

    @Headers({"BaseUrl:zh"})
    @POST("home/club/clubModule")
    Call<ZResponse<List<ClubeItem>>> getFootClubItems(@Query("id") int id);

    @Headers({"BaseUrl:zh"})
    @POST("home/club/detailList")  //ClubeType.id   ,    ClubeItem.type
    Call<ZResponse<List<ClubePostJF>>> getFootClubPostJF(@Query("class_id") int class_id, @Query("module") String module, @Query("page") int page, @Query("pageSize") int pageSize);

    @Headers({"BaseUrl:zh"})
    @POST("home/club/detailList")  //ClubeType.id   ,    ClubeItem.type
    Call<ZResponse<List<ClubePostSS>>> getFootClubPostSS(@Query("class_id") int class_id, @Query("module") String module, @Query("page") int page, @Query("pageSize") int pageSize);

    @Headers({"BaseUrl:zh"})
    @POST("home/club/detailList")  //ClubeType.id   ,    ClubeItem.type
    Call<ZResponse<List<ClubePostSC>>> getFootClubPostSC(@Query("class_id") int class_id, @Query("module") String module, @Query("page") int page, @Query("pageSize") int pageSize);



    //热门
    @Headers({"BaseUrl:zh"})
    @POST("home/sport/remen")
    Call<ZResponse<List<ClubePostSC>>> getFootClubTypesHot(@Query("page") int page, @Query("pageSize") int pageSize);
}
