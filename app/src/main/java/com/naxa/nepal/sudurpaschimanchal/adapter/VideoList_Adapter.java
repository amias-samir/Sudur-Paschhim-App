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
import com.naxa.nepal.sudurpaschimanchal.model.VideoList_Model;
import com.naxa.nepal.sudurpaschimanchal.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ramaan on 5/31/2016.
 */
public class VideoList_Adapter extends RecyclerView.Adapter<VideoList_Adapter.ContactViewHolder> {

    private List<VideoList_Model> colorList;
    Context context;

    public VideoList_Adapter(Context context, List<VideoList_Model> cList) {
        this.colorList = cList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        VideoList_Model ci = colorList.get(i);
        final SharedPreferences wmbPreference = PreferenceManager
                .getDefaultSharedPreferences(context);
//        final boolean setData = wmbPreference.getBoolean("SET_ENGLISH_ON", true);
//
//
//        if (setData) {
//
//            contactViewHolder.vName.setText(ci.title_np);
//            contactViewHolder.vDesc.setText(ci.description_np);
//
//
//        } else {
        contactViewHolder.vName.setText(ci.title_np);
        contactViewHolder.vDesc.setText(ci.description_np);

        //}

        String img_url = ci.photos;
        if (!img_url.equals("")|| img_url != null) {
            img_url = "www";
            Glide.with(context)
                    .load(img_url)
                    .into(contactViewHolder.imageView);
        }else {
            contactViewHolder.imageView.setImageResource(R.drawable.ic_video);
        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.youtube_cardview, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName, vDesc;
        protected ImageView imageView;

        public ContactViewHolder(View v) {
            super(v);
            vName = (TextView) v.findViewById(R.id.textView_VideoTitle);
            vDesc = (TextView) v.findViewById(R.id.tv_desc_youtube);
            imageView = (ImageView) v.findViewById(R.id.img_thumbnail);

        }
    }

}