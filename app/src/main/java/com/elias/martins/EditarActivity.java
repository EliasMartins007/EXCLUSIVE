package com.elias.martins;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.elias.martins.callback.PostmonService;
import com.elias.martins.model.Endereco;
import com.elias.martins.model.EstadoCivilModel;
import com.elias.martins.model.PessoaModel;
import com.elias.martins.model.ServicoModel;
import com.elias.martins.repository.PessoaRepository;
import com.elias.martins.uteis.Uteis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditarActivity  extends AppCompatActivity {
    //region Componentes da Tela
    //COMPONENTES DA TELA
    EditText editTextCodigo;
    EditText editTextNome;
    EditText editTextEndereco;

    EditText edtCEP;//masck cep
    //campos que adicionei 01/07/2019
    EditText editTextEmail;
    EditText editTextTelefone;
    Spinner spinnerServico;
    //fim


    //Dados Serviço
    EditText editTextTipoCabelo;
    EditText editTextGramasCabelo;
    EditText editTextCentimetrosCabelo;
    EditText editTextManutencoes;
    EditText editTextObservacao;


    RadioButton radioButtonMasculino;
    RadioButton radioButtonFeminino;
    RadioGroup radioGroupSexo;
    EditText editTextDataNascimento;
    Spinner spinnerEstadoCivil;
    CheckBox checkBoxRegistroAtivo;
    Button buttonAlterar;
    Button buttonVoltar;


    // Button btnExcluir;//teste elias 08/07/2019
    ImageButton btnExcluir;//teste elias 18/07/2019

    PessoaRepository pessoaRepository;//para excluir 08/07/2019
    PessoaModel pessoaModels;//para excluir 08/07/2019


    //elias 15/02/2019
    private Context myContext = null;//teste  //tenho de confirmar esse contexto de posso utilizalo em outra activity 01/07/2019

    //endregion

    //region POPUP CALENDARIO
    DatePickerDialog datePickerDialogDataNascimento;
    //endregion

    ArrayAdapter<EstadoCivilModel> arrayAdapterEstadoCivil;

    ArrayAdapter<ServicoModel> arrayAdapterServico;


    //teste retrofit
    private Retrofit retrofit;
    private EditText edtCEPEditar;
    Button btCEP;


    //region Oncreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //teste tirar barra superior padrao do app   24/07/2019
        getSupportActionBar().hide(); //aqui a mágica


        setContentView(R.layout.activity_editar);


        try {
            //region CRIA OS COMPONENTES
//        pessoaR = new List<PessoaRepository>() {
//        }


            //chama o metodo para criar os componentes da Tela
            this.CriarComponentes();
            //endregion

            //region Cria Eventos
            //chama o metodo que cria eventos para os componentes
            this.CriarEventos();
            //endregion

            //region carregar opções do estado civil
            //carrega as opções de estado civil
            this.CarregaEstadoCivis();
            //endregion


            //teste 11/07/2019 esta carregando todos porem não esta setando o salvo na edição do registro 11/07/2019
            this.carregaServicos();

            //region PREENCHER CAMPOS COM DADOS PARA EDITAR
            //carrega os valores nos campos da tela
            this.CarregaValoresCampos();//teste 15/02/2019 // erro aki não esta carregando na tela pode ser erro em outro lugar!!!!
            //endregion
        } catch (Exception e) {
            Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }


    // desabilitar botao voltar android na aplicação    funciona somente para acitivity que colocar o metodo
    @Override
    public void onBackPressed() {
        // não chame o super desse método
    }
    //fim teste


    //Vincula os componentes da Tela aos obj declarados   (recuperar componentes!!!)
    protected void CriarComponentes() {

        try {
            editTextCodigo = (EditText) this.findViewById(R.id.editTextCodigo);//estava comentado 01/07/2019

            editTextNome = (EditText) this.findViewById(R.id.editTextNome);

            editTextEndereco = (EditText) this.findViewById(R.id.editTextEndereco);

            radioButtonMasculino = (RadioButton) this.findViewById(R.id.radioButtonMasculino);

            radioButtonFeminino = (RadioButton) this.findViewById(R.id.radioButtonFeminino);

            radioGroupSexo = (RadioGroup) this.findViewById(R.id.radioGroupSexo);

            editTextDataNascimento = (EditText) this.findViewById(R.id.editTextDataNascimento);

            spinnerEstadoCivil = (Spinner) this.findViewById(R.id.spinnerEstadoCivil);

            checkBoxRegistroAtivo = (CheckBox) this.findViewById(R.id.checkBoxRegistroAtivo);

            buttonAlterar = (Button) this.findViewById(R.id.buttonAlterar);//estava comentado 01/07/2019

            buttonVoltar = (Button) this.findViewById(R.id.buttonVoltar);


            btnExcluir = (ImageButton) this.findViewById(R.id.btExcluir); // teste 18/07/2019


            //campos que adicionei 01/04/2019
            editTextEmail = (EditText) this.findViewById(R.id.edtEmail);//ok
            editTextTelefone = (EditText) this.findViewById(R.id.edtTelefone);
            spinnerServico = (Spinner) this.findViewById(R.id.spinnerServico);


            //serviço
            editTextTipoCabelo = (EditText) this.findViewById(R.id.editTextTipoCabeloEditar);
            editTextGramasCabelo = (EditText) this.findViewById(R.id.editTextGramasEditar);
            editTextCentimetrosCabelo = (EditText) this.findViewById(R.id.editTextCentimetrosEditar);
            editTextManutencoes = (EditText) this.findViewById(R.id.editTextManutencoesEditar);
            editTextObservacao = (EditText) this.findViewById(R.id.editTextObservacaoEditar);


            btCEP = (Button) this.findViewById(R.id.btcep);
            edtCEP = (EditText) this.findViewById(R.id.edtCEP);
            //retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.postmon.com.br/v1/cep/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            //campo telefone  //para adicionar mascara no campo 09/07/2019
            MaskEditTextChangedListener maskTel = new MaskEditTextChangedListener("(##)#########", editTextTelefone);
            editTextTelefone.addTextChangedListener(maskTel);// teste mascara 09/07/2019

            //mascara de CEP
            MaskEditTextChangedListener masCEP = new MaskEditTextChangedListener("#####-###", edtCEP);
            edtCEP.addTextChangedListener(masCEP);//teste mascara CEP 11/07/2019

        } catch (Exception e) {

            Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_LONG).show();//adicionei 15/02/2019

        }

    }

    //metodo que cria os eventos para os componetes
    protected void CriarEventos() {

        final Calendar calendarDataAtual = Calendar.getInstance();
        int anoAtual = calendarDataAtual.get(Calendar.YEAR);
        int mesAtual = calendarDataAtual.get(Calendar.MONTH);
        int diaAtual = calendarDataAtual.get(Calendar.DAY_OF_MONTH);

        try {
            //criando a POPUPcom calendario
            datePickerDialogDataNascimento = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int anoSelecionado, int mesSelecionado, int diaSelecionado) {

                    //formatando o mês com dois dígitos
                    String mes = (String.valueOf(mesSelecionado + 1)).length() == 1 ? "0" + (mesSelecionado + 1) : String.valueOf(mesSelecionado);

                    editTextDataNascimento.setText(diaSelecionado + "/" + mes + "/" + anoSelecionado);

                }
            }, anoAtual, mesAtual, diaAtual);
            //criando evento click para campo data de nascimento do POPUP
            editTextDataNascimento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    datePickerDialogDataNascimento.show();
                }
            });
            //criando evento click para o alterar
            buttonAlterar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Alterar_onClick();
                }
            });

            //criando evento click para o botão voltar
            buttonVoltar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertVoltar();
                    //original
//                    Intent intntMainActivity = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intntMainActivity);
//                    finish();
                }
            });

            //teste exclusão 08/07/2019    ainda em teste    ainda a implementar *************
            //region excluir registro
//            btnExcluir.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        //devo altera para alert 08/07/2019
//                    pessoaRepository.Excluir(pessoaModels.getCodigo());//.Excluir(pessoaModels.get(position).getCodigo());//agora não tem position mais pois e uma tela exclusiva 08/07/2019 // veio nulo
//                        //mostra a mensagem após excluir um registro
//                        Toast.makeText(myContext, "Registro excluido com sucesso !!!", Toast.LENGTH_SHORT).show();
//
//                     //chama o metodo atualizar
//
//                        AtualizaTelaAposExclusao();//crie novo metodo 08/07/2019
//                    } catch (Exception ex) {
//                        Toast.makeText(myContext, ex.getMessage(), Toast.LENGTH_SHORT).show();//teste 08/07/2019
//                    }
//
//                }
//            });

            //so para não fechar app  18/07/2019
            btnExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Teste", Toast.LENGTH_SHORT).show();
                }
            });


            //endregion

        } catch (Exception e) {

            Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_LONG).show();//adicionei 15/02/2019
        }


        //botao CEP
        btCEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitarEnderecoEditar();
            }
        });
    }


    protected void Alterar_onClick() {

        try {//aki ele ja tem que estar com estado civil carregado !!!! não é aki    então tambem tera de estar com servico carregado 17/07/2019 elias
            //Valida se os campos estão vazios antes de alterar o registro
            if (editTextNome.getText().toString().trim().equals("")) {
                Uteis.Alert(this, this.getString(R.string.nome_obrigatorio));

                //foco no campo
                editTextEndereco.requestFocus();
            } else if (editTextEndereco.getText().toString().trim().equals("")) {
                Uteis.Alert(this, this.getString(R.string.endereco_obrigatorio));

                //foco no campo
                editTextEmail.requestFocus();
            } else if (editTextEmail.getText().toString().trim().equals("")) {
                Uteis.Alert(this, this.getString(R.string.email_obrigatorio));

                //foco no campo
                editTextEmail.requestFocus();
            } else if (editTextTelefone.getText().toString().trim().equals("")) {
                Uteis.Alert(this, this.getString(R.string.telefone_obrigatorio));

                //foco no campo
                editTextTelefone.requestFocus();
            } else if (!radioButtonMasculino.isChecked() && !radioButtonFeminino.isChecked()) {
                Uteis.Alert(this, this.getString(R.string.sexo_obrigatorio));

            } else if (editTextDataNascimento.getText().toString().trim().equals("")) {
                Uteis.Alert(this, this.getString(R.string.data_nascimento_obrigatorio));

                //foco no campo
                editTextDataNascimento.requestFocus();

                //terei de acrescentar os campos do servço 17/07/2019

            } else {


                AlertAlterar();

            }
        } catch (Exception e) {
            Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    //CARREGA OS ESTADOS CIVIS DO CAMPO SpinnerEstadoCivil
    private void CarregaEstadoCivis() {
        try {
            List<EstadoCivilModel> itens = new ArrayList<>();

            itens.add(new EstadoCivilModel("S", "Solteiro(a)"));
            itens.add(new EstadoCivilModel("C", "Casado(a)"));
            itens.add(new EstadoCivilModel("V", "Viuvo(a)"));
            itens.add(new EstadoCivilModel("D", "Divorciado(a)"));

            arrayAdapterEstadoCivil = new ArrayAdapter<EstadoCivilModel>(this, android.R.layout.simple_spinner_item, itens);//estava esquecendo itens
            arrayAdapterEstadoCivil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerEstadoCivil.setAdapter(arrayAdapterEstadoCivil);
        } catch (Exception e) {
            Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_LONG).show();//teste
        }

    }

    //POSICIONA O ESTADO CIVIL PARA EDIÇÃO
    protected void PosicionaEstadoCivil(String chaveEstadoCivil) {//ESTA CHEGANDO VALOR DO ESTADO CIVIL AKI 18/02/2019

        try {
            for (int index = 0; index < arrayAdapterEstadoCivil.getCount(); index++) {

                if (((EstadoCivilModel) arrayAdapterEstadoCivil.getItem(index)).getCodigo().equals(chaveEstadoCivil)) {

                    spinnerEstadoCivil.setSelection(index);
                    break;
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    //teste serviço
    //POSICIONA O Serviço PARA EDIÇÃO teste 11/07/2019
    // region Carregar informações no SPinner Serviço
    protected void carregaServicos() {
//teste desse metodo 05/07/2019
        try {
            //       ArrayAdapter<ServicosModel> arrayAdapter;

            List<ServicoModel> itensServico = new ArrayList<>();//new ArrayList<ServicosModel>()

            itensServico.add(new ServicoModel("Alongamento", "Alongamento adesivado"));//valor esta fixo aki  deve ser via app cadastrado pela usuaria ! 05/07/2019
            itensServico.add(new ServicoModel("Outros", "OutrosServiço"));//valor deve ser dinamico 05/07/2019

            arrayAdapterServico = new ArrayAdapter<ServicoModel>(this, android.R.layout.simple_spinner_item, itensServico);
            arrayAdapterServico.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerServico.setAdapter(arrayAdapterServico);

        } catch (Exception ex) {
            Toast.makeText(myContext, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    //fim
    protected void PosicionaServico(String chaveServico) {//ESTA CHEGANDO VALOR DO ESTADO CIVIL AKI 18/02/2019  somente para edição de registro que posiciono o spinner

        try {
            for (int index = 0; index < arrayAdapterServico.getCount(); index++) {

                if (((ServicoModel) arrayAdapterServico.getItem(index)).getServicoCodigo().equals(chaveServico)) {

                    spinnerServico.setSelection(index);//estava estado civil aki 17/07/2019 pode sr por isso não estava carregando do banco de dados
                    break;
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    //endregion


    //CARREGA OS VALORES NOS CAMPOS APÓS RETORNAR DO SQLITE
    protected void CarregaValoresCampos() {//aki não esta realizando o carregamento das informações no campo !!!

        PessoaRepository pessoaRepository = new PessoaRepository(this);
        //adicionei try 18/02/2019
        try {

            //PEGA O ID DA PESSOA QUE FOI PASSADO COMO PARAMETRO ENTRE AS TELAS
            Bundle extra = this.getIntent().getExtras();
            int id_pessoa = extra.getInt("id_pessoa");//id=  numero de pessoa cadastradas no app

            //CONSULTA PESSOA POR ID
            PessoaModel pessoaModel = pessoaRepository.GetPessoa(id_pessoa);//id= 4

            //SETA O CODIGO NA VIEW
            editTextCodigo.setText(String.valueOf(pessoaModel.getCodigo()));//da erro nessa linha 15/02/2019
            //SETA O NOME NA VIEW
            editTextNome.setText(String.valueOf(pessoaModel.getNome()));
            //SETA O ENDEREÇO NA VIEW
            editTextEndereco.setText(String.valueOf(pessoaModel.getEndereco()));
            //setar EMAIL  01/07/2019
            editTextEmail.setText(String.valueOf(pessoaModel.getEmail()));//validado ok 01/07/2019

            //setando Telefone
            editTextTelefone.setText(String.valueOf(pessoaModel.getTelefone()));//validado ok 02/07/2019


            //SETA O SEXO NA VIEW
            if (pessoaModel.getSexo().equals("M"))
                radioButtonMasculino.setChecked(true);
            else
                radioButtonFeminino.setChecked(true);

            //SETA A DATA DE NASCIMENTO NA VIEW
            editTextDataNascimento.setText(pessoaModel.getDataNascimento());

            //POSICIONA O ESTADO CIVIL
            this.PosicionaEstadoCivil(pessoaModel.getEstadoCivil());


            //teste novos campos serviço 19/07/2019  22/07/2019 está carregando informações  ok
            editTextTipoCabelo.setText(String.valueOf(pessoaModel.getTipoCabelo()));//catch aki
            editTextGramasCabelo.setText(String.valueOf(pessoaModel.getGramasCabelo()));
            editTextCentimetrosCabelo.setText(String.valueOf(pessoaModel.getCmCabelo()));
            editTextManutencoes.setText(String.valueOf(pessoaModel.getManutencoesCabelo()));
            editTextObservacao.setText(String.valueOf(pessoaModel.getObservacoesCabelo()));//ainda em teste 19/07/2019

            //teste serviço
            this.PosicionaServico(pessoaModel.getServico());//ainda em teste 11/07/2019

            // SETA SE O REGISTRO ESTÁ ATIVO
            if (pessoaModel.getRegistroAtivo() == 1)
                checkBoxRegistroAtivo.setChecked(true);


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    //endregion


    //para excluir cliente quando implementar nessa tela 08/07/2019
    private void AtualizaTelaAposExclusao() {
        Intent ConsultarActivity = new Intent(getApplicationContext(), ConsultarActivity.class);
        startActivity(ConsultarActivity);
        finish();
    }


    //region alerts

    public void AlertVoltar() {
        //cria o gerenciador do AlertDialog
        android.support.v7.app.AlertDialog.Builder alerta = new android.support.v7.app.AlertDialog.Builder(EditarActivity.this);

        //define o  titulo
        alerta.setTitle("titulo");


        //definir icone para alert em caso de usar alert padrão
        alerta.setIcon(R.mipmap.voltar); // se quiser colocar icone ! 25/06/2019


        //define a mensagem
        alerta.setMessage("Deseja voltar, todas as alterações não salvas seram perdidas");

        //define um botão com positivo
        alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  Toast.makeText(EditarActivity.this, "positivo = " + which, Toast.LENGTH_LONG).show();

                //voltar para tela Principal
                Intent intntMainActivity = new Intent(getApplicationContext(), ConsultarActivity.class);// MainActivity.class);  // alterei para voltar para tela de lista de clientes cadasrados 03/07/2019
                startActivity(intntMainActivity);
                finish();

//ainda em teste 25/06/2019

            }
        });

        //teste 25/06/2019
        alerta.setNegativeButton("NÃO", null);

        //exibe
        alerta.show();

    }


    //atualizar registro

    public void AlertAlterar() {//ainda a implementar 26/06/2019
        //cria o gerenciador do AlertDialog
        android.support.v7.app.AlertDialog.Builder alerta = new android.support.v7.app.AlertDialog.Builder(EditarActivity.this);

        //define o  titulo
        alerta.setTitle("titulo");


        //definir icone para alert em caso de usar alert padrão
        alerta.setIcon(R.mipmap.atualizar); // se quiser colocar icone ! 25/06/2019


        //define a mensagem

        alerta.setMessage("Deseja atualizar esse registro?");

        //define um botão com positivo

        alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(EditarActivity.this, "positivo = " + which, Toast.LENGTH_LONG).show();
                //inicio teste
                //original
                //Criando um OBJETO Pessoa
                PessoaModel pessoaModel = new PessoaModel();
                pessoaModel.setCodigo(Integer.parseInt(editTextCodigo.getText().toString()));

                //setando valor do campo nome
                pessoaModel.setNome(editTextNome.getText().toString().trim());

                //setando o endereco
                pessoaModel.setEndereco(editTextEndereco.getText().toString().trim());

                //setando Email 01/07/2019
                pessoaModel.setEmail(editTextEmail.getText().toString().trim());//ok

                //setando Telefone
                pessoaModel.setTelefone(editTextTelefone.getText().toString().trim());//ok

                //setando o sexo
                if (radioButtonMasculino.isChecked())//ate aki foi blz 15/10/2019
                    pessoaModel.setSexo("M");
                else
                    pessoaModel.setSexo("F");

                //setando a data de nascimento
                pessoaModel.setDataNascimento(editTextDataNascimento.getText().toString().trim());
//teste novo tipo de campo
//                pessoaModel.setDataNascimento(Integer.parseInt(editTextDataNascimento.getText().toString().trim()));//teste 09/07/2019
//TIVE DE VOLTAR PARA TEXTO


                //realizando cast para pegar o obj do estado civil selecionado
                EstadoCivilModel estadoCivilModel = (EstadoCivilModel) spinnerEstadoCivil.getSelectedItem();
                //setando estado civil
                pessoaModel.setEstadoCivil(estadoCivilModel.getCodigo());//aki demora 15/10/2019   da erroa aki


                //teste serviço 11/07/2019
                ServicoModel servicoModel = (ServicoModel) spinnerServico.getSelectedItem();//teste 11/07/2019
                //setando serrviço
                pessoaModel.setServico(servicoModel.getServicoCodigo());//teste 11/07/2019


                //seta o registro como inativo
                pessoaModel.setRegistroAtivo((byte) 0);

                //se tiver selecionado seta o registro como Ativo
                if (checkBoxRegistroAtivo.isChecked())
                    pessoaModel.setRegistroAtivo((byte) 1);


                //teste novos campos serviço 22/07/2019

                //setando o TipoCabelo
                pessoaModel.setTipoCabelo(editTextTipoCabelo.getText().toString().trim());

                //setando o gramas
                pessoaModel.setGramasCabelo(editTextGramasCabelo.getText().toString().trim());

                //setando o CM
                pessoaModel.setCmCabelo(editTextCentimetrosCabelo.getText().toString().trim());

                //setando o manutenções
                pessoaModel.setManutencoesCabelo(editTextManutencoes.getText().toString().trim());

                //setando o observação
                pessoaModel.setObservacoesCabelo(editTextObservacao.getText().toString().trim());
                //fim teste 22/07/2019


                //alterando o registro
                new PessoaRepository(EditarActivity.this).Atualizar(pessoaModel);

                //mensagem a ser exibida
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditarActivity.this);

                //adicionando titulo a mensagem de alerta
                alertDialog.setTitle(R.string.app_name);

                //mensagem a ser exibida
                alertDialog.setMessage("Registro alterado com sucesso !");

                //cria um botão com o texto OK sem Ação
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //retorna para a tela de consulta
                        Intent intentRedirecionar = new Intent(getApplicationContext(), ConsultarActivity.class);
                        startActivity(intentRedirecionar);

                        finish();

                    }
                });

                //mostra a mensagem na tela
                alertDialog.show();


                //fim teste 26/06/2019
            }
        });

        //teste 25/06/2019
        alerta.setNegativeButton("NÃO", null);


        //exibe

        alerta.show();

    }

    //endregion

    private void solicitarEnderecoEditar() {
        String cep = edtCEP.getText().toString();
        PostmonService service = retrofit.create(PostmonService.class);

        Call<Endereco> call = service.getEndereco(cep);

        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful()) {
                    Endereco endereco = response.body();
                    String strEndereco = "Cidade :" + endereco.getCidade() + "\n" +
                            "bairro : " + endereco.getBairro() + "\n" +
                            "Logradouro : " + endereco.getLogradouro();

                    editTextEndereco.setText(strEndereco);

                } else
                    editTextEndereco.setText("Erro :" + response.code());//envia mensagem de err de conexão 11/07/2019
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Não foi possivel realizar a requisição", Toast.LENGTH_LONG).show();
            }
        });

    }

}
