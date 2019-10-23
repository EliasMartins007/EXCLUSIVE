package com.elias.martins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ServicoActivity extends AppCompatActivity {
    Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedStancestate) {
        super.onCreate(savedStancestate);

        //teste tirar barra superior padrao do app   24/07/2019
        getSupportActionBar().hide(); //aqui a mágica

        setContentView(R.layout.activity_servicos);


        btnVoltar = (Button) findViewById(R.id.btnVoltar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);

                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {//para desabilitar o botão voltar do celular e obrigar o usuario a utilizar o botão voltar do app 15/07/2019


    }

}
