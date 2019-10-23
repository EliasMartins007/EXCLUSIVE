package com.elias.martins;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

//classe criada por elias não pertencia ao projeto inicial 04/07/2019
public class Login extends AppCompatActivity {

    //declaro componentes que irei utilizar ex. campos da tela
    private Button btLogar; //botao de login da tela
    private EditText txtLogin = null;//campo login da tela
    private EditText txtSenha = null;//senha senha da tela



    //teste permissões
 //   private static final int REQUEST_TAKE_PHOTO = 1;//adicionei constante 26/07/2019  //parece ser somente para foto
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //teste tirar barra superior padrao do app   24/07/2019
        getSupportActionBar().hide(); //aqui a mágica


        setContentView(R.layout.activity_login);
        //permissões
        getPermissions();

        //recuperar componentes
        criarComponentes();

        validarDadosAcesso();
    }

    //region componentes da tela
    private void criarComponentes() {

        txtLogin = (EditText) findViewById(R.id.txtLogin);//login
        txtSenha = (EditText) findViewById(R.id.txtPassword);//senha


        btLogar = (Button) findViewById(R.id.btnLogin);
        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDadosAcesso();
            }
        });
    }
//endregion


    //region validar acesso via login
    private void validarDadosAcesso() {
        try {

            if (txtLogin.getText().toString().length() == 0) {
                txtLogin.setError("Digite Usuario ");
            } else if (txtSenha.getText().toString().length() == 0) {
                txtSenha.setError("Digite Senha");//não mostra mensagem devo verificar
            } else if (txtLogin.getText().toString().trim().equals("naty") && txtSenha.getText().toString().trim().equals("naty")) {
                usuarioLogado();
                //   return;//como é void não necessita esse return; 04/07/2019
            } else {
                Toast.makeText(this, "Usuario ou Senha invalidos !!!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    //endregion


    //region usuario logado com sucesso
    private void usuarioLogado() {
        Intent home = new Intent(Login.this, MainActivity.class);//problema aqui para acessar 04/07/2019
        startActivity(home);
        super.finish();

    }
    //endregion

    //region Permissões


    private void getPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.VIBRATE}, 1);

        }

    }

    //outros metodos necessarios
    @Override
    public void onRequestPermissionsResult(int requestCode, String permission[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatckPictureIntent();//metodo a ser criado  , se aceitar a permissão entra aki 26/07/2019
                }else
                    Toast.makeText(this, " O aplicativo não vai funcionar da maneira correta sem as permissões !!!", Toast.LENGTH_LONG).show();

            }
        }
    }

    private void dispatckPictureIntent() {//parece ser para foto photo  apenas ?????
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null; // para foto 26/07/2019
            try {
                File storeDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                photoFile = File.createTempFile("PHOTOAPP", ".jpg", storeDir);
                mCurrentPhotoPath = "file :" + photoFile.getAbsolutePath();
            } catch (IOException ex) {
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            }
            //            if (photoFile != null) {//esta entrando aki ???? 26/07/2019     // comentei essa parte para testes 26/07/2019
//                //para foto 26/07/2019
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
        }

    }


    //Procurar Foto no device
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                //  ImageView imagem = (ImageView) findViewById(R.id.imagem);
                Bitmap bml = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(mCurrentPhotoPath)));
                //      imagem.setImageBitmap(bml);  // comentei ppois não terei foto nessa atividade agora !!! 26/07/2019
            } catch (FileNotFoundException fnex) {
                Toast.makeText(getApplicationContext(), "Foto não encontrada", Toast.LENGTH_LONG).show();
            }

        }

    }

    //endregion

}
