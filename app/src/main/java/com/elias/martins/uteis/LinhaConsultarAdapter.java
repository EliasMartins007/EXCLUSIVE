package com.elias.martins.uteis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.elias.martins.AgendamentoActivity;
import com.elias.martins.ConsultarActivity;
import com.elias.martins.EditarActivity;
import com.elias.martins.R;
import com.elias.martins.model.PessoaModel;
import com.elias.martins.repository.PessoaRepository;

import java.util.ArrayList;
import java.util.List;

public class LinhaConsultarAdapter extends BaseAdapter {

    //criando um obj layoutInflater para fazer link a nossa view
    private static LayoutInflater layoutInflater = null;

    //criando lista de pessoas
    List<PessoaModel> pessoaModels = new ArrayList<PessoaModel>();

    //criando um obj da nossa classe que faz acesso ao banco de dados
    PessoaRepository pessoaRepository;//utilizar para DAO tambem!! 02/07/2019

    //criando um obj da nossa atividade que contem a lista
    private ConsultarActivity consultarActivity;


    private AgendamentoActivity agendamentoActivity;//teste 23/07/2019

    //Construtor que ira receber a nossa atividade como parametro e a lista de pessoas que vai retornar
    //da nossa base de dados
    public LinhaConsultarAdapter(ConsultarActivity consultarActivity, List<PessoaModel> pessoaModels) {//(ConsultarActivity consultarActivity, List<PessoaModel> pessoaModels, AgendamentoActivity agendamentoActivity)  // original

        this.pessoaModels = pessoaModels;
        this.consultarActivity = consultarActivity;
        this.layoutInflater = (LayoutInflater) this.consultarActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.pessoaRepository = new PessoaRepository(consultarActivity);
    }



    //teste Horario 23/07/2019
    public LinhaConsultarAdapter(AgendamentoActivity agendamentoActivity, List<PessoaModel> pessoaModels) {//(ConsultarActivity consultarActivity, List<PessoaModel> pessoaModels, AgendamentoActivity agendamentoActivity)  // original

        this.pessoaModels = pessoaModels;
        //     this.consultarActivity = consultarActivity;
        this.agendamentoActivity = agendamentoActivity;//teste 23/07/2019
        //      this.layoutInflater = (LayoutInflater) this.consultarActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.pessoaRepository = new PessoaRepository(agendamentoActivity);//(consultarActivity) original
    }
    //fim teste 23/07/209




    //retorna a quantidade de registros encontrados    // olhar essa parte pode ser o numero de registros que preciso na tela  18/07/2019
    @Override
    public int getCount() {
        return pessoaModels.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    //metodo seta os valores de um item da nossa lista de pessoas para  uma  linha  do nosso listView

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        try {

            //criando um obj do tipo View para acessar o nosso arquivo de layout activity_linha_consultar.xml
            final View viewLinhaLista = layoutInflater.inflate(R.layout.activity_linha_consultar, null);

            //vinculando os campos do arquivo de layout(activity_linha_consultar.xml) aos obj declarados
            //campo que ira mostrar o código da pessoa  // esta com alguma coisa  nessa parte não esta vinculando as pessoas na tela?? 13/12/2018
            TextView textViewCodigo = (TextView) viewLinhaLista.findViewById(R.id.textViewCodigo);

            //campo que mostra o nome da pessoa
            TextView textViewNome = (TextView) viewLinhaLista.findViewById(R.id.textViewNome);

            //campo que mostra o endereço da pessoa
            TextView textViewEndereco = (TextView) viewLinhaLista.findViewById(R.id.textViewEndereco);

            //campo email 01/07/2019   funcionando ok

            TextView textViewEmail = (TextView) viewLinhaLista.findViewById(R.id.textViewEmail);


            //campo Telefone 02/07/2019
            TextView textViewTelefone = (TextView) viewLinhaLista.findViewById(R.id.textViewTelefone);

            //campo que mostra o Sexo da pessoa
            TextView textViewSexo = (TextView) viewLinhaLista.findViewById(R.id.textViewSexo);

            //campo que mostra o estado civil da pessoa
            TextView textViewEstadoCivil = (TextView) viewLinhaLista.findViewById(R.id.textViewEstadoCivil);



            //teste recuperar novos campos serviço a implementar

            TextView textViewTipoCabelo  = (TextView) viewLinhaLista.findViewById(R.id.textViewTipoCabelo);//teste 22/07/2019

            TextView textViewGramas  = (TextView) viewLinhaLista.findViewById(R.id.textViewGramas);

            TextView textViewCM  = (TextView) viewLinhaLista.findViewById(R.id.textViewCM);

            TextView textViewManutencoes  = (TextView) viewLinhaLista.findViewById(R.id.textViewManutencoes);

            TextView textViewObservacao  = (TextView) viewLinhaLista.findViewById(R.id.textViewObservacao);






//fim dos campos serviço 22/07/2019


            //campo que mostra a data de nascimento da pessoa
            TextView textViewNascimento = (TextView) viewLinhaLista.findViewById(R.id.textViewTracoNascimento);//18/07/2019 campo nasc estava (R.id.textViewDataNascimento);obj esta vindo null pois não existe esse obj na tela e sim outro textview !!!!! resolvido


            //TESTE servico // ainda em teste 11/07/2019
            TextView textViewServico = (TextView) viewLinhaLista.findViewById(R.id.textViewServico);//teste 11/07/2019  até então nenhuma alteração


            //campo que mostra se o registro da pessoa esta ativo ou não
            TextView textViewRegistroAtivo = (TextView) viewLinhaLista.findViewById(R.id.textViewRegistroAtivo);

            //criando o botao de  excluir um registro do banco
            Button buttonExcluir = (Button) viewLinhaLista.findViewById(R.id.buttonExcluir);

            //criando o botao de editar um registro
            Button buttonEditar = (Button) viewLinhaLista.findViewById(R.id.buttonEditar);

            //fim do recuperar os componentes


//SETANDO OS DADOS DO CLIENTE NO COMPONENTES DA LISTA

            //inicio de setar os valores 01/07/2019
            //setando o codigo no campo da nossa view
            textViewCodigo.setText(String.valueOf(pessoaModels.get(position).getCodigo()));

            //setando o nome no campo da nossa view
            textViewNome.setText(pessoaModels.get(position).getNome());

            //setando endereço no campo da nossa view
            textViewEndereco.setText(pessoaModels.get(position).getEndereco());

            //setando email no campo da nossa view
            textViewEmail.setText(pessoaModels.get(position).getEmail());//ok 02/07/2019

            //setando telefone no campo da nossa view
            textViewTelefone.setText(pessoaModels.get(position).getTelefone());//ok 03/07/2019


            //setando Sexo no campo da nossa view
            if (pessoaModels.get(position).getSexo().toUpperCase().equals("M"))
                textViewSexo.setText("Masculino");
            else
                textViewSexo.setText("Feminino");

            //setando Estado civil no campo da nossa view
            textViewEstadoCivil.setText(this.GetEstadoCivil(pessoaModels.get(position).getEstadoCivil()));

            //setando DataNascimento no campo da nossa view  estava dando erro aki 17/07/2019  17:48
            textViewNascimento.setText(pessoaModels.get(position).getDataNascimento());//fecha aplicação nessa linha  //quando vai setar dat da o erro   teste 03/07/2019 não funciona


            //setando serviço   esta dando nulo nessa linha  17/07/2019  era falta de Textview na tela de consulta para exibir
            textViewServico.setText(this.GetServicoConsulta(pessoaModels.get(position).getServico()));//ainda em teste 11/07/2019  //da catch aki  estava comentado 17/07/2018


            //setando Registro Ativo no campo da nossa view
            if (pessoaModels.get(position).getRegistroAtivo() == 1)
                textViewRegistroAtivo.setText("Registro Ativo:Sim");
            else
                textViewRegistroAtivo.setText("Registro Ativo:Não");


            //inicio campos serviço 22/07/2019    ok validado
            textViewTipoCabelo.setText(pessoaModels.get(position).getTipoCabelo());//ok 22/07/2019

            textViewGramas.setText(pessoaModels.get(position).getGramasCabelo());//ok 22/07/2019

            textViewCM.setText(pessoaModels.get(position).getCmCabelo());//ok 22/07/2019

            textViewManutencoes.setText(pessoaModels.get(position).getManutencoesCabelo());//ok 22/07/2019

            textViewObservacao.setText(pessoaModels.get(position).getObservacoesCabelo());//ok 22/07/2019




            //fim teste novos campos serviço


            //criando evento click para o botão excluir registro
            buttonExcluir.setOnClickListener(new View.OnClickListener() {//função de excluir registro na tela Consulta 24/06/2019
                @Override
                public void onClick(View v) {
                    try {
//antes de excluir tenho que adicionar validação por parte do usuario  24/06/2019 testando

                        //  alertYesNo.showMessage(myContext, "Deseja realmente excluir esta imagem?");//MyContext 24/06/2019
                        //                   chamaAlert(); // 26/06/2019 ainda sem sucesso, novo teste 02/07/2019
                        //fim teste

                        //original 25/06/2019
                        //excluindo um registro
                        pessoaRepository.Excluir(pessoaModels.get(position).getCodigo());

                        //mostra a mensagem após excluir um registro
                        Toast.makeText(consultarActivity, "Registro excluido com sucesso !!!", Toast.LENGTH_SHORT).show();

                        //chama o metodo que atualiza com os registro que ainda estão na base
                        AtualizaLista();
                    } catch (Exception ex) {
                        Toast.makeText(consultarActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();   //teste nesse Toast 26/06/2019
                        //alert.message(myContext, ex.getMessage());
                    }

                }
            });

            //criando evento click para o botão que vai redirecionar para a tela de edição do registro
            buttonEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intentRedirecionar = new Intent(consultarActivity, EditarActivity.class);
                        intentRedirecionar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intentRedirecionar.putExtra("id_pessoa", pessoaModels.get(position).getCodigo());
                        consultarActivity.startActivity(intentRedirecionar);  //comentei  as duas linhas ainda falta testar esta com erro no editar
                        consultarActivity.finish(); // comentei 17/01/2019
                    } catch (Exception e) {
                        //                 Toast.makeText(myContextLinhaConsulta, e.getMessage(), Toast.LENGTH_LONG).show();//retirei myContextLinhaConsulat para teste  02/07/2019
                        // Toast.makeText("erro",e.getMessage(),Toast.LENGTH_LONG).show(); // erro no toast 17/01/2019
                    }
                }
            });

            return viewLinhaLista;
        } catch (Exception ex) {
            Toast.makeText(layoutInflater.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();//teste
            return null;
        }
        //   return viewLinhaLista;

    }

    //region Alert exclusão a implementar
    //inicio teste alert 02/07/2019
    public void chamaAlert() {//não estou chamando alert ainda pois estou com problemas para excluir pessoa ainda


        try {


            //ainda em implementação 25/06/2019

            //      if(myContextLinhaConsulta != null){

            //cria o  gerenciador do AlerDialog   //  this.consultarActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            AlertDialog.Builder alerta = new AlertDialog.Builder(consultarActivity);//(myContext)//erro contexto ??? // (consultarActivity); 26/06/2019 funciona o app mas sem funcionalidade no botao
//android.support.v7.app.                                 //da erro de cara nesse contexto com esse contexto (myContextLinhaConsulta);02/07/2019

            //define o Titulo
            alerta.setTitle("Aviso");

            //define a mensagem
            alerta.setMessage("Deseja realmente excluir essa pessoa ?");

            //defini os botões
            alerta.setNegativeButton("Não", null);

            //positivo
            alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //excluindo um registro


                    //inicio teste DAO

                    PessoaModel pessoaModel = pessoaRepository.GetPessoa(i);//teste com i mas estava dando erro 02/07/2019 pois no metodo que funciona seria (position)
                    if (pessoaModel.getCodigo() != null) {

                        pessoaRepository.deleta(pessoaModel);
                    }
                    //fim teste DAO

                    //original
//                   pessoaRepository.Excluir(pessoaModels.get(position).getCodigo());//erro aki position

                    //mostra a mensagem após excluir um registro
                    Toast.makeText(consultarActivity, "Registro excluido com sucesso !!!", Toast.LENGTH_SHORT).show();

                    //chama o metodo que atualiza com os registro que ainda estão na base
                    AtualizaLista();

                }
            });

            alerta.show();

            //     }

            //       Toast.makeText(myContextLinhaConsulta , "ERROR : " , Toast.LENGTH_SHORT).show();//teste desse toast 02/07/2019
        } catch (Exception ex) {
            Toast.makeText(consultarActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();//teste Toast 26/06/2019
        }


//fim teste 02/07/2019


    }
//endregion


    public String GetServicoConsulta(String codigoServico) {

        if (codigoServico.equals("Alongamento"))
            return "Alongamento adesivado";
        else if (codigoServico.equals("Outros"))
            return "Outros serviços";
        else
            return "nenhum serviço escolhido";//mudar essa palavra futuramente 11/07/2019
    }


    //metodo que retorna a descrição do estado civil pelo codigo

    public String GetEstadoCivil(String codigoEstadoCivil) {


        if (codigoEstadoCivil.equals("S"))
            return "Solteiro(a)";
        else if (codigoEstadoCivil.equals("C"))
            return "Casado(a)";
        else if (codigoEstadoCivil.equals("V"))
            return "Viuvo(a)";
        else
            return "Divorciado(a)";

    }

    //region Atualização da tela após exclusao de registro
    //Atualiza a lista depois de uma exclusão de registro
    private void AtualizaLista() {

        this.pessoaModels.clear();
        this.pessoaModels = pessoaRepository.SelecionarTodos();//selecionar todos vem de repository que seria aonde realiza  query
        this.notifyDataSetChanged();

    }

//endregion


}
