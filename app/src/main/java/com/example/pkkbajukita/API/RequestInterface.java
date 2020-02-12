package com.example.pkkbajukita.API;
import com.example.pkkbajukita.Response.JSONResponse;
import com.example.pkkbajukita.Value.ValueLogin;
import com.example.pkkbajukita.Value.ValueRespon;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RequestInterface {

    @GET("/select_barang.php")
    Call<JSONResponse> getJSON();


    @FormUrlEncoded
    @POST("/upload_barang.php")
    Call<ValueRespon> uploadBrg(@Field("nama_brg") String nama_brg,
                                @Field("ket_brg") String ket_brg,
                                @Field("lokasi_brg") String lokasi_brg,
                                @Field("img_brg_str") String img_brg_str);


    @FormUrlEncoded
    @POST("/update_profile.php")
    Call<ValueRespon> updateprofile(@Field("imgstr") String imgstr,
                                @Field("nama") String nama,
                                @Field("nowa") String nowa,
                                @Field("userid") String userid);

    @FormUrlEncoded
    @POST("/unggahan_barang.php")
    Call<JSONResponse> unggahan(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("/selesai_barang.php")
    Call<JSONResponse> selesai(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("/update_proses.php")
    Call<ValueRespon> updateproses(@Field("userid") String userid,
                                   @Field("idbarang") String idbarang);

    @FormUrlEncoded
    @POST("/login.php")
    Call<ValueLogin> login(@Field("email") String username,
                           @Field("password") String password);

    @FormUrlEncoded
    @POST("/register.php")
    Call<ValueRespon> register(
            @Field("nama_lengkap") String nama_lengkap,
            @Field("no_wa") String no_wa,
            @Field("username") String username,
            @Field("password") String password,
            @Field("confirm_password") String confirm_password);

}
