package com.fydo.Setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fydo.Config.CommonUtilities;
import com.fydo.Config.ServerAccess;
import com.fydo.Config.SlidingTabLayout;
import com.fydo.Model.PracticeListDetails;
import com.fydo.Model.ReferringDoctorDetails;
import com.fydo.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Practices_Details_Activity extends AppCompatActivity {

    Activity act;

    SlidingTabLayout mSlidingTabLayout;
    ViewPager mViewPager;

    @Bind(R.id.txtPractice)
    TextView txtPractice;

    PracticeListDetails practiceListDetails;

    String res = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_practice);

        act = Practices_Details_Activity.this;
        ButterKnife.bind(act);
        CommonUtilities.setOrientation(act);

//        CommonUtilities.setFontFamily(act, txtTitle, CommonUtilities.AvenirNextLTPro_Regular);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Practice");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        if (getIntent() != null && getIntent().hasExtra("practice_details")) {

            res = getIntent().getStringExtra("practice_details");
        }
        setData();
        CommonUtilities.setFontFamily(act, txtPractice, CommonUtilities.AvenirNextLTPro_Demi);

        if (getIntent() != null && getIntent().hasExtra("practice_details")) {

            res = getIntent().getStringExtra("practice_details");
        }
        setData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        menu.findItem(R.id.action_Edit).setVisible(true);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void setData() {
        practiceListDetails = new PracticeListDetails().response(res);
        txtPractice.setText(practiceListDetails.PN);
        setUpPager();
        setUpTabColor();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_Edit) {
            editPractice();
        }
        return super.onOptionsItemSelected(item);
    }

    void setUpPager() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new ViewPagerAdapterPractice(getSupportFragmentManager(), res));
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    void setUpTabColor() {
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                // TODO Auto-generated method stub
                return Practices_Details_Activity.this.getResources().getColor(R.color.light_sky_blue);
            }

            @Override
            public int getDividerColor(int position) {
                // TODO Auto-generated method stub
                return Practices_Details_Activity.this.getResources().getColor(R.color.light_sky_blue);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    public void OnBack(View view) {
        finish();
    }

    public void editPractice() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("PID", practiceListDetails.ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerAccess.getResponse(act, "Practice.svc/PraceticeDetails", "PraceticeDetails", jsonObject, true, new ServerAccess.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                ReferringDoctorDetails response = new ReferringDoctorDetails().response(result);
                if (response.RS == 1) {
                    Intent intent = new Intent(act, Practice_Edit.class);
                    intent.putExtra("practice_details", result);
                    CommonUtilities.setPreference(act, CommonUtilities.Pref_referring_doctor_detail, result);
                    startActivityForResult(intent, 300);
                } else {
                    CommonUtilities.ShowCustomToast(act, response.Msg, false);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300) {
            if (resultCode == Activity.RESULT_OK) {
                res = data.getStringExtra("practice_details");
                setData();
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }

    }
}

class ViewPagerAdapterPractice extends FragmentStatePagerAdapter {

    public String Titles[] = {"Details", "Referring Doctors"};
    String res;

    public ViewPagerAdapterPractice(FragmentManager fm, String res) {
        super(fm);
        this.res = res;

    }


    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putString("practice_details", res);
        Fragment fragment = null;
        if (position == 0) {
            fragment = new Practices_Details_Fragment();
        } else {
            fragment = new Referring_Doctors__setting_Fragment();
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return Titles.length;
    }
}
