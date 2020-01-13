package app.zikko.swivltest.service;

import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersDataSource extends ItemKeyedDataSource<Integer, User> {

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<User> callback) {
        Log.i("UsersDataSource", "loadInitial: ");

        UserRepository.getInstance().getGitHubService().getUserList(0, params.requestedLoadSize).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body() != null) {
                    callback.onResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                //no error handling at this point
            }
        });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<User> callback) {
        Log.i("UsersDataSource", "Loading Rang sinceId=" + params.key + " Count " + params.requestedLoadSize);
        UserRepository.getInstance().getGitHubService().getUserList(params.key, params.requestedLoadSize).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body() != null) {
                    callback.onResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                //no error handling at this point
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<User> callback) {
        //empty
    }

    @NonNull
    @Override
    public Integer getKey(@NonNull User item) {
        return item.getId();
    }

}
