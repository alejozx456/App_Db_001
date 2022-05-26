package fisei.app_db_001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fisei.app_db_001.dal.ClienteDAL;
import fisei.app_db_001.entidades.Cliente;

public class MainActivity extends AppCompatActivity {


    private EditText editTextCodigo;
    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextCorreo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextCodigo=findViewById(R.id.idCodigo);
        editTextNombre=findViewById(R.id.idNombre);
        editTextApellido=findViewById(R.id.idApellido);
        editTextCorreo=findViewById(R.id.idEmail);
    }
    public  void onClickInsertar(View view){



        //InsertarSinClases(view);
        insertarConClases(view);



    }
    public void InsertarSinClases(View view){
        ClientesHelper clientesHelper=new ClientesHelper(this,
                "clientesDB",null , 1);
        //abrir la BD
        // SQLiteDatabase sql = clientesHelper.getReadableDatabase();//lectura
        SQLiteDatabase sql =clientesHelper.getWritableDatabase();//escritura

        String nombre=editTextNombre.getText().toString();
        String apellido=editTextApellido.getText().toString();
        String correo=editTextCorreo.getText().toString();
        if(!nombre.equals("") && !apellido.equals("") && !correo.equals("")){
            ContentValues listaValores=new ContentValues();
            listaValores.put("Nombre",nombre);
            listaValores.put("Apellido",apellido);
            listaValores.put("Correo",correo);

            //enviar a la BD
            sql.insert("Clientes", null,listaValores);

            //cerrar la BD
            sql.close();


            //limpiar vista

            editTextNombre.setText("");
            editTextApellido.setText("");
            editTextCorreo.setText("");

            Toast.makeText(this, "Se inserto con exito",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "los campos son obligatorios",Toast.LENGTH_LONG).show();
        }
        //enviar datos  a la BD(vulnearble a INYECCION SQL)
        // sql.execSQL("INSERT INTO Clientes (Nombre,Apllido,Correo) " +
        //    "VALUES ('"+nombre+"','"+apellido+"','"+correo+"')");

        //crear una coleccion
    }
    public void insertarConClases(View view){
        Cliente cliente=new Cliente();
        ClienteDAL dal=new ClienteDAL(this);
        dal.open();
        String nombre=editTextNombre.getText().toString();
        String apellido=editTextApellido.getText().toString();
        String correo=editTextCorreo.getText().toString();

        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setCorreo(correo);

        if(!nombre.equals("") && !apellido.equals("") && !correo.equals("")){
            long cantidad=dal.insert(cliente);
            editTextNombre.setText("");
            editTextApellido.setText("");
            editTextCorreo.setText("");

            Toast.makeText(this, "Se inserto con exito",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Hubo algun error",Toast.LENGTH_LONG).show();
        }
    }
    public void buscarSinClases(View view){
        ClientesHelper clientesHelper=new ClientesHelper(this,
                "clientesDB",null , 1);
        //abrir la BD
        SQLiteDatabase sql = clientesHelper.getReadableDatabase();//lectura

        String codigo= editTextCodigo.getText().toString();

        // String consulta="SELECT * FROM Cliente";
        // String consulta="SELECT Codigo,Nombre,Apellido,Correo FROM Cliente";
        //String consulta="SELECT Codigo,Nombre,Apellido,Correo FROM Cliente ORDER BY Codigo";

        String consulta="SELECT Codigo,Nombre,Apellido,Correo" +
                " FROM Clientes WHERE Codigo ="+codigo;

        Cursor cursor =sql.rawQuery(consulta,null);
        //ciclo while
        if(cursor.moveToFirst()){
            editTextNombre.setText(cursor.getString(1));
            editTextApellido.setText(cursor.getString(2));
            editTextCorreo.setText(cursor.getString(3));
        }else {
            Toast.makeText(this,"No se encontraron registros en la tabla",Toast.LENGTH_LONG).show();
        }
        sql.close();
    }

    public  void onClickBuscar(View view){
       buscarSinClases(view);
       //buscarConClases(view);
    }
    public void buscarConClases(View view){
        Cliente cliente=new Cliente();// instanciar en el oncreate
        ClienteDAL dal=new ClienteDAL(this);
        dal.openGet();
        String codigo=editTextCodigo.getText().toString();
        dal.selectByCodigo(Integer.parseInt(codigo));
        if(cliente!=null){
            editTextNombre.setText(cliente.getNombre());
            editTextApellido.setText(cliente.getApellido());
            editTextCorreo.setText(cliente.getCorreo());
        }else {
            Toast.makeText(this,"Hubo algun error",Toast.LENGTH_LONG).show();
        }

    }



    public  void onClickEliminar(View view){
      // eliminarSinClases(view);
        eliminarConClases(view);
    }
    public void eliminarSinClases(View view){
        ClientesHelper clientesHelper=new ClientesHelper(this,
                "clientesDB",null , 1);
        //abrir la BD
        // SQLiteDatabase sql = clientesHelper.getReadableDatabase();//lectura
        SQLiteDatabase sql =clientesHelper.getWritableDatabase();//escritura

        String codigo=editTextCodigo.getText().toString();
        long cantidad= sql.delete("Clientes","Codigo="+codigo,null);
        sql.close();
        if(cantidad>0){
            Toast.makeText(this,"Registro eliminado",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"El regitro no se encontro para eliminar",Toast.LENGTH_LONG).show();
        }
        editTextNombre.setText("");
        editTextApellido.setText("");
        editTextCorreo.setText("");
    }
    public void eliminarConClases(View view){
        Cliente cliente=new Cliente();// instanciar en el oncreate
        ClienteDAL dal=new ClienteDAL(this);
        dal.open();
        String codigo=editTextCodigo.getText().toString();
       long cantidad= dal.delete(Integer.parseInt(codigo));
        if(cantidad>0){
            Toast.makeText(this,"Registro eliminado",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"El regitro no se encontro para eliminar",Toast.LENGTH_LONG).show();
        }
        editTextNombre.setText("");
        editTextApellido.setText("");
        editTextCorreo.setText("");
    }

    public  void onClickModificar(View view){

        //modificarSinClases(view);
        modificarConClases(view);
    }
    public void modificarSinClases(View view){
        ClientesHelper clientesHelper=new ClientesHelper(this,
                "clientesDB",null , 1);
        //abrir la BD
        // SQLiteDatabase sql = clientesHelper.getReadableDatabase();//lectura
        SQLiteDatabase sql =clientesHelper.getWritableDatabase();//escritura

        String codigo=editTextCodigo.getText().toString();
        String nombre=editTextNombre.getText().toString();
        String apellido=editTextApellido.getText().toString();
        String correo=editTextCorreo.getText().toString();

        ContentValues ListaValores= new ContentValues();
        ListaValores.put("Nombre",nombre);
        ListaValores.put("Apellido",apellido);
        ListaValores.put("Correo",correo);
        // ListaValores.put("Codigo",codigo);

        int cantidad=sql.update("Clientes",ListaValores,"Codigo="+codigo,null);
        sql.close();
        if(cantidad>0){
            Toast.makeText(this,"Registro modficado",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"El registro no se pudo modificar",Toast.LENGTH_LONG).show();
        }
    }
    public void modificarConClases(View view){
        Cliente cliente=new Cliente();// instanciar en el oncreate
        ClienteDAL dal=new ClienteDAL(this);
        dal.open();
        String id=editTextCodigo.getText().toString();
        String nombre=editTextNombre.getText().toString();
        String apellido=editTextApellido.getText().toString();
        String correo=editTextCorreo.getText().toString();
        cliente.setCodigo(Integer.parseInt(id));
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setCorreo(correo);
       long contar= dal.update(cliente);
       if(contar>0){
           Toast.makeText(this,"Registro modficado",Toast.LENGTH_LONG).show();

       }else {
           Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
       }
    }
    public void mostrarActivityListaV(View view){

        Intent intent =new Intent(this,MainActivityListarListView.class);
        this.startActivity(intent);
    }
}