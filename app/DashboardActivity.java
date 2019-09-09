package com.dbs.easyhomeloan.View.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbs.easyhomeloan.Helper;
import com.dbs.easyhomeloan.Model.PropertyModel;
import com.dbs.easyhomeloan.R;
import com.dbs.easyhomeloan.VIewModel.PropertyViewModel;
import com.dbs.easyhomeloan.View.UI.Adapter.PropertyAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

public class DashboardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PropertyAdapter adapter;
    private PropertyViewModel model;

    boolean eligibleForLoan;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            initializeViews();

            recyclerView = findViewById(R.id.propertyRecyclerView);
            progress = findViewById(R.id.progress);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            model = ViewModelProviders.of(this).get(PropertyViewModel.class);
            adapter = new PropertyAdapter(DashboardActivity.this);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        progress.setVisibility(View.VISIBLE);
        updateUI();

        Bundle fromLogin = getIntent().getExtras();
        try {
            if (Helper.isEligible(this)) {
                Helper.saveEligible(this, false);
                if (eligibleForLoan) carouselAlert(true);
            }
            /*if (fromLogin != null && fromLogin.get("fromLogin").equals("yes")) {
                if (eligibleForLoan) carouselAlert(true);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUI() {

        try {
            String userId = Helper.getPreference(DashboardActivity.this,"UserId");
            model.getRecommendatonProperty(userId).observe(this, new Observer<PropertyModel>() {
                @Override
                public void onChanged(@Nullable PropertyModel propertyModel) {
                    progress.setVisibility(View.GONE);
                    try {
                        if (adapter != null) {
                            if (propertyModel != null) {
                                Log.e("eligibleForLoan >> ",propertyModel.getRecommended().toString());
                                eligibleForLoan = propertyModel.getRecommended();
                                adapter.setData(propertyModel.getPropertiesList());
                                if (propertyModel.getPropertiesList().isEmpty()) {
                                    Snackbar.make(progress, "Something Went wrong", Snackbar.LENGTH_SHORT).show();
                                }
                            } else {
                                Snackbar.make(progress, "Something Went wrong", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeViews() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("Dashboard");
            setSupportActionBar(toolbar);
            /*if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case android.R.id.home:
                onBackPressed();
                break;*/
            case R.id.notification_menu:
                carouselAlert(eligibleForLoan);
                break;
            case R.id.logout_menu:
                Helper.saveBool(this, false);
                Helper.saveEligible(this, false);
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onViewClicked(View view) {
        if (view.getId() == R.id.filterBtn) {
            showFilterAlert();
        }
    }

    private String price, type, area;

    private void showFilterAlert() {
        try {
            price = "";
            type = "";
            area = "";
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.filter_alert, null);
            AppCompatSpinner priceSP = dialogView.findViewById(R.id.priceSP);
            priceSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int pos = adapterView.getSelectedItemPosition();
                    switch (pos) {
                        case 0:
                            return;
                        case 1:
                            price = "1";
                            break;
                        case 2:
                            price = "2";
                            break;
                        case 4:
                            price = "3";
                            break;
                        case 5:
                            price = "4";
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            final AppCompatSpinner typeSP = dialogView.findViewById(R.id.typeSP);
            typeSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int pos = adapterView.getSelectedItemPosition();
                    switch (pos) {
                        case 0:
                            return;
                        case 1:
                            type = "1";
                            break;
                        case 2:
                            type = "2";
                            break;
                        case 3:
                            type = "3";
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            final AppCompatSpinner areaSP = dialogView.findViewById(R.id.areaSP);
            areaSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int pos = adapterView.getSelectedItemPosition();
                    switch (pos) {
                        case 0:
                            return;
                        case 1:
                            area = "1";
                            break;
                        case 2:
                            area = "2";
                            break;
                        case 3:
                            area = "3";
                            break;
                        case 4:
                            area = "4";
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            AppCompatTextView clearFilters = dialogView.findViewById(R.id.clearFilters);
            AppCompatButton filterBtn = dialogView.findViewById(R.id.filterBtn);
            dialogBuilder.setView(dialogView);
            final AlertDialog dialog = dialogBuilder.create();
            dialog.show();
            clearFilters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    updateUI();
                }
            });
            filterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("price", price);
                    jsonObject.addProperty("type", type);
                    jsonObject.addProperty("area", area);
                    model.loadRecWithFilters(jsonObject);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carouselAlert(boolean isEligible) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.carousel_alert, null);
        AppCompatImageView smiley = dialogView.findViewById(R.id.smiley);
        AppCompatTextView desc = dialogView.findViewById(R.id.desc);
        smiley.setImageResource(isEligible ? R.drawable.smiley : R.drawable.frown);
        desc.setText(isEligible ? R.string.eligible_desc : R.string.not_eligible_desc);
        AppCompatButton ok = dialogView.findViewById(R.id.ok);
        dialogBuilder.setView(dialogView);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {
            updateUI();
        }
    }
}
