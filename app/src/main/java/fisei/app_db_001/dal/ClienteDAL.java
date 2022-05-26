package fisei.app_db_001.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fisei.app_db_001.ClientesHelper;
import fisei.app_db_001.entidades.Cliente;

public class ClienteDAL {
    private ClientesHelper clientesHelper;
    private SQLiteDatabase sql;
    private Context context;


    public ClienteDAL(Context context){
        this.context=context;
    }
    public void open(){
        clientesHelper= new ClientesHelper(context,"clientesDB",null,1);
        sql=clientesHelper.getWritableDatabase();
    }
    public void openGet(){
        clientesHelper= new ClientesHelper(context,"clientesDB",null,1);
        sql=clientesHelper.getReadableDatabase();
    }
    public void close(){
        sql.close();
    }
    public long insert(Cliente cliente){
        long count=0;
        try{


                ContentValues listaValores=new ContentValues();
                listaValores.put("Nombre",cliente.getNombre());
                listaValores.put("Apellido",cliente.getApellido());
                listaValores.put("Correo",cliente.getCorreo());
                count=sql.insert("Clientes",null,listaValores);


        }catch (Exception e){
            throw e;
        }finally {
            sql.close();
        }
        return count;
    }
    public Cliente selectByCodigo(int codigo){

        Cliente cliente= null;
        try{
            String select="SELECT Nombre,Apellido,Correo " +
                    "FROM Clientes " +
                    "WHERE Codigo="+codigo;

            Cursor  cursor=sql.rawQuery(select,null);

            if(cursor.moveToFirst()){
                cliente=new Cliente();
                cliente.setNombre(cursor.getString(0));
                cliente.setApellido(cursor.getString(1));
                cliente.setCorreo(cursor.getString(2));
            }



        }catch (Exception e){
            throw e;
        }finally {
            sql.close();
        }
        return cliente;
    }
    public ArrayList<String> select(){
        ArrayList<String> list=null;
        try{

            String select="SELECT Codigo, Nombre, Apellido, Correo" +
                    "FROM Clientes";
            Cursor cursor=sql.rawQuery(select,null);
            if(cursor.moveToFirst()){
                list=new ArrayList<String>();

                do{
                    //deber
                    //Cliente cliente=new Cliente();
                    list.add(cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2)
                    +" "+cursor.getString(3));

                }while (cursor.moveToNext());
            }

        }catch (Exception e){
            throw e;
        }finally {

        }
        return  list;
    }
    public int delete(int codigo){
        int count=0;
        try{
            count=sql.delete("Clientes","Codigo="+codigo,null);
        }catch ( Exception e){
            throw e;
        }finally {
            sql.close();
        }
        return count;
    }
    public int update(Cliente cliente){
        int count=0;
        try {
            ContentValues values= new ContentValues();
            values.put("Nombre",cliente.getNombre());
            values.put("Apellido",cliente.getApellido());
            values.put("Correo",cliente.getCorreo());

            count=sql.update("Clientes",values,"Codigo="+cliente.getCodigo(),null);
        }catch ( Exception e){
            throw e;
        }finally {
            sql.close();
        }
        return count;
    }
    public void ConsultarListav(Cliente cliente,ArrayList<Cliente>lista){

        String select="SELECT Nombre,Apellido,Correo " +
                "FROM Clientes ";
        Cursor cursor=sql.rawQuery(select,null);
       do{
            cliente=new Cliente();
            cliente.setCodigo(cursor.getInt(0));
            cliente.setNombre(cursor.getString(1));
            cliente.setApellido(cursor.getString(2));
            cliente.setCorreo(cursor.getString(3));
            lista.add(cliente);
        }while (cursor.moveToNext());

    }

}
