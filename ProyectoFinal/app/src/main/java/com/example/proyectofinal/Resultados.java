package com.example.proyectofinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class Resultados extends AppCompatActivity {

    String clasesNames[] = {"Patatas fritas airfryer", "Aceite de oliva virgen extra", "Huevos", "Canelones de carne y boletus", "Proteina de soja textutizada", "Café molido mezcla", "Dulce de leche", "Tacos", "Yerba mate"};

    String residuos[] = {
            "Residuos generados principales: " +
                    "Cáscaras de papa (orgánico), envase plástico o bolsa (si es precocida)\n" +
                    "Daño potencial / Tiempo de descomposición estimado: " +
                    "Orgánico: 2-3 semanas. Plástico: hasta 500 años si no se recicla",
            "Residuos generados principales: " +
                    "Botella de vidrio o plástico, tapón metálico o plástico\n" +
                    "Daño potencial / Tiempo de descomposición estimado: " +
                    "Vidrio: hasta 4,000 años si no se recicla. Plástico: hasta 500 años",
            "Residuos generados principales: " +
                    "Cáscara (orgánico), cartón o plástico (envase)\n" +
                    "Daño potencial / Tiempo de descomposición estimado: " +
                    "Cáscara: semanas. Cartón: 2-3 meses. Plástico: hasta 500 años",
            "Residuos generados principales: " +
                    "Envase plástico o aluminio, restos orgánicos (comida)\n" +
                    "Daño potencial / Tiempo de descomposición estimado: " +
                    "Aluminio: +200 años. Orgánico: semanas. Plástico: hasta 500 años",
            "Residuos generados principales: " +
                    "Bolsa plástica o envase de cartón\n" +
                    "Daño potencial / Tiempo de descomposición estimado: " +
                    "Plástico: hasta 500 años. Cartón: 2-3 meses si compostado",
            "Residuos generados principales: " +
                    "Envase (aluminio, plástico o papel), posos de café (orgánico)\n" +
                    "Daño potencial / Tiempo de descomposición estimado: " +
                    "Posos: compostables (1-2 semanas). Envase: depende del material",
            "Residuos generados principales: " +
                    "Frasco de vidrio o plástico, tapa metálica/plástica\n" +
                    "Daño potencial / Tiempo de descomposición estimado: " +
                    "Vidrio: 4,000 años. Plástico: hasta 500 años",
            "Residuos generados principales: " +
                    "Residuos orgánicos (comida), envase plástico o cartón\n" +
                    "Daño potencial / Tiempo de descomposición estimado: " +
                    "Orgánico: semanas. Cartón: meses. Plástico: hasta 500 años",
            "Residuos generados principales: " +
                    "Residuos orgánicos (hojas), envase (plástico o papel)\n" +
                    "Daño potencial / Tiempo de descomposición estimado: " +
                    "Yerba: compostable (2-3 semanas). Papel: meses. Plástico: 500 años"
    };

    ArrayList<HashMap<String, Object>> listaDatos = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();
        boolean [] seleccion = extras.getBooleanArray("Seleccion");

        int count = 0;
        for(int j = 0; j < seleccion.length; j++){
            if(seleccion[j] == true){
                count++;
            }
        }

        for(int i = 0; i < count; i++) {
            // creating an Object of HashMap class
            HashMap<String, Object> map = new HashMap<>();

            if(seleccion[i] == true) {
                // Data entry in HashMap
                map.put("nombreClase", clasesNames[i]);
                map.put("residuos", residuos[i]);
            }

            // adding the HashMap to the ArrayList
            listaDatos.add(map);
        }

        //Creamos arrays con datos
        String[] from = {"nombreClase", "residuos"};
        int to[] = {R.id.textViewNombre2, R.id.textViewResultados};

        //Creamos adaptador
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                listaDatos, R.layout.item_resultados, from, to);

        //Recupera listView y seteamos adaptador

        ListView listadoDatos = findViewById(R.id.ListViewResultados);
        listadoDatos.setAdapter(simpleAdapter);

        CardView cardViewInicio = findViewById(R.id.cardViewInicio3);
        CardView cardViewLocalizacion = findViewById(R.id.cardViewLocalizacion);

        cardViewInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Crear intent explicito
                Intent actividad1 = new Intent(Resultados.this, MainActivity.class);
                //Iniciar Actividad
                startActivity(actividad1);
            }
        });

        cardViewLocalizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.google.es/maps/search/punto+limpio/@43.544856,-5.7082523,13z/data=!3m1!4b1?entry=ttu&g_ep=EgoyMDI1MDQwMi4xIKXMDSoASAFQAw%3D%3D";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}