package com.elias.martins;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class Splash extends AppCompatActivity {

    //teste internet 18/07/2019
    private final static String APP_PREFERENCE = "br.com.tutorialandroid.shortcut.MinhasPreferencias";
    private final static String PREFERENCE_TAG = "br.com.tutorialandroid.shortcut.ATALHO";


    //definição de variavel com Tempo de execução da minha Splash
    private static int SPLASH_TIME_OUT = 3000;


    //teste exemplo joao praxis 25/07/2019
    private SharedPreferences appPref;
    private final String PREFS_NAME = "MyprefsFile";
    private boolean isFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//teste tirar barra superior padrao do app   24/07/2019
        getSupportActionBar().hide(); //aqui a mágica


        //executa splash
        setContentView(R.layout.activity_splash);

        Toast.makeText(this, "Aguarde carregamento da aplicação", Toast.LENGTH_SHORT).show();

        //teste shortcut 25/07/2019

        // verificaPrimeiroAcesso();
        createShortCut();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent home = new Intent(Splash.this, MainActivity.class);
//
//                //inicio minha tela splash// ao inves de iniciar app dessa forma mudei para chamar via metodo 01/07/2019
//                startActivity(home);
//                finish();

                iniciarApp();
            }
        }, SPLASH_TIME_OUT);


    }

    private void iniciarApp() {
        Intent home = new Intent(Splash.this, Login.class);//mudei MainActivity para Login 07/04/2019

        //dou start na minha tela splash
        startActivity(home);
        finish();
    }


    private void verificaPrimeiroAcesso() { // primeiro acesso ao app
        appPref = getSharedPreferences(PREFS_NAME, 0);
        isFirstTime = appPref.getBoolean("my_first_time", true);

        Boolean isAppInstalled = appPref.getBoolean("IsAppInstalled", false);
        if (isFirstTime) {//(!isFirstTime)
            createShortCut();  //teste icone na tela principal 18/07/2019  25/07/2019
            appPref.edit().putBoolean("my_first_time", false).commit();
        }
    }

    //icone na tela principal

    private void createShortCut() {

        SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isAppInstalled = appPreferences.getBoolean("isAppInstalled", false);
        // verifica se o atalho já foi criado anteriormente
        //if (!getSharedPreferences(APP_PREFERENCE, Activity.MODE_PRIVATE).getBoolean(PREFERENCE_TAG, false)) {//false  original
        if (isAppInstalled == false) {
            //        try {//retirei o try pois não tem em nenhum exemplo encontrado 18/07/2019
            Intent shortcutIntent = new Intent(getApplicationContext(), Splash.class);//alterei de Maictivy para splash 25/07/2019

            shortcutIntent.setAction(Intent.ACTION_MAIN);

            Intent addIntent = new Intent();
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getResources().getString(R.string.app_name));// THOTH ECV // EXEMPLO JOAO PRAXIS
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                    Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.app_principal));//, R.drawable.ic_launcher)

            addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            getApplicationContext().sendBroadcast(addIntent);


            //teste 25/07/2019 elias
            // salva informação de que o atalho foi criado
            SharedPreferences.Editor editor = appPreferences.edit();
            editor.putBoolean("isAppInstalled", true);
            editor.commit();
 //           getSharedPreferences(APP_PREFERENCE, Activity.MODE_PRIVATE).edit().putBoolean(PREFERENCE_TAG, true).apply();
//        } catch (Exception ex) {
//            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
//        }
        }
        //}
    }


}
