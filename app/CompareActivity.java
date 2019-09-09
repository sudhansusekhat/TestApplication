package com.dbs.easyhomeloan.View.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dbs.easyhomeloan.Model.CompareModel;
import com.dbs.easyhomeloan.Model.PropertyModel;
import com.dbs.easyhomeloan.R;
import com.dbs.easyhomeloan.VIewModel.CompareViewModel;
import com.dbs.easyhomeloan.VIewModel.EMIViewModel;

import java.util.List;

public class CompareActivity extends AppCompatActivity {

    private AppCompatTextView priceVal1, priceVal2, areaVal1, areaVal2,
            typeVal1, typeVal2, addressVal1,
            addressVal2, address2Val1, address2Val2;
    private PropertyModel.PropertiesList model1, model2;
    private AppCompatTextView property1, property2;
    String property1Str,property2Str;

    private CompareViewModel compareViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        try {
            initialiseView();

            compareViewModel = ViewModelProviders.of(this).get(CompareViewModel.class);
            Bundle b = getIntent().getExtras();
            if (b != null) {
                property1Str =  b.getString("model1");
                property2Str = b.getString("model2");

                compareViewModel.getComparedData(property1Str,property2Str).observe(this, new Observer<List<CompareModel>>() {
                    @Override
                    public void onChanged(List<CompareModel> compareModel) {
                        if(compareModel!=null){
                            property1.setText(compareModel.get(0).getPropertyName());
                            property2.setText(compareModel.get(1).getPropertyName());
                            priceVal1.setText(String.valueOf(compareModel.get(0).getPrice()));
                            priceVal2.setText(String.valueOf(compareModel.get(1).getPrice()));
                            areaVal1.setText(String.valueOf(compareModel.get(0).getArea()));
                            areaVal2.setText(String.valueOf(compareModel.get(1).getArea()));
                            typeVal1.setText(compareModel.get(0).getType());
                            typeVal2.setText(compareModel.get(1).getType());
                            addressVal1.setText(compareModel.get(0).getAddr1());
                            addressVal2.setText(compareModel.get(1).getAddr1());
                            address2Val1.setText(compareModel.get(0).getAddr2());
                            address2Val2.setText(compareModel.get(1).getAddr2());
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialiseView() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("Compare");
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }

            property1 = findViewById(R.id.property1);
            property2 = findViewById(R.id.property2);
            priceVal1 = findViewById(R.id.priceVal1);
            priceVal2 = findViewById(R.id.priceVal2);
            areaVal1 = findViewById(R.id.areaVal1);
            areaVal2 = findViewById(R.id.areaVal2);
            typeVal1 = findViewById(R.id.typeVal1);
            typeVal2 = findViewById(R.id.typeVal2);
            addressVal1 = findViewById(R.id.addressVal1);
            addressVal2 = findViewById(R.id.addressVal2);
            address2Val1 = findViewById(R.id.address2Val1);
            address2Val2 = findViewById(R.id.address2Val2);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
