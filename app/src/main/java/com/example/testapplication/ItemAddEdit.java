package com.fydo.Setting;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fydo.Config.CommonUtilities;
import com.fydo.Config.ServerAccess;
import com.fydo.Model.ItemDetails;
import com.fydo.R;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sudhansu on 4/20/2017.
 */

public class ItemAddEdit extends AppCompatActivity {

    @Bind(R.id.no_ide)
    TextView noIde;

    @Bind(R.id.lblNumber)
    TextView lblNumber;

    @Bind(R.id.number)
    EditText number;

    @Bind(R.id.lblProcedure)
    TextView lblProcedure;

    @Bind(R.id.Procedure)
    EditText procedure;

    @Bind(R.id.lblCategory)
    TextView lblCategory;

    @Bind(R.id.Category)
    TextView category;

    @Bind(R.id.lblGroup)
    TextView lblGroup;

    @Bind(R.id.Group)
    TextView group;

    @Bind(R.id.lblSubGroup)
    TextView lblSubGroup;

    @Bind(R.id.SubGroup)
    TextView subGroup;

    @Bind(R.id.lblStatus)
    TextView lblStatus;

    @Bind(R.id.name_layout)
    LinearLayout name_layout;

    Activity act;

    @Bind(R.id.edStatus)
    TextView status;

    @Bind(R.id.cbStatus)
    CheckBox cbStatus;

    String res = "";
    ItemDetails response;
    boolean active = true;
    String ACT = "0";

    String onsave = "done";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_item);

        act = ItemAddEdit.this;
        ButterKnife.bind(act);
        CommonUtilities.setOrientation(act);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Items");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        procedure.setSelection(procedure.getText().length());

        noIde.setTextColor(Color.RED);
        noIde.setBackgroundColor(Color.parseColor("#F4F4F4"));

        if (getIntent().hasExtra("fromDetails") && !getIntent().getExtras().getString("fromDetails").equals("")) {
            if (getIntent().getExtras().getString("fromDetails").equals("yes")) {

                if (!CommonUtilities.getPreference(act, CommonUtilities.Pref_items_detail).equals("")) {
                    res = CommonUtilities.getPreference(act, CommonUtilities.Pref_items_detail);
                }
                response = new ItemDetails().response(res);
                setDetails();
            } else {
                ACT = "0";
                number.setCursorVisible(true);
                number.setEnabled(true);
                procedure.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            cehckExistItem(true);
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(procedure, InputMethodManager.SHOW_FORCED);
                        }
                    }
                });
            }
        }

        cbStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbStatus.isChecked()) {
                    active = true;
                    status.setTextColor(Color.parseColor("#00897B"));
                    status.setText("Active");
                } else {
                    active = false;
                    status.setTextColor(Color.RED);
                    status.setText("Inactive");
                }
            }
        });

        setFont();
    }

    private void setFont() {
        CommonUtilities.setFontFamily(act, noIde, CommonUtilities.AvenirNextLTPro_Demi);
        CommonUtilities.setFontFamily(act, lblNumber, CommonUtilities.AvenirNextLTPro_Regular);
//        CommonUtilities.setFontFamily(act, txtTitle, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, status, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lblStatus, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lblSubGroup, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, group, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lblGroup, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, category, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lblCategory, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, procedure, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lblProcedure, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, number, CommonUtilities.AvenirNextLTPro_Regular);
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
            if (number.getText().toString().equals("")) {
                CommonUtilities.ShowCustomToast(act, "Please Enter a Number", false);
            } else if (procedure.getText().toString().equals("")) {
                CommonUtilities.ShowCustomToast(act, "Please Enter a Procedure Name", false);
            } else {
                if (onsave.equals("ndone")) {
                    cehckExistItem(true);
                } else {
                    call_add_item(true);
                }

            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void call_add_item(boolean isDialogShow) {
        try {
            JSONObject params = new JSONObject();

            params.put("ICC", number.getText().toString());
            params.put("IDE", procedure.getText().toString());
            params.put("ICA", category.getText().toString());
            params.put("IGR", group.getText().toString());
            params.put("ISU", subGroup.getText().toString());
            params.put("IST", active);
            params.put("ACT", ACT);

            ServerAccess.getResponse(act, "Items.svc/AddUpdateItem", "AddUpdateItem", params, isDialogShow, new ServerAccess.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    response = new ItemDetails().response(result);
                    if (response.RS == 1) {
                        if (!ACT.equals(0)) {
                            CommonUtilities.setPreference(act, CommonUtilities.Pref_items_detail, result);
                        }
                    }
                    CommonUtilities.ShowCustomToast(act, response.Msg, true);

                    finish();
                }

                @Override
                public void onError(String error) {

                }
            });

        } catch (Exception e) {
        }

    }

    private void cehckExistItem(boolean isDialogShow) {
        try {
            JSONObject params = new JSONObject();

            params.put("ICC", number.getText().toString());
//            params.put("ACT", "0");

            ServerAccess.getResponse(act, "Items.svc/CheckItemExists", "CheckItemExists", params, isDialogShow, new ServerAccess.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    response = new ItemDetails().response(result);
                    if (response.RS == 2) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(act);
                        builder1.setTitle(R.string.app_name);
                        builder1.setIcon(R.mipmap.app_icon);
                        builder1.setMessage(response.Msg);
                        builder1.setCancelable(false);
                        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                onsave = "done";
                                setDetails();
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(procedure, InputMethodManager.SHOW_IMPLICIT);
                            }
                        });
                        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                onsave = "ndone";
                                dialog.cancel();
                                number.requestFocus();
                            }
                        });
                        builder1.show();
                    } else {

                    }

                }

                @Override
                public void onError(String error) {

                }
            });

        } catch (Exception e) {
        }

    }

    public void setDetails() {
        noIde.setText(response.ICC + " - " + response.IDE);
        noIde.setTextColor(getResources().getColor(R.color.light_black));
        noIde.setBackgroundColor(0);
        name_layout.setBackgroundColor(Color.parseColor("#EEEEEE"));
        number.setCursorVisible(false);
        number.setText(response.ICC + "");
        number.setEnabled(false);
        procedure.setCursorVisible(true);
        procedure.requestFocus();
        procedure.setText(response.IDE);
        category.setText(response.ICA);
        group.setText(response.IGR);
        subGroup.setText(response.ISU);
        procedure.setSelection(procedure.getText().length());
        active = response.IST;

        if (response.IST == true) {
            status.setText("Active");
            status.setTextColor(Color.parseColor("#00897B"));
            cbStatus.setChecked(true);
        } else {
            status.setText("Inactive");
            cbStatus.setChecked(false);
            status.setTextColor(Color.RED);
        }
        ACT = "1";
        noIde.setTextColor(Color.BLACK);
    }

}
