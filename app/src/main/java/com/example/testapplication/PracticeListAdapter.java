package com.fydo.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fydo.Config.CommonUtilities;
import com.fydo.Model.PracticeList;
import com.fydo.Model.ReferringDoctorList;
import com.fydo.R;

import java.util.ArrayList;


public class PracticeListAdapter extends RecyclerView.Adapter<PracticeListAdapter.MyViewHolder> {

    ArrayList<PracticeList.LstAllPracticeList> model;
    private Activity mContext;
    boolean fromEdit;

    public PracticeListAdapter(Activity mContext, ArrayList<PracticeList.LstAllPracticeList> model, boolean fromEdit) {
        this.model = model;
        this.mContext = mContext;
        this.fromEdit = fromEdit;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_patients, parent, false);
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        String text = "";

        if (fromEdit){
            holder.ivArrorw.setVisibility(View.GONE);
        }

        CommonUtilities.setFontFamily(mContext, holder.txt_name, CommonUtilities.AvenirNextLTPro_Demi);
        CommonUtilities.setFontFamily(mContext, holder.txt_detail, CommonUtilities.AvenirNextLTPro_Regular);

        if(CommonUtilities.getPhoneNumber(model.get(position).Phone, 1).equals("")){
            text = model.get(position).Suburb;
        } else if (model.get(position).Suburb.equals("")){
            text = CommonUtilities.getPhoneNumber(model.get(position).Phone, 1);
        } else {
            text = CommonUtilities.getPhoneNumber(model.get(position).Phone, 1) + ", " + model.get(position).Suburb;
        }

        holder.txt_name.setText(model.get(position).PN);
        holder.txt_detail.setText(text);

    }

    @Override
    public int getItemCount() {
        return this.model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rel_next;
        TextView txt_name, txt_detail;
        ImageView ivArrorw;

        public MyViewHolder(View view) {
            super(view);
            rel_next = (RelativeLayout) view.findViewById(R.id.rel_next);
            txt_name = (TextView) view.findViewById(R.id.txt_name);
            txt_detail = (TextView) view.findViewById(R.id.txt_detail);
            ivArrorw = (ImageView) view.findViewById(R.id.ivArrorw);
        }
    }
}
