package com.example.listatareas.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listatareas.R;
import com.example.listatareas.controller.GestionTareas;
import com.example.listatareas.model.Tarea;

import java.util.ArrayList;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.ViewHolder> {

    private static Context context;
    private static ArrayList<Tarea> dataSet;

    public TareaAdapter(ArrayList<Tarea> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNombreTarea.setText(dataSet.get(position).getNombre());
        holder.txtFechaTarea.setText((dataSet.get(position).getFecha()));
        holder.txtHoraTarea.setText((dataSet.get(position).getHora()));
        holder.imgCategoria.setImageResource(dataSet.get(position).getCategoriaDrawable());

        holder.cv.setOnClickListener(view -> infoTarea(dataSet.get(position).getId()));

        holder.cv.setOnLongClickListener(view -> {
            PopupMenu popup = new PopupMenu(context, view);
            popup.setOnMenuItemClickListener(new menuItemClickListener(dataSet.get(position).getId()));
            popup.inflate(R.menu.mnu_item);
            popup.show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView txtNombreTarea, txtFechaTarea, txtHoraTarea;
        public ImageView imgCategoria;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            txtNombreTarea = itemView.findViewById(R.id.txtNombreTarea);
            txtFechaTarea = itemView.findViewById(R.id.txtFechaTarea);
            txtHoraTarea = itemView.findViewById(R.id.txtHoraTarea);
            imgCategoria = itemView.findViewById(R.id.imgCategoria);
        }

    }

    static class menuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private long id;
        menuItemClickListener(long id) {
            this.id = id;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            boolean valorRetorno = false;
            if (item.getItemId() == R.id.mnuInfo) {
                infoTarea(id);
                valorRetorno = true;
            }
            if (item.getItemId() == R.id.mnuEdit) {
                editTarea(id);
                valorRetorno = true;
            }
            if (item.getItemId() == R.id.mnuBorrar) {
                borrarTarea(id);
                valorRetorno = true;
            }
            return valorRetorno;
        }
    }

    private static void infoTarea(long id) {
        Intent intent = new Intent(context, InfoActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    private static void editTarea(long id) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("editar", true);
        context.startActivity(intent);
    }

    private static void borrarTarea(long id) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.ad_confirmBorrado_tit)
                .setCancelable(false)
                .setMessage(R.string.ad_confirmBorrado_msg)
                .setNegativeButton(R.string.ad_confirmBorrado_no, null)
                .setPositiveButton(R.string.ad_confirmBorrado_si, (dialog, which) -> {
                    new GestionTareas().deleteTarea(id);
                    MainActivity.adaptador.notifyDataSetChanged();
                })
                .show();
    }
}
