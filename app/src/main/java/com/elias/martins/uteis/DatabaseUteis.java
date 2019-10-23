package com.elias.martins.uteis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseUteis extends SQLiteOpenHelper {
    protected final static String NOME_TABELA_PRODUTO = "tb_Produtos";
    protected final static String COLUNA_PRODUTO_ID = "id_produto";
    protected final static String COLUNA_PRODUTO = "produto_nome";
    protected final static String COLUNA_VALOR = "produto_valor";
    protected final static String COLUNA_VALOR_TOTAL = "produto_valor_total";
    protected final static String COLUNA_QUANTIDADE = "produto_quantidade";


    //exemplo joão , acredito que context aki e no construtor seja para ter acesso a ele em outras classes 28/06/2019
    //    private Context MyContextDB = null;

    /**
     * nome banco
     **/
    //Nome da base de dados
    private static final String NOME_BASE_DADOS = "SISTEMA_NATALIA.db";

    /**
     * versão banco
     **/
    //versão do banco de dados
    private static final int VERSAO_BASE_DE_DADOS = 2; // ALTEREI PARA 3  VOLTAR PARA 1 QUANDO FOR COLOCAR EM PRODUÇÃO
    //teste log
    //   private static  final String LOG_TAG = System.getenv("SISTEMA_NATALIA.db");//teste tag personalizada 09/07/2019


    //tetse outra tabela exemplo joão 03/07/2019
    private static final String TABELA_SEVICO = "CREATE TABLE tb_servico(id_servico INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, valor REAL)";
    private static final String TABELA_HORARIO = "CREATE TABLE tb_horario(id_horario INTEGER PRIMARY KEY AUTOINCREMENT, hora_atendimento INTEGER NOT NULL, id_servico INTEGER, id_pessoa INTEGER, status TEXT NOT NULL, valor REAL)";
    //inicialmente os comandos SQL NÃO FICAM "LARANJADOS" SOMENTE APÓS SEREM CHAMASDOS PELO db.execSQl(nome da tabela !!) 03/07/2019

    //tabela Produtos = para cabelo  17/07/2019   ainda em teste
    private static final String TABELA_PRODUTOS = "CREATE TABLE tb_Produtos(id_produto INTEGER PRIMARY KEY AUTOINCREMENT, produto_nome TEXT, produto_quantidade INTEGER, produto_valor REAL, produto_valor_total REAL)";


    public DatabaseUteis(Context context) {
        super(context, NOME_BASE_DADOS, null, VERSAO_BASE_DE_DADOS);//sempre passo as variaveis do Banco de dados , não seus valores !!! 28/06/2019
//        MyContextDB = context;//teste joão 218/06/2019
        //aki joão utilizaria o Alert(); 28/06/2019
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//VALIDAR CAMPO IMAGE_FOTO 25/07/2019

        StringBuilder stringBuilderCreateTable = new StringBuilder();
        try {
            stringBuilderCreateTable.append("CREATE TABLE tb_pessoa(");
            stringBuilderCreateTable.append(" id_pessoa       INTEGER PRIMARY KEY AUTOINCREMENT,");
            stringBuilderCreateTable.append(" ds_nome         TEXT NOT NULL,    ");
            stringBuilderCreateTable.append(" ds_endereco     TEXT NOT NULL,    ");
            stringBuilderCreateTable.append(" ds_email        TEXT NOT NULL,    ");//ADICIONEI 01/07/2019 OK
            stringBuilderCreateTable.append(" ds_telefone     TEXT NOT NULL,    ");//ADICIONEI 02/07/2019 OK
            stringBuilderCreateTable.append(" fl_sexo         TEXT NOT NULL,    ");
            stringBuilderCreateTable.append(" dt_nascimento   TEXT NOT NULL,    ");//ALTEREI DE TEXT PARA INTEGER 09/07/2019 ELIAS  TESTE   TEREI DE MUDAR PARA INTEGER DEPOIS PARA PESQUISAR DE DATAS
            stringBuilderCreateTable.append(" fl_estadoCivil  TEXT NOT NULL,    ");
            stringBuilderCreateTable.append(" fl_tipoCabelo    TEXT,            ");//teste 19/07/2019
            stringBuilderCreateTable.append(" fl_gramas        TEXT,            ");//teste 19/07/2019
            stringBuilderCreateTable.append(" fl_cm            TEXT,            ");//teste 19/07/2019
            stringBuilderCreateTable.append(" fl_manutencoes   TEXT,            ");//teste 19/07/2019
            stringBuilderCreateTable.append(" fl_observacoes   TEXT,            ");//teste 19/07/2019
            stringBuilderCreateTable.append(" fl_foto          BLOB,            ");//TESTE             25/07/2019 ELIAS  AINDA NÃO IREI SALVAR SOMENTE CRIAR CAMPO NA TABELA !!!
            stringBuilderCreateTable.append(" fl_servico      TEXT  NOT NULL,   ");//ADICIONEI 11/07/2019 ainda em teste criou campo ok
            stringBuilderCreateTable.append(" fl_ativo        INT   NOT NULL )  ");

            db.execSQL(stringBuilderCreateTable.toString());

            //adicioada e testada em 03/07/2019
            db.execSQL(TABELA_SEVICO);
            db.execSQL(TABELA_HORARIO);

            //teste outra tabela 17/07/2019
//
//            String CRIA_TABELA_PRODUTOS = "CREATE TABLE " +
//                    NOME_TABELA_PRODUTO + "(" +
//                    COLUNA_PRODUTO_ID + " INTEGER PRIMARY KEY," +
//                    COLUNA_PRODUTO + " TEXT," +
//                    COLUNA_QUANTIDADE + " INTEGER," +
//                    COLUNA_VALOR + " REAL," +
//                    COLUNA_VALOR_TOTAL + " REAL" +
//                    ")";
//            db.execSQL(CRIA_TABELA_PRODUTOS);
//   esse teste não funcionou


            db.execSQL(TABELA_PRODUTOS);
            //fim

        } catch (Exception ex) {
            //      Log.w(LOG_TAG)
            //          Toast.makeText(null, "Erro criação de tabelas", Toast.LENGTH_SHORT).show(); // testando com contexto null 28/06/2019  ainda tenho que validar isso !!!
        }
    }


    //se trocar a versão do banco de dados você pode executar alguma rotina
    // como criar colunas exclui ...
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //      if (newVersion > oldVersion)
        //         db.execSQL(TABELA_PRODUTOS);
        // db.execSQL(TABELA_SEVICO); //colocar aki a tabela que quero gerar 02/07/2019
        //      db.execSQL("DROP TABLE IF EXISTS tb_pessoa");//alterar nome da tabela para Clientes 27/06/2019

        // Isto é apenas didático. Na vida real, você terá de adicionar novas colunas e não apenas recriar o mesmo banco
        onCreate(db);//teste 09/07/2019 elias
        //exemplo joão praxis no futuro quando tiver mais tabelas devo verificar no onUpgrade 28/06/2019
//        if (newVersion > oldVersion) {
//            db.execSQL(DATABASE_CREATE_TIPOLAUDOTO);
//            db.execSQL(ALTER_DATABASE_LAUDO_ADD_TIPO_VISTORIA);
//            db.execSQL(DATABASE_CREATE_VIDEO);
//        }

    }
    //metodo que utilizarei na classe que vai executar as rotinas no banco de dados
    public SQLiteDatabase getConexaoDataBase() {
        return this.getWritableDatabase();//no exemplo do joão ele utiliza aki getwritabledatabase no comando de salvar do sqlLite 28/06/2019
    }
}
