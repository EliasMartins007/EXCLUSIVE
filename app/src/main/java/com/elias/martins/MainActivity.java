package com.elias.martins;

import android.content.Intent;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //teste log
    private static final String TAG = "MyActivityLog";//ainda a implementar  27/06/2019

    ListView listViewOpcoesDaTela;

    //teste data
    TextView txtHora;
    //Uteis hora_atual;


    //vibrator
    Vibrator vibrator;
    long milliseconds = 500;//defini tempo personalizado para vibrar 27/06/2019

    //para Diretorio do Aplicativo27/06/2019
    private String diretorioApp;//path
    private File diretorio;//arquivo texto dentro do Diretorio // tenho de alterar aki futuramente 27/06/2019
    //    private String nomeDiretotio = getResources().getString(R.string.app_name).toUpperCase();//nome do diretorio que será criado tenho que ver se é possivel pegar o nome do app tipo na tela sobre 27/06/2019
    private String nomeDiretotio = "EXCLUSIVE EXTENSÕES CAPILARES";//"BEAUTY SALON";//"APP_NATALIA" // ALTEREI NOME 01/07/2019

    //teste subDiretorio 29/07/2019
    public String pathFolderImagens = "";//static
    public String pathFolderVideos = "";//static

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//teste tirar barra superior padrao do app   24/07/2019
        getSupportActionBar().hide(); //aqui a mágica
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//exemplo joão 25/07/2019


        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);//global 27/06/2019

        setContentView(R.layout.activity_main);//(R.layout.activity_main)  alterei para teste da tela login 04/07/2019


        try {


            gerarDiretorioDoApp();
            vibrator.vibrate(milliseconds);//vibrar no inicio do app


            //carrega metodo de inicialização dos componentes 27/06/2019  rexupera listview da tela principal
            this.criarComponentes();
            this.criarEventos();
            this.carregaOpcoesDaLista();
            exibirhoraAtualCelular();//teste exibir hora 03/07/2019
        } catch (Exception ex) {
            ex.printStackTrace();//não sei porq?
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();//mensagem do erro padrão 27/06/2019

        }

    }

    //region criarComponentes
    private void criarComponentes() {
        //vinculo a lista da tela principal que declarei 27/06/2019
        listViewOpcoesDaTela = (ListView) findViewById(R.id.listViewOpcoes);
        //teste hora
        txtHora = (TextView) findViewById(R.id.txtHora);//para exibir hora na tela


    }

    //endregion

    //region carrega opções da minha lista da tela principal
    private void carregaOpcoesDaLista() {
        try {
            String[] itens = new String[7]; // defino aki o numero de opçoes da minha lista //originalmente eram 2 depois 5

            itens[0] = "CADASTRAR CLIENTE";
            itens[1] = "LISTAR TODOS OS CLIENTES";
            itens[2] = "PRODUTOS";//a implementar 27/06/2019
            itens[3] = "ENVIAR EMAIL";//a implementar 27/06/2019
            itens[4] = "MANUTENÇÕES";//a implementar 27/06/2019
            itens[5] = "AGENDAMENTOS";//a implementar 08/07/2019
            itens[6] = "SOBRE";//a implementar 27/06/2019

            ArrayAdapter<String> arrayItens = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, itens);//aki defini meu list view tamanho do texto  original  android.R.layout.simple_list_item_1, itens)
            listViewOpcoesDaTela.setAdapter(arrayItens);

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //endregion

    //region criarEventos
    protected void criarEventos() {
        try {//evento de click em iten da lista para redirecionar para outra tela 16/09/2019

            listViewOpcoesDaTela.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String opcoesDoMenu = ((TextView) view).getText().toString();
                    redirecionarTela(opcoesDoMenu);
                }
            });
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    //endregion


    //region redirecionar opções do menu para tela escolhida

    protected void redirecionarTela(String opcoesDoMenu) {

        Intent intentRedirecionarMenu;

        if (opcoesDoMenu.equals(("CADASTRAR CLIENTE"))) {
            //redireciona para tela de cadastro de novos clientes 27/06/2019
            intentRedirecionarMenu = new Intent(this, CadastrarActivity.class);
            startActivity(intentRedirecionarMenu);
            //finalizar activity anterior
            finish();

        } else if (opcoesDoMenu.equals(("LISTAR TODOS OS CLIENTES"))) {
            //redireciona para tela de Clientes cadastradios no banco
            intentRedirecionarMenu = new Intent(this, ConsultarActivity.class);
            startActivity(intentRedirecionarMenu);
            //finalizar activity anterior
            finish();


        } else if (opcoesDoMenu.equals(("PRODUTOS"))) {
            //redireciona para tela de Clientes cadastrados no banco
            intentRedirecionarMenu = new Intent(this, ProdutoActivity.class);//ainda tenho que definir se sera essa classe ou criarei uma expecifica para manuteção 15/07/2019  elias
            startActivity(intentRedirecionarMenu);
            //finalizar activity anterior
            finish();


        } else if (opcoesDoMenu.equals(("SOBRE"))) {
            //redireciona para tela Sobre do app
            intentRedirecionarMenu = new Intent(this, Sobre.class);//class aparece somente apos incluir onCreate na class 27/06/2019
            startActivity(intentRedirecionarMenu);
            //finalizar activity anterior
            //       finish(); //teste não finalizr activity para poder voltar pela setinha do device 28/06/2019   ,  Deu certo "OK"

        } else if (opcoesDoMenu.equals(("AGENDAMENTOS"))) {
            //redireciona para tela Horario
            intentRedirecionarMenu = new Intent(this, AgendamentoActivity.class);
            startActivity(intentRedirecionarMenu);
            //finalizar activity anterior
            //    finish(); // não pode ter finish aki pois tem de poder voltar a tela anterior
        } else if (opcoesDoMenu.equals(("MANUTENÇÕES"))){
            //REDIRECIONAR para tela Manutenções  // 08/08/2019
            intentRedirecionarMenu = new Intent(this, ServicoActivity.class);
            startActivity(intentRedirecionarMenu);
            //finalizar actyivity anterior (no caso a o menu pricipal)
            finish();

        }else
        Toast.makeText(this, "A implementar", Toast.LENGTH_SHORT).show();

    }
    //endregion


    //region Mensagem Personalizada
    private void Mensagem(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    //endregion

    //region gerarDiretorio do aplicativo
    private void gerarDiretorioDoApp() {//adicionei path(String path)02/07/2019

        try {

            //deve se definir o local aonde sera criado o Diretorio no celular
//            diretorioApp = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + nomeDiretotio + "/";// funcionando 02/07/2019  diretorioApp = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + nomeDiretotio + "/";

            //utilizado no inicio do app
            //diretorio esta publico dependendo da aplicação diretorio não pode ser publico
//            diretorio = new File(diretorioApp);//cria arquivo 27/06/2019   duvida aki ainda

            //            if (!diretorio.exists()) {
//           //se quiser exibir alguma mensagem aki 27/06/2019
//            diretorio.mkdirs();//cria o diretorio
//            Mensagem("Diretorio criado com sucesso !!!");
//            } else
//              //  diretorio.mkdirs();//olhar na documentação sobre diferenças entre mkdirs e mkdir  27/06/2019
//              return;//continua aplicação normalmnte já que diretorio já existe 27/06/2019


            //diretorio esta em no armazenamento do celular agora 02/07/2019
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath(), "/" + nomeDiretotio + "/");//File mediaStorageDir = new File("/sdcard/", "JCG Camera");

            // mediaStorageDir.mkdirs();
            if (!mediaStorageDir.exists()) {
//           //se quiser exibir alguma mensagem aki 27/06/2019
                mediaStorageDir.mkdirs();//cria o diretorio


//                //teste subdiretorio 29/07/2019 elias  ainda em a implementar 30/07/2019
//                String nameFolderImagens = "IMAGENS";
//                String nameFolderVideos = "VIDEOS";
//                File folderApp = new File(Environment.getExternalStorageDirectory()+ File.separator + nomeDiretotio);
//                File folderImagens = new File(folderApp.getAbsolutePath()+
//                File.separator + nameFolderImagens);
//                File folderVideos = new File(folderApp.getAbsolutePath()+ File.separator+nameFolderVideos);
//                pathFolderImagens = folderImagens.getAbsolutePath();
//                pathFolderVideos = folderVideos.getAbsolutePath();
//
//                //fim teste

                Mensagem("Diretorio criado com sucesso !!!");
            } else
                //  diretorio.mkdirs();//olhar na documentação sobre diferenças entre mkdirs e mkdir  27/06/2019
                return;//continua aplicação normalmnte já que diretorio já existe 27/06/2019
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //endregion

    //ainda tenho de ver essa questão de criar o arquivo qual a real utilidade nesse app 27/06/2019
    public void exibirhoraAtualCelular() {//utilizar na principal para exibir hora que entrou no "app"  03/07/2019
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

            txtHora.setText(hora_atual);//exibir hora no textView 03/07/2019

        } catch (Exception ex) {
            Mensagem("Erro :" + ex.getMessage());//teste mensagem 03/07/2019
        }
    }

}
