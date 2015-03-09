package com.carloscb21.contactos;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.carloscb21.contactos.util.ContactReceiver;
import com.carloscb21.contactos.util.Contacto;
import com.carloscb21.contactos.util.TextChangedListener;

/**
 * Created by Administrador on 06/03/2015.
 */
public class CrearContactosFragment extends Fragment implements View.OnClickListener{
    private EditText txtNombre, txtTelefono, txtEmail, txtDireccion;
    private ImageView imgViewContacto;
    private Button btnAgregar;
    private int request_code=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_crear_contacto, container,false);
        inicializarComponentes(rootView);
        return rootView;

    }

    private void inicializarComponentes(final View view) {
        txtNombre = (EditText) view.findViewById(R.id.cmpNombre);
        txtTelefono = (EditText) view.findViewById(R.id.cmpTelefono);
        txtEmail = (EditText) view.findViewById(R.id.cmpEmail);
        txtDireccion = (EditText) view.findViewById(R.id.cmpDireccion);
        imgViewContacto = (ImageView) view.findViewById(R.id.imgViewContacto);
        imgViewContacto.setOnClickListener(this);

        txtNombre.addTextChangedListener(new TextChangedListener(){

            @Override
            //vamos a seleccionar el metodo que queremos sobreescribir, control+o y le damos al onTextChanged, va a reflejar todos los cambios del editext por el usuario
            public void onTextChanged(CharSequence seq, int i, int i2, int i3) {
                btnAgregar.setEnabled(!seq.toString().trim().isEmpty());
            }
        });
        btnAgregar = (Button) view.findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view == btnAgregar) {
            agregarContacto(txtNombre.getText().toString(), txtTelefono.getText().toString(), txtEmail.getText().toString(), txtDireccion.getText().toString(),
                    String.valueOf(imgViewContacto.getTag())
            );
            String mesg = String.format("%s ha sido agredado a la lista", txtNombre.getText());
            Toast.makeText(view.getContext(), mesg, Toast.LENGTH_SHORT).show();
            btnAgregar.setEnabled(false);
            limpiarCampos();

        } else if (view == imgViewContacto) {
            Intent intent = null;
            //Verificamos la version de la plataforma
            if (Build.VERSION.SDK_INT < 19) {
                //Android JellyBean 4.3 y anteriores
                intent = new Intent();
                //le decimos a Android que nos permita acceder donde hay conteido en el movil
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //el tipo de contenido que queremos entrar ponemos, el * dice que puede ser cualquier tipo de image, es decir, imagen
                intent.setType("image/*");
                //solicitamos a Android que nos habra el dialogo y el usuario seleccione la imagen deseada, nos devuelva la imagen seleccionada
                startActivityForResult(intent, request_code);
            } else {
                //Android KitKat 4.4 o superior
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, request_code);

            }
        }
    }
    private void agregarContacto(String nombre, String telefono, String email, String direccion, String imageUri) {
        Contacto nuevo = new Contacto(nombre,telefono,email,direccion,imageUri);
        Intent intent = new Intent("listacontactos");
        intent.putExtra("operacion", ContactReceiver.CONTACTO_AGREGADO);
        intent.putExtra("datos",nuevo);
        //con esto enviamos la señal a todos lo que quieran escucharlo ListaContactosFragment
        getActivity().sendBroadcast(intent);
    }

    private void limpiarCampos() {
        txtNombre.getText().clear();
        txtDireccion.getText().clear();
        txtEmail.getText().clear();
        txtTelefono.getText().clear();
        //Restablecemos la imagen predeterminada del contacto, setImageResource porque es una imagen dentro de nuestro packge
        imgViewContacto.setImageResource(R.drawable.contacticon);
        //vamos a agregar un metodo para que pueda mandar llamar el foco dentro del campo nombre, obviamente si el usuario escribio hasta la direccion el foco se quedaria en la direccion, se queda hsta donde se escribe
        txtNombre.requestFocus();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode== Activity.RESULT_OK && requestCode ==requestCode){
            imgViewContacto.setImageURI(data.getData());
            imgViewContacto.setTag(data.getData());
        }
    }
 }
