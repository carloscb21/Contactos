package com.carloscb21.contactos.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.carloscb21.contactos.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Administrador on 15/03/2015.
 *
 * Esta clase es utilizada para administrar la creacion y actualizacion de tu base de datos
 * Esta clase usualmente proporciona las clases DAO(Patro de diseño Data Access Object)
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    //Nombre de la base de datos
    private static final String DATABASE_NAME = "agenda.db";
    //version de la base de datos, si se actuliza sera el 2...etc etc
    private static final int DATABASE_VERSION =1;

    //objetos DAO que se utilizaran para acceder a la base de datos
    //se pone Integrer por que el tipo de Id de la clase contacto es INT
    private Dao<Contacto,Integer> contactoDAO = null;

    //para evitar excepciones que controla la maquina automaticamente
    private RuntimeExceptionDao<Contacto,Integer> contactoRunTimeDAO = null;

    public DatabaseHelper(Context context){
        //TODO: Al finalizar el helper crear archivo de configuracion ORMLite(hecho ya, era el R.raw.ornolite_config desdepues de crear el txt de base de datos aparitr de databaseConfigUtil)
        super(context,DATABASE_NAME, null,DATABASE_VERSION, R.raw.ornlite_config);
    }

    //metodo invocado cuando la base de datos es creada, usualmente se hacen llamadas a metodos
    //createTable para crear las tablas que almacenaran los datos
    public void onCreate(SQLiteDatabase db, ConnectionSource source){
        //log es mereramente informativo
        try {
            Log.i(DatabaseHelper.class.getSimpleName(), "onCreate()");
            TableUtils.createTable(source, Contacto.class);
        }
        catch (SQLException ex){
            Log.e(DatabaseHelper.class.getSimpleName(),"imposible crear la base de datos",ex);
            throw new RuntimeException(ex);

        }
    }

    @Override
    //metodo invocado cuando la aplicacion es actualizada y tiene un numero de version superior
    //Permmite el ajuste de los datos para alinearse con la nueva version
    public void onUpgrade(SQLiteDatabase db, ConnectionSource source, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getSimpleName(), "onUpgrade()");
            TableUtils.dropTable(source, Contacto.class, true);
            //despues de eliminar las tablas anteriores, creamos nuevamente la base de datos
            onCreate(db, source);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getSimpleName(), "imposible eliminar la base de datos", e);
            throw new RuntimeException(e);
        }
    }
    //inicializamos los daos
    public Dao<Contacto,Integer> getContactoDAO() throws SQLException{
        if(contactoDAO == null) contactoDAO = getDao(Contacto.class);
        return contactoDAO;
    }
    public RuntimeExceptionDao<Contacto,Integer> getContactoRunTimeDAO(){
        if (contactoRunTimeDAO==null) contactoRunTimeDAO = getRuntimeExceptionDao(Contacto.class);
        return contactoRunTimeDAO;
    }

    //cierra las conexiones a la base de datos cuando se cierra la aplicacion
    public void close(){
        super.close();
        contactoDAO = null;
        contactoRunTimeDAO = null;
    }
}

