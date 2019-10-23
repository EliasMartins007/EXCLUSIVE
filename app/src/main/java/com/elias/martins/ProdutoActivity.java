package com.elias.martins;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.elias.martins.model.ProdutoModel;
import com.elias.martins.repository.ProdutoRepository;

public class ProdutoActivity extends AppCompatActivity {
    EditText editProduto;
    EditText editValor;
    EditText editQuantidade;
    ProdutoRepository produto;//controller 17/07/2019
    Cursor cursor;
    String[] nomeCampos;
    int[] idViews;
    Spinner produtos;
    ImageButton btnCadastrar;
    ImageButton btnAlterar;
    ImageButton btnExcluir;



    //teste botão voltar 18/07/2019
    Button btnVoltat;


    ProdutoModel teste;//teste elias questão dos parametros no preencherAdaptador 17/07/2019 ainda não validei


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //teste tirar barra superior padrao do app   24/07/2019
        getSupportActionBar().hide(); //aqui a mágica


        setContentView(R.layout.activity_cadastrar_produtos);

        teste = new ProdutoModel();//teste elias questão dos parametros no preencherAdaptador 17/07/2019 ainda não validei


        //recuperando obj da tela
        produto = new ProdutoRepository(getBaseContext());// a implementar 17/07/2019
        btnCadastrar = (ImageButton) findViewById(R.id.btnCadastrar);
        btnAlterar = (ImageButton) findViewById(R.id.btnAlterar);
        btnExcluir = (ImageButton) findViewById(R.id.btnApagar);
        editProduto = (EditText) findViewById(R.id.editProduto);
        editQuantidade = (EditText) findViewById(R.id.editQuantidade);
        editValor = (EditText) findViewById(R.id.editValor);
        produtos = (Spinner) findViewById(R.id.spinnerProd);
        // preencherAdaptador(teste);//teste elias questão dos parametros no preencherAdaptador 17/07/2019 ainda não validei


        btnVoltat = (Button) findViewById(R.id.btnVoltar);

        criarEventos();
    }

    private void criarEventos() {//evento para voltar a tela principal deverei alterar todos os outros para dentro desse metodo 18/07/2019

        btnVoltat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {// para desabilitar o botao voltar do android nessa tela 17/07/2019
        //não chamo o super desse  metodo
    }

//    @Override
//    public void onClick(View v) {//TEREI DE VALIDAR ESSE NOVO ERRO 25/07/2019
//
//    }


    //preencherAdaptador
    public void preencherAdaptador(ProdutoModel produtoModel) {//teste de parametros 17/07/2019

        try {
            //teste elias questão dos parametros no preencherAdaptador 17/07/2019 ainda não validei
            cursor = produto.preencheSpinner(teste);//somente erro aki devido aos parametros que não existem no outro exemplo
            nomeCampos = new String[]{"id_produto" + produtoModel.getProdutoId(), "produto_nome" + produtoModel.getProdutoNome(), "" + produtoModel.getProdutoValor(), "" + produtoModel.getProdutoQuantidade()};
            idViews = new int[]{R.id.txtNomedoProduto, R.id.txtQuantidadedoProduto, R.id.txtValorUnitario, R.id.txtValorTotal};
            SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(), R.layout.spinnerprodutos, cursor, nomeCampos, idViews, 0);//parametros contexto, layout, dados
            produtos.setAdapter(adaptador);//adicionando o adapter na ListView

            btnExcluir.setVisibility(View.INVISIBLE);
            btnAlterar.setVisibility(View.INVISIBLE);
            btnCadastrar.setVisibility(View.INVISIBLE);

        } catch (Exception ex) {


        }
    }
}
