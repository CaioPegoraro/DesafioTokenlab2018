package desafio2018.tokenlab.com.desafiotokenlab2018;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import desafio2018.tokenlab.com.desafiotokenlab2018.Model.AdapterGameListViewClass;
import desafio2018.tokenlab.com.desafiotokenlab2018.Model.GameClass;

public class ListaJogosActivity extends AppCompatActivity {

    //Elementos da interface
    private AdapterGameListViewClass adapterGameList;
    private ListView listviewGameList;

    //Variaveis de leitura JSON
    private final String URL_JSON = "https://dl.dropboxusercontent.com/s/1b7jlwii7jfvuh0/games.txt";
    ArrayList<GameClass> listaGames;

    //Caixa de alerta para o trailer
    private AlertDialog.Builder dialogBuilderTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remover barra de notificação quando o aplicativo for iniciado
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_lista_jogos);

        //Configuracao da listView para exibicao dos <games>
        listviewGameList = (ListView) findViewById(R.id.listGameListView);

        listviewGameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //onItemClick(AdapterView<?> parent, View view, int position, long id)
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final GameClass gameClicado = listaGames.get(i);

                dialogBuilderTrailer = new AlertDialog.Builder(ListaJogosActivity.this);
                dialogBuilderTrailer.setTitle("Trailer");
                dialogBuilderTrailer.setMessage("Abrir trailer do " + gameClicado.getName() + " no Youtube?");

                dialogBuilderTrailer.setNegativeButton("Não",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Clicou no NAO
                                Toast.makeText(getApplicationContext(),"okay!",Toast.LENGTH_SHORT).show();
                            }
                        });
                dialogBuilderTrailer.setPositiveButton("SIM",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Clicou no SIM
                                Toast.makeText(getApplicationContext(),"Abrindo trailer!",Toast.LENGTH_SHORT).show();

                                Intent webIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(gameClicado.getTrailer()));
                                getApplicationContext().startActivity(webIntent);
                            }
                        });

                dialogBuilderTrailer.create();
                dialogBuilderTrailer.show();
            }
        });


        //Aloca a lista de itens (games)
        listaGames = new ArrayList<GameClass>();

        //configuracao do adaptador linkado no listview
        adapterGameList = new AdapterGameListViewClass(this,listaGames);

        //Tarefa assincrona: trata a requicisao dos dados (leitura JSON) e armazena os dados em uma lista de objetos do tipo Game
        new LeituraDadosJSON(ListaJogosActivity.this).execute(URL_JSON);
    }

    //<Parametros,Progress,Retorno>
    private class LeituraDadosJSON extends AsyncTask<String , Context , Void> {

        private ProgressDialog progressDialog ;
        private Context targetCtx ;

        public LeituraDadosJSON ( Context context ) {
            this.targetCtx = context ;
            //this.needToShow = true;
            progressDialog = new ProgressDialog ( targetCtx ) ;
            progressDialog.setCancelable ( false ) ;
            progressDialog.setMessage ( "Carregando lista de jogos" ) ;
            progressDialog.setTitle ( "Aguarde" ) ;
            progressDialog.setIndeterminate ( true ) ;
        }

        @ Override
        protected void onPreExecute ( ) {
            progressDialog.show ( ) ;
        }

        protected Void doInBackground(String... urls) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try{
                URL url = new URL(urls[0]);//primeiro (e unico) parametro eh a url do JSON
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject objetoJson = new JSONObject(finalJson);
                JSONArray listaJson = objetoJson.getJSONArray("games");

                Gson gson = new Gson();

                for(int i=0; i<listaJson.length(); i++) {
                    JSONObject objetoJsonTMP = listaJson.getJSONObject(i);
                    GameClass game1 = gson.fromJson(objetoJsonTMP.toString(), GameClass.class);
                    //adiciona o objeto <game> lido na lista de objetos
                    listaGames.add(game1);
                }
                //LISTA JA ALIMENTADA COM OBJETOS JSON!!
                //Log.d("loadingJSONcomplete", "JSON carregado com sucesso");

            } //tratamento de excecoes

            catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("MalformedURLException", "erro2");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("IOException", "erro3");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("JSONException", "erro4");
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.currentThread();
                Thread.sleep(100 * listaGames.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        @ Override
        protected void onPostExecute ( Void result ) {
            listviewGameList.setAdapter(adapterGameList);
            if(progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss ( ) ;
            }
        }
    }//FIM TAREFA ASSINCRONA
}
