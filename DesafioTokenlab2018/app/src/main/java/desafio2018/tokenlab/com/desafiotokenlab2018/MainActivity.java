package desafio2018.tokenlab.com.desafiotokenlab2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton btnAbrirJogos;

    //Variaveis de leitura JSON
    private final String URL_JSON = "https://dl.dropboxusercontent.com/s/1b7jlwii7jfvuh0/games.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remover barra de notificação quando o aplicativo for iniciado
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        //Configura o botao para abrir a view de exibicao dos jogos com evento de "clique"
        btnAbrirJogos = (ImageButton) findViewById(R.id.btnAbrirJogosID);
        btnAbrirJogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Origem -> destino (tela de listagem dos jogos)
                startActivity(new Intent(MainActivity.this, ListaJogosActivity.class));
            }
        });
    }
}
