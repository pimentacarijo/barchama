package br.com.pimentacarijo.barchama6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class LoginActivity extends AppCompatActivity {

    // inicio do conteudo geral

    Button btnEntrar;
    EditText editLogin;
    EditText editSenha;


    String urlWebServices = "https://teste.pimentacarijo.com.br/app/login.php";
    String CHAVEDB = "chave1"; // aqui controla o acesso do APP ao banco de dados

    StringRequest stringRequest;
    RequestQueue requestQueue;

    // fim do conteudo geral

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // inicio do programa principal

        Toast.makeText(getApplicationContext(), "BarChama", Toast.LENGTH_LONG).show();

        btnEntrar = findViewById(R.id.btnEntrar);
        editLogin = findViewById(R.id.editLogin);
        editSenha = findViewById(R.id.editSenha);

        requestQueue = Volley.newRequestQueue(this);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean validado = true;

                if (editSenha.getText().length()==0) {
                    editSenha.setError("Campo senha obrigatório!");
                    editSenha.requestFocus();
                    validado = false;
                }

                if (editLogin.getText().length()==0) {
                    editLogin.setError("Campo login obrigatório!");
                    editLogin.requestFocus();
                    validado = false;
                }

                if (validado) { // verifica se o usuário digitou ao menos 1 caractere em cada um dois campos do login

                    Toast.makeText(getApplicationContext(), "Aguarde...", Toast.LENGTH_SHORT).show();

                    validarLogin();

                }

            }
        });

        editLogin.requestFocus(); // para que o cursor já esteja no campo de login quando o aplicativo é inicializado

        // fim do programa principal
    }

    private void validarLogin() {

        stringRequest = new StringRequest(Request.Method.POST, urlWebServices,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("LogLogin", response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            boolean isError = jsonObject.getBoolean("error");

                            if (isError) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("mensagem"), Toast.LENGTH_LONG).show();
                            } else {

                                int perfil = jsonObject.getInt("perfil");

                                if ( perfil==1 ) { // é garçon

                                    Intent novaTela = new Intent(LoginActivity.this, MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("usid", jsonObject.getString("usid"));
                                    bundle.putString("nome", jsonObject.getString("nome"));
                                    bundle.putString("chave", CHAVEDB);
                                    novaTela.putExtras(bundle);
                                    startActivity(novaTela);
                                    finish();

                                } else if ( perfil>1 ) { // é barman ou de outro setor, cozinha, recepção, caixa

                                    Intent novaTela = new Intent(LoginActivity.this, BarActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("usid", jsonObject.getString("usid"));
                                    bundle.putString("nome", jsonObject.getString("nome"));
                                    bundle.putString("chave", CHAVEDB);
                                    novaTela.putExtras(bundle);
                                    startActivity(novaTela);
                                    finish();

                                }

                            }

                        }catch (Exception e) {
                            Log.v("LogLogin", e.getMessage());
                            Toast.makeText(getApplicationContext(), "Json: "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LogLogin", error.getMessage());
                        Toast.makeText(getApplicationContext(), "Acesso: "+error.getMessage(), Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), "Sem conexão com a internet!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("chave", CHAVEDB);
                params.put("login", editLogin.getText().toString());
                params.put("senha", editSenha.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
