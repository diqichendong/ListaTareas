package com.example.listatareas.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.listatareas.R;
import com.example.listatareas.controller.GestionTareas;
import com.example.listatareas.model.Tarea;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    long id;
    boolean esEditar;

    Tarea tarea;

    TextView txtAcctionTarea;
    EditText txtTareaNombre, txtTareaFecha, txtTareaHora, txtTareaDescripcion;
    RadioGroup opcCategoriaBlq1, opcCategoriaBlq2;
    RadioButton opcCat0, opcCat1, opcCat2, opcCat3, opcCat4, opcCat5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        id = getIntent().getLongExtra("id", -1);
        esEditar = getIntent().getBooleanExtra("editar", false);

        txtAcctionTarea = findViewById(R.id.txtAcctionTarea);
        txtTareaNombre = findViewById(R.id.txtTareaNombre);
        txtTareaFecha = findViewById(R.id.txtTareaFecha);
        txtTareaHora = findViewById(R.id.txtTareaHora);
        txtTareaDescripcion = findViewById(R.id.txtTareaDescripcion);

        opcCategoriaBlq1 = findViewById(R.id.opcCategoriaBlq1);
        opcCategoriaBlq2 = findViewById(R.id.opcCategoriaBlq2);

        opcCat0 = findViewById(R.id.opcCat0);
        opcCat1 = findViewById(R.id.opcCat1);
        opcCat2 = findViewById(R.id.opcCat2);
        opcCat3 = findViewById(R.id.opcCat3);
        opcCat4 = findViewById(R.id.opcCat4);
        opcCat5 = findViewById(R.id.opcCat5);

        findViewById(R.id.btnTareaCancelar).setOnClickListener(v -> finish());
        findViewById(R.id.btnTareaGuardar).setOnClickListener(v -> guardar());
        findViewById(R.id.btnTareaFecha).setOnClickListener(v -> setFecha());
        findViewById(R.id.btnTareaHora).setOnClickListener(v -> setHora());

        opcCategoriaBlq1.setOnCheckedChangeListener((group, checkedId) -> { if (checkedId != -1) categoriaBlq2(); });
        opcCategoriaBlq2.setOnCheckedChangeListener((group, checkedId) -> { if (checkedId != -1) categoriaBlq1(); });

        mostrarDatos();
    }

    public void categoriaBlq1() {
        opcCategoriaBlq1.setOnCheckedChangeListener(null);
        opcCategoriaBlq1.clearCheck();
        opcCategoriaBlq1.setOnCheckedChangeListener((group, checkedId) -> categoriaBlq2());
    }

    public void categoriaBlq2() {
        opcCategoriaBlq2.setOnCheckedChangeListener(null);
        opcCategoriaBlq2.clearCheck();
        opcCategoriaBlq2.setOnCheckedChangeListener((group, checkedId) -> categoriaBlq1());
    }

    private void setFecha() {
        int actAno, actMes, actDia;

        if (txtTareaFecha.getText().toString().length() > 0) {
            String[] texto = txtTareaFecha.getText().toString().split("/");
            actAno = Integer.parseInt(texto[2]);
            actMes = Integer.parseInt(texto[1]) - 1;
            actDia = Integer.parseInt(texto[0]);
        } else {
            Calendar c = Calendar.getInstance();
            actAno = c.get(Calendar.YEAR);
            actMes = c.get(Calendar.MONTH);
            actDia = c.get(Calendar.DAY_OF_MONTH);
        }

        new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) ->
                txtTareaFecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                , actAno, actMes, actDia).show();
    }

    private void setHora() {
        int actHora, actMin;

        if (txtTareaHora.getText().toString().length() > 0) {
            String[] texto = txtTareaHora.getText().toString().split(":");
            actHora = Integer.parseInt(texto[0]);
            actMin = Integer.parseInt(texto[1]);
        } else {
            Calendar c = Calendar.getInstance();
            actHora = c.get(Calendar.HOUR_OF_DAY);
            actMin = c.get(Calendar.MINUTE);
        }

        new TimePickerDialog(this, (view, hourOfDay, minute) ->
                txtTareaHora.setText(hourOfDay + ":" + minute)
                , actHora, actMin, false).show();
    }

    private void guardar() {
        tarea.setNombre(txtTareaNombre.getText().toString());
        tarea.setFecha(txtTareaFecha.getText().toString());
        tarea.setHora(txtTareaHora.getText().toString());

        if (opcCat0.isChecked()) tarea.setCategoria(0);
        if (opcCat1.isChecked()) tarea.setCategoria(1);
        if (opcCat2.isChecked()) tarea.setCategoria(2);
        if (opcCat3.isChecked()) tarea.setCategoria(3);
        if (opcCat4.isChecked()) tarea.setCategoria(4);
        if (opcCat5.isChecked()) tarea.setCategoria(5);

        if(esEditar) {
            new GestionTareas().updateTarea(id, tarea);
            int position = new GestionTareas().getPosition(id);
            MainActivity.adaptador.notifyItemChanged(position);
        } else {
            new GestionTareas().addTarea(0, tarea);
            MainActivity.adaptador.notifyItemInserted(0);
        }
        finish();
    }

    private void mostrarDatos() {
        tarea = esEditar ? new GestionTareas().getTarea(id) : new Tarea();

        int accion = esEditar ? R.string.acc_editar : R.string.acc_nuevo;
        txtAcctionTarea.setText(getResources().getString(accion));

        txtTareaNombre.setText(tarea.getNombre());
        txtTareaFecha.setText(tarea.getFecha());
        txtTareaHora.setText(tarea.getHora());
        txtTareaDescripcion.setText(tarea.getDescripcion());

        opcCategoriaBlq1.clearCheck();
        opcCategoriaBlq2.clearCheck();
        switch (tarea.getCategoria()) {
            case 0:
                opcCat0.setChecked(true);
                break;
            case 1:
                opcCat1.setChecked(true);
                break;
            case 2:
                opcCat2.setChecked(true);
                break;
            case 3:
                opcCat3.setChecked(true);
                break;
            case 4:
                opcCat4.setChecked(true);
                break;
            case 5:
                opcCat5.setChecked(true);
                break;
        }
    }
}