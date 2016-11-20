package com.project.parcvehicule;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Soussi on 19/05/2016.
 */
public class RecyclerAdaptateurMission extends RecyclerView.Adapter<RecyclerAdaptateurMission.ServicesViewHolder> {

    private Context context;
    private ArrayList<ConstParc> items;
    private ArrayList<ConstParc> list_agent=new ArrayList<ConstParc>();

    public RecyclerAdaptateurMission(Context context, ArrayList<ConstParc> items) {
        this.items = items;
        this.context = context;
        this.list_agent=items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ServicesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_card_agent, parent, false);
        ServicesViewHolder viewHolder = new ServicesViewHolder(context, view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ServicesViewHolder holder, final int position) {

        ConstParc films = items.get(position);
        holder.objectif.setText(films.getObjet_mission());
        holder.date_debut.setText(films.getDate_debut_mission());
        holder.date_fin.setText(films.getDate_fin_mission());

        /*// ImageLoader class instance
        Picasso.with(context).load(films.getImage_film())
                //.transform(new BlurTransformation(this))
                //.transform(new ResizeTransformation(50))
                //.resize(50,50)
                //.fit().centerCrop()
                .error(R.drawable.logo3)
                .fit().centerInside()
                .into(holder.ivFlag);
*/

    }

    public class ServicesViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        public TextView objectif, date_fin, date_debut;
        public ImageView ivFlag;




        public ServicesViewHolder(final Context context, final View itemView) {
            super(itemView);
            this.context = context;
            objectif = (TextView) itemView.findViewById(R.id.objectif);
            date_debut = (TextView) itemView.findViewById(R.id.date_debut);
            date_fin = (TextView) itemView.findViewById(R.id.date_fin);
            ivFlag = (ImageView) itemView.findViewById(R.id.image_services);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent aff_detail =new Intent(context,Detail_mission.class);
                    int position = getPosition();
                    aff_detail.putExtra("pos", items.get(position));
                    context.startActivity(aff_detail);
                }
            });

        }

    }





}
