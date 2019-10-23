package com.elias.martins.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.elias.martins.model.PessoaModel;
import com.elias.martins.uteis.DatabaseUteis;

import java.util.ArrayList;
import java.util.List;

public class PessoaRepository {//Controller_pessoa 25/07/2019
    //exemplo joão praxis contexto
    private Context myContext = null;//ok 03/07/2019 não interfere em nada no cadastro!!!!

    DatabaseUteis databaseUteis; // banco // exemplo androidDB 23/07/2019


    //teste elias   18/07/2019
    SQLiteDatabase banco;//db    SQLiteDatabase         // exemplo androidDB 23/07/2019

    public PessoaRepository(Context context) {

        databaseUteis = new DatabaseUteis(context);

        // myContext = context; // exmplo joao acredito que databaseUteis receberia myContext tenho de verificar ainda 28/06/2019
    }

    //salva um novo registro na base de dados
    public void Salvar(PessoaModel pessoaModel) { //esta salvando ok 14/12/2018

        ContentValues contentValues = new ContentValues();

        //montando os parametros para serem salvos
        contentValues.put("ds_nome", pessoaModel.getNome());
        contentValues.put("ds_endereco", pessoaModel.getEndereco());
        contentValues.put("ds_email", pessoaModel.getEmail());
        contentValues.put("ds_telefone", pessoaModel.getTelefone());
        contentValues.put("fl_sexo", pessoaModel.getSexo());
        contentValues.put("dt_nascimento", pessoaModel.getDataNascimento());
        contentValues.put("fl_estadoCivil", pessoaModel.getEstadoCivil());
        contentValues.put("fl_tipoCabelo", pessoaModel.getTipoCabelo());
        contentValues.put("fl_gramas", pessoaModel.getGramasCabelo());
        contentValues.put("fl_cm", pessoaModel.getCmCabelo());
        contentValues.put("fl_manutencoes", pessoaModel.getManutencoesCabelo());
        contentValues.put("fl_observacoes", pessoaModel.getObservacoesCabelo());
        contentValues.put("fl_servico", pessoaModel.getServico());
        contentValues.put("fl_ativo", pessoaModel.getRegistroAtivo());

        //executando insert de um novo registro
        databaseUteis.getConexaoDataBase().insert("tb_pessoa", null, contentValues);
    }


    //atualizar registro já existente na base       // ESTES METODOS SÃO COLOCADOS NO DIRETOIO "DAO" 11/07/2019
    public void Atualizar(PessoaModel pessoaModel) {

        ContentValues contentValues = new ContentValues();

        //montando os parametros para  atualizados os campos
        contentValues.put("ds_nome", pessoaModel.getNome());
        contentValues.put("ds_endereco", pessoaModel.getEndereco());
        contentValues.put("ds_email", pessoaModel.getEmail());//OK 01/07/2019
        contentValues.put("ds_telefone", pessoaModel.getTelefone());//OK 02/07/2019
        contentValues.put("fl_sexo", pessoaModel.getSexo());
        contentValues.put("dt_nascimento", pessoaModel.getDataNascimento());
        contentValues.put("fl_estadoCivil", pessoaModel.getEstadoCivil());

        contentValues.put("fl_tipoCabelo", pessoaModel.getTipoCabelo());//teste 19/07/2019
        contentValues.put("fl_gramas", pessoaModel.getGramasCabelo());//teste 19/07/2019
        contentValues.put("fl_cm", pessoaModel.getCmCabelo());//teste 19/07/2019
        contentValues.put("fl_manutencoes", pessoaModel.getManutencoesCabelo());//teste 19/07/2019
        contentValues.put("fl_observacoes", pessoaModel.getObservacoesCabelo());//teste 19/07/2019


        contentValues.put("fl_servico", pessoaModel.getServico());//testando 11/07/2019
        contentValues.put("fl_ativo", pessoaModel.getRegistroAtivo());

        //realiza atualização pela chave da tabela
        databaseUteis.getConexaoDataBase().update("tb_pessoa", contentValues, "id_pessoa =?", new String[]{Integer.toString(pessoaModel.getCodigo())});//estava "ID_PESSOA + ?" QUANDO ERA + ?  18/02/2019
    }


    //teste de atualizar exemplo joao 18/02/2019

    //public boolean atualizaLaudoEnviado(int codigoLaudo) {
//    public boolean atualizaPessoa(int codigoLaudo) {
//        String sql = "";
//        SQLiteDatabase dt;
//        boolean retorno = true;
//        try {
//
//            if (codigoLaudo == 0)
//                return false;
//
//            //dt = db.getWritableDatabase();
//            dt = databaseUteis.getWritableDatabase();
//
//        //    sql = "UPDATE tb_pessoa SET contentValues, WHERE id_pessoa = " + codigoLaudo;  // tenho de ver isso amanhã 18/02/2019
//
//            dt.execSQL(sql);
//
//            retorno = true;
//
//        } catch (SQLException e) {
//            retorno = false;
//        } catch (Exception e) {
//            retorno = false;
//        }
//
//        return retorno;
//    }


    //


    //excluirm um registro pelo código
    public Integer Excluir(int codigo) {

        //excluindo registro e retornando numero de linha afetadas
        return databaseUteis.getConexaoDataBase().delete("tb_pessoa", "id_pessoa = ?", new String[]{Integer.toString(codigo)});
    }

    //consulta uma pessoa pelo codigo
    public PessoaModel GetPessoa(int codigo) {

        Cursor cursor = databaseUteis.getConexaoDataBase().rawQuery("SELECT * FROM tb_pessoa WHERE id_pessoa= " + codigo, null);

        //cursor.moveToNext();
        cursor.moveToFirst();

        //criando uma nova pessoa
        PessoaModel pessoaModel = new PessoaModel();

        try {//adicionando os dados da pessoa     //erro ao tentar setas os dados nos campos!!!!!15/02/2019  dando erro antes de entrar no try!!!!!!

            //     if (cursor.moveToFirst()) {//adicionei 15/02/2019
            pessoaModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_pessoa"))); // da erro nessa linha 15/02/2019
            pessoaModel.setNome(cursor.getString(cursor.getColumnIndex("ds_nome")));//veio ate aki 15/02/2019  // estava com nome da coluna errada ds_pessoa
            // apresentava esse erro (java.lang.IllegalStateException: Couldn't read row 0, col -1         from CursorWindow.  Make sure the Cursor is initialized correctly before accessing data from it.)

            pessoaModel.setEndereco(cursor.getString(cursor.getColumnIndex("ds_endereco")));
            pessoaModel.setEmail(cursor.getString(cursor.getColumnIndex("ds_email")));
            pessoaModel.setTelefone(cursor.getString(cursor.getColumnIndex("ds_telefone")));//teste
            pessoaModel.setSexo(cursor.getString(cursor.getColumnIndex("fl_sexo")));
            //terei de alterar no futuro,devido aolteração do tipo do campo devido pesquisa de dats query consulta aniversariantes 11/07/2019
            pessoaModel.setDataNascimento(cursor.getString(cursor.getColumnIndex("dt_nascimento")));
//              pessoaModel.setDataNascimento(Integer.parseInt(cursor.getString(cursor.getColumnIndex("dt_nascimento"))));//teste 09/07/2019 elias
            pessoaModel.setEstadoCivil(cursor.getString(cursor.getColumnIndex("fl_estadoCivil")));

            //teste novos campos serviço 19/07/2019   22/07/2019
            pessoaModel.setTipoCabelo(cursor.getString(cursor.getColumnIndex("fl_tipoCabelo")));//teste 19/07/2019
            pessoaModel.setGramasCabelo(cursor.getString(cursor.getColumnIndex("fl_gramas")));//teste 19/07/2019
            pessoaModel.setCmCabelo(cursor.getString(cursor.getColumnIndex("fl_cm")));//teste 19/07/2019
            pessoaModel.setManutencoesCabelo(cursor.getString(cursor.getColumnIndex("fl_manutencoes")));//teste 19/07/2019
            pessoaModel.setObservacoesCabelo(cursor.getString(cursor.getColumnIndex("fl_observacoes")));//teste 19/07/2019


            //teste Serviço
            pessoaModel.setServico(cursor.getString(cursor.getColumnIndex("fl_servico")));//ainda em teste 11/07/2019

            pessoaModel.setRegistroAtivo((byte) cursor.getShort(cursor.getColumnIndex("fl_ativo")));
            //retornando a pessoa
            return pessoaModel;
        } catch (SQLException e) {
            Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        } catch (Exception e) {
            Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_LONG).show();
            return null;//adicionei 15/02/2019
        }
    }

    //consulta todas as pessoas cadastradas na base
    public List<PessoaModel> SelecionarTodos() {//para tela ConsultarClientes cadastrados 28/06/2019   poso utilizar esse mesmo metodo para carregar spinner da tela horario 23/07/2019
//o erro e nessa query
        //cria obj pessoas
        List<PessoaModel> pessoas = new ArrayList<PessoaModel>();


        try {


            //montra a  QUERY a ser executada
            StringBuilder stringBuilderQuery = new StringBuilder();

            stringBuilderQuery.append("SELECT id_pessoa,       ");
            stringBuilderQuery.append("        ds_nome,        ");
            stringBuilderQuery.append("        ds_endereco,    ");
            stringBuilderQuery.append("        ds_email,       ");//OK 01/07/2019  estava esquecendo a virgula 01/07/2019
            stringBuilderQuery.append("        ds_telefone,    ");//OK 02/07/2019
            stringBuilderQuery.append("        fl_sexo,        ");
            stringBuilderQuery.append("        dt_nascimento,  ");
            stringBuilderQuery.append("        fl_estadoCivil, ");
            stringBuilderQuery.append("        fl_tipoCabelo,  ");//teste 19/07/2019
            stringBuilderQuery.append("        fl_gramas,      ");
            stringBuilderQuery.append("        fl_cm,          ");
            stringBuilderQuery.append("        fl_manutencoes, ");
            stringBuilderQuery.append("        fl_observacoes, ");//teste 19/07/2019
            stringBuilderQuery.append("        fl_servico,     ");//OK 11/07/2019
            stringBuilderQuery.append("        fl_ativo        ");//tinha colocado uma virgula nessa linha  e não tinha no exemplo
            stringBuilderQuery.append("FROM    tb_pessoa       ");
            stringBuilderQuery.append("ORDER BY ds_nome        ");

            //consultando os registros cadastrados
            Cursor cursor = databaseUteis.getConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);//da erro aki no horario 23/07/2019

            //posiciona o cursor no primeiro registro
            cursor.moveToFirst();

            PessoaModel pessoaModel;

            //realiza a leitura dos registros enquanto não for o fim do cursor
            while (!cursor.isAfterLast()) {

                //criando uma nova pessoa
                pessoaModel = new PessoaModel();

                //adicionando os dados da pessoa
                pessoaModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_pessoa")));
                pessoaModel.setNome(cursor.getString(cursor.getColumnIndex("ds_nome")));
                pessoaModel.setEndereco(cursor.getString(cursor.getColumnIndex("ds_endereco")));
                pessoaModel.setEmail(cursor.getString(cursor.getColumnIndex("ds_email")));//teste 01/07/2019  da cath aki 01/07/2019
                pessoaModel.setTelefone(cursor.getString(cursor.getColumnIndex("ds_telefone"))); //teste 02/07/2019
                pessoaModel.setSexo(cursor.getString(cursor.getColumnIndex("fl_sexo")));
                pessoaModel.setDataNascimento(cursor.getString(cursor.getColumnIndex("dt_nascimento")));
                //teste apos alterar tipo do campo 09/07/2019
                //pessoaModel.setDataNascimento(Integer.parseInt(cursor.getString(cursor.getColumnIndex("dt_nascimento"))));//teste
                pessoaModel.setEstadoCivil(cursor.getString(cursor.getColumnIndex("fl_estadoCivil")));
                pessoaModel.setTipoCabelo((cursor.getString(cursor.getColumnIndex("fl_tipoCabelo"))));////teste 19/07/2019
                pessoaModel.setGramasCabelo((cursor.getString(cursor.getColumnIndex("fl_gramas"))));////teste 19/07/2019
                pessoaModel.setCmCabelo((cursor.getString(cursor.getColumnIndex("fl_cm"))));////teste 19/07/2019
                pessoaModel.setManutencoesCabelo((cursor.getString(cursor.getColumnIndex("fl_manutencoes"))));////teste 19/07/2019
                pessoaModel.setObservacoesCabelo((cursor.getString(cursor.getColumnIndex("fl_observacoes"))));////teste 19/07/2019
                pessoaModel.setServico(cursor.getString(cursor.getColumnIndex("fl_servico")));//teste serviço 11/07/2019
                pessoaModel.setRegistroAtivo((byte) cursor.getShort(cursor.getColumnIndex("fl_ativo")));

                //adicionando uma pessoa na lista
                pessoas.add(pessoaModel);

                //vai para o proximo registro
                cursor.moveToNext();
            }
            //retornando a lista de pessoas
            return pessoas;

        } catch (Exception ex) {
            //Toast.makeText(getClass(), ex.getMessage(),Toast.LENGTH_SHORT).show();
            Toast.makeText(myContext, ex.getMessage(), Toast.LENGTH_SHORT).show();//teste
        }
        return null;//teste ainda 01/07/2019
    }


    //ainda a implementar 28/06/2019  11/07/2019
    //region excluir cliente cadastradam exemplo DAO

    public void deleta(PessoaModel cliente) {
        SQLiteDatabase db = databaseUteis.getConexaoDataBase();//getWritableDatabase();
        String[] params = {cliente.getCodigo().toString()};
        db.delete("Alunos", "id = ?", params);
    }
//    public void deleta(Aluno aluno) {// original exemplo app DAO 02/07/2019
//        SQLiteDatabase db = getWritableDatabase();
//        String[] params = {aluno.getId().toString()};
//        db.delete("Alunos", "id = ?", params);
//    }    databaseUteis.getConexaoDataBase()

    //endregion

    //region pegar numero de registros   // pode ser esse 23/04/2019
    public PessoaRepository pegarTodosRegistros() {//(int numero)

        PessoaRepository u;// ainda em teste 18/07/2019
        banco = databaseUteis.getReadableDatabase();

        Cursor cursor = databaseUteis.getConexaoDataBase().rawQuery("SELECT*FROM tb_pessoa", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {

            u = new PessoaRepository(myContext);
            u.SelecionarTodos();

        } else {
            u = null;
        }

        cursor.close();
        databaseUteis.close();

        return u;
    }
//endregion


//    public Cursor preencheSpinner() {
////        Cursor cursor;
////
////        String[] campos = new String[]{""};
////        banco = databaseUteis.getConexaoDataBase();
////
////        cursor = banco.query(databaseUteis.)
////
////        banco.close();
////        return cursor;
//    }

//
//    ArrayList<String> ArrayListNames = new ArrayList<String>();
//
// // Cursor c = db.rawQuery("SELECT * FROM table_one", null);
//    Cursor c = banco.rawQuery("SELECT * FROM table_one", null);
//
//    if (c.moveToFirst()) {
//        do {
//            ArrayListNames.add(c.getString(c.getColumnIndex("ds_nome")));//("name")//original 23/07/2019
//           // ArrayListNames.add(c.getString(c.getColumnIndex("age")));
//        }
//        while (c.moveToNext());
//    }
//
//    //c.close();
//    banco.close();


    //teste horario 23/07/2019
    //consulta todas as pessoas cadastradas na base
    public List<PessoaModel> SelecionarTodosHoraio() {//para tela ConsultarClientes cadastrados 28/06/2019   poso utilizar esse mesmo metodo para carregar spinner da tela horario 23/07/2019
//o erro e nessa query
        //cria obj pessoas
        List<PessoaModel> pessoas = new ArrayList<PessoaModel>();
        try {
            //tenho que colocar todos os campos da tabela caso contrario gera erro !!!! 24/07/2019
            //montra a  QUERY a ser executada
            StringBuilder stringBuilderQuery = new StringBuilder();
            stringBuilderQuery.append("SELECT id_pessoa,       ");
            stringBuilderQuery.append("        ds_nome,        ");
            stringBuilderQuery.append("        ds_endereco,    ");
            stringBuilderQuery.append("        ds_email,       ");
            stringBuilderQuery.append("        ds_telefone,    ");
            stringBuilderQuery.append("        fl_sexo,        ");
            stringBuilderQuery.append("        dt_nascimento,  ");
            stringBuilderQuery.append("        fl_estadoCivil, ");
            stringBuilderQuery.append("        fl_tipoCabelo, ");//teste 19/07/2019
            stringBuilderQuery.append("        fl_gramas, ");//teste 19/07/2019
            stringBuilderQuery.append("        fl_cm, ");//teste 19/07/2019
            stringBuilderQuery.append("        fl_manutencoes, ");//teste 19/07/2019
            stringBuilderQuery.append("        fl_observacoes, ");//teste 19/07/2019
            stringBuilderQuery.append("        fl_servico,     ");//OK 11/07/2019
            stringBuilderQuery.append("        fl_ativo       ");//tinha colocado uma virgula nessa linha  e não tinha no exemplo
            stringBuilderQuery.append("FROM    tb_pessoa       ");
            stringBuilderQuery.append("ORDER BY ds_nome        ");

            //consultando os registros cadastrados
            Cursor cursor = databaseUteis.getConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);//da erro aki no horario 23/07/2019  24/07/2019 catch

            //posiciona o cursor no primeiro registro
            cursor.moveToFirst();

            PessoaModel pessoaModel;

            //realiza a leitura dos registros enquanto não for o fim do cursor
            while (!cursor.isAfterLast()) {

                //criando uma nova pessoa
                pessoaModel = new PessoaModel();

                //adicionando os dados da pessoa
                pessoaModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_pessoa")));
                pessoaModel.setNome(cursor.getString(cursor.getColumnIndex("ds_nome")));
                pessoaModel.setEndereco(cursor.getString(cursor.getColumnIndex("ds_endereco")));
                pessoaModel.setEmail(cursor.getString(cursor.getColumnIndex("ds_email")));//teste 01/07/2019  da cath aki 01/07/2019
                pessoaModel.setTelefone(cursor.getString(cursor.getColumnIndex("ds_telefone"))); //teste 02/07/2019
                pessoaModel.setSexo(cursor.getString(cursor.getColumnIndex("fl_sexo")));
                pessoaModel.setDataNascimento(cursor.getString(cursor.getColumnIndex("dt_nascimento")));
                pessoaModel.setEstadoCivil(cursor.getString(cursor.getColumnIndex("fl_estadoCivil")));
                pessoaModel.setTipoCabelo((cursor.getString(cursor.getColumnIndex("fl_tipoCabelo"))));////teste 19/07/2019
                pessoaModel.setGramasCabelo((cursor.getString(cursor.getColumnIndex("fl_gramas"))));////teste 19/07/2019
                pessoaModel.setCmCabelo((cursor.getString(cursor.getColumnIndex("fl_cm"))));////teste 19/07/2019
                pessoaModel.setManutencoesCabelo((cursor.getString(cursor.getColumnIndex("fl_manutencoes"))));////teste 19/07/2019
                pessoaModel.setObservacoesCabelo((cursor.getString(cursor.getColumnIndex("fl_observacoes"))));////teste 19/07/2019

                //OK serviço 11/07/2019
                pessoaModel.setServico(cursor.getString(cursor.getColumnIndex("fl_servico")));//teste serviço 11/07/2019
                pessoaModel.setRegistroAtivo((byte) cursor.getShort(cursor.getColumnIndex("fl_ativo")));

                //adicionando uma pessoa na lista
                pessoas.add(pessoaModel);

                //vai para o proximo registro
                cursor.moveToNext();
            }
            //retornando a lista de pessoas
            return pessoas;

        } catch (Exception ex) {
            //Toast.makeText(getClass(), ex.getMessage(),Toast.LENGTH_SHORT).show();
            Toast.makeText(myContext, ex.getMessage(), Toast.LENGTH_SHORT).show();//teste
        }
        return null;
    }


    //nova query para testes futuros 24/07/2019

    /**
     * Getting all labels
     * returns list of labels
     * */
//    public List<String> getAllLabels(){
//        List<String> labels = new ArrayList<String>();
//
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + databaseUteis.getConexaoDataBase();//TABLE_LABELS          nome da tabela
//
//        SQLiteDatabase db = this.databaseUteis.getConexaoDataBase();//getReadableDatabase()
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                labels.add(cursor.getString(1));
//            }while (cursor.moveToNext());
//        }
//
//        // closing connection
//        cursor.close();
//        db.close();
//
//        // returning lables
//        return labels;
//    }

}
