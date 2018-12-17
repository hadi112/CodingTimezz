package com.hadi.codingtimezz;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVcontest;
    private Adaptercontest mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    boolean connected = false;
    String abc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swifeRefresh);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncFetch().execute();
            }
        });
        new AsyncFetch().execute();
    }
    private class AsyncFetch extends AsyncTask<String, Void, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\t Updating Contests...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL("https://www.stopstalk.com/contests.json");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }
            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    Log.d("bbbvbxbv","fj"+result.toString());
                    abc=result.toString();
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }
        @Override
        protected void onPostExecute(String result) {
            //this method will be running on UI thread
            pdLoading.dismiss();
            List<Datalist> data = new ArrayList<>();
            JSONArray jArray ;
            JSONObject jsonObject;

            try {
                jsonObject=new JSONObject(result);
                jArray=jsonObject.getJSONArray("upcoming");

                // Extract data from json and store into ArrayList as class objects
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Datalist listData = new Datalist();
                    listData.setName(json_data.getString("Name"));
                    listData.setUrls(json_data.getString("url"));
                    listData.setPlatform(json_data.getString("Platform"));
                    listData.setStartTime("Starts at:"+json_data.getString("StartTime"));
                    listData.setDuration("Duration :"+json_data.getString("Duration"));
                    listData.setEndTime(json_data.getString("EndTime"));
                    data.add(listData);

                }
                Log.e("AdapterContext", " " + data);
                mRVcontest = (RecyclerView) findViewById(R.id.contestList);
                LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                mRVcontest.setLayoutManager(llm);
                mRVcontest.setHasFixedSize(true);
                mRVcontest.setItemViewCacheSize(50);
                mRVcontest.setDrawingCacheEnabled(true);
                mRVcontest.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                mAdapter = new Adaptercontest(MainActivity.this, data);
                mRVcontest.setAdapter(mAdapter);
                mSwipeRefreshLayout.setRefreshing(false);
                runAnimation(mRVcontest,0);
            }
            catch (JSONException e) {
                Toast.makeText(MainActivity.this, "No Internet Connection!!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                mSwipeRefreshLayout.setRefreshing(false);
            }
            pdLoading.dismiss();
        }
    }

    private void runAnimation(RecyclerView mRVcontest,int type)
    {
        final Context context = mRVcontest.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right);
        mRVcontest.setLayoutAnimation(controller);
        mRVcontest.getAdapter().notifyDataSetChanged();
        mRVcontest.scheduleLayoutAnimation();
    }

    public void getData(String result) throws JSONException {
    }
}