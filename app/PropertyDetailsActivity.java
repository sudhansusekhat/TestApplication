package com.dbs.easyhomeloan.View.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.dbs.easyhomeloan.Helper;
import com.dbs.easyhomeloan.Model.PropertyModel;
import com.dbs.easyhomeloan.R;

public class PropertyDetailsActivity extends AppCompatActivity {

    PropertyModel.PropertiesList proRecommendedpropertyList;

    private AppCompatTextView propertyTv;
    private AppCompatTextView nameValueTv;
    private AppCompatTextView typeValueTv;
    private AppCompatTextView priceValueTv;
    private AppCompatTextView areaValueTv;
    private AppCompatTextView emi10YrsValue;
    private AppCompatTextView emi20YrsValue;
    private AppCompatTextView emi30YrsValue;
    private AppCompatButton emaiCalBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);
        initializeViews();
        Bundle pData = getIntent().getExtras();
        if (pData != null) {
            proRecommendedpropertyList = (PropertyModel.PropertiesList) pData.getSerializable("pData");
            typeValueTv.setText(proRecommendedpropertyList.getType());
            nameValueTv.setText(proRecommendedpropertyList.getPropertyName());
            priceValueTv.setText("RS. " + proRecommendedpropertyList.getPrice() + "/-");
            areaValueTv.setText(proRecommendedpropertyList.getArea() + " sqft");
            String tenYrs = proRecommendedpropertyList.getEmi10yrs();
            if (tenYrs != null) {
                emi10YrsValue.setText("RS. " + tenYrs + "/-");
            } else {
                emi10YrsValue.setText(" ---- ");
            }
            String fifteenYrs = proRecommendedpropertyList.getEmi15yrs();
            if (fifteenYrs != null) {
                emi20YrsValue.setText("RS. " + fifteenYrs + "/-");
            } else {
                emi20YrsValue.setText(" ---- ");
            }
            String twentyYrs = proRecommendedpropertyList.getEmi20yrs();
            if (twentyYrs != null) {
                emi30YrsValue.setText("RS. " + twentyYrs + "/-");
            } else {
                emi30YrsValue.setText(" ---- ");
            }
        }

        emaiCalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PropertyDetailsActivity.this,
                        EMICalculatorActivity.class));
            }
        });
    }

    private void initializeViews() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("Property Details");
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
            propertyTv = findViewById(R.id.propertyTv);
            nameValueTv = findViewById(R.id.nameValueTv);
            typeValueTv = findViewById(R.id.typeValueTv);
            priceValueTv = findViewById(R.id.priceValueTv);
            areaValueTv = findViewById(R.id.areaValueTv);
            emi10YrsValue = findViewById(R.id.emi10YrsValue);
            emi20YrsValue = findViewById(R.id.emi20YrsValue);
            emi30YrsValue = findViewById(R.id.emi30YrsValue);
            emaiCalBtn = findViewById(R.id.emaiCalBtn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
