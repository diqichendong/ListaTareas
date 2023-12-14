package com.example.listatareas.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listatareas.R;
import com.example.listatareas.controller.GestionTareas;
import com.example.listatareas.model.Tarea;

public class InfoActivity extends AppCompatActivity {

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        id = getIntent().getLongExtra("id", -1);
        if (id == -1) finish();

        findViewById(R.id.btnVolver).setOnClickListener(v -> finish());
        findViewById(R.id.btnEditar).setOnClickListener(v -> {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("editar", true);
            startActivity(intent);
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mostrarDatos(id);
    }

    private void mostrarDatos(long id) {

        GestionTareas ctrl = new GestionTareas();
        Tarea tarea = ctrl.getTarea(id);
        if (tarea == null) finish();

        TextView txtTareaNombre = findViewById(R.id.txtTareaNombre);
        TextView txtTareaDescripcion = findViewById(R.id.txtTareaDescripcion);
        TextView txtTareaFecha = findViewById(R.id.txtTareaFecha);
        TextView txtTareaHora = findViewById(R.id.txtTareaHora);
        TextView txtCategoriaNombre = findViewById(R.id.txtCategoriaNombre);
        ImageView imgCategoriaImagen = findViewById(R.id.imgCategoriaImagen);

        txtTareaNombre.setText(tarea.getNombre());
        txtTareaFecha.setText(tarea.getFecha());
        txtTareaHora.setText(tarea.getHora());
        txtTareaDescripcion.setText(tarea.getDescripcion());
        txtCategoriaNombre.setText(tarea.getCategoriaNombre(this));
        imgCategoriaImagen.setImageResource(tarea.getCategoriaDrawable());

    }
}