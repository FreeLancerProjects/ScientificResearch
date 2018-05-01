package com.semicolon.scientificresearch.Services;

import com.semicolon.scientificresearch.Models.ResponseModel;
import com.semicolon.scientificresearch.Models.UserModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
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
    @POST("/api/advancedtranslation")
    Call<ResponseModel> UploadTa7keemFile(@FieldMap Map<String,String> map);
}
