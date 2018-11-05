package com.semicolon.scientificresearch.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.semicolon.scientificresearch.Activities.CourseDetailsActivity;
import com.semicolon.scientificresearch.Models.NotificationModel;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.databinding.NotificationRowBinding;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter <NotificationAdapter.MyHolder>{

    private List<NotificationModel> notificationModelList;
    private Context context;
    private CourseDetailsActivity activity;

    public NotificationAdapter(List<NotificationModel> notificationModelList, Context context) {
        this.notificationModelList = notificationModelList;
        this.context = context;
        this.activity = (CourseDetailsActivity) context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationRowBinding notificationRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.notification_row,parent,false);
        return new MyHolder(notificationRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        NotificationModel notificationModel = notificationModelList.get(position);
        holder.notificationRowBinding.setNotificationModel(notificationModel);
        if (notificationModel.getApproved().equals("1"))
        {
            holder.notificationRowBinding.tvReply.setText("تم الموافقة على الحجز");
        }else
            {
                holder.notificationRowBinding.tvReply.setText("تم رفض الحجز");

            }
            holder.notificationRowBinding.tvCourseDate.setText("تاريخ الكورس "+notificationModel.getCourse_date());

    }



    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private NotificationRowBinding notificationRowBinding;
        public MyHolder(NotificationRowBinding notificationRowBinding) {
            super(notificationRowBinding.getRoot());
            this.notificationRowBinding = notificationRowBinding;

        }
    }

}
