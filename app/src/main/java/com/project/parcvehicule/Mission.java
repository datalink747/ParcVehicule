package com.project.parcvehicule;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mission extends AppCompatActivity {

    private ArrayList<ConstParc> list_mission=new ArrayList<ConstParc>();
    private RecyclerView mRecyclerView_mission;
    private RecyclerView.Adapter mAdapter;
    private JSONArray all_mission = null;
    private CollapsingToolbarLayout collapsingtoolbarlayout;
    private SessionManager session;
    private ImageView affiche_image;
    private String id_individu;
    private FloatingActionButton fab_load;
    private SharedPreferences sharedPreferences_server;
    private String myip;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_mission);
        setSupportActionBar(toolbar);


        sharedPreferences_server = getBaseContext().getSharedPreferences("server", MODE_PRIVATE);
        myip=sharedPreferences_server.getString("ip",getString(R.string.ip_adresse));

        // Session class instance
        session = new SessionManager(Mission.this);
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        id_individu = user.get(SessionManager.KEY_NAME);

        mRecyclerView_mission = (RecyclerView)findViewById(R.id.recyclerView_mission);
        collapsingtoolbarlayout =(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_mission);
        affiche_image = (ImageView)findViewById(R.id.backgroundImageView);
        affiche_image.setImageDrawable(getDrawable(R.drawable.affiche_22));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(Mission.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        mRecyclerView_mission.setLayoutManager(mLayoutManager);
        mRecyclerView_mission.setHasFixedSize(true);

        if(list_mission.isEmpty())
        {
            new GetMission().execute();
        }
        else {
            list_mission.clear();
            new GetMission().execute();
        }


           fab_load = (FloatingActionButton) findViewById(R.id.btn_load_mission);
           fab_load.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(list_mission.isEmpty())
                   {
                       new GetMission().execute();
                   }
                   else {
                       list_mission.clear();
                       new GetMission().execute();
                   }
               }
           });

    }

    /**
     * Async task class to get json by making HTTP call
     * *//*
     */
    private class GetMission extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("cin_mission",id_individu));


            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("http://" + myip + EndPonts.url_mission, ServiceHandler.GET, params);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    all_mission = jsonObj.getJSONArray("list_mission");
                    Log.d("all mission: ", "> " + all_mission);

                    for (int i = 0; i < all_mission.length(); i++) {
                        JSONObject c = all_mission.getJSONObject(i);
                        int idmission = c.getInt("idmission");
                        String nomindividu = c.getString("nomindividu");
                        String prenomindividu = c.getString("prenomindividu");
                        String immatriculationvehicule = c.getString("immatriculationvehicule");
                        String objectifmission = c.getString("objectifmission");
                        String kmparcourumission = c.getString("kmparcourumission");
                        String qtecarburantmission = c.getString("qtecarburantmission");
                        String datereservation = c.getString("datereservation");
                        double logitude_m = c.getDouble("Logitude_m");
                        double latitude_m = c.getDouble("Latitude_m");
                        String datefinreservation = c.getString("datefinreservation");
                        String etat = c.getString("etat");


                        ConstParc item = new ConstParc();
                        item.setId_mission(idmission);
                        item.setNom_individi(nomindividu);
                        item.setPrenom_individi(prenomindividu);
                        item.setMatricule_mission(immatriculationvehicule);
                        item.setObjet_mission(objectifmission);
                        item.setKm_mission(kmparcourumission);
                        item.setQte_carburant(qtecarburantmission);
                        item.setDate_debut_mission(datereservation);
                        item.setDate_fin_mission(datefinreservation);
                        item.setEtat_mission(etat);
                        item.setLatitude_m(latitude_m);
                        item.setLongitude_m(logitude_m);

                        list_mission.add(item);
                        Log.d("all json mission 2: ", "> " + c);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            mAdapter = new RecyclerAdaptateurMission(Mission.this, list_mission);
            mRecyclerView_mission.setAdapter(mAdapter);
            collapsingtoolbarlayout.setTitle("Mes Mission ("+mAdapter.getItemCount()+")");

        }


    }//end asynctas

}
