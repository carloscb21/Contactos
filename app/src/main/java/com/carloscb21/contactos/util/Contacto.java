package com.carloscb21.contactos.util;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Administrador on 02/03/2015.
 */

@DatabaseTable(tableName = "contacto")
//la clase contacto solo va a contener 4 atributos de tipo cadena, String.
//1 mas para la imagen
//Serializable = esta clase puede ser almacenable en una base de datos
public class Contacto implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;//Primary Key

    @DatabaseField(index = true, canBeNull = false)
    private String nombre;

    @DatabaseField
    private String telefono;

    @DatabaseField
    private String email;

    @DatabaseField
    private String direccion;

    @DatabaseField
    //los objetos Uri no son serializables
    private String imageUri;

    //ejemplo con objeto Uri
    //@DatabaseField(persistedClass = /*clase para convertir objeto Uri a String, y luego al reves, tenemos una cadena String y lo convertimos a Uri*/)

    //ORMLIFE nos exige un constructor predeterminado
    public Contacto(){
    }

    public Contacto(String nombre, String telefono, String email, String direccion, String imageUri){
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.imageUri = imageUri;
    }

    //metodos Get

    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getImageUri(){
        return imageUri;
    }
    //metodos Set
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    //Para formar un programa homogeneo, es decir, controlar que cuando cree un contacto, al volverlo a abrir, ese contacto sea el mismo
    @Override
    public boolean equals(Object o) {
        if (this==o)return true;
        if (o==null||getClass() !=o.getClass())return false;
        Contacto contacto = (Contacto) o;

        if (direccion!= null? !direccion.equals(contacto.direccion):contacto.direccion !=null)
            return false;
        if (email !=null ? !email.equals(contacto.email): contacto.email != null)
            return false;
        if (imageUri != null ? !imageUri.equals(contacto.imageUri): contacto.imageUri != null)
            return false;
        if (!nombre.equals(contacto.nombre))
            return false;
        if (telefono!= null? !telefono.equals(contacto.telefono):contacto.telefono !=null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nombre.hashCode();
        result=31*result*(telefono !=null ? telefono.hashCode():0);
        result=31*result*(email !=null ? email.hashCode():0);
        result=31*result*(direccion !=null ? direccion.hashCode():0);
        result=31*result*(imageUri !=null ? imageUri.hashCode():0);
        return result;
    }

}


