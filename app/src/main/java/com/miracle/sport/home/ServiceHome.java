package com.miracle.sport.home;


import com.miracle.base.network.ZResponse;
import com.miracle.michael.common.bean.NewsDetailBean;
import com.miracle.sport.home.bean.ChannerlKey;
import com.miracle.sport.home.bean.Football;
import com.miracle.sport.home.bean.HomeBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceHome {

    /**
     * 麻将列表
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/sport/type")
    Call<ZResponse<List<ChannerlKey>>> getSearchKeys();
    @Headers({"BaseUrl:zh"})
    @POST("home/sport/sportlist")
    Call<ZResponse<HomeBean>> getNewsList(@Query("class_id") int class_id, @Query("page") int page, @Query("pageSize") int pageSize);
//    Call<ZResponse<List<Football>>> getNewsList(@Query("class_id") int class_id, @Query("page") int page, @Query("pageSize") int pageSize);
    @Headers({"BaseUrl:zh"})
    @POST("home/sport/detail")
    Call<ZResponse<NewsDetailBean>> getNewsDetail(@Query("id") int id);



}
