package com.example.a308_e41registrodeparticipantes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ListaGeneral extends AppCompatActivity {

    //11.c.ii referencias a las vistas
    private TextView mLista, mLista2, mTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_general);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //11.c.ii se instancian las vistas
        mLista = findViewById(R.id.lista);
        mLista2 = findViewById(R.id.lista2);
        mTotal = findViewById(R.id.total);

        //11.c.iii
        Intent intento = getIntent();
        String cad = intento.getStringExtra("LISTA");
        String cad2 = intento.getStringExtra("LISTA2");
        double total = intento.getDoubleExtra("TOTAL",0);

        //11.c.iv se depositan los valores en las vistas
        mLista.setText(cad);
        mLista2.setText(cad2);
        mTotal.setText("Total pagado: $ "+total);

    }
}