package fisei.app_db_001;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClientesHelper extends SQLiteOpenHelper {

    private  String createTableClientes="CREATE TABLE Clientes(Codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Nombre TEXT NOT NULL," +
            "Apellido TEXT," +
            "Correo TEXT)";

    public ClientesHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    //ejecutar el sql para crear la estructura de las tablas
        sqLiteDatabase.execSQL(createTableClientes);
        //sql: segunda
        //sql tercera
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //borrar la tablas
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Clientes");

        //SQL para crear la tabla o tablas con la nueva estructura

        sqLiteDatabase.execSQL(createTableClientes);
    }
}
