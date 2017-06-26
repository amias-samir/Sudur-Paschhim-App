package com.naxa.nepal.sudurpaschimanchal.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naxa.nepal.sudurpaschimanchal.R;
import com.naxa.nepal.sudurpaschimanchal.model.District;
import com.naxa.nepal.sudurpaschimanchal.model.LocalRepresentativeModel;

import java.util.List;

/**
 * Created by User on 11/6/2016.
 */

public class LocalRepresentativeList_Adapter extends RecyclerView.Adapter<LocalRepresentativeList_Adapter.ContactViewHolder> {

    private List<LocalRepresentativeModel> colorList;
    Context context;

    public LocalRepresentativeList_Adapter(Context context, List<LocalRepresentativeModel> cList) {
        this.colorList = cList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    @Override
    public void onBindViewHolder(LocalRepresentativeList_Adapter.ContactViewHolder contactViewHolder, int i) {
        LocalRepresentativeModel ci = colorList.get(i);
        final SharedPreferences wmbPreference = PreferenceManager
                .getDefaultSharedPreferences(context);
            contactViewHolder.pName.setText(ci.getNpName());
    }

    @Override
    public LocalRepresentativeList_Adapter.ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.district_items, viewGroup, false);

        return new LocalRepresentativeList_Adapter.ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView pName;

        public ContactViewHolder(View v) {
            super(v);
            pName = (TextView) v.findViewById(R.id.tv_district_name);
        }
    }
}
