package com.miracle.base.network;

import com.miracle.base.bean.MyCollectionBean;
import com.miracle.base.bean.QQWechatBean;
import com.miracle.base.bean.UserBean;
import com.miracle.base.bean.UserInfoBean;
import com.miracle.michael.chess.bean.ChessNewsBean;
import com.miracle.michael.chess.bean.ChessNewsDetailBean;
import com.miracle.michael.chess.bean.ChessTypeBean;
import com.miracle.michael.common.bean.NewsBean;
import com.miracle.michael.common.bean.NewsDetailBean;
import com.miracle.michael.doudizhu.bean.DDZIndexBean;
import com.miracle.michael.football.bean.FootballBabyAlbumListBean;
import com.miracle.michael.football.bean.FootballBabyBean;
import com.miracle.michael.lottery.bean.LotteryNewsListBean;
import com.miracle.michael.football.bean.FootballNewsDetailBean;
import com.miracle.michael.football.bean.FootballNewsListBean;
import com.miracle.michael.lottery.bean.LotteryBean;
import com.miracle.michael.lottery.bean.LotteryResultBean;
import com.miracle.michael.football.bean.FootballClubBean;
import com.miracle.michael.football.bean.FootballRankDetailBean;
import com.miracle.michael.football.bean.FootballBannerBean;
import com.miracle.michael.football.bean.FootballMatchIndexBean;
import com.miracle.michael.football.bean.FootballRankTypeBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Michael on 2018/10/19 17:23 (星期五)
 */
public interface ZService {

    /*▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮小米▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮*/

    /**
     * 登录
     */
    @Headers({"BaseUrl:mi"})
    @POST("loginSet")
    Call<ZResponse<UserBean>> login(@Query("username") String username, @Query("password") String password);

    /**
     * 注册
     */
    @Headers({"BaseUrl:mi"})
    @POST("login")
    Call<ZResponse<UserBean>> register(@Query("username") String username, @Query("password") String password, @Query("nickname") String nickname);

    /**
     * 发送用户手机号
     */
    @Headers({"BaseUrl:mi"})
    @POST("set_tel")
    Call<ZResponse> sendPhoneNum(@Query("phone") String phone);


    /**
     * 足球资讯列表
     */
    @Headers({"BaseUrl:mi"})
    @POST("input")
    Call<ZResponse<List<FootballNewsListBean>>> getFootballNewsList(@Query("create") String create, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 足球资讯详情
     */
    @Headers({"BaseUrl:mi"})
    @POST("articleRow")
    Call<ZResponse<FootballNewsDetailBean>> getFootballNewsDetail(@Query("id") int id);

    /**
     * 轮播图
     */
    @Headers({"BaseUrl:mi"})
    @GET("article_play")
    Call<ZResponse<List<FootballBannerBean>>> getFootballBanner();

    /**
     * 收藏或取消收藏
     */
    @Headers({"BaseUrl:mi"})
    @POST("footballCollect")
    Call<ZResponse> likeOrDislike(@Query("type") String type, @Query("createid") int createid);

    /**
     * 赛事名称列表for资讯
     */
    @Headers({"BaseUrl:mi"})
    @GET("article_football")
    Call<ZResponse<List<FootballMatchIndexBean>>> getFootballMatchesIndex();

    /**
     * 赛事名称列表for数据排行榜
     */
    @Headers({"BaseUrl:mi"})
    @GET("football/data")
    Call<ZResponse<List<FootballClubBean>>> getFootballClubList();

    /**
     * 积分榜类型
     */
    @Headers({"BaseUrl:mi"})
    @POST("football")
    Call<ZResponse<List<FootballRankTypeBean>>> getFootballRankTypes(@Query("type") String type);

    /**
     * 积分榜详情
     */
    @Headers({"BaseUrl:mi"})
    @POST("footballRows")
    Call<ZResponse<FootballRankDetailBean>> getFootballRankDetail(@Query("type") String type);

    /**
     * 我的收藏列表
     */
    @Headers({"BaseUrl:mi"})
    @POST("my_collection")
    Call<ZResponse<List<MyCollectionBean>>> getMycollections(@Query("type") String type, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 个人信息
     */
    @Headers({"BaseUrl:mi"})
    @POST("user_info")
    Call<ZResponse<UserInfoBean>> getUserInfo();

    /**
     * 修改个人头像
     */
    @Headers({"BaseUrl:mi"})
    @Multipart
    @POST("login_edit_img")
    Call<ZResponse> uploadHeadImg(@Query("username") String username, @Part() MultipartBody.Part imgs);

    /**
     * 修改密码
     */
    @Headers({"BaseUrl:mi"})
    @POST("login_edit")
    Call<ZResponse> modifyPassword(@Query("oldPassWord") String oldPassWord, @Query("newPassWord") String newPassWord);

    /**
     * 获取客服联系方式
     */
    @Headers({"BaseUrl:mi"})
    @GET("get_qq")
    Call<ZResponse<QQWechatBean>> getCustomerServiceAccount();


    /*▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮小庄▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮▮*/

    /**
     * 彩票资讯列表
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/index/index")
    Call<ZResponse<List<LotteryNewsListBean>>> getLotteryNewslList(@Query("bigtype") String bigtype, @Query("smalltype") String smalltype, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 彩票分类
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/caipiao/index")
    Call<ZResponse<List<LotteryBean>>> getLotteryList();

    /**
     * 开奖结果
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/caipiao/detail")
    Call<ZResponse<List<LotteryResultBean>>> getLotteryResultList(@Query("class_id") int class_id, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 棋牌列表
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/qipai/qipaiList")
    Call<ZResponse<List<ChessNewsBean>>> getChessNewsList(@Query("class_id") int class_id, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 棋牌详情
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/qipai/detail")
    Call<ZResponse<ChessNewsDetailBean>> getChessNewsDetail(@Query("id") int id);

    /**
     * 棋牌类型
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/qipai/qipaiType")
    Call<ZResponse<List<ChessTypeBean>>> getQipaiTypes();

    /**
     * 足球宝贝相册列表
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/footballbaby/index")
    Call<ZResponse<List<FootballBabyAlbumListBean>>> getFootballBabyAlbums(@Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 足球宝贝列表
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/footballbaby/album")
    Call<ZResponse<List<FootballBabyBean>>> getFootballBabies(@Query("class_id") int class_id);

    /**
     * 斗地主新闻类型列表
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/dizhu/dizhutype")
    Call<ZResponse<List<DDZIndexBean>>> getDDZNewsTypeList();

    /**
     * 斗地主新闻列表
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/dizhu/dizhulist")
    Call<ZResponse<List<NewsBean>>> getDDZNewsList(@Query("class_id") int class_id, @Query("page") int page, @Query("pageSize") int pageSize);


    /**
     * 斗地主详情
     */
    @Headers({"BaseUrl:zh"})
    @POST("home/dizhu/detail")
    Call<ZResponse<NewsDetailBean>> getDDZNewsDetail(@Query("id") int id);
}
