package br.com.pimentacarijo.barchama6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BarActivity extends AppCompatActivity {

    // inicio do conteudo geral

    Button btnJonathan;
    Button btnKleber;
    Button btnLuciano;
    Button btnJessica;
    Button btnLoren;
    Button btnMayco;

    String USID;
    String NOME;
    String CHAVEDB;
    TextView textoNome; // variável responsável por armazenar e mostrar o nome do usuário na tela de chamar garçom

     String urlWebServices = "https://teste.pimentacarijo.com.br/app/receben.php";

    StringRequest stringRequest;
    RequestQueue requestQueue;

    private void receberUsidNome() { // Recebe os dados da tela de login

        Intent telaLogin = getIntent();
        Bundle bundle = telaLogin.getExtras();
        USID = bundle.getString("usid"); // ID do usuário que está logado no App
        NOME = bundle.getString("nome");
        CHAVEDB = bundle.getString("chave");
        //Toast.makeText(getApplicationContext(), "Nome= "+NOME+" "+USID, Toast.LENGTH_LONG).show();
        StringBuilder str = new StringBuilder();
        str.append("Olá ");
        str.append(NOME);
        str.append("!");
        textoNome.setText( str );
    }

    public void testaBotao(View view ) { // apenas para testar os botões na durante o desenvolvimento do App
        Toast.makeText(getApplicationContext(), "Testando o botão! :) ", Toast.LENGTH_SHORT).show();
    }

    public void registraNotificacao( String garcomId, String nomeGarcom ) {
        stringRequest = new StringRequest(Request.Method.POST, urlWebServices,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("LogLogin: ", response);
                        Toast.makeText(getApplicationContext(), nomeGarcom, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LogLogin", error.getMessage());
                        Toast.makeText(getApplicationContext(), "Acesso: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), "Sem conexão com a internet!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("chave", CHAVEDB);
                params.put("usid", USID); // quem chamou - bar
                params.put("garcomid", garcomId); // quem está sendo chamado - garçom
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    // fim do conteudo geral

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        // inicio do programa principal

        Toast.makeText(getApplicationContext(), "Bem-vindo! :)", Toast.LENGTH_LONG).show();

        textoNome = findViewById(R.id.textNomeBarman); // para mostrar nesta tela, o nome do usuário logado
        receberUsidNome(); // após logar, pega os dados do usuário para usar aqui nessa tela

        requestQueue = Volley.newRequestQueue(this); // para acessar a URL do banco de dados

        btnJonathan = findViewById(R.id.btnJonathan);
        btnKleber = findViewById(R.id.btnKleber);
        btnLuciano = findViewById(R.id.btnLuciano);
        btnJessica = findViewById(R.id.btnJessica);
        btnLoren = findViewById(R.id.btnLoren);
        btnMayco = findViewById(R.id.btnMayco);
        btnJonathan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Jonathan chamado!", Toast.LENGTH_SHORT).show();
                registraNotificacao("7", "Jonathan");
            }
        });
        btnKleber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Kléber chamado!", Toast.LENGTH_SHORT).show();
                registraNotificacao("6", "Kléber");
            }
        });
        btnLuciano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Luciano chamado!", Toast.LENGTH_SHORT).show();
                registraNotificacao("3", "Luciano");
            }
        });
        btnJessica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Jéssica chamada!", Toast.LENGTH_SHORT).show();
                registraNotificacao("4", "Jéssica");
            }
        });
        btnLoren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Loren chamada!", Toast.LENGTH_SHORT).show();
                registraNotificacao("5", "Loren");
            }
        });
        btnMayco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Mayco chamado!", Toast.LENGTH_SHORT).show();
                registraNotificacao("2", "Mayco");
            }
        });

        // fim do programa principal
    }
}

