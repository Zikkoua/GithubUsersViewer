package app.zikko.swivltest.view.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.drawee.view.DraweeView;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import app.zikko.swivltest.R;
import app.zikko.swivltest.service.User;
import app.zikko.swivltest.view.adapter.UsersAdapter;
import app.zikko.swivltest.viewmodel.UserListViewModel;

public class UserListFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserListViewModel mViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private UsersAdapter usersAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserListViewModel.class);

        usersAdapter = new UsersAdapter(userClickCallback);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(usersAdapter);
        swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener);
        swipeRefreshLayout.setRefreshing(true);

        observeViewModel(mViewModel);
    }

    private void observeViewModel(UserListViewModel viewModel) {
        viewModel.getUsersListLiveData().observe(this, users -> {
            usersAdapter.submitList(users);
            Log.i("takk", "onChanged: "+users.size());
            swipeRefreshLayout.setRefreshing(false);
        });

    }

    private final SwipeRefreshLayout.OnRefreshListener swipeRefreshListener = () -> {
        swipeRefreshLayout.setRefreshing(true);
        mViewModel.refresh();
    };

    private final UsersAdapter.UserClickCallback userClickCallback = (user, imageView) -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            Bundle bundle = new Bundle();
            bundle.putString("login", user.getLogin());
            bundle.putString("avatarUrl", user.getAvatar_url());
            FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder().addSharedElement(imageView,user.getAvatar_url()).build();
            NavHostFragment.findNavController(UserListFragment.this).navigate(R.id.action_userListFragment_to_userDetailsFragment, bundle,null, extras);
        }
    };

}
