package com.fydo.Setting;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fydo.Config.CommonUtilities;
import com.fydo.Model.PatientDetails;
import com.fydo.Model.PracticeListDetails;
import com.fydo.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class Practices_Details_Fragment extends Fragment {

    String res, address;
    PracticeListDetails practiceListDetails;
    Activity act;

    @Bind(R.id.txtID)
    TextView txtID;
    @Bind(R.id.lblId)
    TextView lblID;

    @Bind(R.id.txtAddress)
    TextView txtAddress;
    @Bind(R.id.lblAddress)
    TextView lblAddress;

    @Bind(R.id.txtPhone)
    TextView txtPhone;
    @Bind(R.id.lblPhone)
    TextView lblPhone;

    @Bind(R.id.txtFax)
    TextView txtFax;
    @Bind(R.id.lblFax)
    TextView lblFax;

    @Bind(R.id.txtStatus)
    TextView txtStatus;
    @Bind(R.id.lblStatus)
    TextView lblStatus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_practices_details, container, false);

        ButterKnife.bind(this, v);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            res = bundle.getString("practice_details");

            practiceListDetails = new PracticeListDetails().response(res);

            setFont();

            practiceListDetails = new PracticeListDetails().response(res);

            txtID.setText(CommonUtilities.getText("" + practiceListDetails.ID));

            address = CommonUtilities.getAddress(practiceListDetails.Add1, practiceListDetails.Add2, practiceListDetails.Suburb, practiceListDetails.State, practiceListDetails.PC);
            txtAddress.setText(CommonUtilities.getText(address));

            txtPhone.setText(CommonUtilities.getText(CommonUtilities.getPhoneNumber(practiceListDetails.Phone, 1)));
            txtFax.setText(CommonUtilities.getText(CommonUtilities.getPhoneNumber(practiceListDetails.Fax, 1)));

            if (practiceListDetails.Active) {
                txtStatus.setText("Active");
                txtStatus.setTextColor(Color.parseColor("#00897B"));
            } else {
                txtStatus.setText("Inactive");
                txtStatus.setTextColor(Color.RED);
            }

        }
        return v;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        act = activity;
    }

    void setFont() {

        CommonUtilities.setFontFamily(act, txtID, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, txtAddress, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, txtPhone, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, txtFax, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, txtStatus, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lblID, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lblAddress, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lblPhone, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lblFax, CommonUtilities.AvenirNextLTPro_Regular);
        CommonUtilities.setFontFamily(act, lblStatus, CommonUtilities.AvenirNextLTPro_Regular);
    }

}
