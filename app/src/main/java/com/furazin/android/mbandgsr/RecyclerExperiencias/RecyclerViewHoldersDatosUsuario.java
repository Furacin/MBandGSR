package com.furazin.android.mbandgsr.RecyclerExperiencias;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.furazin.android.mbandgsr.InfoSujeto;
import com.furazin.android.mbandgsr.R;
import com.furazin.android.mbandgsr.SujetosExperiencia;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by manza on 15/06/2017.
 */

public class RecyclerViewHoldersDatosUsuario extends RecyclerView.ViewHolder {
//    private static final String TAG = RecyclerViewHoldersListaExperiencias.class.getSimpleName();
    //    public ImageView markIcon;
    public TextView Name;
    public TextView Apellidos;
    public CircleImageView MarcaMultimedia;
    public ImageButton btnInicioPrueba;
    public String sujeto_key;

    private ArrayList<ArrayList<String>> experienciasObject;
    public RecyclerViewHoldersDatosUsuario(final View itemView, final ArrayList<ArrayList<String>> experienciasObject, final Context parent) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(itemView.getContext(), InfoSujeto.class);
                i.putExtra("id_usuario",sujeto_key);
                itemView.getContext().startActivity(i);

                // Cerramos el activity del que venimos
                ((SujetosExperiencia)parent).finish();
            }
        });

        this.experienciasObject = experienciasObject;
        Name = (TextView) itemView.findViewById(R.id.nombre_sujeto);
        Apellidos = (TextView) itemView.findViewById(R.id.sujeto_apellidos);
        MarcaMultimedia = (CircleImageView) itemView.findViewById(R.id.marca_multimedia);
        btnInicioPrueba = (ImageButton) itemView.findViewById(R.id.btnInicioPrueba);

        btnInicioPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(itemView.getContext(), InfoSujeto.class);
                i.putExtra("id_usuario",sujeto_key);
                itemView.getContext().startActivity(i);

                // Cerramos el activity del que venimos
                ((SujetosExperiencia)parent).finish();
            }
        });
    }
}
