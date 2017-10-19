package info.androidhive.jsonparsing;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

//import static android.R.attr.action;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override

        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

//            // Making a request to url and getting response
            String url = "https://api.sportradar.us/soccer-t3/intl/en/tournaments/sr:tournament:755/schedule.json?api_key=b5ynmx3z2e9k8tqaae3h5c8t";
//            String url = "https://api.sportradar.us/soccer-xt3/eu/en/schedules/2016-08-18/results.xml?api_key=={b5ynmx3z2e9k8tqaae3h5c8t}";
            String jsonStr = sh.makeServiceCall(url);
            //String jsonStr1 = sh.makeServiceCall(url);


            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String country = "";
                    String country1 = "";
                    String country_code="";
                    String name="";
                    // Getting JSON Array node
                    JSONArray sport_events = jsonObj.getJSONArray("sport_events");


                    //for cmptators
                 //   JSONObject jsonObj1 = new JSONObject(jsonStr1);

                    // Getting JSON Array node
                   // JSONArray competitors = jsonObj1.getJSONArray("competitors");


                    // looping through All Contacts
                    for (int i = 0; i < sport_events.length(); i++) {
                        JSONObject c = sport_events.getJSONObject(i);
                        JSONArray competitors = c.getJSONArray("competitors");

                        for (int j = 0; j <competitors.length(); j++)
                        {
                            JSONObject comp = competitors.getJSONObject(j);
                            country = comp.optString("country");
                            name = comp.optString("name");
                            //country_code = comp.optString("country_code");


                    }
                        for (int k = 0; k <competitors.length(); k++) {
                            JSONObject comp1 = competitors.getJSONObject(k);
                            name = comp1.optString("name");
                        }

                            // country1 = comp.optString("country1");




//                        for (int j=0; j<=1;j++){
//                            JSONObject comp  = competitors.getJSONObject(j);
//                        }

                        String id = c.optString("id");
                        String competitionId = c.optString("competitionId");
                        String scheduled = c.optString("scheduled");
                        String matchdaye = c.optString("matchday");


                        String homeTeamId = c.optString("homeTeamId");
                        //String awayTeamName = c.optString("awayTeamName");
                        String awayTeamId = c.optString("awayTeamId");


                        //String gender = c.getString("gender");

                        /* Tournament node is JSON Objectc*/
                      JSONObject tournament = c.getJSONObject("tournament");
                       String name1 = tournament.optString("name");
                         /* competitors node is JSON Objectc*/
                        //JSONObject competitors = c.getJSONObject("competitors");
                        //String country = competitors.optString("country");
                      // JSONArray competitors = c.getJSONArray("competitors");
                        //String country = competitors.optString("country");
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value

                        contact.put("id", id);
                        contact.put("competitionId", competitionId);
                        contact.put("scheduled", scheduled);
                        contact.put("matchday", matchdaye);
                        contact.put("country", country);
                        contact.put("country1", country1);
                        contact.put("country_code", country_code);
                        contact.put("name",name);



                        contact.put("homeTeamId", homeTeamId);
                        contact.put("name", name1);
                        contact.put("awayTeamId", awayTeamId);

//                        contact.put("goalsHomeTeam", goalsHomeTeam);
//                        contact.put("goalsAwayTeam", goalsAwayTeam);

//                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // Getting JSON Array node
//                        JSONArray sport_events = jsonObj.getJSONArray("sport_events");

//                        JSONArray competitors = jsonObj.getJSONArray("competitors");
//
//                        for(int j = 0; j <1; j++){
//
//                            JSONObject a = competitors.getJSONObject(j);
//                            String country = a.optString("country");
//                            HashMap<String, String> f = new HashMap<>();
//                            f.put("country", country);
//                            contactList.add(f);
//
//                        }

                        // adding contact to contact list
                        contactList.add(contact);

                        // Getting JSON Array node




                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
//                if (jsonStr1 != null){
//                    try {
//                        JSONObject jsonObj = new JSONObject(jsonStr1);
//
//                        // Getting JSON Array node
//                        JSONArray competitors = jsonObj.getJSONArray("competitors");
//                        for(int j = 0; j <1; j++){
//
//                            JSONObject a = competitors.getJSONObject(j);
//                            String country = a.optString("country");
//                            HashMap<String, String> f = new HashMap<>();
//                            f.put("country", country);
//                            contactList.add(f);
//
//                        }
//                    }
//                    catch (final JSONException e){
//                        Log.e(TAG, "Json parsing error: " + e.getMessage());
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getApplicationContext(),
//                                        "Json parsing error: " + e.getMessage(),
//                                        Toast.LENGTH_LONG)
//                                        .show();
//                            }
//                        });
//
//                    }
//
//                }
//                try {
//                    JSONObject jsonObj1 = new JSONObject(jsonStr1);
//
//                    // Getting JSON Array node
//                    JSONArray competitors = jsonObj1.getJSONArray("competitors");
//
//                    // looping through All Contacts
//                    for (int j = 0; j < competitors.length(); j++) {
//                        JSONObject a = competitors.getJSONObject(j);
//                        String name = a.optString("name");
//                        HashMap<String, String> cname = new HashMap<>();
//                        cname.put("name", name);
//                        contactList.add(cname);
//                    }
//                }
//                catch (final  JSONException e) {
//                    Log.e(TAG, "Json parsing error: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG)
//                                    .show();
//                        }
//                    });
//                }
            }
                    else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, contactList,
                    R.layout.list_item, new String[]{"id", "competitionId",
                    "scheduled","matchday", "country","country1","homeTeamId", "name","name","awayTeamId","country_code"}, new int[]{R.id.id,
                    R.id.competitionId,R.id.scheduled,R.id.country,R.id.country1,R.id.matchday,R.id.homeTeamId,R.id.name1,R.id.awayTeamId,R.id.country_code,R.id.name});



            lv.setAdapter(adapter);
        }

    }
}
