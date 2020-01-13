package app.zikko.swivltest.service;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class UsersDataFactory extends DataSource.Factory {

    private MutableLiveData<UsersDataSource> mutableLiveData;
    private UsersDataSource usersDataSource;

    public UsersDataFactory() {
        this.mutableLiveData = new MutableLiveData<>();
    }

    @Override
    @NonNull
    public UsersDataSource create() {
        usersDataSource = new UsersDataSource();
        mutableLiveData.postValue(usersDataSource);
        return usersDataSource;
    }

    public MutableLiveData<UsersDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}