package com.elias.martins;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Sobre extends AppCompatActivity {
    //teste exemplo joão praxis
    private TextView lblNomeApp = null;
    private TextView lblVersaoApp = null;

    //elias
    private Button endereço;//para direcionamento para site que eu desejar 27/06/2019

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //teste tirar barra superior padrao do app   24/07/2019
        getSupportActionBar().hide(); //aqui a mágica

//        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);//global 27/06/2019

        setContentView(R.layout.activity_sobre);

        try {

//            vibrator.vibrate(milliseconds);//vibrar no inicio do app

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            iniciarComponentes();

        } catch (Exception ex) {
            ex.printStackTrace();//não sei porq?
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();//mensagem do erro padrão 27/06/2019

        }

    }

    private void iniciarComponentes() {
        //recuperar Componentes da TelaSobre 27/06/2019
        lblNomeApp = (TextView) findViewById(R.id.lblNomeApp);
        lblVersaoApp = (TextView) findViewById(R.id.lblVersaoApp);

        getInfoApp();
        //  Intent();  //acredito que esse metodo seja para direcionar para o site expecifico da empresa que eu definir aki 27/06/2019
    }


    //region informações do aplicativo "NOME E VERSÃO"
    private void getInfoApp() {
        PackageManager manager = getPackageManager();
        PackageInfo info = null;

        try {

            info = manager.getPackageInfo(getPackageName(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();//duvida
        }

        lblNomeApp.setText(getResources().getString(R.string.app_name).toUpperCase());//("AppPessoa:".toUpperCase());
        lblVersaoApp.setText("Versão:  "+ info.versionName);//pega versão atual do arquivo gradlle, devo alterar em toda atualização que fizer
    }
    //endregion

    //region Direcionar para site no link da tela
//    private void internet() {
//
//        endereco = (Button) findViewById(R.id.btSyncMunicipio);//botao da tela site   funcionando perfeitamente 10/08/2018
//        try {
//            endereco.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String endereco = ("http://www.ugcpraxis.com.br");
//                    Uri uri = Uri.parse(endereco);
//
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intent);
//                }
//            });
//
//        } catch (Exception ex) {
//            Toast.makeText(getApplication(), ex.getMessage(), Toast.LENGTH_LONG).show();
//
//        }
//    }
    //endregion


    //teste botao voltar do celular 28/06/2019


}
