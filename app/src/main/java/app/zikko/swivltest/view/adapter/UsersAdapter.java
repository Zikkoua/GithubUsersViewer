package app.zikko.swivltest.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import app.zikko.swivltest.R;
import app.zikko.swivltest.service.User;

public class UsersAdapter extends PagedListAdapter<User, UsersAdapter.UserViewHolder> {

    public interface UserClickCallback {
        void onClick(User user, ImageView view);
    }

    private final UserClickCallback callback;

    public UsersAdapter(UserClickCallback clickCallback) {
        super(User.CALLBACK);
        callback = clickCallback;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_user_list, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        if (getItem(position) != null) {
            holder.tw.setText(getItem(position).getLogin());
            holder.draweeView.setImageURI(getItem(position).getAvatar_url());
            holder.draweeView.setTransitionName(getItem(position).getAvatar_url());
            holder.cardView.setOnClickListener(view -> callback.onClick(getItem(position), holder.draweeView));
        }
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tw;
        SimpleDraweeView draweeView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tw = itemView.findViewById(R.id.userNickName);
            draweeView = itemView.findViewById(R.id.userAvatar);
            cardView = itemView.findViewById(R.id.card);
        }
    }
}
