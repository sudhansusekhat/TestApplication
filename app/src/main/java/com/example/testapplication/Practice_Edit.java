package com.fydo.Setting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.fydo.Config.CommonUtilities;
import com.fydo.Config.CustomEditText;
import com.fydo.Config.CustomEditText.DrawableClickListener;
import com.fydo.Config.MaskedEditText;
import com.fydo.Config.ServerAccess;
import com.fydo.Model.PracticeListDetails;
import com.fydo.R;

import org.json.JSONObject;

import butterknife.ButterKnife;

public class Practice_Edit extends AppCompatActivity {

    Activity act;

    boolean fromEdit = false;
    PracticeListDetails practiceListDetails;
    String res = "";
    CheckBox chk_active;
    EditText txtPractice_name, txtAddress_1, txtAddress_2, txtState, txtPost_code;
    CustomEditText txtSuburb;
    MaskedEditText txtPhone, txtFax;
    TextView title, lbl_pname, lbl_add1, lbl_add2, lbl_suburb, lbl_state, lbl_pc, lbl_phone, lbl_fax, lbl_status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practices_edit);

        act = Practice_Edit.this;
        ButterKnife.bind(act);
        CommonUtilities.setOrientation(act);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Practice");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().hasExtra("fromEdit")) {
            fromEdit = true;
        }

        chk_active = (CheckBox) findViewById(R.id.chk_active);
        txtAddress_1 = (EditText) findViewById(R.id.txtAddress_1);
        txtAddress_2 = (EditText) findViewById(R.id.txtAddress_2);
        txtPractice_name = (EditText) findViewById(R.id.txtPractice_name);
        txtSuburb = (CustomEditText) findViewById(R.id.txtSuburb);
        txtState = (EditText) findViewById(R.id.txtState);
        txtPost_code = (EditText) findViewById(R.id.txtPost_code);
        txtPhone = (MaskedEditText) findViewById(R.id.txtPhone);
        txtFax = (MaskedEditText) findViewById(R.id.txtFax);
        title = (TextView) findViewById(R.id.title);
        lbl_add1 = (TextView) findViewById(R.id.lbl_add1);
        lbl_add2 = (TextView) findViewById(R.id.lbl_add2);
        lbl_fax = (TextView) findViewById(R.id.lbl_fax);
        lbl_pc = (TextView) findViewById(R.id.lbl_pc);
        lbl_phone = (TextView) findViewById(R.id.lbl_phone);
        lbl_pname = (TextView) findViewById(R.id.lbl_pname);
        lbl_state = (TextView) findViewById(R.id.lbl_state);
        lbl_status = (TextView) findViewById(R.id.lbl_status);
        lbl_suburb = (TextView) findViewById(R.id.lbl_suburb);
        setFont();
txtPractice_name.requestFocus();
        if(txtPractice_name.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        if (getIntent() != null && getIntent().hasExtra("practice_details")) {

            res = getIntent().getStringExtra("practice_details");

            practiceListDetails = new PracticeListDetails().response(res);
            if (practiceListDetails.Active == true) {
                chk_active.setText("Active");
                chk_active.setTextColor(Color.parseColor("#00897B"));
                chk_active.setChecked(true);

            } else {
                chk_active.setText("Inactive");
                chk_active.setTextColor(Color.RED);
                chk_active.setChecked(false);
            }
            txtAddress_1.setText(practiceListDetails.Add1);
            txtAddress_2.setText(practiceListDetails.Add2);
            txtPost_code.setText(practiceListDetails.PC);
            txtState.setText(practiceListDetails.State);
            txtSuburb.setText(practiceListDetails.Suburb);
            txtPractice_name.setText(practiceListDetails.PN);
            title.setText(practiceListDetails.PN);
            if (practiceListDetails.Phone.equals("")) {
                txtPhone.setText("");
            }
            if (practiceListDetails.Suburb.equals("")) {
                txtSuburb.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.downarrow, 0);
            }
            if (practiceListDetails.Fax.equals("")) {
                txtFax.setText("");
            }
            if (!practiceListDetails.Phone.equals("")) {
                txtPhone.setText(CommonUtilities.getText(CommonUtilities.getPhoneNumber(practiceListDetails.Phone, 1)));
            }
            if (!practiceListDetails.Fax.equals("")) {
                {
                    txtFax.setText(CommonUtilities.getText(CommonUtilities.getPhoneNumber(practiceListDetails.Fax, 1)));
                }
            } else {


            }


        } else {
            txtPhone.setText("");
            txtSuburb.setText("");
            txtFax.setText("");
            txtAddress_1.setText("");
            txtAddress_2.setText("");
            txtPost_code.setText("");
            txtState.setText("");
            chk_active.setChecked(true);
            chk_active.setText("Active");
            title.setText("NEW PRACTICE BEING CREATED");
            title.setTextColor(Color.RED);
            txtSuburb.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.downarrow, 0);


        }

 txtSuburb.setOnFocusChangeListener(new View.OnFocusChangeListener() {
     @Override
     public void onFocusChange(View v, boolean hasFocus) {
         if(hasFocus)
         {
             Intent i = new Intent(Practice_Edit.this, Suburb_Activity.class);
             startActivityForResult(i, 100);
         }

     }
 });
        chk_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chk_active.setText("Active");
                    chk_active.setTextColor(Color.parseColor("#00897B"));
                } else {
                    chk_active.setText("Inactive");
                    chk_active.setTextColor(Color.RED);
                }
            }
        });

        txtSuburb.setDrawableClickListener(new DrawableClickListener() {


            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        txtSuburb.setText("");
                        txtState.setText("");
                        txtPost_code.setText("");
                        txtSuburb.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.downarrow, 0);
                        break;

                    default:
                        break;
                }
            }

        });
        txtSuburb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Practice_Edit.this, Suburb_Activity.class);
                startActivityForResult(i, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            txtSuburb.setText(data.getStringExtra("newSuburb_name"));
            txtState.setText(data.getStringExtra("newSuburb_state"));
            txtPost_code.setText(data.getStringExtra("newSuburb_pc"));

        } else if (resultCode == Activity.RESULT_CANCELED) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        menu.findItem(R.id.action_Save).setVisible(true);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_Save) {
            if (txtPractice_name.getText().toString().trim().length() == 0) {
                CommonUtilities.ShowCustomToast(act, "Please enter a Practice Name", false);
            } else {
                call_add_update_practice_api(true);

            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void OnBack(View view) {
        finish();
    }

    private void call_add_update_practice_api(boolean isDialogShow) {
        try {
            JSONObject params = new JSONObject();
            if (res.equals("")) {
                params.put("PracticeID", 0);

            } else {
                params.put("PracticeID", Long.parseLong(String.valueOf(practiceListDetails.ID)));

            }
            params.put("Name", txtPractice_name.getText().toString());
            params.put("Add1", txtAddress_1.getText().toString());
            params.put("Add2", txtAddress_2.getText().toString());
            params.put("Suburb", txtSuburb.getText().toString());
            params.put("State", txtState.getText().toString());
            params.put("PostCode", txtPost_code.getText().toString());
            params.put("Phone", txtPhone.getText().toString().replaceAll("\\D+", ""));
            params.put("Fax", txtFax.getText().toString().replaceAll("\\D+", ""));
            params.put("IsActive", chk_active.isChecked());
            params.put("UserId", CommonUtilities.getPreference(act, CommonUtilities.Pref_User_Id));

            ServerAccess.getResponse(act, "Practice.svc/AddUpdatePractice", "AddUpdatePractice", params, isDialogShow, new ServerAccess.VolleyCallback() {
                @Override
                public void onSuccess(String result) {

                    PracticeListDetails res1 = new PracticeListDetails().response(result);

                    if (res1.RS == 1) {
                        CommonUtilities.ShowCustomToast(act, res1.Msg, true);
                        if (getIntent() != null && getIntent().hasExtra("practice_details")) {
                            Intent intent = new Intent();
                            intent.putExtra("practice_details", result);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        } else if (fromEdit) {
                            Intent intent = new Intent();
                            intent.putExtra("practiceDetails", result);
                            setResult(2, intent);
                            finish();
                        } else {
                            Intent intent = new Intent();
                            intent.putExtra("practice_details", result);
                            setResult(400, intent);
                            finish();
                        }
                    } else {
                        CommonUtilities.ShowCustomToast(act, res1.Msg, false);
                    }

                }

                @Override
                public void onError(String error) {
                }
            });
        } catch (Exception e) {
        }

    }


    public void setFont() {


        CommonUtilities.setFontFamily(act, txtAddress_1, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, txtAddress_2, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, txtFax, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, txtState, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, txtPractice_name, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, chk_active, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, txtPhone, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, title, CommonUtilities.AvenirNextLTPro_Regular);


        CommonUtilities.setFontFamily(act, lbl_add1, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lbl_add2, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lbl_fax, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lbl_pc, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lbl_phone, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lbl_state, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lbl_pname, CommonUtilities.AvenirNextLTPro_Regular);

    }

}