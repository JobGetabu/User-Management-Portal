package com.job.userportal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.job.userportal.R;
import com.job.userportal.model.UserData;
import com.job.userportal.util.ImageProcessor;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Job on Friday : 10/26/2018.
 */
public class UserViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_col_image)
    ImageView itemColImage;
    @BindView(R.id.item_col_name)
    TextView itemColName;

    @BindView(R.id.item_col_phonenum)
    TextView itemColPhonenum;

    private Context mContext;
    private ImageProcessor imageProcessor;

    private static final String TAG = "ClientVH";


    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        //LayoutInflater.from(mContext).inflate(R.layout.cell_expand, null);
        //LayoutInflater.from(mContext).inflate(R.layout.item_collapse, null);
    }

    public void init(Context mContext) {
        this.mContext = mContext;
        imageProcessor = new ImageProcessor(mContext);
    }

    public void setUpUiSmall(UserData userData) {

        itemColName.setText(userData.getFirstName() + " " + userData.getLastName());
        itemColPhonenum.setText(userData.getId());
        imageProcessor.setMyImage(itemColImage, userData.getAvatar());
    }


}
