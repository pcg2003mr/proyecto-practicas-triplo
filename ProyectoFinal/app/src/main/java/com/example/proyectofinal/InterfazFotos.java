package com.example.proyectofinal;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class InterfazFotos extends AppCompatActivity {

    private static final int PICK_IMAGES_REQUEST = 1;
    private static final int TAKE_PHOTO_REQUEST = 2;

    private ArrayList<Uri> imageUris = new ArrayList<>();
    private Uri photoUriActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_interfaz_fotos);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        CardView cardViewInicio = findViewById(R.id.cardViewInicio);
        CardView cardViewResultados = findViewById(R.id.cardViewResultados);
        CardView cardViewCamara = findViewById(R.id.CardViewCamara);

        cardViewInicio.setOnClickListener(view -> {
            Intent actividad1 = new Intent(InterfazFotos.this, MainActivity.class);
            startActivity(actividad1);
        });

        cardViewResultados.setOnClickListener(view -> {
            Intent actividad4 = new Intent(InterfazFotos.this, Resultados.class);
            startActivity(actividad4);
        });

        cardViewCamara.setOnClickListener(view -> mostrarOpciones());
    }

    private void mostrarOpciones() {
        String[] opciones = {"Seleccionar de la galería", "Hacer una foto"};

        new AlertDialog.Builder(this)
                .setTitle("Elige una opción")
                .setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int seleccion) {
                        if (seleccion == 0) {
                            abrirGaleria();
                        } else if (seleccion == 1) {
                            hacerFoto();
                        }
                    }
                })
                .show();
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Selecciona imágenes"), PICK_IMAGES_REQUEST);
    }

    private void hacerFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File fotoArchivo = null;
            try {
                fotoArchivo = crearArchivoImagen();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (fotoArchivo != null) {
                photoUriActual = FileProvider.getUriForFile(
                        this,
                        "com.example.proyectofinal.fileprovider", // ⚠️ Cambia esto según tu package
                        fotoArchivo
                );
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUriActual);
                startActivityForResult(intent, TAKE_PHOTO_REQUEST);
            }
        }
    }

    private File crearArchivoImagen() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_REQUEST && resultCode == RESULT_OK) {
            imageUris.clear();

            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    imageUris.add(imageUri);
                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                imageUris.add(imageUri);
            }

        } else if (requestCode == TAKE_PHOTO_REQUEST && resultCode == RESULT_OK) {
            if (photoUriActual != null) {
                imageUris.add(photoUriActual);
            }
        }
    }
}
