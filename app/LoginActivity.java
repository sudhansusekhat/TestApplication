package com.dbs.easyhomeloan.View.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dbs.easyhomeloan.Helper;
import com.dbs.easyhomeloan.Model.LoginModel;
import com.dbs.easyhomeloan.Model.PropertyModel;
import com.dbs.easyhomeloan.R;
import com.dbs.easyhomeloan.VIewModel.CompareViewModel;
import com.dbs.easyhomeloan.VIewModel.LoginViewModel;
import com.dbs.easyhomeloan.VIewModel.PropertyViewModel;
import com.dbs.easyhomeloan.View.UI.Adapter.PropertyAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static com.dbs.easyhomeloan.Helper.isStringNotEmpty;

public class LoginActivity extends AppCompatActivity {

    private AppCompatEditText emailEt, pswEt;
    private String emailStr, pswStr;
    LoginViewModel model;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            if (Helper.getBool(this))
                startActivity(new Intent(this, DashboardActivity.class).putExtra("fromLogin", "Yes"));
            setContentView(R.layout.activity_login);
            model = ViewModelProviders.of(this).get(LoginViewModel.class);
            initializeViews();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeViews() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("Login");
            setSupportActionBar(toolbar);
            /*if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }*/
            emailEt = findViewById(R.id.emailEt);
            pswEt = findViewById(R.id.pswEt);
            progress = findViewById(R.id.progress);
            progress.setVisibility(View.GONE);
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

    public void onViewClicked(View view) {
        if (view.getId() == R.id.submitBtn && valid()) {
            progress.setVisibility(View.VISIBLE);
            model.getLoginData(emailStr, Helper.changeBase64String(pswStr)).observe(this, new Observer<LoginModel>() {
                @Override
                public void onChanged(@Nullable LoginModel loginData) {
                    try {
                        if (loginData == null) {
                            progress.setVisibility(View.GONE);
                            Snackbar.make(emailEt, "Something went wrong, Please try again!", 3000).show();
                            return;
                        }
                        Log.e("Login Data >> ", loginData.getResponse());

                        if (loginData.getResponse().equalsIgnoreCase("Valid User")) {
                            progress.setVisibility(View.GONE);
                            Helper.saveBool(LoginActivity.this, true);
                            Helper.saveEligible(LoginActivity.this, true);
                            Helper.setPreference(LoginActivity.this,"UserId",loginData.getUserid());
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        } else {
                            progress.setVisibility(View.GONE);
                            Snackbar.make(emailEt, "Not a valid user", 3000).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        progress.setVisibility(View.GONE);
                        Snackbar.make(emailEt, "Something went wrong, Please try again!", 3000).show();
                    }
                }
            });
        }
    }

    private boolean valid() {
        try {
            emailStr = emailEt.getText().toString().trim();
            pswStr = pswEt.getText().toString().trim();
            if (isStringNotEmpty(emailStr)) {
                if (isStringNotEmpty(pswStr)) {
                    return true;
                } else pswEt.setError("Password is required!");
            } else emailEt.setError("Email Id is required!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
