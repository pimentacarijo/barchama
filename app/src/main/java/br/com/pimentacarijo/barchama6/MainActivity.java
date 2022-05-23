package br.com.pimentacarijo.barchama6;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {

    // início do conteudo geral

    // início das declarações de variáveis

    String USID;
    String NOME;
    String CHAVEDB;
    TextView textoNome;
    TextView textoVerificador;

    // Notification channel ID.
    private static final String CHANNEL_ID = "primary_notification_channel"; // Notification channel ID.
    private static int NOTIFICATION_ID = 0;

    private static final String TAG = "MinhaTag"; // para olhar informações no Logcat

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

    ApiInterface apiInterface;


    // início dos métodos

    private void receberUsidNome() { // Recebe os dados da tela de login

        Intent telaLogin = getIntent();
        Bundle bundle = telaLogin.getExtras();
        USID = bundle.getString("usid");
        NOME = bundle.getString("nome");
        CHAVEDB = bundle.getString("chave");
        //Toast.makeText(getApplicationContext(), "Nome= "+NOME+" "+USID, Toast.LENGTH_LONG).show();
        StringBuilder str = new StringBuilder();
        str.append("Olá ");
        str.append(NOME);
        str.append("!");
        textoNome.setText( str );
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        //}
    }

    private void vibrar(int duracao) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(duracao);
    }

    private void mostrarNotificacaoBar(String setor, String mensagem) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_drink)
                    .setContentTitle(setor)
                    .setContentText(mensagem)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        // estas duas linhas de código abaixo chamam a notificação
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build()); // notificationId is a unique int for each notification that you must define
        NOTIFICATION_ID++; // para que apareça mais uma notificação, cada vez que é chamada esta função
    }

    private void mostrarNotificacaoCozinha(String setor, String mensagem) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_food)
                .setContentTitle(setor)
                .setContentText(mensagem)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        // estas duas linhas de código abaixo chamam a notificação
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build()); // notificationId is a unique int for each notification that you must define
        NOTIFICATION_ID++; // para que apareça mais uma notificação, cada vez que é chamada esta função
    }

    private void mostrarNotificacaoCaixa(String setor, String mensagem) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_money)
                .setContentTitle(setor)
                .setContentText(mensagem)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        // estas duas linhas de código abaixo chamam a notificação
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build()); // notificationId is a unique int for each notification that you must define
        NOTIFICATION_ID++; // para que apareça mais uma notificação, cada vez que é chamada esta função
    }

    private void mostrarNotificacaoRecepcao(String setor, String mensagem) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_recepcao)
                .setContentTitle(setor)
                .setContentText(mensagem)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        // estas duas linhas de código abaixo chamam a notificação
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build()); // notificationId is a unique int for each notification that you must define
        NOTIFICATION_ID++; // para que apareça mais uma notificação, cada vez que é chamada esta função
    }

    public void direcionarSetor( int setor ) {

        if ( setor==1 ) { // é garçon



        } else if ( setor==2 ) { // é barman

            Toast.makeText(getApplicationContext(), "Bar chamando!", Toast.LENGTH_SHORT).show();
            mostrarNotificacaoBar(getString(R.string.textSetorBar), getString(R.string.textMsgChamando));

        } else if ( setor==3 ) { // é cozinha

            Toast.makeText(getApplicationContext(), "Cozinha chamando!", Toast.LENGTH_SHORT).show();
            mostrarNotificacaoCozinha("COZINHA", "Cozinha chamando!");

        } else if ( setor==4 ) { // é caixa

            Toast.makeText(getApplicationContext(), "Caixa chamando!", Toast.LENGTH_SHORT).show();
            mostrarNotificacaoCaixa("CAIXA", "Caixa chamando!");

        } else if ( setor==5 ) { // é recepção

            Toast.makeText(getApplicationContext(), "Recepção chamando!", Toast.LENGTH_SHORT).show();
            mostrarNotificacaoRecepcao("RECEPÇÃO", "Recepção chamando!");

        }

        vibrar(2000);
    }

    public void verificarChamado() {

        //Toast.makeText(getApplicationContext(), "Verificando...5", Toast.LENGTH_SHORT).show();

        Call<TodoOBJ> call = apiInterface.getTodoObj();
        call.enqueue(new Callback<TodoOBJ>() {
            @Override
            public void onResponse(Call<TodoOBJ> call, Response<TodoOBJ> response) {
                Log.e(TAG, "Resposta do Servidor: " + response.body());
                //Toast.makeText(getApplicationContext(), "onResponse:" + response.code(), Toast.LENGTH_SHORT).show(); // mostra por exemplo 200
                TodoOBJ notificacao = response.body();
                Toast.makeText(getApplicationContext(), "onResponse:" + notificacao.getMensagem(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<TodoOBJ> call, Throwable t) {
                Log.e(TAG, "Falha do Servidor: " + t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(), "onFailure:" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void verificarChamadoQuery() {

        Call<TodoOBJ> todoOBJCall = apiInterface.getTodoUsingQuery(USID, CHAVEDB);
        todoOBJCall.enqueue(new Callback<TodoOBJ>() {
            @Override
            public void onResponse(Call<TodoOBJ> call, Response<TodoOBJ> response) {
                textoVerificador.setText("Conectato!");
                Log.e(TAG, "Resposta do Servidor: " + response.body());
                if ( response.isSuccessful() ) {
                    TodoOBJ noti = response.body();
                    if ( noti.getNotificacao() == true ) { // tem notificação para este garçom
                        direcionarSetor(noti.getSetor());
                    }
                } else { // não recebeu uma resposta 200 por exemplo do servidor
                    Log.e(TAG, "Erro Servidor: " + response.code()); // aqui é quando não consegue ter uma resposta adequada do servidor
                    Toast.makeText(getApplicationContext(), "Erro Servidor: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TodoOBJ> call, Throwable t) {
                Log.e(TAG, "Falha de acesso: " + t.getLocalizedMessage());
                //Toast.makeText(getApplicationContext(), "Falha de acesso: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                textoVerificador.setText("Falha de acesso: " + t.getLocalizedMessage());
            }
        });

    }

    public void verificarChamadoPost() {
        TodoPostOBJ todoPostOBJ = new TodoPostOBJ("chave1", USID);
        //Call<TodoOBJ> todoPostCall = apiInterface.postTodoPostObj(todoPostOBJ);
        //todoPostCall.enqueue(new Callback<TodoOBJ>() {
        Call<TodoOBJ> postTodoPostObj = apiInterface.postTodoPostObj(todoPostOBJ);
        postTodoPostObj.enqueue(new Callback<TodoOBJ>() {
            @Override
            public void onResponse(Call<TodoOBJ> call, Response<TodoOBJ> response) {
                Log.e(TAG, "Resposta do Servidor: " + response.body());
                TodoOBJ notificacao = response.body();
                Toast.makeText(getApplicationContext(), "onResponse:" + notificacao.getMensagem(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<TodoOBJ> call, Throwable t) {
                Log.e(TAG, "Falha do Servidor Post: " + t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(), "onFailure:" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    // fim do conteudo geral

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inicio do programa principal

        Toast.makeText(getApplicationContext(), "Bem-vindo! :D", Toast.LENGTH_LONG).show();

        createNotificationChannel(); // cria um canal para poder mostrar as notificações

        textoNome = findViewById(R.id.textNomeGarcon);
        receberUsidNome();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        textoVerificador = findViewById(R.id.textVerificando);

        // verifica se tem alguém chamando a cada 10 segundos
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //verificarChamado();
                        //verificarChamadoPost();

                        verificarChamadoQuery();

                    }
                });

            }

        }, 1, 4, TimeUnit.SECONDS);



        // fim do programa principal
    }
}

