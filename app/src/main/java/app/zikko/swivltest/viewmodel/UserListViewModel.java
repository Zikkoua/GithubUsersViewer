package app.zikko.swivltest.viewmodel;

import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import app.zikko.swivltest.service.User;
import app.zikko.swivltest.service.UsersDataFactory;

public class UserListViewModel extends ViewModel {

    private Executor executor;

    private final LiveData<PagedList<User>> usersListLivedata;
    private final UsersDataFactory usersDataFactory;

    public UserListViewModel() {

        executor = Executors.newFixedThreadPool(5);
        usersDataFactory = new UsersDataFactory();
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(40)
                .build();

        usersListLivedata = (new LivePagedListBuilder(usersDataFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<User>> getUsersListLiveData(){
        return usersListLivedata;
    }

    public void refresh(){
        if (usersDataFactory.getMutableLiveData().getValue() != null) {
            usersDataFactory.getMutableLiveData().getValue().invalidate();
        }
    }
}
