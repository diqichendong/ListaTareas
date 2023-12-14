package com.example.listatareas.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.listatareas.R;
import com.example.listatareas.controller.GestionTareas;
import com.example.listatareas.model.Tarea;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    public static TareaAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));

        findViewById(R.id.btnNuevo).setOnClickListener(v -> crearNuevaTarea());

        GestionTareas ctrl = new GestionTareas();
        ctrl.cargarDatos();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adaptador = new TareaAdapter(ctrl.getList());

        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adaptador);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                GestionTareas ctrl = new GestionTareas();

                int position = viewHolder.getAdapterPosition();
                Tarea tarea = ctrl.getList().get(position);
                ctrl.deleteTarea(tarea.getId());

                adaptador.notifyItemRemoved(viewHolder.getAdapterPosition());

                Snackbar.make(rv, getResources().getString(R.string.acc_borrar), Snackbar.LENGTH_LONG).setAction(R.string.acc_deshacer, v -> {
                    ctrl.addTarea(position, tarea);
                    adaptador.notifyItemInserted(position);
                }).show();
            }
        }).attachToRecyclerView(rv);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Forzar: IconWithText
        // ((MenuBuilder) menu).setOptionalIconsVisible(true);
        getMenuInflater().inflate(R.menu.mnu_ppal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean valorRetorno = super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.mnuNuevo) {
            crearNuevaTarea();
            valorRetorno = true;
        }
        if (item.getItemId() == R.id.mnuOrdCat) {
            new GestionTareas().sortCategoria();
            adaptador.notifyDataSetChanged();
            valorRetorno = true;
        }
        if (item.getItemId() == R.id.mnuOrdFch) {
            new GestionTareas().sortFecha();
            adaptador.notifyDataSetChanged();
            valorRetorno = true;
        }
        if (item.getItemId() == R.id.mnuSalir) {
            salirApp();
            valorRetorno = true;
        }
        return valorRetorno;
    }

    private void crearNuevaTarea() {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("editar", false);
        startActivity(intent);
    }

    private void salirApp() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.ad_salir_tit)
                .setCancelable(false)
                .setMessage(R.string.ad_salir_msg)
                .setNegativeButton(R.string.ad_salir_no, null)
                .setPositiveButton(R.string.ad_salir_si, (dialog, which) -> {
                    finish();
                })
                .show();
    }
}