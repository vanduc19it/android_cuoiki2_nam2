package vku.phungduc.myapplication.api;




import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONStringer;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import vku.phungduc.myapplication.model.comment.result_comment;
import vku.phungduc.myapplication.model.congthuc.result_congthuc;
import vku.phungduc.myapplication.model.danhmuc.result_danhmuc;
import vku.phungduc.myapplication.model.tintuc.result_tintuc;
import vku.phungduc.myapplication.model.user.result_user;

import static vku.phungduc.myapplication.constant.url_api;

public interface ApiService {


    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    ApiService  apiService = new Retrofit.Builder()
            .baseUrl(url_api+ "/api/")
               // .baseUrl("http://192.168.1.148/api1/") //http://localhost/api1/foodRecipes/
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class) ;
    @GET("foodRecipes/getDanhMuc.php")
    Call<result_danhmuc> getDanhmucApi();

    @GET("foodRecipes/getUsername.php")
    Call<result_user> getUserNameApi(@Query("email") String email , @Query("password") String password ) ;

    @GET("foodRecipes/getUserId.php")
    Call<result_user> getUserId(@Query("id") int id ) ;

    @GET("foodRecipes/getCongthuc.php")
    Call<result_congthuc> getCongthucApi() ;

    @GET("foodRecipes/getCongthuc.php")
    Call<result_congthuc> getCongthucApi_danhmuc(@Query("idDanhmuc") int id ) ;

    @GET("foodRecipes/getCongthuc.php")
    Call<result_congthuc> getCongthucApi_user(@Query("idUser") String id ) ;

    @GET("foodRecipes/getCongthuc.php")
    Call<result_congthuc> getCongthucApi_search(@Query("search") String search) ;

    @GET("foodRecipes/getTintucs.php")
    Call<result_tintuc> getTintuc() ;


    @GET("foodRecipes/getComment.php")
    Call<result_comment> getCommentApi(@Query("idMon_an") String idMon_an) ;

    @GET("foodRecipes/sendCode.php")
    Call<String> getCodePass(@Query("email") String email);



    @Multipart
    @POST("foodRecipes/postUser.php")
    Call<String> postUser_register(@Part MultipartBody.Part user_regis);

    @Multipart
    @POST("foodRecipes/postUserUpdate.php")
    Call<String> post_updateUser(@Part MultipartBody.Part imgPart, @Part MultipartBody.Part user_update ) ;

    @Multipart
    @POST("foodRecipes/post_comment.php")
    Call<String> post_Comment(@Part MultipartBody.Part comment ) ;

    @Multipart
    @POST("foodRecipes/post_congthuc.php")
    Call<String> postCongthuc(@Part MultipartBody.Part img ,@Part MultipartBody.Part data  ) ;


    @Multipart
    @POST("foodRecipes/update_congthuc.php")
    Call<String> postUpdateCongthuc(@Part MultipartBody.Part img ,@Part MultipartBody.Part data , @Part MultipartBody.Part id ) ;

    @Multipart
    @POST("foodRecipes/XoaCongThuc.php")
    Call<String> postDeleteFood(@Part MultipartBody.Part id, @Part MultipartBody.Part idUser ) ;

    @Multipart
    @POST("foodRecipes/updatePass.php")
    Call<String> postUpdatePass(@Part MultipartBody.Part pass, @Part MultipartBody.Part email ) ;


}

