package app.zikko.swivltest.service;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private GitHubService gitHubService;
    private static UserRepository userRepository;

    private UserRepository(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.API_GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gitHubService = retrofit.create(GitHubService.class);
    }


    public static UserRepository getInstance() {
            if (userRepository == null) {
                userRepository = new UserRepository();
            }
        return userRepository;
    }

    GitHubService getGitHubService() {
        return gitHubService;
    }


    public LiveData<UserDetails> getUserDetails(String login) {
        final MutableLiveData<UserDetails> data = new MutableLiveData<>();

        gitHubService.getUserDetails(login).enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
