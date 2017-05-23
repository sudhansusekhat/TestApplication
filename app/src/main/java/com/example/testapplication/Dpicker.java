package com.fydo.Config;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fydo.R;

import java.util.Calendar;

public class Dpicker extends Dialog implements OnClickListener {
    private final String format = "EEE, dd MMM yyyy";
    public String selectedDay, selectedMonth, selectedYear;
    public DateCallBack dateCallBack;
    Button btnOk, btnCancel;
    DatePicker datePicker;
    LinearLayout layout;
    TextView title;
    Calendar c;
    DatePicker view;

    public Dpicker(Context context) {

        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        this.setContentView(R.layout.date_picker);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        layout = (LinearLayout) findViewById(R.id.layout);

        title = (TextView) findViewById(R.id.txttitle);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);


        datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                // TODO Auto-generated method stub
                c.set(year, monthOfYear, dayOfMonth);
                title.setText(DateFormat.format(format, c));
                dateCallBack.onDateChangedd(view, year, monthOfYear, dayOfMonth);
            }
        });


        title.setText(DateFormat.format(format, c));
        this.setCancelable(true);
        this.show();
    }

    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
        selectedDay = String.valueOf(dayOfMonth);
        selectedMonth = String.valueOf(monthOfYear);
        selectedYear = String.valueOf(year);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnOk:
                dateCallBack.onDateSelected(view, datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                this.dismiss();
                break;
            case R.id.btnCancel:
                if (!btnCancel.getText().toString().equals("Cancel"))
                    dateCallBack.onDateSelected(view, 0, 0, 0);
                this.dismiss();
                break;
            default:
                break;
        }
    }

    public void setDateCallBack(DateCallBack listener, int year, int month, int day, int flag, boolean value) {
        dateCallBack = listener;
        datePicker.updateDate(year, month, day);
        if (flag == CommonUtilities.Flag_without_Day) {
            datePicker.findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
        } else if (flag == CommonUtilities.Flag_Allow_only_Past) {
            datePicker.setMaxDate(System.currentTimeMillis() - 1000);
        }
        if (value)
            btnCancel.setText("Remove Date");
        else
            btnCancel.setText("Cancel");
    }

    public interface DateCallBack {
        public void onDateSelected(DatePicker view, int year, int month, int day);

        public void onDateChangedd(DatePicker view, int year, int month, int day);
    }
}
