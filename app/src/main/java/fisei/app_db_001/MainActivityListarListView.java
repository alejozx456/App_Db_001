package fisei.app_db_001;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import fisei.app_db_001.dal.ClienteDAL;
import fisei.app_db_001.entidades.Cliente;

public class MainActivityListarListView extends AppCompatActivity {

    public ListView listaV;
    ArrayList<String> listaInformacion;
    ArrayList<Cliente> listaClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_listar_list_view);
        listaV=findViewById(R.id.idListaPersonasV);

        listarClientesV();
        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);
        listaV.setAdapter(adapter);

    }
    private void listarClientesV(){
        try {
            listaInformacion=new ArrayList<>();

            ClientesHelper clientesHelper=new ClientesHelper(this,
                    "clientesDB",null , 1);
            //abrir la BD
            SQLiteDatabase sql = clientesHelper.getReadableDatabase();//lectura
            String consulta="SELECT * FROM Clientes";

            Cursor cursor =sql.rawQuery(consulta,null);
            if(cursor.moveToFirst()){
                do{
                    listaInformacion.add(cursor.getString(1));
                }while (cursor.moveToNext());
            }else {
                Toast.makeText(this,"Ocurrio algo",Toast.LENGTH_LONG).show();
            }
            sql.close();



        }catch (Exception e){
            Toast.makeText(this,"Ocurrio algo",Toast.LENGTH_LONG).show();
        }


  }

  }
