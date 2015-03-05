package com.carloscb21.contactos;


//prueba de carlos
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.carloscb21.contactos.util.ContactListAdapter;
import com.carloscb21.contactos.util.Contacto;
import com.carloscb21.contactos.util.TextChangedListener;

import java.util.ArrayList;
// ARRAY ADAPTER su funcion es integrar es nuestro ArrayList que habiamos creado para poder representar dentro del layout que habiamos creado previamente, el tab2

public class MainActivity extends ActionBarActivity {

    //declaramos el tipo de atributos del activity_main
    private EditText txtNombre, txtTelefono, txtEmail, txtDireccion;
    //el adapter ya tiene toda la infraestructura de notificiaciones cuando vamos agregando la lista de contactos
    private ArrayAdapter<Contacto> adapter;
    private ListView contactosListView;
    private ImageView imgViewContacto;
    private Button btnAgregar;
    //creamos un request code por cada notificacion, nos permite saber que tipo de respuesta estamos recibiendo
    private int request_code=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Metodo inicializar componentes, que dara el paso al private void InicializarComponentesUI()
        inicializarComponentesUI();
        //nos falta crear el view, nuestra LISTA, creamos el metodo
        inicializarListaViewContactos();
        //Incializamos las pestañas de los Tabs, el tab1 y tab2, para poder modificarlas
        inicializarTabs();
    }

    private void inicializarListaViewContactos() {
        adapter = new ContactListAdapter(this, new ArrayList<Contacto>());
        contactosListView.setAdapter(adapter);
    }

    private void inicializarTabs() {
        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        //con esto decimos a Android que vamos a configurar las pestañas de los tabs
        tabHost.setup();
        //generamos un objeto de tipo TabSpec, y con esto configuramos las pestañas, le asignamos una etiqueta "tab1"
        TabHost.TabSpec spec = tabHost.newTabSpec("tab1");
        //asignamos el contenido que habiamos dicho tab1
        spec.setContent(R.id.tab1);
        //Indicamos lo que queremos que diga la pestaña
        spec.setIndicator("Crear");
        //añadimos los cambios
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Lista");
        tabHost.addTab(spec);
    }

    private void inicializarComponentesUI() {
        //declaramos las variables que vamos a utilizar
        txtNombre = (EditText) findViewById(R.id.cmpNombre);
        txtTelefono = (EditText) findViewById(R.id.cmpTelefono);
        txtEmail = (EditText) findViewById(R.id.cmpEmail);
        txtDireccion = (EditText) findViewById(R.id.cmpDireccion);
        contactosListView = (ListView) findViewById(R.id.listView);
        imgViewContacto = (ImageView) findViewById(R.id.imgViewContacto);

        //vamos a asignar a nuestro auditor(Nombre) un textchangeListener, es decir, una nueva clase Java creada en com.carloscb21.contactos.util
        txtNombre.addTextChangedListener(new TextChangedListener(){

            @Override
            //vamos a seleccionar el metodo que queremos sobreescribir, control+o y le damos al onTextChanged, va a reflejar todos los cambios del editext por el usuario
            public void onTextChanged(CharSequence seq, int i, int i2, int i3) {
                btnAgregar = (Button) findViewById(R.id.btnAgregar);
                //setEnable para ver si esta activado el boton
                //el editext txtNombre el toString lo pasamos a string, Trim le quita los espacios y Empty para ver si esta vacia o no, si esta vacia no hace nada, si esta llena se activa el boton
                btnAgregar.setEnabled(!seq.toString().trim().isEmpty());
            }
        });

    }

    //antes fuimos al xml del activity_main y le dimos al Button la etiqueta de onClick, que nos el evento del boton public void onClick
    public void onClick(View view) {
        //creamos un metodo agregarContacto
        //Obtenemos el atributo TAG con la Uri de la imagen
        agregarContacto(txtNombre.getText().toString(),txtTelefono.getText().toString(),txtEmail.getText().toString(),txtDireccion.getText().toString(),
                (Uri) imgViewContacto.getTag()
        );

        //con esto damos un mensaje por la interfaz que se ha realizado la entrada del contacto nombre
        String mesg = String.format("%s ha sido agredado a la lista", txtNombre.getText());
        Toast.makeText(this, mesg,Toast.LENGTH_SHORT).show();
        //con esto desactivamos el boton cuando el usuario termina de agregar el contacto
        btnAgregar.setEnabled(false);
        //Metodo para limpiar los campos cuando se agregado
        limpiarCampos();
    }
    //necesitamos una clase(ContacListAdapter) mas que va a heredar de arrayAdapter, su funcion es integrar cada uno de los elementos del arraylist(contacto a contacto) en el listview

    private void agregarContacto(String nombre, String telefono, String email, String direccion, Uri imageUri) {
        Contacto nuevo = new Contacto(nombre,telefono,email,direccion,imageUri);
        //con esto se le notifica automaticamnete al listView el nuevo contacto
        adapter.add(nuevo);
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
    //cuando el usuario presione en el imgview, le permita al usuario que pueda poner una foto

    public void onImgClick(View view){
        Intent intent = null;
        //Verificamos la version de la plataforma
        if (Build.VERSION.SDK_INT<19){
            //Android JellyBean 4.3 y anteriores
            intent = new Intent();
            //le decimos a Android que nos permita acceder donde hay conteido en el movil
            intent.setAction(Intent.ACTION_GET_CONTENT);
            //el tipo de contenido que queremos entrar ponemos, el * dice que puede ser cualquier tipo de image, es decir, imagen
            intent.setType("image/*");
            //solicitamos a Android que nos habra el dialogo y el usuario seleccione la imagen deseada, nos devuelva la imagen seleccionada
            startActivityForResult(intent,request_code);
        }
        else{
            //Android KitKat 4.4 o superior
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, request_code);

        }
        //la ultima parte se puede poner fuera y asi borrar tanto del if como del else
        //            intent.setType("Image/*");
        //startActivityForResult(intent, request_code);

    }
    //para poder obtener la imagen que fue seleccionada, necesitamos el public void onActivityResult el cual nos a permitir lo que hizo el usuario, si el usuario acepto o rechazo la accion
   protected  void onActivityResult(int requestCode,int resultadoCode, Intent data){
       if (resultadoCode==RESULT_OK && requestCode==request_code) {
           imgViewContacto.setImageURI(data.getData());
           //utilizamos el atributo TAG para almacenar el Uri al archivo seleccionado
           imgViewContacto.setTag(data.getData());
       }
   }

}
