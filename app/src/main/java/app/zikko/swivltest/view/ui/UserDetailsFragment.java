package app.zikko.swivltest.view.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.TransitionInflater;
import app.zikko.swivltest.R;
import app.zikko.swivltest.service.UserDetails;
import app.zikko.swivltest.view.MainActivity;
import app.zikko.swivltest.viewmodel.UserDetailsViewModel;

public class UserDetailsFragment extends Fragment {

    private TextView name;
    private TextView website;
    private TextView repoCount;
    private TextView gistsCount;
    private TextView followersCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            String avatarUrl = getArguments().getString("avatarUrl");
            SimpleDraweeView dw = view.findViewById(R.id.userAvatar);
            dw.setTransitionName(avatarUrl);
            dw.setImageURI(avatarUrl);
        }
        name = view.findViewById(R.id.name);
        website = view.findViewById(R.id.website);
        repoCount = view.findViewById(R.id.repoCount);
        gistsCount = view.findViewById(R.id.gistsCount);
        followersCount = view.findViewById(R.id.followersCount);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String login = getArguments().getString("login");
        UserDetailsViewModel.UserVMFactory factory =
                new UserDetailsViewModel.UserVMFactory(login);
        UserDetailsViewModel mViewModel = ViewModelProviders.of(this, factory)
                .get(UserDetailsViewModel.class);

        observeViewModel(mViewModel);

    }

    private void observeViewModel(UserDetailsViewModel mViewModel) {
        mViewModel.getUserDetailsListLiveData().observe(this, userDetails -> {
            if (getActivity() != null) {
                ((MainActivity) getActivity()).setActionBarTitle(userDetails.getName());
            }
            name.setText(userDetails.getName());
            website.setText(userDetails.getBlog());
            website.setOnClickListener(view -> openWebPage(userDetails.getBlog()));
            repoCount.setText(String.valueOf(userDetails.getPublic_repos()));
            gistsCount.setText(String.valueOf(userDetails.getPublic_gists()));
            followersCount.setText(String.valueOf(userDetails.getFollowers()));
        });
    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}
