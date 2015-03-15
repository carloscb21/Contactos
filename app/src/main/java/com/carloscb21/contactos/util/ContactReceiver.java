package com.carloscb21.contactos.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;

/**
 * Created by Administrador on 06/03/2015.
 */
public class ContactReceiver extends BroadcastReceiver {
    public static final int CONTACTO_AGREGADO = 1;
    public static final int CONTACTO_ELIMINADO = 2;
    public static final int CONTACTO_ACTUALIZADO = 3;

    private final OrmLiteBaseActivity<DatabaseHelper> activity;
    private final ArrayAdapter<Contacto> adapter;
    public ContactReceiver(ArrayAdapter<Contacto> adapter, OrmLiteBaseActivity<DatabaseHelper> activity){
        this.adapter = adapter;
        this.activity = activity;
    }

    //se va ejecutar este metodo cuando se de nuevo contacto o se elminine el contacto
    public void onReceive(Context context, Intent intent){
        int operacion = intent.getIntExtra("operacion",-1);
        switch (operacion){
            case CONTACTO_AGREGADO:agregarContacto(intent);break;
            case CONTACTO_ELIMINADO:eliminarContacto(intent);break;
            case CONTACTO_ACTUALIZADO:actualizarContacto(intent); break;
        }
    }

    private void agregarContacto(Intent intent) {
        Contacto contacto = (Contacto) intent.getSerializableExtra("datos");
        if (activity!=null){
            DatabaseHelper helper = activity.getHelper();
            RuntimeExceptionDao<Contacto,Integer> dao = helper.getContactoRunTimeDAO();
            dao.create(contacto);
        }
        adapter.add(contacto);
    }
    private void eliminarContacto(Intent intent) {
        ArrayList<Contacto> lista = (ArrayList<Contacto>) intent.getSerializableExtra("datos");
        if (activity!=null){
            DatabaseHelper helper = activity.getHelper();
            RuntimeExceptionDao<Contacto,Integer> dao = helper.getContactoRunTimeDAO();
            dao.delete(lista);
        }
        for (Contacto c: lista){ adapter.remove(c);}
    }
    private void actualizarContacto(Intent intent) {
        Contacto contacto = (Contacto) intent.getSerializableExtra("datos");
        if (activity!=null){
            DatabaseHelper helper = activity.getHelper();
            RuntimeExceptionDao<Contacto,Integer> dao = helper.getContactoRunTimeDAO();
            dao.update(contacto);
        }
        int posicion = adapter.getPosition(contacto);
        adapter.insert(contacto,posicion);
    }

}
