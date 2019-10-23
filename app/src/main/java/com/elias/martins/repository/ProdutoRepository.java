package com.elias.martins.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.elias.martins.model.ProdutoModel;
import com.elias.martins.uteis.DatabaseUteis;

public class ProdutoRepository {//Controller_Produto 25/07/2019
    private SQLiteDatabase db;
    private DatabaseUteis banco;

    public ProdutoRepository(Context context) {
        banco = new DatabaseUteis(context);
    }


    //cadastrar
    public void salvarProduto(ProdutoModel produtoModel) {//String produto, int quantidade, float valor

        ContentValues valores = new ContentValues();
        //montando os parametros para inserir o registro

        //       long resultado;  //como não possui retorno comentei essa linha seria resultado para retorno 17/07/2019
//        db = banco.getConexaoDataBase(); // no exemplo seria getWritableDatbase()  aki 17/07/2019 // seria em outro lugar não aki
        valores = new ContentValues();
        valores.put("produto_nome", produtoModel.getProdutoNome());
        valores.put("produto_valor", produtoModel.getProdutoQuantidade());
        valores.put("produto_valor_total", produtoModel.getProdutoValor());

        //executando insert de um novo registro
        banco.getConexaoDataBase().insert("tb_Produtos", null, valores);// banco = databaseUteisa       valores =contentvalue
    }

    //atualizar
    public void AtualizarProduto(ProdutoModel produtoModel) {

        ContentValues valores = new ContentValues();
        //montando os parametros para atualizar o registro
        valores.put("produto_nome", produtoModel.getProdutoNome());
        valores.put("produto_valor", produtoModel.getProdutoQuantidade());
        valores.put("produto_valor_total", produtoModel.getProdutoValor());

        //realiza atualizaçã pela chave da tabela
        banco.getConexaoDataBase().update("tb_Produtos", valores, "id_produto =?", new String[]{Integer.toString(produtoModel.getProdutoId())});
    }


    //excluir
    public Integer Excluir(int codigo) {

        return banco.getConexaoDataBase().delete("tb_Produtos", "id_produto =?", new String[]{Integer.toString(codigo)});
    }


    //preencher spinner
    public Cursor preencheSpinner(ProdutoModel produtoModel) {//teste de parametros elias 17/07/2019
        Cursor cursor;
        String[] campos = new String[]{"id_produto" + produtoModel.getProdutoId(), "produto_nome" + produtoModel.getProdutoNome(), ""+produtoModel.getProdutoValor(), "" + produtoModel.getProdutoQuantidade()};//utilizei forma diferente de passagem de parametros ainda em teste elias 17/07/2019
        db = banco.getConexaoDataBase();
        cursor = db.query("tb_Produtos", campos, null, null, null,null, banco.getConexaoDataBase() +"ASC", null  ); //ainda em teste
        if(cursor!= null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;

    }
}
