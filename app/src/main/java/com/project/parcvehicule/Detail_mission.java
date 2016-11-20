package com.project.parcvehicule;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Detail_mission extends AppCompatActivity {
    private Intent intent_recu;
    private ConstParc const1;
    private TextView nom_prenom,immat,objet,km,qtcar,date_debut,date_fin;
    private AppCompatButton gotomap;
    private Switch btn_confirme;
    private ImageView affiche_image;
    private boolean is_enable=false;
    private FloatingActionButton switch_confirme;
    //private JSONArray update_etat = null;
    private String msg,id_individu,valeur_etat;
    private SessionManager session;
    private CollapsingToolbarLayout collapsingtoolbarlayout;
    private SharedPreferences sharedPreferences_server;
    private String myip;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mission);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detai_intervention);
        setSupportActionBar(toolbar);

        sharedPreferences_server = getBaseContext().getSharedPreferences("server", MODE_PRIVATE);
        myip=sharedPreferences_server.getString("ip",getString(R.string.ip_adresse));

        // Session class instance
        session = new SessionManager(Detail_mission.this);
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        id_individu = user.get(SessionManager.KEY_NAME);

        nom_prenom = (TextView)findViewById(R.id.text_name_agent);
        immat = (TextView)findViewById(R.id.text_imat_agent);
        objet = (TextView)findViewById(R.id.text_objectif_agent);
        km = (TextView)findViewById(R.id.text_km_mission);
        qtcar = (TextView)findViewById(R.id.text_qtecarburant_mission);
        date_debut = (TextView)findViewById(R.id.text_date_debut_mission);
        date_fin = (TextView)findViewById(R.id.text_date_fin_mission);
        gotomap = (AppCompatButton)findViewById(R.id.map_mission);
        btn_confirme = (Switch)findViewById(R.id.switch_etat_mission);
        switch_confirme = (FloatingActionButton)findViewById(R.id.confirmation_mission);
        collapsingtoolbarlayout =(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_detai_mission);

        affiche_image = (ImageView)findViewById(R.id.backgroundImageView2);
        affiche_image.setImageDrawable(getDrawable(R.drawable.affiche_11));

        intent_recu=getIntent();
        const1= (ConstParc) intent_recu.getParcelableExtra("pos");
        System.out.println("data recu :" + const1.getObjet_mission());

        nom_prenom.setText(const1.getNom_individi()+" "+const1.getPrenom_individi());
        immat.setText(const1.getMatricule_mission());
        objet.setText(const1.getObjet_mission());
        km.setText(const1.getKm_mission()+" Km");
        qtcar.setText(const1.getQte_carburant()+" L");
        date_debut.setText(const1.getDate_debut_mission());
        date_fin.setText(const1.getDate_fin_mission());
        collapsingtoolbarlayout.setTitle(const1.getObjet_mission());
       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        gotomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // launchGoogleMaps(Detail_mission.this, const1.getLatitude_m(), const1.getLongitude_m(), const1.getObjet_mission());
                Intent gotomap = new Intent(Detail_mission.this,Map_Activity.class);
                gotomap.putExtra("lattitude",const1.getLatitude_m());
                gotomap.putExtra("longitude",const1.getLongitude_m());
                gotomap.putExtra("objet",const1.getObjet_mission());
                startActivity(gotomap);
            }
        });

        if( ! const1.getEtat_mission().isEmpty())
        {
            btn_confirme.setChecked(true);
        }
        else {
            btn_confirme.setChecked(false);
        }


        btn_confirme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_enable=isChecked;
            }
        });

        switch_confirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_etat_confirme();
            }
        });



    }

    private void show_etat_confirme() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Detail_mission.this);
        builder.setTitle(getString(R.string.titre_app));
        builder.setMessage("Êtes-vous sûr ?");

        String positiveText = "Oui";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        if(is_enable)
                        {
                            Toast.makeText(Detail_mission.this,"Mission bien effectuer",Toast.LENGTH_SHORT).show();
                            valeur_etat="valider";
                            btn_confirme.setChecked(true);
                            new Update_mission().execute();
                        }
                        else
                        {
                            Toast.makeText(Detail_mission.this,"Mission n'est pas effectuer",Toast.LENGTH_SHORT).show();
                            valeur_etat="";
                            btn_confirme.setChecked(false);
                            new Update_mission().execute();
                        }

                    }
                });

        String negativeText = "Non";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }


    private class Update_mission extends AsyncTask<Void, Void, Void> {

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
            params.add(new BasicNameValuePair("etat_mission",valeur_etat));
            params.add(new BasicNameValuePair("id_mission",String.valueOf(const1.getId_mission())));

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("http://" + myip + EndPonts.url_update_mission, ServiceHandler.GET, params);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    msg=  jsonObj.getString("message");
                    Log.e("msg", msg);

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

            //  Toast.makeText(Detail_intervention.this,msg,Toast.LENGTH_SHORT).show();


        }


    }//end asynctas

    public static void launchGoogleMaps(Context context, double latitude, double longitude, String label) {
        String format = "geo:0,0?q=" + Double.toString(latitude) + "," + Double.toString(longitude) + "(" + label + ")";
        Uri uri = Uri.parse(format);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

}
