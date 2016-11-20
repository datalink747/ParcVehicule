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

public class Intervention extends AppCompatActivity {
    private ArrayList<ConstParc> list_intervention=new ArrayList<ConstParc>();
    private RecyclerView mRecyclerView_mission;
    private RecyclerView.Adapter mAdapter;
    private JSONArray all_intervention = null;
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
        setContentView(R.layout.activity_intervention);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_intervention);
        setSupportActionBar(toolbar);

        sharedPreferences_server = getBaseContext().getSharedPreferences("server", MODE_PRIVATE);
        myip=sharedPreferences_server.getString("ip",getString(R.string.ip_adresse));

        // Session class instance
        session = new SessionManager(Intervention.this);
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        id_individu = user.get(SessionManager.KEY_NAME);

        mRecyclerView_mission = (RecyclerView)findViewById(R.id.recyclerView_intervention);
        collapsingtoolbarlayout =(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_intervention);
        affiche_image = (ImageView)findViewById(R.id.backgroundImageView);
        affiche_image.setImageDrawable(getDrawable(R.drawable.affiche_33));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(Intervention.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        mRecyclerView_mission.setLayoutManager(mLayoutManager);
        mRecyclerView_mission.setHasFixedSize(true);

        if(list_intervention.isEmpty())
        {
            new GetIntervention().execute();
        }
        else {
            list_intervention.clear();
           new GetIntervention().execute();
        }
        fab_load = (FloatingActionButton) findViewById(R.id.btn_load_intervention);
        fab_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list_intervention.isEmpty())
                {
                    new GetIntervention().execute();
                }
                else {
                    list_intervention.clear();
                    new GetIntervention().execute();
                }
            }
        });

    }

    /**
     * Async task class to get json by making HTTP call
     * *//*
     */
    private class GetIntervention extends AsyncTask<Void, Void, Void> {

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
            String jsonStr = sh.makeServiceCall("http://" + myip + EndPonts.url_intervention, ServiceHandler.GET, params);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    all_intervention = jsonObj.getJSONArray("list_intervention");
                    Log.d("all intervention: ", "> " + all_intervention);

                    for (int i = 0; i < all_intervention.length(); i++) {
                        JSONObject c = all_intervention.getJSONObject(i);
                        int idintervention = c.getInt("idintervention");
                        String nomindividu = c.getString("nomindividu");
                        String prenomindividu = c.getString("prenomindividu");
                        String immatriculationvehicule = c.getString("immatriculationvehicule");
                        String libellepanne = c.getString("libellepanne");
                        String dureeintervention = c.getString("dureeintervention");
                        String compterenduintervention = c.getString("compterenduintervention");
                        String dateprobintervention = c.getString("dateprobintervention");
                        double logitude_m = c.getDouble("Logitude_m");
                        double latitude_m = c.getDouble("Latitude_m");
                        String dateintervention = c.getString("dateintervention");
                        String etat = c.getString("etat");


                        ConstParc item = new ConstParc();
                        item.setId_intervention(idintervention);
                        item.setNom_individi(nomindividu);
                        item.setPrenom_individi(prenomindividu);
                        item.setMatricule_mission(immatriculationvehicule);
                        item.setProbleme_intervention(libellepanne);
                        item.setDuree_intervention(dureeintervention);
                        item.setCompterendu_intervention(compterenduintervention);
                        item.setDate_probléme(dateprobintervention);
                        item.setDate_intervention(dateintervention);
                        item.setLatitude_m(latitude_m);
                        item.setLongitude_m(logitude_m);
                        item.setEtat_intervention(etat);

                        list_intervention.add(item);
                        Log.d("all json interv 2: ", "> " + c);

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


            mAdapter = new RecyclerAdaptateurIntervention(Intervention.this, list_intervention);
            mRecyclerView_mission.setAdapter(mAdapter);
            collapsingtoolbarlayout.setTitle("Mes Intervention ("+mAdapter.getItemCount()+")");

        }


    }//end asynctas

}
