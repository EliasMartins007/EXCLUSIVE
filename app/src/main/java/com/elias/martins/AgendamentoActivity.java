package com.elias.martins;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.elias.martins.model.HorarioModel;
import com.elias.martins.model.PessoaModel;
import com.elias.martins.repository.PessoaRepository;
import com.elias.martins.uteis.LinhaConsultarAdapter;
import com.elias.martins.uteis.Uteis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

//classe adicionada por elias não faz parte do projeto original
public class AgendamentoActivity extends AppCompatActivity {
    Spinner spinnerHorario;
    Spinner spinnerClientesHorario;

    //criar POPUP calendario
    DatePickerDialog datePickerDialogDataNascimentoHorario;//alterar nome da variavel depois 23/07/2019

    EditText editTextDatanascimentoHorario;

    //para spinner clientes 23/07/2019
    String[] nomeCampos;

    Context myContext = null;


    PessoaRepository dbTeste;//24/07/2019


    //teste botao 24/07/2019
    ImageButton btnAgendarHorario;
    ImageButton btnAlterarHorario;
    ImageButton btnCancelarHorario;


    private String selecionado;//teste para spinner vazio 24/07/2019
    private PessoaModel pessoaModel;//teste para spinner vazio 24/07/2019
    private int ultima_posicao;


    @Override
    public void onCreate(Bundle savedInstanceState) {//tenho de validar se existe registro de pesso cadastrada , pois esta fechando o app 25/07/2019
        super.onCreate(savedInstanceState);

        //teste tirar barra superior padrao do app   24/07/2019
        getSupportActionBar().hide(); //aqui a mágica

        setContentView(R.layout.activity_agendamento);


        //recuperar componentes tela
        this.criarComponentesTela();

        //criar Eventos tela
        this.criarEventos();

        //Localização para calendadrio
        this.localizacao();

        //carregar calendario no EditText
        this.criarEventoCalendario();//adicionei o this 23/07/2019

        //carregar hora no spinner
        this.carregaHorariosTela();//adicionei this pois estav apresentando o pacote na tela ao invez do horario descrição 22/07/2019

        //teste app banco spinner 23/07/2019  17:40
        //spinnerClientesHorario.setOnItemSelectedListener();
        this.loadSpinnerData();

        //teste para spinnerCientes
        //     this.carregarClientesSpinnerHorario();
    }


    private void loadSpinnerData() {//teste buscar clientes no banco 23/07/2019

        dbTeste = new PessoaRepository(getApplicationContext());//estava com this 24/07/2019
        try {
            //  DatabaseUteis db2 = new DatabaseUteis(getApplicationContext());

            List<PessoaModel> pessoa = dbTeste.SelecionarTodosHoraio();//getAllLabels(); // metodo vem da classe banco terei de implementar ainda

            //criando adaptador para o spinner
            ArrayAdapter<PessoaModel> dataAdapter = new ArrayAdapter<PessoaModel>(AgendamentoActivity.this, android.R.layout.simple_spinner_item, pessoa) {//;//lables


                //teste  comentei pois estav carregando spinner vazio sem opção olhar mais tarde
//                @Override
//                public int getCount() {
//                    return super.getCount() - 1;
//                }
            };

            //list view com botões de opção
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


      //      dataAdapter.toString();//teste 24/07/2019


            //anexando o adaptador de dados ao spinner
            spinnerClientesHorario.setAdapter(dataAdapter);//dataAdapter.getFilter().convertResultToString("") teste ainda a resolver 24/07/2019

            //  dataAdapter.toString();//teste elias converter texto do spinner de pacote para o texto que gostari;24/07/2019
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //region criaComponentesTela
    private void criarComponentesTela() {

        btnAgendarHorario = (ImageButton) this.findViewById(R.id.btnAgendarHorario);

        btnAlterarHorario = (ImageButton) this.findViewById(R.id.btnAlterarHorario);

        btnCancelarHorario = (ImageButton) this.findViewById(R.id.btnCancelarHorario);


        spinnerHorario = (Spinner) this.findViewById(R.id.spinnerHorarios);//o this é importante aki

        //teste para clientes cadastradas 23/07/2019
        spinnerClientesHorario = (Spinner) this.findViewById(R.id.spinnerClientesHorario);//ainda em teste 23/07/2019

        editTextDatanascimentoHorario = (EditText) this.findViewById(R.id.editTextDataNascimentoHorario);//teste 23/07/2019  //duvida se teria o this aki ???  23/07/2019


    }

    //endregion

    //region Carrega Horarios

    protected void carregaHorariosTela() {//metodo ainda em teste  22/07/2019
        ArrayAdapter<HorarioModel> arrayAdapter;//esta carregando o pacote ao invez dos itens 22/07/2019

        List<HorarioModel> horarios = new ArrayList<HorarioModel>();

        horarios.add(new HorarioModel("8", "8:00"));
        horarios.add(new HorarioModel("9", "9:00"));
        horarios.add(new HorarioModel("10", "10:00"));
        horarios.add(new HorarioModel("11", "11:00"));
        horarios.add(new HorarioModel("12", "12:00"));
        horarios.add(new HorarioModel("13", "13:00"));
        horarios.add(new HorarioModel("14", "14:00"));
        horarios.add(new HorarioModel("15", "15:00"));
        horarios.add(new HorarioModel("16", "16:00"));
        horarios.add(new HorarioModel("17", "17:00"));
        horarios.add(new HorarioModel("18", "18:00"));
        horarios.add(new HorarioModel("19", "19:00"));
        horarios.add(new HorarioModel("20", "20:00"));
        horarios.add(new HorarioModel("21", "21:00"));

        arrayAdapter = new ArrayAdapter<HorarioModel>(this, android.R.layout.simple_spinner_item, horarios);  // {//;
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerHorario.setAdapter(arrayAdapter);
    }
    // endregion

    //region Localização para traduzir o calendario
    private void localizacao() {

        Locale locale = new Locale("pt", "BR");
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);
    }
//endregion

    //region Evento para calendario
    private void criarEventoCalendario() {

        final Calendar calendarDataAtual = Calendar.getInstance();
        int anoAtual = calendarDataAtual.get(Calendar.YEAR);
        int mesAtual = calendarDataAtual.get(Calendar.MONTH);
        int diaAtual = calendarDataAtual.get(Calendar.DAY_OF_MONTH);

        try {
            //Montando obj de data para preencher os campos quando for selecionado
            datePickerDialogDataNascimentoHorario = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {
                    //formatando o mês com dois digitos
                    String mes = (String.valueOf((mesSelecionado + 1)).length() == 1 ? " 0 " + (mesSelecionado + 1) : String.valueOf(mesSelecionado));

                    editTextDatanascimentoHorario.setText(diaSelecionado + "/" + mes + "/" + anoSelecionado);
                }
            }, anoAtual, mesAtual, diaAtual);

            //CRIANDO EVENTO NO CAMPO DE DATA PARA ABRIR O POPUP
            editTextDatanascimentoHorario.setOnClickListener(new View.OnClickListener() {//da catch aki 23/07/2019  estav dandoo erro devido a ordem dos eventos ainda não tinha recuperado da tela o ediText 23/07/2019
                @Override
                public void onClick(View v) {
                    datePickerDialogDataNascimentoHorario.show();
                }
            });


//            //criando evento   retirei o evento de focus pois ja estava abrindo a tela carregando o calendario , não é viavel 23/07/2019
//            editTextDatanascimentoHorario.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    datePickerDialogDataNascimentoHorario.show();
//                }
//            });

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
    //endregion

    //region Eventos tela
    private void criarEventos() {

        //botão agendar Horário
        btnAgendarHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCampos();
            }
        });


//        //spinner hora  // não funcionou 100% 24/07/2019
//        spinnerHorario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//teste 24/07/2019
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//                if (position == 0) {
//                    Toast.makeText(getApplicationContext(), "spinner esta vazio ", Toast.LENGTH_SHORT).show();
//                } else
//                    Toast.makeText(getApplicationContext(), "Selecionado !!!", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
////fim spinner


    }

    //endregion

    // region validarCampos
    private void validarCampos() {
        try {
            // spinnerHorario.
            if (editTextDatanascimentoHorario.getText().toString().trim().equals("")) {
                Uteis.Alert(this, "Campo Data do agendamento de preenchimento obrigatório !");
            } else
                alert_agendar();

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    //endregion


    //region AlertAgendar
    private void alert_agendar() {
    }
    //endregion


    //criar outros metodos aki 23/07/2019 elias

    // buscarNoBanco
    public void carregarClientesSpinnerHorario() {

        try {
            PessoaRepository pessoaRepository = new PessoaRepository(myContext);//this //original

            //busca clientes cadastradas
            List<PessoaModel> clientes = pessoaRepository.SelecionarTodosHoraio();//seleciono metodo que irei buscar os usuarios na controller(PessoaRepository)23/07/2019

            //seta o adatpter com os registros
            spinnerClientesHorario.setAdapter(new LinhaConsultarAdapter(this, clientes));//.setAdapter(new LinhaConsultarAdapter(this, clientes));//estou com problema aki era this, mas joga no consultActivity


            //      spinnerClientesHorario.setAdapter(clientes.getAdapter(getApplicationContext()));
            //      spinnerClientesHorario.setPrompt("Selecione um cliente");


        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //outro exemplo
//    ArrayList<String> ArrayListNames = new ArrayList<String>();
//
//    Cursor c = db.rawQuery("SELECT * FROM table_one", null);
//    if (c.moveToFirst()) {
//        do {
//            ArrayListNames.add(c.getString(c.getColumnIndex("name")));
//            ArrayListNames.add(c.getString(c.getColumnIndex("age")));
//        }
//        while (c.moveToNext());
//    }
//    c.close();
//


}
