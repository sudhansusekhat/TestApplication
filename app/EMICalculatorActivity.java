package com.dbs.easyhomeloan.View.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dbs.easyhomeloan.Helper;
import com.dbs.easyhomeloan.Model.EMIModel;
import com.dbs.easyhomeloan.R;
import com.dbs.easyhomeloan.VIewModel.EMIViewModel;
import com.google.android.material.snackbar.Snackbar;

import static com.dbs.easyhomeloan.Helper.isStringNotEmpty;

public class EMICalculatorActivity extends AppCompatActivity {

    private TextView textView;
    private TextView totalAmount;
    private TextView interestAmount;
    private TextView monthlyemi;
    private CardView cardView;
    private String principle, tenure, roi;
    EMIViewModel emiViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_emi_calc);
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("EMI Calculator");
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
            textView = findViewById(R.id.textView);
            totalAmount = findViewById(R.id.totalAmount);
            interestAmount = findViewById(R.id.interestAmount);
            monthlyemi = findViewById(R.id.monthlyemi);
            cardView = findViewById(R.id.cardView);
            emiViewModel = ViewModelProviders.of(this).get(EMIViewModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculate(View v) {

        try {
            principle = getPrincipleText().getText().toString().trim();
            tenure = getTenure().getText().toString().trim();
            roi = getRoi().getText().toString().trim();
            if (isStringNotEmpty(principle)) {
                if (isStringNotEmpty(roi)) {
                    if (isStringNotEmpty(tenure)) {
                        emiViewModel.getEMIData(Float.valueOf(principle), Float.valueOf(roi), Integer.parseInt(tenure) * 12).observe(this, new Observer<EMIModel>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onChanged(EMIModel emiModel) {
                                if (emiModel != null) {
                                    Helper.getHideKeyBoard(EMICalculatorActivity.this, totalAmount);
                                    totalAmount.setText("Total Payble Amount : " + emiModel.getTotalPayment());
                                    interestAmount.setText("Total Payble Interest Amount : " + emiModel.getTotalInterestPayable());
                                    monthlyemi.setText("Monthly Payble EMI Amount : " + emiModel.getLoanEMI());
                                    Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                            R.anim.slideupanim);
                                    cardView.startAnimation(slide_up);
                                    cardView.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                    } else
                        Snackbar.make(getPrincipleText(), "Please Enter the tenure", 3000).show();
                } else
                    Snackbar.make(getPrincipleText(), "Please Enter the rate of interest", 3000).show();
            } else
                Snackbar.make(getPrincipleText(), "Please Enter the principle amount", 3000).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private EditText getRoi() {
        return (EditText) findViewById(R.id.roi);
    }

    private EditText getTenure() {
        return (EditText) findViewById(R.id.tenure);
    }

    private EditText getPrincipleText() {
        return (EditText) findViewById(R.id.principleText);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
