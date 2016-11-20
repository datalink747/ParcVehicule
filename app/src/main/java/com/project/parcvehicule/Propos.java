package com.project.parcvehicule;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class Propos extends AppCompatActivity {
    private ImageView aff_propos;
    private CollapsingToolbarLayout collapsingtoolbarlayout2;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_propos);
        setSupportActionBar(toolbar);

        aff_propos=(ImageView)findViewById(R.id.backgroundImageView2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.send_mail_propos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mailIntent = new Intent();
                mailIntent.setAction(Intent.ACTION_SEND);
                mailIntent.setType("message/rfc822");
                mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {""});
                mailIntent.putExtra(Intent.EXTRA_SUBJECT, "contact");
                mailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(mailIntent, getString(R.string.name_email)));
            }
        });

        aff_propos.setImageDrawable(getDrawable(R.drawable.them));
        collapsingtoolbarlayout2 =(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_propos);
        collapsingtoolbarlayout2.setTitle(getString(R.string.name_titre));


    }

}
