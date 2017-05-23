package com.fydo.Setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fydo.Adapter.PracticeListAdapter;
import com.fydo.Config.CommonUtilities;
import com.fydo.Config.RecyclerItemClickListener;
import com.fydo.Config.ServerAccess;
import com.fydo.Config.SimpleDividerItemDecoration;
import com.fydo.Model.PracticeList;
import com.fydo.Model.ReferringDoctorDetails;
import com.fydo.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PracticesActivity extends AppCompatActivity {

    Activity act;



    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;

    @Bind(R.id.lin_dialog)
    LinearLayout lin_dialog;

    @Bind(R.id.edt_search)
    EditText edt_search;

    @Bind(R.id.ivSearch)
    ImageView ivSearch;

    @Bind(R.id.ivClear)
    ImageView ivClear;

    PracticeListAdapter mAdapter;
    int page_index = 0, tc;
    LinearLayoutManager layoutManager;
    ArrayList<PracticeList.LstAllPracticeList> array_practiceList;
    PracticeList res;
    boolean isLoading = false;
    boolean isClear = false;
    ReferringDoctorDetails response;
    boolean fromEdit = false;
    boolean isRunning = false;
    String response1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practices_list);

        ButterKnife.bind(this);

        act = PracticesActivity.this;
        CommonUtilities.setOrientation(act);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Practices");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().hasExtra("fromEdit")) {
            if (getIntent().getExtras().getString("fromEdit").equals("1")) {
                fromEdit = true;
                toolbar.setTitle("Select Practice");
            }
        }

        array_practiceList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(act, LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        lin_dialog.setVisibility(View.GONE);

        edt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_search.setCursorVisible(true);
            }
        });

        edt_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s.toString().length() >= 1) {
                    isClear = true;
                    ivClear.setVisibility(View.VISIBLE);
                } else {
                    ivClear.setVisibility(View.GONE);
                    isClear = false;
                }
                if (s.toString().length() >= 3) {
                    page_index = 0;
                    CountDownTimer timer = new CountDownTimer(2000, 2000) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            isRunning = false;
                            call_practices_api(true, s.toString());
                        }
                    };

                    if (!isRunning) {
                        timer.start();
                        isRunning = true;
                    }
                } else if (s.toString().length() == 0) {
                    call_practices_api(true, s.toString());
                }
            }
        });

        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClear) {
                    edt_search.setText("");
                }
            }
        });

        recycler_view.addOnItemTouchListener(new RecyclerItemClickListener(act, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (fromEdit) {
                    Gson gson = new Gson();
                    String abc = gson.toJson(array_practiceList.get(position));
                    Intent intent = new Intent();
                    intent.putExtra("practiceDetails", abc);
                    setResult(2, intent);
                    finish();
                } else {

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("PID", array_practiceList.get(position).ID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ServerAccess.getResponse(act, "Practice.svc/PraceticeDetails", "PraceticeDetails", jsonObject, true, new ServerAccess.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            response = new ReferringDoctorDetails().response(result);

                            if (response.RS == 1) {
                                Intent intent = new Intent(act, Practices_Details_Activity.class);
                                intent.putExtra("practice_details", result);
                                startActivity(intent);
                            } else {
                                CommonUtilities.ShowCustomToast(act, response.Msg, false);
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                }
            }
        }));

        recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int total = layoutManager.getItemCount();
                int firstVisibleItemCount = layoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemCount = layoutManager.findLastVisibleItemPosition();

                if (!isLoading) {
                    if (total > 0)
                        if ((total - 1) == lastVisibleItemCount) {
                            if (array_practiceList.size() < tc) {
                                page_index++;
                                lin_dialog.setVisibility(View.VISIBLE);
                                isLoading = true;
                                call_practices_api(false, "");
                            }
                        } else {
                            lin_dialog.setVisibility(View.GONE);
                        }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 400) {
            call_practices_api(true, "");
        } else if (requestCode == 1 && resultCode == 2){
            Intent intent = new Intent();
            intent.putExtra("practiceDetails", data.getExtras().getString("practiceDetails"));
            setResult(2, intent);
            finish();
        }
    }


    private void call_practices_api(boolean isDialogShow, String keyword) {
        try {
            JSONObject params = new JSONObject();

            params.put("SK", keyword);
            params.put("PI", page_index);

            ServerAccess.getResponse(act, "Practice.svc/PracticeList", "PracticeList", params, isDialogShow, new ServerAccess.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    res = new PracticeList().response(result);
                    isLoading = false;
                    if (res.RS == 1) {
                        tc = res.TR;
                        if (page_index == 0) {
                            array_practiceList.clear();
                            array_practiceList.addAll(res.LstAllPracticeList);
                            mAdapter = new PracticeListAdapter(act, array_practiceList, fromEdit);
                            mAdapter.notifyDataSetChanged();
                            recycler_view.setAdapter(mAdapter);
                        } else {
                            array_practiceList.addAll(res.LstAllPracticeList);
                            mAdapter.notifyDataSetChanged();
                            lin_dialog.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onError(String error) {

                }
            });

        } catch (Exception e) {
        }

    }

    public void addDoctor() {
        Intent intent = new Intent(act, Practice_Edit.class);
        if (fromEdit){
            intent.putExtra("fromEdit", "1");
            startActivityForResult(intent, 1);
        } else
            startActivityForResult(intent,400);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        menu.findItem(R.id.action_add).setVisible(true);
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
        } else if (id == R.id.action_add) {
            addDoctor();
        }
        return super.onOptionsItemSelected(item);
    }

    public void OnBack(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        page_index = 0;
        call_practices_api(true, edt_search.getText().toString());
        edt_search.setCursorVisible(false);

    }
}
