package com.furazin.android.mbandgsr;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.furazin.android.mbandgsr.FirebaseBD.Usuario;
import com.furazin.android.mbandgsr.RecyclerExperiencias.RecyclerViewAdapterDatosUsuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by manza on 15/06/2017.
 */

public class SujetosExperiencia extends AppCompatActivity {

    private SharedPreferences sharedPref;
    public static String EMAIL_USUARIO;

    public static String NOMBRE_EXPERIENCIA;
    private ArrayList<ArrayList<String>> usuarios;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapterDatosUsuario recyclerViewAdapter;
    private TextView txtNombreExperiencia;

    public static Context usuariosExperienciaContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        NOMBRE_EXPERIENCIA = getIntent().getExtras().getString("id_experiencia");
        usuariosExperienciaContext = this.getApplicationContext();

        // Instanciamos una referencia al Contexto
        Context context = this.getApplicationContext();
        //Instanciamos el objeto SharedPreferences y creamos un fichero Privado bajo el
        //nombre definido con la clave preference_file_key en el fichero string.xml
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        // Obtenemos email del usuario que se ha logueado
        EMAIL_USUARIO = sharedPref.getString((getString(R.string.email_key)), "");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");

//        usuarios = new ArrayList<String>();
        usuarios = new ArrayList<ArrayList<String>>();
        recyclerView = (RecyclerView)findViewById(R.id.experiencias_list);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        txtNombreExperiencia = (TextView) findViewById(R.id.nombreExperiencia);
        txtNombreExperiencia.setText(NOMBRE_EXPERIENCIA);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                usuarios.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Usuario user = snapshot.getValue(Usuario.class);
                    final String user_key;
                    if (user.getEmail().equals(EMAIL_USUARIO)) {
                        // Obtenemos la key del usuario logueado
                        user_key = snapshot.getKey();
                        myRef.child(user_key).child("Experiencias").child(NOMBRE_EXPERIENCIA).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                    ArrayList<String> infoSujeto = new ArrayList<String>();
                                    String num_sujeto = singleSnapshot.getKey();
                                    if (!num_sujeto.equals("fechaRealizacion") && !num_sujeto.equals("pruebaTerminada")) {
                                        String nombreSujeto = singleSnapshot.child("nombre").getValue().toString() + " " + singleSnapshot.child("apellidos").getValue().toString();
                                        String opcionMultimedia = singleSnapshot.child("opcion_multimedia").getValue().toString();
                                        String pruebaTerminada = singleSnapshot.child("terminada").getValue().toString();
                                        if (pruebaTerminada.equals("si")) {
                                            infoSujeto.add("Finalizada");
                                        }
                                        else {
                                            infoSujeto.add("Pendiente");
                                        }
                                        infoSujeto.add(nombreSujeto);    // Nombre y Apellidos del sujeto
                                        infoSujeto.add(opcionMultimedia);  // Multimedia que contiene la prueba
                                        infoSujeto.add(singleSnapshot.getKey()); // key del sujeto
                                        usuarios.add(infoSujeto);
                                        recyclerViewAdapter = new RecyclerViewAdapterDatosUsuario(SujetosExperiencia.this, usuarios);
                                        recyclerView.setAdapter(recyclerViewAdapter);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

