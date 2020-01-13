package app.zikko.swivltest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import app.zikko.swivltest.service.UserDetails;
import app.zikko.swivltest.service.UserRepository;

public class UserDetailsViewModel extends ViewModel {

    private final LiveData<UserDetails> userDetailsLiveData;

    private UserDetailsViewModel(String login) {

        userDetailsLiveData = UserRepository.getInstance().getUserDetails(login);
    }

    public LiveData<UserDetails> getUserDetailsListLiveData() {
        return userDetailsLiveData;
    }

    public static class UserVMFactory extends ViewModelProvider.NewInstanceFactory {
        private final String userLogin;

        public UserVMFactory(String projectID) {
            this.userLogin = projectID;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new UserDetailsViewModel(userLogin);
        }
    }
}
