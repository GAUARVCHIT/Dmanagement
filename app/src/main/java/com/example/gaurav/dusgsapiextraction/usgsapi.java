package com.example.gaurav.dusgsapiextraction;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface usgsapi {
     String minmagnitude="4";

//    public static  PostService postService=null;
//
//    public static PostService getService(){
//        if(postService==null){
//            Retrofit retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
//            postService=retrofit.create(PostService.class);
//        }
//
//        return postService;
//    }


        @GET("query")
        Call<Example> getpostlist(
                @Query("format") String json,
                @Query("endtime") String estring,
                @Query("starttime") String sstring,
                @Query("minmagnitude") Integer minteger,
                @Query("limit") Integer linteger
        );

}
