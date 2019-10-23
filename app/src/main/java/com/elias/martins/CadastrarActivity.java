package com.elias.martins;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import com.elias.martins.callback.PostmonService;
import com.elias.martins.model.Endereco;
import com.elias.martins.model.EstadoCivilModel;
import com.elias.martins.model.PessoaModel;
import com.elias.martins.repository.PessoaRepository;
import com.elias.martins.model.ServicoModel;
import com.elias.martins.uteis.Uteis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;//para utilizar JAR mascara na classe 09/07/2019

//para retrofit
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CadastrarActivity extends AppCompatActivity {
    //Dados cliente
    EditText editTextNome;
    EditText editTextEndereco;
    EditText edtCEP;  // para o retrofit cep  11/07/2019 elias  OK
    EditText editTextEmail;//adicionei 27/06/2019
    EditText editTextTelefone;//adicionei 27/06/2019
    RadioButton radioButtonMasculino;
    RadioButton radioButtonFeminino;
    RadioGroup radioGroupSexo;
    EditText editTextDataNascimento;
    Spinner spinnerEstadoCivil;
    Spinner spinnerServico;//adicionei 27/06/2019
    CheckBox checkBoxRegistroAtivo;
    Button buttonSalvar;
    Button buttonVoltar;
    ImageButton btfoto;


    //Dados Serviço
    EditText editTipoCabelo;
    EditText editGramasCabelo;
    EditText editCentimetrosCabelo;
    EditText editManutencoes;
    EditText editObservacao;


    //teste img
    ImageView imgfoto;
    Button btfoto1;
    //fim teste 27/06/2019

    private Vibrator vibrator;//12/06/2019
    private long milliseconds = 500;//12/06/2019

    //region POPUP CALENDARIO
    //cria popup com o calendário
    DatePickerDialog datePickerDialogDataNascimento;
    //endregion

    //teste retrofit 11/07/2019
    private Retrofit retrofit;// OK
    Button btcep;//OK

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //teste tirar barra superior padrao do app   24/07/2019
        getSupportActionBar().hide(); //aqui a mágica de sumir a barra


        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        setContentView(R.layout.activity_cadastrar);


        //region chama o metodo Localização para calendario
        this.localizacao();
        //endregion

        //region recupera os componentes da tela
        this.criaComponentes();
        //endregion

        //region Eventos Tela
        this.criarEventos();
        //endregion


        //region carrega EstadoCivil
        this.carregaEstadoCivis();
        //endregion
        //region carreag Serviço
        this.carregaServicos();
        //endregion
    }


    @Override
    public void onBackPressed() {// para desabilitar o botao voltar do android nessa tela 27/06/2019
        // não chame o super desse método
    }


    //region Carregr informações no Spinner Estado civil
    protected void carregaEstadoCivis() {
        ArrayAdapter<EstadoCivilModel> arrayAdapter;

        List<EstadoCivilModel> itens = new ArrayList<EstadoCivilModel>();

        itens.add(new EstadoCivilModel("S", "Solteiro(a)"));
        itens.add(new EstadoCivilModel("C", "Casado(a)"));
        itens.add(new EstadoCivilModel("V", "Viuvo(a)"));
        itens.add(new EstadoCivilModel("D", "Divorciado(a)"));

        arrayAdapter = new ArrayAdapter<EstadoCivilModel>(this, android.R.layout.simple_spinner_item, itens);//instancio o adapter do tipo <EstadoCivil> e passo como parametros  contexto, layout, e os dados no caso foram dados preenchidos por min itens mas poderiam vir do banco de dados 12/09/2019
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEstadoCivil.setAdapter(arrayAdapter);
    }
    //endregion

    // region Carregar informações no SPinner Serviço
    protected void carregaServicos() {
//teste desse metodo 05/07/2019
        ArrayAdapter<ServicoModel> arrayAdapter;

        List<ServicoModel> itensServico = new ArrayList<ServicoModel>();

        itensServico.add(new ServicoModel("Alongamento", "Alongamento adesivado"));//valor esta fixo aki  deve ser via app cadastrado pela usuaria ! 05/07/2019
        itensServico.add(new ServicoModel("Outros", "OutrosServiço"));//valor deve ser dinamico 05/07/2019

        arrayAdapter = new ArrayAdapter<ServicoModel>(this, android.R.layout.simple_spinner_item, itensServico);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerServico.setAdapter(arrayAdapter);
    }
    // endregion


    //region recuperando componentes
    private void criaComponentes() {

        //Cliente
        editTextNome = (EditText) this.findViewById(R.id.editTextNome);

        editTextEndereco = (EditText) this.findViewById(R.id.editTextEndereco);

        edtCEP = (EditText) this.findViewById(R.id.edtCEP);//teste cep retrofit  11/07/2019

        editTextEmail = (EditText) this.findViewById(R.id.edtEmail);//adicionei por ultimo

        editTextTelefone = (EditText) this.findViewById(R.id.edtTelefone);//adicionei por ultimo

        radioButtonMasculino = (RadioButton) this.findViewById(R.id.radioButtonMasculino);

        radioButtonFeminino = (RadioButton) this.findViewById(R.id.radioButtonFeminino);

        radioGroupSexo = (RadioGroup) this.findViewById(R.id.radioGroupSexo);

        editTextDataNascimento = (EditText) this.findViewById(R.id.editTextDataNascimento);

        spinnerEstadoCivil = (Spinner) this.findViewById(R.id.spinnerEstadoCivil);

        spinnerServico = (Spinner) this.findViewById(R.id.spinnerServico);//adicionei por ultimo

        checkBoxRegistroAtivo = (CheckBox) this.findViewById(R.id.checkBoxRegistroAtivo);

        buttonSalvar = (Button) this.findViewById(R.id.buttonSalvar);

        buttonVoltar = (Button) this.findViewById(R.id.buttonVoltar);

        btfoto = (ImageButton) this.findViewById(R.id.btfoto);

        //teste retrofit  OK
        btcep = (Button) this.findViewById(R.id.btcep);//teste 11/07/2019 elias


        //teste retrofit 11/07/2019 elias  OK
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.postmon.com.br/v1/cep/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        //serviço

        editTipoCabelo = (EditText) findViewById(R.id.edtTipoCabeloCad);
        editGramasCabelo = (EditText) findViewById(R.id.editGramasCabeloCad);
        editCentimetrosCabelo = (EditText) findViewById(R.id.editCentimetrosCabeloCad);
        editManutencoes = (EditText) findViewById(R.id.editManutencoesCad);
        editObservacao = (EditText) findViewById(R.id.editObservacaoCad);
    }

    //endregion


    //region Botoes  CriarEventos
    protected void criarEventos() {

        final Calendar calendarDataAtual = Calendar.getInstance();
        int anoAtual = calendarDataAtual.get(Calendar.YEAR);//testando inicializar com data diferente da atual  15/07/2019 teste elias
        int mesAtual = calendarDataAtual.get(Calendar.MONTH);//testando inicializar com data diferente da atual  15/07/2019
        int diaAtual = calendarDataAtual.get(Calendar.DAY_OF_MONTH);

        //Montando obj de data para preencher os campos quando for selecionado
        //FORMATO  DD/MM/YYYY
        datePickerDialogDataNascimento = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {

                //formatando o mês com dois digitos
                String mes = (String.valueOf((mesSelecionado + 1)).length() == 1 ? " 0 " + (mesSelecionado + 1) : String.valueOf(mesSelecionado));


                editTextDataNascimento.setText(diaSelecionado + "/" + mes + "/" + anoSelecionado);

            }
        }, anoAtual, mesAtual, diaAtual);

        //criando evento no campo de data para abrir o popup
        editTextDataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialogDataNascimento.show();
            }
        });

        //criando evento no campo de data para abrir a popup
        editTextDataNascimento.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                datePickerDialogDataNascimento.show();
            }
        });


        //region Botao Salvar
        //criando evento do botao salvar
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Salvar_onClick();
            }
        });
        //endregion


        //  region Botão voltar
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Voltar_onClick();
            }
        });
        //endregion


        //campo telefone  //para adicionar mascara no campo 09/07/2019
        MaskEditTextChangedListener maskTel = new MaskEditTextChangedListener("(##)#########", editTextTelefone);
        editTextTelefone.addTextChangedListener(maskTel);// teste mascara 09/07/2019


        //mascara campo CEP
        MaskEditTextChangedListener mastCEP = new MaskEditTextChangedListener("#####-###", edtCEP);
        edtCEP.addTextChangedListener(mastCEP);//test mascara CEP 11/07/2019


        //teste retrofit 11/07/2019
        btcep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitarEndereco();
            }
        });


        //serviço a implementar se houver necessidade
    }


    private void Voltar_onClick() {
        alertVoltar();
    }

    private void alertVoltar() {
        //cria o gerenciador do AlertDialog
        AlertDialog.Builder alerta = new AlertDialog.Builder(CadastrarActivity.this);

        //define o  titulo
        alerta.setTitle("titulo");


        //definir icone para alert em caso de usar alert padrão
        alerta.setIcon(R.mipmap.voltar); // se quiser colocar icone ! 25/06/2019


        //define a mensagem

        alerta.setMessage("Deseja realmente Voltar ? Todos os dados não salvos seram perdidos !");

        //define um botão com positivo

        alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //redireciona para tela principal
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);// Aki ação do botão de voltar da tela consulta para principal 13/12/2018
                startActivity(intentMainActivity);

                //finaliza atividade atual
                finish();

                //  Toast.makeText(CadastrarActivity.this, "positivo = " + which, Toast.LENGTH_LONG).show();
            }
        });
        //define um botão com negativo
        // alerta.setCancelable(false); // outro exemplo 25/06/2019


        //teste 25/06/2019
        alerta.setNegativeButton("NÃO", null);
        //exibe

        alerta.show();

    }

    private void Salvar_onClick() {

        if (editTextNome.getText().toString().trim().equals("")) {

            Uteis.Alert(this, this.getString(R.string.nome_obrigatorio));
            //foco no campo nome
            editTextNome.requestFocus();

        } else if (editTextEndereco.getText().toString().trim().equals("")) {
            Uteis.Alert(this, this.getString(R.string.endereco_obrigatorio));
            //foco no campo Endereço
            editTextEndereco.requestFocus();
        } else if (editTextEmail.getText().toString().trim().equals("")) {
            Uteis.Alert(this, this.getString(R.string.email_obrigatorio));
            //foco no campo Email
            editTextEmail.requestFocus();
        } else if (editTextTelefone.getText().toString().trim().equals("")) {

            Uteis.Alert(this, this.getString(R.string.telefone_obrigatorio));
            //foco no campoTelefone
            editTextTelefone.requestFocus();
        } else if (!radioButtonMasculino.isChecked() && !radioButtonFeminino.isChecked()) {


            Uteis.Alert(this, this.getString(R.string.sexo_obrigatorio));
        } else if (editTextDataNascimento.getText().toString().trim().equals("")) {

            Uteis.Alert(this, this.getString(R.string.data_nascimento_obrigatorio));
            //foco no campo datanascimento
            editTextDataNascimento.requestFocus();

            //acrescentar os campos do serviço aki 17/07/2019 elilas
        } else {


            alert_Cadastrar();

        }
    }

    private void alert_Cadastrar() {

        //cria o gerenciador do AlertDialog
        android.support.v7.app.AlertDialog.Builder alerta = new AlertDialog.Builder(CadastrarActivity.this);

        //define o  titulo
        alerta.setTitle("AVISO");


        //definir icone para alert em caso de usar alert padrão
        alerta.setIcon(R.mipmap.salvar); // se quiser colocar icone ! 25/06/2019


        //define a mensagem

        alerta.setMessage("Deseja realizar Cadastro ?");

        //define um botão com positivo

        alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
//inicio
                    //criando um obj pessoa
                    PessoaModel pessoaModel = new PessoaModel();
//
//            //setando o valor do campo nome
                    pessoaModel.setNome(editTextNome.getText().toString().trim());
//
//            //setando o endereco
                    pessoaModel.setEndereco(editTextEndereco.getText().toString().trim());//no DAO não tem o trim ??
//

                    //ok 01/07/2019
                    pessoaModel.setEmail(editTextEmail.getText().toString().trim());//ok 01/07/2019

                    //setando telefone01/07/2019
                    pessoaModel.setTelefone(editTextTelefone.getText().toString().trim());//ok 01/07/2019   //talvez terei de tirar o trim dessa linha???
                    //fim teste


//            //setando o sexo
                    if (radioButtonMasculino.isChecked())
                        pessoaModel.setSexo("M");
                    else
                        pessoaModel.setSexo("F");
//
//            //setando a data de nascimento // original campo text
                    pessoaModel.setDataNascimento(editTextDataNascimento.getText().toString().trim());

                    //teste apos altear campo para integer 09/07/2019 elias
                    //   String nasc =

//                  if (editTextDataNascimento != null) {
//                        pessoaModel.setDataNascimento(Integer.valueOf(editTextDataNascimento.getText().toString()));//ainda em teste 09/07/2019 elias
//                    }

                    //exemplo erro   voltei tenho de alterar depois 09/07/2019
                    //"01/07/2019"

                    //
//            //realizando um cast para pegar o obj do estado civil selecionado
                    EstadoCivilModel estadoCivilModel = (EstadoCivilModel) spinnerEstadoCivil.getSelectedItem();
//
//            //setando estado civil
                    pessoaModel.setEstadoCivil(estadoCivilModel.getCodigo());


                    //teste campos novos 19/07/2019

                    pessoaModel.setTipoCabelo(editTipoCabelo.getText().toString().trim());//ok 01/07/2019
                    pessoaModel.setGramasCabelo(editGramasCabelo.getText().toString().trim());//ok 01/07/2019
                    pessoaModel.setCmCabelo(editCentimetrosCabelo.getText().toString().trim());//ok 01/07/2019
                    pessoaModel.setManutencoesCabelo(editManutencoes.getText().toString().trim());//ok 01/07/2019
                    pessoaModel.setObservacoesCabelo(editObservacao.getText().toString().trim());//ok 01/07/2019


//
                    //cadastrar serviço 11/07/2019   OK
                    ServicoModel servicoModel = (ServicoModel) spinnerServico.getSelectedItem();

                    pessoaModel.setServico(servicoModel.getServicoCodigo());

//            //seta o registro como inativo
                    pessoaModel.setRegistroAtivo((byte) 0);
//
//            //se tive selecionado seta como ativo
                    if (checkBoxRegistroAtivo.isChecked())
                        pessoaModel.setRegistroAtivo((byte) 1);
//
//            //salvando um novo registro
                    new PessoaRepository(CadastrarActivity.this).Salvar(pessoaModel);//  aki salva o registro  //falta implementar repository 27/06/2019
//
                    vibrator.vibrate(milliseconds);
//
//            //mensagem de sucesso
                    Toast.makeText(getApplicationContext(), "cadatro realizado com sucesso", Toast.LENGTH_LONG).show();
                    //Uteis.Alert(this, this.getString(R.string.registro_salvo_sucesso));//uteis parece ser o act do carluluite

                    limparCampos();

                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();//teste 09/07/2019 elias
                }
                //fim
            }
        });
        //define um botão com negativo
        // alerta.setCancelable(false); // outro exemplo 25/06/2019


        //teste 25/06/2019
        alerta.setNegativeButton("NÃO", null);


        //exibe

        alerta.show();


    }

    //metodo para limpar campos apos cadastro de cliente
    protected void limparCampos() {

        editTextNome.setText(null);
        editTextEndereco.setText(null);
        editTextEmail.setText(null);
        editTextTelefone.setText(null);

        radioGroupSexo.clearCheck();

        editTextDataNascimento.setText(null);

        //novos campos serviço 19/07/2019
        editTipoCabelo.setText("");
        editGramasCabelo.setText("");
        editCentimetrosCabelo.setText("");
        editManutencoes.setText("");
        editObservacao.setText("");

        //     checkBoxRegistroAtivo.setChecked(false);  // teste elias 02/07/2019
    }
    //endregion


    //region Localização para traduzir o calendario (Brasil)
    //diz qual a localização para traduzir os texto do calendario
    protected void localizacao() {

        Locale locale = new Locale("pt", "BR");
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);

    }
    //endregion


    //region retrofit CEP  // OK 11/07/2019
    private void solicitarEndereco() {
        String cep = edtCEP.getText().toString();//ALTERAR PARA CAMPO DE BUSCA DO CEP
        PostmonService service = retrofit.create(PostmonService.class);

        Call<Endereco> call = service.getEndereco(cep);

        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful()) {
                    Endereco endereco = response.body();
                    String strEndereco = "Cidade : " + endereco.getCidade() + "\n" +
                            "bairro : " + endereco.getBairro() + "\n" +
                            "Logradouro : " + endereco.getLogradouro();

                    editTextEndereco.setText(strEndereco);
                } else
                    editTextEndereco.setText("Erro :" + response.code());//envia mensagem de err de conexão 11/07/2019
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable throwable) {
                Toast.makeText(CadastrarActivity.this, "Não foi posssivel realizar a requisição", Toast.LENGTH_SHORT).show();
            }
        });

    }
//endregion
}
