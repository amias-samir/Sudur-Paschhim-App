package com.naxa.nepal.sudurpaschimanchal.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.model.DevActivity_Pojo;

import java.util.List;

public class DevActivityRecyclerViewAdapter extends RecyclerView.Adapter<DevActivityRecyclerViewAdapter.ContactViewHolder> implements View.OnClickListener {


    private List<DevActivity_Pojo> faqList;
    Context context;

    public DevActivityRecyclerViewAdapter(Context context, List<DevActivity_Pojo> cList) {
        this.faqList = cList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        DevActivity_Pojo ci = faqList.get(i);
        final SharedPreferences wmbPreference = PreferenceManager
                .getDefaultSharedPreferences(context);
//        final boolean setData = wmbPreference.getBoolean("SET_ENGLISH_ON", true);
//
//        if(setData){
            contactViewHolder.tvDevTitle.setText(ci.getDev_title_en());
            contactViewHolder.tvContractor.setText(ci.getDev_contractor_en());
            contactViewHolder.tvBudget.setText(ci.getDev_budget_en());
            contactViewHolder.tvDistrict.setText(ci.getDistrict_name_en());
//        }else{
//            contactViewHolder.tvDevTitle.setText(ci.getDev_title_np());
//            contactViewHolder.tvContractor.setText(ci.getDev_contractor_np());
//            contactViewHolder.tvBudget.setText(ci.getDev_budget_np());
//            contactViewHolder.tvDistrict.setText(ci.getDistrict_name_np());
//        }


        String img_url = ci.getmThumbnail() ;

        if(img_url != null) {
            Glide.with(context)
                    .load(img_url)
                    .thumbnail(0.5f)
                    .override(130, 90)
                    .into(contactViewHolder.imageView);
        }


    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cardview_dev_row, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onClick(View v) {

    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvDevTitle, tvContractor, tvBudget, tvDistrict;
        protected ImageView imageView;

        public ContactViewHolder(View v) {
            super(v);
            tvDevTitle = (TextView) v.findViewById(R.id.dev_title);
            tvContractor = (TextView) v.findViewById(R.id.tv_contractor);
            tvBudget = (TextView) v.findViewById(R.id.tv_budget);
            tvDistrict = (TextView) v.findViewById(R.id.tv_district);
            imageView = (ImageView) v.findViewById(R.id.project_imageView);

        }
    }

}
