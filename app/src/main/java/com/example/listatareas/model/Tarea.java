package com.example.listatareas.model;

import android.content.Context;

import com.example.listatareas.R;

import java.util.Calendar;

public class Tarea {
    private long id;
    private String nombre;
    private String descripcion;
    private String fecha;
    private String hora;
    private int categoria;

    public Tarea() {
        this(0, "", "", "", "", 0);
    }

    public Tarea(long id, String nombre, String descripcion, String fecha, String hora, int categoria) {
        setId(id);
        setNombre(nombre);
        setDescripcion(descripcion);
        setFecha(fecha);
        setHora(hora);
        setCategoria(categoria);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        if (fecha.equals("")) {
            Calendar c = Calendar.getInstance();
            int ano = c.get(Calendar.YEAR);
            int mes = c.get(Calendar.MONTH);
            int dia = c.get(Calendar.DAY_OF_MONTH);
            fecha = "" + ano + "/" + (mes + 1) + "/" + dia;
        }
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        if (hora.equals("")) {
            Calendar c = Calendar.getInstance();
            int h = c.get(Calendar.HOUR_OF_DAY);
            int m = c.get(Calendar.MINUTE);
            hora = "" + h + ":" + m;
        }
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getCategoriaDrawable() {
        int[] drawables = new int[]{R.drawable.cat_0, R.drawable.cat_1, R.drawable.cat_2, R.drawable.cat_3, R.drawable.cat_4, R.drawable.cat_5};
        return drawables[getCategoria()];
    }

    public String getCategoriaNombre(Context context) {
        String[] str = new String[]{context.getString(R.string.cat_0), context.getString(R.string.cat_1), context.getString(R.string.cat_2), context.getString(R.string.cat_3), context.getString(R.string.cat_4), context.getString(R.string.cat_5)};
        return str[getCategoria()];
    }

}