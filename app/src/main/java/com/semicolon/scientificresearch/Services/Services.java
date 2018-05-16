package com.semicolon.scientificresearch.Services;

import com.semicolon.scientificresearch.Models.ResponseModel;
import com.semicolon.scientificresearch.Models.TrainingModel;
import com.semicolon.scientificresearch.Models.UserModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Services {
    @FormUrlEncoded
    @POST("/api/registration")
    Call<UserModel> CreateNewAccount(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/api/auth")
    Call<UserModel> Login(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/api/advancedtranslation")
    Call<ResponseModel> UploadTranslateFile(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/api/arbitration")
    Call<ResponseModel> UploadTa7keemFile(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/api/analyses")
    Call<ResponseModel> UploadTa7lel(@FieldMap Map<String,String> map);
    @FormUrlEncoded
    @POST("/api/citation")
    Call<ResponseModel> Uploadeqtbas(@FieldMap Map<String,String> map);
    @GET("/api/courses")
    Call<List<TrainingModel>> TrainingData();

    @FormUrlEncoded
    @POST("/api/bookcourse")
    Call<ResponseModel> ReserveCourse(@FieldMap Map<String,String> map);
}
