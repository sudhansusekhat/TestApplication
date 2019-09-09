package com.dbs.easyhomeloan.View.UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.dbs.easyhomeloan.Model.PropertyModel;
import com.dbs.easyhomeloan.R;
import com.dbs.easyhomeloan.View.UI.CompareActivity;
import com.dbs.easyhomeloan.View.UI.DashboardActivity;
import com.dbs.easyhomeloan.View.UI.PropertyDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {

    private Context mCtx;
    List<PropertyModel.PropertiesList> proRecommendedpropertyList = new ArrayList<>();
    int clickedItemCount = 0;

    String property1,property2;
    public PropertyAdapter(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void setData(List<PropertyModel.PropertiesList> proRecommendedpropertyList) {
        this.proRecommendedpropertyList = proRecommendedpropertyList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.dashboard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        PropertyModel.PropertiesList propertyModel = proRecommendedpropertyList.get(position);

        holder.nameTv.setText(propertyModel.getPropertyName());
        holder.priceTv.setText(String.valueOf(propertyModel.getPrice()));
        holder.locationTv.setText(propertyModel.getAddr2());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCtx.startActivity(new Intent(mCtx, PropertyDetailsActivity.class).putExtra("pData", proRecommendedpropertyList.get(position)));
            }
        });

        holder.btn_compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent compareIntent = new Intent(mCtx, CompareActivity.class);
                holder.btn_compare.setText("COMPARE");
                holder.btn_compare.setBackgroundColor(mCtx.getResources().getColor(R.color.darkgrey));
                clickedItemCount = clickedItemCount + 1;
                if (clickedItemCount > 1) {
                    clickedItemCount = 0;
                    property2 = proRecommendedpropertyList.get(position).getPropertyId();
                    compareIntent.putExtra("model1", property1);
                    compareIntent.putExtra("model2", property2);
                    ((DashboardActivity) mCtx).startActivityForResult(compareIntent, 7);
                } else {
                    property1 = proRecommendedpropertyList.get(position).getPropertyId();
                    holder.btn_compare.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));
                    holder.btn_compare.setText("COMPARE WITH");

                }
            }
        });

        if (proRecommendedpropertyList.size() > 1) {
            holder.btn_compare.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return proRecommendedpropertyList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv, priceTv, locationTv;
        AppCompatButton btn_compare;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.nameTv);
            priceTv = itemView.findViewById(R.id.priceTv);
            locationTv = itemView.findViewById(R.id.locationTv);
            btn_compare = (AppCompatButton) itemView.findViewById(R.id.btn_compare);
        }
    }
}
