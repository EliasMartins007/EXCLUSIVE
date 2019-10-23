package com.elias.martins.uteis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.elias.martins.MainActivity;
import com.elias.martins.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Uteis {
    private Context myContext = null;

    public Uteis(Context context) {

        myContext = context;
    }


    public static void Alert(Context context, String mensagem) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //adicionando um titulo a nossa mensagem de alerta //   Titulo da janela
        alertDialog.setTitle(R.string.app_name);

        //mensagem a ser exibida     // Mensagem do alert
        alertDialog.setMessage(mensagem);

        //cria um botao com texto ok sem ação    // botao do alert
        alertDialog.setPositiveButton("OK", null);

        //mostra a mensagem na tela
        alertDialog.show();

    }

    //region exibir Hora
    public void exibirhoraAtualCelular() {//utilizar na principal para exibir hora que entrou no "app"  03/07/2019 não sei como utilizar em outra activity
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm:ss");
            //ou
            SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");

            Date data = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(data);
            Date data_atual = cal.getTime();

            String data_completa = dateFormat.format(data_atual);
            String hora_atual = dateFormat_hora.format(data_atual);

            Log.i("data_completa", data_completa);
            Log.i("data_atual", data_atual.toString());
            Log.i("hora_atual", hora_atual);

        } catch (Exception ex) {
            Mensagem("Erro :" + ex.getMessage());//teste mensagem 03/07/2019
        }
    }
    //endregion


    //mensagem personalisada (log)12/06/2019
    private void Mensagem(String msg) {//não extend activity por isso não consigo utilizar gtAplication ou context aki
        Toast.makeText(myContext, msg, Toast.LENGTH_SHORT).show();

    }

    //devo colocar o metodo icone na tela principal aki 25/07/2019

}
