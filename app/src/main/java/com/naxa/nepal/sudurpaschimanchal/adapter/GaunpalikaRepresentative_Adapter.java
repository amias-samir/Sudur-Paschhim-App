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

import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.model.Local_Level_Representative_Model;

import java.util.List;

/**
 * Created by susan on 6/26/2017.
 */

public class GaunpalikaRepresentative_Adapter extends RecyclerView.Adapter<GaunpalikaRepresentative_Adapter.ContactViewHolder> {

    private List<Local_Level_Representative_Model> colorList;
    Context context;

    public GaunpalikaRepresentative_Adapter(Context context, List<Local_Level_Representative_Model> cList) {
        this.colorList = cList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    @Override
    public void onBindViewHolder(GaunpalikaRepresentative_Adapter.ContactViewHolder contactViewHolder, int i) {
        Local_Level_Representative_Model ci = colorList.get(i);
        final SharedPreferences wmbPreference = PreferenceManager
                .getDefaultSharedPreferences(context);
            contactViewHolder.rName.setText(ci.get_name_en());
//            contactViewHolder.rDistrict.setText(ci.getDi());
            contactViewHolder.rPalikaName.setText(ci.get_palika_name_en());
//            contactViewHolder.rContact.setText(ci.get_number());
    }

    @Override
    public GaunpalikaRepresentative_Adapter.ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.gaupalika_representative_items, viewGroup, false);

        return new GaunpalikaRepresentative_Adapter.ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView rName, rDistrict, rPalikaName, rContact;
        private ImageView thumbImage;
        public ContactViewHolder(View v) {
            super(v);
            thumbImage = (ImageView) v.findViewById(R.id.img_thumbnail);
            rName = (TextView) v.findViewById(R.id.textView_representative);
//            rDistrict = (TextView) v.findViewById(R.id.textView_district);
            rPalikaName = (TextView) v.findViewById(R.id.textView_palika_name);
//            rContact = (TextView) v.findViewById(R.id.textView_contact);
        }
    }
}
