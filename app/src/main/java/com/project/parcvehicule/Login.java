package com.project.parcvehicule;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    private EditText edit_cin,edit_prenom;
    private Button btn_login;
    private TextInputLayout input_layout_cin, input_layout_prenom;
    private static String TAG = Login.class.getSimpleName();
    private ArrayList<ConstParc> list_parc=new ArrayList<ConstParc>();
    private Switch switch_btn;
    private SessionManager session;
    private boolean get_session=false;
    private SharedPreferences sharedPreferences,sharedPreferences_server;
    private TextView connect_server;
    private final Context context = this;
    private String myip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Session Manager
        session = new SessionManager(Login.this);
        sharedPreferences = getBaseContext().getSharedPreferences("parc", MODE_PRIVATE);
        //save ip adresse
        sharedPreferences_server = getBaseContext().getSharedPreferences("server", MODE_PRIVATE);
        myip=sharedPreferences_server.getString("ip",getString(R.string.ip_adresse));

        edit_cin =(EditText)findViewById(R.id.edit_cin);
        edit_prenom =(EditText)findViewById(R.id.edit_prenom);
        input_layout_cin =(TextInputLayout)findViewById(R.id.input_layout_cin);
        input_layout_prenom =(TextInputLayout)findViewById(R.id.input_layout_prenom);
        switch_btn = (Switch)findViewById(R.id.switchsession);
        btn_login =(Button)findViewById(R.id.btn_login);
        connect_server = (TextView)findViewById(R.id.text_server);

        connect_server.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
// custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.config_server);
                dialog.setTitle(getString(R.string.titre_app));
                Button ajouter = (Button) dialog.findViewById(R.id.btn_server_add);
                Button annuler = (Button) dialog.findViewById(R.id.btn_server_no);
                final EditText ipserver = (EditText)dialog.findViewById(R.id.edit_server);
                ipserver.setText(myip);

                ajouter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ip =ipserver.getText().toString().trim();
                        sharedPreferences_server
                                .edit()
                                .putString("ip", ip)
                                .apply();
                        Login.this.finish();
                        Intent restart =new Intent(Login.this,Start.class);
                        startActivity(restart);

                    }
                });

                annuler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    dialog.dismiss();
                    }
                });

                dialog.show();
                return true;

            }
        });

        if(sharedPreferences.contains("cin"))
        {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitForm();
                String username = edit_prenom.getText().toString();
                String password = edit_cin.getText().toString();

                for (ConstParc str : list_parc) {

                    if(username.trim().length() > 0 && password.trim().length() > 0){


                        if(username.equals(str.getPrenom_individi()) && password.equals(str.getCin_individu())){

                            // Creating user login session
                            if(get_session)
                            {
                                sharedPreferences
                                        .edit()
                                        .putString("cin", str.getCin_individu())
                                        .apply();
                            }

                            session.createLoginSession(String.valueOf(str.getId_individu()), String.valueOf(str.getNom_individi()), String.valueOf(str.getPrenom_individi()));
                           // Toast.makeText(Login.this,String.valueOf(str.getId_individu()),Toast.LENGTH_SHORT).show();

                            // Staring MainActivity
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();

                        }else{


                            edit_cin.setText("");
                            edit_prenom.setText("");
                        }
                    }else{

                        //Toast.makeText(Login.this,"password vide",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        makeJsonObjectRequest();


        switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               get_session=isChecked;
            }
        });

    }

    private void submitForm() {
        if (!validate_prenom()) {
            return;
        }

        if (!validate_cin()) {
            return;
        }


      //  new GetEvants().execute();


    }

    private boolean validate_cin() {
        if (edit_cin.getText().toString().trim().isEmpty()) {
            input_layout_cin.setError("Champs vide !");
            requestFocus(edit_cin);
            return false;
        } else {
            input_layout_cin.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validate_prenom() {
        if (edit_prenom.getText().toString().trim().isEmpty()) {
            input_layout_prenom.setError("Champs vide !");
            requestFocus(edit_prenom);
            return false;
        } else {
            input_layout_prenom.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void makeJsonObjectRequest() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,"http://"+myip+EndPonts.url_individu, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object


                    JSONArray service_clients = response.getJSONArray("list_individu");
                    for (int i = 0; i < service_clients.length(); i++) {
                        JSONObject c = service_clients.getJSONObject(i);
                        int id_individu = c.getInt("IDINDIVIDU");
                        String nom_individu = c.getString("NOMINDIVIDU");
                        String prenom_individu = c.getString("PRENOMINDIVIDU");
                        String cin_individu = c.getString("CININDIVIDU");

                        ConstParc item = new ConstParc();
                        item.setId_individu(id_individu);
                        item.setNom_individi(nom_individu);
                        item.setPrenom_individi(prenom_individu);
                        item.setCin_individu(cin_individu);

                        list_parc.add(item);
                        Log.d("all individu: ", "> " + c);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                  /*  Toast.makeText(MainActivity.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();*/
                }




            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                /*Toast.makeText(MainActivity.this,
                        error.getMessage(), Toast.LENGTH_SHORT).show();*/

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    /*
    * */


}
