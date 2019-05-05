package com.example.realmapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

import io.realm.Realm;

public class Adapter extends BaseAdapter {
    private Context context;
    private List<Contact> list;
    private int layout;

    public Adapter(Context contextExt, List<Contact> listExt, int layoutExt){
        this.context = contextExt;
        this.list = listExt;
        this.layout = layoutExt;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(layout,null);
            viewHolder = new ViewHolder();
            viewHolder.card = view.findViewById(R.id.cards);
            viewHolder.name = view.findViewById(R.id.contact_name);
            viewHolder.genero = view.findViewById(R.id.contact_genero);
            viewHolder.edad = view.findViewById(R.id.contact_edad);
            view.setTag(viewHolder);

        }else{
            viewHolder =(ViewHolder) view.getTag();
        }

        viewHolder.name.setText(list.get(pos).getName());
        viewHolder.edad.setText(String.valueOf("Edad: "+list.get(pos).getEdad()));

        if (list.get(pos).isGenero()){
            viewHolder.genero.setText(String.valueOf("Genero: Masculino"));
        }else{
            viewHolder.genero.setText(String.valueOf("Genero: Femenino"));
        }

        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogoEdit(list.get(pos));
            }
        });
        viewHolder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialogodel(list.get(pos));
                return false;
            }
        });
        return view;
    }
    private void dialogodel(final Contact contact){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Borrar contacto?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                contact.deleteFromRealm();
                realm.commitTransaction();
            }
        });

        builder.setNegativeButton("No",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void dialogoEdit(final Contact contact){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edita el contacto");
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.dialog_add,null);
        builder.setView(viewInflate);

        final EditText names = viewInflate.findViewById(R.id.name);
        final EditText numbers = viewInflate.findViewById(R.id.numero);
        final EditText edad = viewInflate.findViewById(R.id.edad);
        final ToggleButton genero = viewInflate.findViewById(R.id.genero);

        names.setText(contact.getName());
        numbers.setText(contact.getNumero());
        edad.setText(String.valueOf(contact.getEdad()));
        genero.setChecked(!contact.isGenero());

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nameStr = names.getText().toString().trim();
                String numberStr = numbers.getText().toString().trim();
                int edadInt = Integer.parseInt(edad.getText().toString());
                boolean sexoBoolean = genero.getText().toString().equals("Hombre");

                if (nameStr.length() >0 && numbers.length() > 0) {
                    //
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    contact.setName(nameStr);
                    contact.setNumero(numberStr);
                    contact.setEdad(edadInt);
                    contact.setGenero(sexoBoolean);
                    realm.copyToRealmOrUpdate(contact);
                    realm.commitTransaction();
                    Toast.makeText(context, "Editado", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(context, "Algunos campos están vacios", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public class ViewHolder{
        CardView card;
        TextView name;
        TextView edad;
        TextView genero;
    }
}
