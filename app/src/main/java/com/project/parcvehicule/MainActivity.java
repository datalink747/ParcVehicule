package com.project.parcvehicule;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    // Session Manager Class
    private SessionManager session;
    private TextView user_name;
    private Button btn_mission,btn_intervention,btn_propos;
    private CollapsingToolbarLayout collapsingtoolbarlayout;
    private FloatingActionButton btn_deconnexion;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_agent);
        setSupportActionBar(toolbar);

        sharedPreferences = getBaseContext().getSharedPreferences("parc", MODE_PRIVATE);

        user_name =(TextView)findViewById(R.id.user_name);
        btn_mission =(Button)findViewById(R.id.btn_mission);
        btn_intervention =(Button)findViewById(R.id.btn_intervention);
        btn_propos =(Button)findViewById(R.id.btn_propos);
        btn_deconnexion =(FloatingActionButton)findViewById(R.id.btn_deconnexion);
        collapsingtoolbarlayout =(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_main);
        collapsingtoolbarlayout.setTitle("Gestion du parc Véhicule STEG");

        // Session class instance
        session = new SessionManager(MainActivity.this);
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);

        // email
        String email = user.get(SessionManager.KEY_EMAIL);
        String prenom = user.get(SessionManager.KEY_prenom);
        user_name.setText(email+" "+prenom);

        btn_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_mission =new Intent(MainActivity.this,Mission.class);
                startActivity(goto_mission);

            }
        });

        btn_intervention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_intervention =new Intent(MainActivity.this,Intervention.class);
                startActivity(goto_intervention);
            }
        });

        btn_deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLocationDialog();
            }
        });

        btn_propos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopropos =new Intent(MainActivity.this,Propos.class);
                startActivity(gotopropos);
            }
        });

    }

    private void showLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.titre_app));
        builder.setMessage("êtes vous sûr de vouloir Quitter !");

        String positiveText = "Oui";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        if (sharedPreferences != null)
                            sharedPreferences.edit().remove("cin").commit();
                        MainActivity.this.finish();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
