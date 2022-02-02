package com.example.a308_e41registrodeparticipantes;

import static com.example.a308_e41registrodeparticipantes.AdminBDEventos.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Nueva variable con los cambios para mostrar en DialogInterface
    private String variableParaGitHub = 'Variable agregada para que se muestren los cambios del fork y se puede hacer un pull request';

    // 3.a referencias a las vistas
    private EditText mClave, mNombre, mInstitucion, mCorreo, mCantidad;

    //5.f se agregan referencias para los botones
    private Button mBtnAgregar, mBtnModificar, mBtnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Poner el icono en el action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //3.b se instancian las vistas
        mClave = findViewById(R.id.clave);
        mNombre = findViewById(R.id.nombre);
        mInstitucion = findViewById(R.id.institucion);
        mCorreo = findViewById(R.id.correo);
        mCantidad = findViewById(R.id.pago);

        //5.f se instancian las vistas
        mBtnAgregar = findViewById(R.id.btn_agregar);
        mBtnModificar = findViewById(R.id.btn_modificar);
        mBtnEliminar = findViewById(R.id.btn_eliminar);

        limpiarCampos();
    }

    public void fn_Agregar(View view) {
        //4.a se instancia y se activa la b. de datos
        AdminBDEventos adminBDEventos = new AdminBDEventos(this);
        SQLiteDatabase bDatos = adminBDEventos.getWritableDatabase();

        // 4.b se obtiene los datos del usuario
        String cad_clave = mClave.getText().toString();
        String nombre = mNombre.getText().toString();
        String institucion = mInstitucion.getText().toString();
        String correo = mCorreo.getText().toString();
        String cad_cantidad = mCantidad.getText().toString();

            if (!cad_clave.isEmpty() && !nombre.isEmpty() && !institucion.isEmpty() && !correo.isEmpty() && !cad_cantidad.isEmpty()) {
                //se convierten los valores que deben de ser numericos
                int clave = Integer.parseInt(cad_clave);
                double cantidad = Double.parseDouble(cad_cantidad);

                //se prepara el Content Values
                ContentValues registro = new ContentValues();
                registro.put(CAMPO1, clave);
                registro.put(CAMPO2, nombre);
                registro.put(CAMPO3, institucion);
                registro.put(CAMPO4, correo);
                registro.put(CAMPO5, cantidad);

                //se crea el registro y se insertan los valores
                bDatos.insert(NOMBRE_TABLA, null, registro);
                Toast.makeText(this, "El participante " + clave + " agregado", Toast.LENGTH_SHORT).show();

                //4.e se vacían las vistas y se cierra la b. de datos
                limpiarCampos();

                bDatos.close();

            } else
                Toast.makeText(this, "Debe de llenar todos los campos", Toast.LENGTH_SHORT).show();
        }

    // 5
    public void fn_Consultar(View view)
    {
        //5.a se instancia y se abre la b. datos para la lectura
        AdminBDEventos adminBDEventos = new AdminBDEventos(this);
        SQLiteDatabase bDatos = adminBDEventos.getReadableDatabase();

        String cad_clave = mClave.getText().toString();
        if (cad_clave.isEmpty())
            Toast.makeText(this, "Escriba una clave", Toast.LENGTH_SHORT).show();
        else
        {
            //5.b se prepara el query para la consulta
            String queryConsulta = "select * from "+NOMBRE_TABLA + " where " + CAMPO1 + "=" + cad_clave;
            // select * from participante where clave=?

            Cursor cursor = bDatos.rawQuery(queryConsulta,null);

            //5.c se extraen los datos del cursor y se colocan en las vistas
            if (cursor.moveToFirst())
            {
                mNombre.setText(cursor.getString(1));
                mInstitucion.setText(cursor.getString(2));
                mCorreo.setText(cursor.getString(3));
                mCantidad.setText(Double.toString(cursor.getDouble(4)));

                //5.e se deshabilita el campo clave
                mClave.setEnabled(false);

                //5.f se habilitan y se deshabilitan algunas vistas
                mBtnAgregar.setEnabled(false);
                mBtnModificar.setEnabled(true);
                mBtnEliminar.setEnabled(true);

            }else {
                Toast.makeText(this, "No existe la clave " + cad_clave, Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }
        }
        bDatos.close();
    }

    //6 funciones para limpiar campos
    public void fn_LimpiarCampos(View view)
    {
        limpiarCampos();
    }
    private void limpiarCampos()
    {
        mClave.setText("");
        mNombre.setText("");
        mInstitucion.setText("");
        mCorreo.setText("");
        mCantidad.setText("");

        //6.b se habilita el campo clave
        mClave.setEnabled(true);

        //6.c se habilitan y se deshabilitan algunas vistas
        mBtnAgregar.setEnabled(true);
        mBtnModificar.setEnabled(false);
        mBtnEliminar.setEnabled(false);
    }

    public void fn_Modificar(View view)
    {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("M o d i f i c a r");
        dialogo.setIcon(R.drawable.ic_modificar);
        dialogo.setMessage("¿Esta seguro que quiere modificar los datos?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                modificar();
            }
        });
        dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }
        });
        dialogo.show();
    }

    public void modificar()
    {
        //8.a se instancia la base de datos y se abre para escritura
        AdminBDEventos adminBDEventos = new AdminBDEventos(this);
        SQLiteDatabase bDatos = adminBDEventos.getWritableDatabase();

        //8.b se colocan los valores en un Content Values
        ContentValues valores = new ContentValues();
        valores.put(CAMPO2, mNombre.getText().toString());
        valores.put(CAMPO3, mInstitucion.getText().toString());
        valores.put(CAMPO4, mCorreo.getText().toString());
        valores.put(CAMPO5, Double.parseDouble(mCantidad.getText().toString()));

        //8.c actualizar registros
        int clave = Integer.parseInt(mClave.getText().toString());
        bDatos.update(NOMBRE_TABLA, valores, CAMPO1+" = "+clave, null);

        //8.d
        limpiarCampos();

        bDatos.close();

        Toast.makeText(this,"Registro modificado",Toast.LENGTH_SHORT).show();
    }

    // 9
    public void fn_Eliminar(View view)
    {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("E l i m i n a r");
        dialogo.setIcon(R.drawable.ic_delete);
        dialogo.setMessage("Confirme si realmente desea eliminar");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                eliminar();
            }
        });
        dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }
        });
        dialogo.show();
    }

    //10. se realiza la eliminación del registro
    private void eliminar()
    {
        //10.a
        AdminBDEventos adminBDEventos = new AdminBDEventos(this);
        SQLiteDatabase bDatos = adminBDEventos.getWritableDatabase();

        //10.b eliminación del registro
        int clave = Integer.parseInt(mClave.getText().toString());
        bDatos.delete(NOMBRE_TABLA, CAMPO1 + " = "+ clave,null);
        Toast.makeText(this, "Regitro ",Toast.LENGTH_SHORT).show();
        limpiarCampos();
        bDatos.close();
    }

    private void Modificador(){
        //Este metodo solo muestra evidencia de que modifique codigo
        Toast.makeText(this, "Aqui estuvo Misael Alexander Salazar Hernández",Toast.LENGTH_SHORT).show();
    }

    //11 crear la funcón para ver la lista
    public void fn_VerLista(View view)
    {
        //11.b.i preparamos la cadena de la lista y el total
        String cad = "";
        String cad2 = "";
        double total = 0;

        //11.b.ii
        AdminBDEventos adminBDEventos = new AdminBDEventos(this);
        SQLiteDatabase bDatos = adminBDEventos.getReadableDatabase();

        //11.b.iii accedemos a la tabla participantes para extraer su contenido
        String query = "SELECT * FROM " + NOMBRE_TABLA;
        Cursor cursor = bDatos.rawQuery(query,null);
        if (cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                // se obtiene la clave, el nombre y la cantidad
                int clave = cursor.getInt(0);
                String nombre = cursor.getString(1);
                double cantidad = cursor.getDouble(4);

                //creamos un nuevo renglón en cad
                cad+= (clave + "   " + nombre+ "\n");
                cad2+= ("$ " + cantidad+ "\n");

                //sumar al total
                total+= cantidad;
            }
            // 11.b.iv se prepara el intent
            Intent listaGeneral = new Intent(this, ListaGeneral.class);
            listaGeneral.putExtra("LISTA", cad);
            listaGeneral.putExtra("LISTA2", cad2);
            listaGeneral.putExtra("TOTAL", total);
            startActivity(listaGeneral);
        }else{
            Toast.makeText(this, "No hay participantes registrados",Toast.LENGTH_SHORT).show();
        }
    }
}
