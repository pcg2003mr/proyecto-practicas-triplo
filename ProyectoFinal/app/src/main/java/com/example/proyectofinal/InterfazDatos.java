package com.example.proyectofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class InterfazDatos extends AppCompatActivity {

    String clasesNames[] = {"Patatas fritas airfryer", "Aceite de oliva virgen extra", "Huevos", "Canelones de carne y boletus", "Proteina de soja textutizada", "Café molido mezcla", "Dulce de leche", "Tacos", "Yerba mate"};

    int ImageIds[] = {R.drawable.foto1,
            R.drawable.foto2,
            R.drawable.foto3,
            R.drawable.foto4,
            R.drawable.foto5,
            R.drawable.foto6,
            R.drawable.foto7,
            R.drawable.foto8,
            R.drawable.foto9};

    boolean [] selection = {false,false,false,false,false,false,false,false,false};

    ArrayList<HashMap<String, Object>> listaDatos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_interfaz_datos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        for(int i = 0; i < clasesNames.length; i++) {
            // creating an Object of HashMap class
            HashMap<String, Object> map = new HashMap<>();

            // Data entry in HashMap
            map.put("nombreClase", clasesNames[i]);
            map.put("imagen", ImageIds[i]);

            // adding the HashMap to the ArrayList
            listaDatos.add(map);
        }

        //Creamos arrays con datos
        String[] from = {"nombreClase", "imagen"};
        int to[] = {R.id.textViewDatos, R.id.imageViewDatos};

        //Creamos adaptador
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                listaDatos, R.layout.item_datos, from, to);

        //Recupera listView y seteamos adaptador

        ListView listadoDatos = findViewById(R.id.listaDatos);
        listadoDatos.setAdapter(simpleAdapter);

        CardView cardViewInicio = findViewById(R.id.cardViewInicio2);
        CardView cardViewResultados = findViewById(R.id.cardViewResultados2);

        cardViewInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Crear intent explicito
                Intent actividad1 = new Intent(InterfazDatos.this, MainActivity.class);
                //Iniciar Actividad
                startActivity(actividad1);
            }
        });

        cardViewResultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Crear intent explicito
                Intent actividad4 = new Intent(InterfazDatos.this, Resultados.class);
                actividad4.putExtra("Seleccion", selection);
                //Iniciar Actividad
                startActivity(actividad4);
            }
        });

        listadoDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String nombreJuegoSeleccionado = clasesNames[position];

                if(selection[position] == false){
                    selection[position] = true;
                    Toast.makeText(InterfazDatos.this, nombreJuegoSeleccionado + " seleccionado", Toast.LENGTH_SHORT).show();
                }else{
                    selection[position] = false;
                    Toast.makeText(InterfazDatos.this, nombreJuegoSeleccionado + " deseleccionado", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}