package com.job.userportal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.job.userportal.R;
import com.job.userportal.model.UserData;
import com.job.userportal.util.ImageProcessor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Job on Friday : 10/26/2018.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<UserData> userDataList;
    private ImageProcessor imageProcessor;

    public UserData getUserDataList(int pos) {
        return userDataList.get(pos);
    }

    private Context mContext;
    private UserItemListener userItemListener;

    public interface UserItemListener {
        void onPostClick(long id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.item_col_image)
        ImageView itemColImage;
        @BindView(R.id.item_col_name)
        TextView itemColName;
        @BindView(R.id.item_col_phonenum)
        TextView itemColPhonenum;



        UserItemListener mItemListener;

        public ViewHolder(View itemView, UserItemListener postItemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            UserData item = getUserDataList(getAdapterPosition());
            //this.mItemListener.onPostClick(item.getData());

            notifyDataSetChanged();
        }

        public void setUpUiSmall(UserData userData) {

            itemColName.setText(userData.getFirstName() + " " + userData.getLastName());
            itemColPhonenum.setText(userData.getFirstName());
            imageProcessor.setMyImage(itemColImage, userData.getAvatar());
        }
    }


    public UsersAdapter(Context context, List<UserData> userDataList, UserItemListener listener) {
        this.userDataList = userDataList;
        mContext = context;
        userItemListener = listener;
        imageProcessor = new ImageProcessor(context);
    }

    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

       // View postView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        View postView = inflater.inflate(R.layout.item_collapse, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.userItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder holder, int position) {

        UserData userData = userDataList.get(position);

        holder.setUpUiSmall(userData);

    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }


    private UserData getItem(int adapterPosition) {
        return userDataList.get(adapterPosition);
    }
}
