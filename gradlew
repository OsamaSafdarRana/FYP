package com.example.darazapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    Fragment1 f1;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        f1 = (Fragment1) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        f1.b.setVisibility(View.INVISIBLE);
        ListView lview = (ListView) findViewById(R.id.list);
        Intent i = getIntent();
        if (i.getStringExtra("test")==null) {
            String num = i.getStringExtra("num");
            String frg = i.getStringExtra("frg");
            f1.SetData(frg);
            populateList("http://ranashop.000webhostapp.com/API/orders.php?num="+num,frg);
        }else{
            final Object orderList = ((ObjectWrapperForBinder)i.getExtras().getBinder("oList")).getData();
            String frg = i.getStringExtra("frg");
            f1.SetData(frg);
            listviewAdapter adapter = new listviewAdapter(this, (ArrayList<model>) orderList);
            lview.setAdapter(adapter);
        }
    }
    private void populateList(final String urlWebService, final String frg) {
        class PopulateList extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                ArrayList<model> arr = loadIntoListView(s);
//                final Object objSent = new Object();
                final Bundle bundle = new Bundle();
                bundle.putBinder("oList", new ObjectWrapperForBinder(arr));
                Intent i = new Intent(Main3Activity.this,Main3Activity.class);
                i.putExtra("test","pass");
                i.putExtra("frg",frg);
                i.putExtras(bundle);
                startActivity(i);
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return null;
                }
            }
        }
        PopulateList p = new PopulateList();
        p.execute();
    }
    private ArrayList<model> loadIntoListView(String json) {
        ArrayList<model> orderList = new ArrayList<model>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            orderList.add(new model("ID","Product","Customer","Date","Qty","Price","Amount","Mobile"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                orderList.add(new model(obj.getString("Order_id"),obj.getString("Pro_Name"),obj.getString("Cus_Name"),obj.getString("Date"),obj.getString("Quantity"),obj.getString("Price"),obj.getString("Amount"),obj.getString("Cus_Mobile")));
            }
        }catch (Exception e) {
        }
        return orderList;
    }

    public class ObjectWrapperForBinder extends Binder {

        private final Object mData;

        public ObjectWrapperForBinder(Object data) {
            mData = data;
        }

        public Object getData() {
            return mData;
        }
    }
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          