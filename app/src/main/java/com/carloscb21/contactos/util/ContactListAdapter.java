package com.carloscb21.contactos.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.carloscb21.contactos.R;

import java.util.List;

/**
 * Created by Administrador on 02/03/2015.
 */

//le pasamos los atributos del codigo java Contacto
public class ContactListAdapter extends ArrayAdapter<Contacto> {
    //cada activity es un contexto en si, y con esto nos aseguramos que nos pasa una actividad, un evento del apk
    private Activity ctx;

    public ContactListAdapter(Activity context, List<Contacto> contactos){
        //listview_item es el nuevo xml que hemos creado, asi pasamos el xml por cada contacto que pasamos, context nos dice que es una actividad
        super(context,R.layout.listview_item, contactos);
        this.ctx = context;
    }
    //inicializamos la variable ctx

    @Override
    //View sera la interfaz que aperece ya hecha
    public View getView(int position, View view, ViewGroup parent) {
        //vamos a validar que la lista en este sea igual a nulo, si es igual a nulo, indica que tenemos que inicializarla
        if (view == null) {
            //atraves de la varible ctx, vamos a inicializar el layout nuevo xml listview_item
            view = ctx.getLayoutInflater().inflate(R.layout.listview_item, parent, false);
        }
            //obtenemos la posicion del elmento en el array
            Contacto actual = this.getItem(position);
            //mandamos llamar y obtner el contacto actual atraves de la posicion que nos fue asiganada y finalmente incializamos cada uno de los textview que fue asiganado
            inicializarCamposDeTexto(view, actual);
            return view;

        }

    private void inicializarCamposDeTexto(View view, Contacto actual) {
        //viewNombre es la id del nombre del xml listview_item.xml
        TextView textview = (TextView) view.findViewById(R.id.viewNombre);
        textview.setText(actual.getNombre());
        textview = (TextView) view.findViewById(R.id.viewTelefono);
        textview.setText(actual.getTelefono());
        textview = (TextView) view.findViewById(R.id.viewDireccion);
        textview.setText(actual.getDireccion());
        textview = (TextView) view.findViewById(R.id.viewEmail);
        textview.setText(actual.getEmail());
        ImageView ivContacImage = (ImageView) view.findViewById(R.id.ivContactimage);
        ivContacImage.setImageURI(actual.getImageUri());


    }
}
