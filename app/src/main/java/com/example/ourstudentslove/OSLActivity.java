package com.example.ourstudentslove;

import androidx.appcompat.app.ActionBar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class OSLActivity extends AppCompatActivity{

    private String TAG = OSLActivity.class.getSimpleName();
    private ListView lv;

    ArrayList<HashMap<String, String>> placesList = new ArrayList<>();
    Integer images[] = {R.drawable.eat4less,R.drawable.mcdonalds,R.drawable.fathippo,
            R.drawable.purplebear,R.drawable.bryon,R.drawable.catpawcino,
            R.drawable.dogandscone,R.drawable.quilliambrothers,R.drawable.shijo,
            R.drawable.smashburgers,R.drawable.yosushi,R.drawable.gp,
            R.drawable.rvi,R.drawable.superdrug,R.drawable.boots,
            R.drawable.trainstation,R.drawable.metro,R.drawable.tesco,R.drawable.eldonsquare,
            R.drawable.grainger,R.drawable.sainsburys,R.drawable.gate,R.drawable.o2,
            R.drawable.marketshaker,R.drawable.l7,R.drawable.escape,R.drawable.ghettogolf,
            R.drawable.easygym,R.drawable.puregym,R.drawable.thegym,R.drawable.nusu,
            R.drawable.kingsgate};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osl);
        
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        lv = (ListView) findViewById(R.id.idListView);

        new GetPlaces().execute();
    }

    private class GetPlaces extends AsyncTask<Void, Void, Void> { // previous OurStudentsLove

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Json Data is downloading");

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

          
            String url = "https://usbplaces.ew.r.appspot.com/places";
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                   
                    JSONArray places = new JSONArray(jsonStr);

                    // looping through All places
                    for (int i = 0; i < places.length(); i++) {
                        JSONObject c = places.getJSONObject(i);
                        String placeId = c.getString("placeId");
                        String name = c.getString("name");
                        String descr = c.getString("descr");
                        // tmp hash map for single place
                        HashMap<String, String> place = new HashMap<>();
                        // adding fields of place to HashMap key => value
                        place.put("id", placeId);
                        place.put("name", name);
                        place.put("descr", descr);
                        place.put("image",Integer.toString(images[i]));
                        placesList.add(place);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(OSLActivity.this, placesList,
                    R.layout.listview_items, new String[]{ "name","descr","image"},
                    new int[]{R.id.name, R.id.descr,R.id.imageDisplay});
            lv.setAdapter(adapter);
        }
    }
}
