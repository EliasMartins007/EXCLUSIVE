package com.elias.martins;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.elias.martins.model.PessoaModel;
import com.elias.martins.repository.PessoaRepository;
import com.elias.martins.uteis.LinhaConsultarAdapter;

import java.util.List;

public class ConsultarActivity extends AppCompatActivity {
    //criando um obj do tipo ListView para receber os registro de um adapter
    ListView listViewClientes;

    //criando o botão voltar a tela principal
    Button buttonVoltar;


    //teste numero de registros
    TextView txtstatus;//18/07/2019


    //pesuisa 19/07/2019  // acredito que os objetos fiquem no consultar não no linhaConsultar a validar ainda
    //    String[] lsFrutas ; não funcionou
    EditText eText;
    //    ArrayList<String> pesquisa = new ArrayList<String>();//ainda em teste 19/07/2019
    //fim pesquisa


    @SuppressLint("ResourcesAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //teste tirar barra superior padrao do app   24/07/2019
        getSupportActionBar().hide(); //aqui a mágica


        setContentView(R.layout.activity_consultar);


        //recupera o componente da tela
        listViewClientes = (ListView) this.findViewById(R.id.listViewClientes);

        buttonVoltar = (Button) this.findViewById(R.id.buttonVoltar);


        //recuperando componentes da pesquisa 19/07/2019
        eText = (EditText) this.findViewById(R.id.eText);//teste ainda


        //teste numero de registros
        //       txtstatus = (TextView) this.findViewById(R.id.txtstatus);//teste 18/07/2019    subistitui pelo pesuisar cliente mais util no momento 19/07/2019


        //chama metodo que carrega as clientes cadastradas
        this.carregarClientesCadastradas();

        //chama o metodo do botão voltar
        this.criarEventos();


        //teste seta numero de registros encontrados
        //this.numeroDeRegistros();// fechou  app tenho de olhar depois 18/07/2019

        //função responsavel pela pesquisa
        //    pesquisar(); // comentei pois estava dando erro 19/07/2019

        //fim teste
//        eText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                //nada
//            }
//
//            @Override
//            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                //pesquisar();
//                listViewClientes.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, pesquisa));
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                //nada
//            }
//        });

    }


    private void criarEventos() {
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertVoltar();
            }
        });
    }

    private void alertVoltar() {

        //cria o gerenciador do AlertDialog
        AlertDialog.Builder alerta = new AlertDialog.Builder(ConsultarActivity.this);

        //define o  titulo
        alerta.setTitle("Aviso");


        //definir icone para alert em caso de usar alert padrão
        alerta.setIcon(R.mipmap.voltar); // se quiser colocar icone ! 25/06/2019


        //define a mensagem
        alerta.setMessage("voltar para tela principal ? ");

        //define um botão com positivo
        alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //redireciona para tela principal
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);// Aki ação do botão de voltar da tela consulta para principal 13/12/2018
                startActivity(intentMainActivity);

                //finaliza atividade atual
                finish();


                //  Toast.makeText(ConsultarActivity.this, "positivo = " + which, Toast.LENGTH_LONG).show();
            }
        });

        // alerta.setCancelable(false); // outro exemplo 25/06/2019
        //define um botão com negativo
        alerta.setNegativeButton("NÃO", null);

        //exibe
        alerta.show();

    }


    @Override
    public void onBackPressed() {//desabilitar botão voltar do celular nessa tela
        // não chame o super desse método
    }

    private void carregarClientesCadastradas() {

        try {
            //antes de carregar a Tela deve verificar se existem registros no banco de dados   ainda tenho que implentar essa parte  27/06/2019

            PessoaRepository pessoaRepository = new PessoaRepository(this);

            //busca as clientes cadastradas 28/06/2019
            List<PessoaModel> clientes = pessoaRepository.SelecionarTodos();

            //seta o adapter com os registros de clientes encontradas 28/06/2019
            listViewClientes.setAdapter(new LinhaConsultarAdapter(this, clientes));//adicionei null

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void numeroDeRegistros() {// esse metodo vai no banco na na minha activity  18/07/2019
        try {
            //      databaseList() = openOrCreateDatabase( ,   MODE_WORLD_READABLE, null );
            //("select*from tb_pessoa",  null);

        } catch (Exception ex) {

        }
    }

    //função responsavel pela pesquisa
//    public void pesquisar() {
//        try {
//            int textlength = eText.getText().length();
////            pesquisa.clear();  // limpa a tela quando digito no editext
//
//
//
//            for (int i = 0; i < lsFrutas.length; i++) {//meu array esta vindo null 19/07/2019
//                if (textlength <= lsFrutas[i].length()) {
//                    if (eText.getText().toString().equalsIgnoreCase((String) lsFrutas[i].subSequence(0, textlength))) {
//                        pesquisa.add(lsFrutas[i]);
//
//                    }
//                }
//            }
//
//        } catch (Exception ex) {
//            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();//teste ainda 19/07/2019
//        }
//    }

}
