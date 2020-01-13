package app.zikko.swivltest.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {
    String API_GITHUB_URL = "https://api.github.com/";

    @GET("users")
    Call<List<User>> getUserList(@Query("since") Integer since, @Query("per_page") Integer perPage);

    @GET("users/{login}")
    Call<UserDetails> getUserDetails(@Path(value = "login", encoded = true) String login);
}
