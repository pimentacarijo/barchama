package br.com.pimentacarijo.barchama6;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("verificarn.php")
    Call<TodoOBJ> getTodoObj();

    @GET("verificarn.php")
    Call<TodoOBJ> getTodoUsingQuery(@Query("usid") String usid, @Query("chave") String chave);

    @POST("verificarn.php")
    Call<TodoOBJ> postTodoPostObj(@Body TodoPostOBJ todoPostOBJ);

}
