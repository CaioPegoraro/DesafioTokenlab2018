package desafio2018.tokenlab.com.desafiotokenlab2018.Model;

/**
 * Created by caio on 27/03/2018.
 */
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import desafio2018.tokenlab.com.desafiotokenlab2018.R;

public class AdapterGameListViewClass extends BaseAdapter {
    private LayoutInflater layoutInflateAdapter;
    private ArrayList<GameClass> listaGamesAdapter;

    //Construtor
    public AdapterGameListViewClass(Context context, ArrayList<GameClass> games)
    {
        //Itens que preencheram o listview
        this.listaGamesAdapter = games;
        //responsavel por pegar o Layout do item.
        layoutInflateAdapter = LayoutInflater.from(context);
    }

    /**
     * Retorna a quantidade de itens na lista
     * @return
     */
    public int getCount()
    {
        return listaGamesAdapter.size();
    }

    /**
     * Retorna o item de acordo com a posicao requisitada
     * @param posicao
     * @return
     */
    public GameClass getItem(int posicao)
    {
        return listaGamesAdapter.get(posicao);
    }

    public long getItemId(int position)
    {
        return position;
    }

    //Retorna o componente view (de cada <game>) com os parametros configurados
    public View getView(int position, View view, ViewGroup parent)
    {
        //Pega o item de acordo com a posção.
        GameClass itemGame = listaGamesAdapter.get(position);

        //infla o layout para podermos preencher os dados
        view = layoutInflateAdapter.inflate(R.layout.activity_game_view, null);

        //Edita os componentes da view com os valores de cada <game>

        //NAME
        ((TextView) view.findViewById(R.id.txtName)).setText(itemGame.getName());

        //URL da img para o NBA17k está offline
        if(itemGame.getId()==13){
            itemGame.setImage("https://www.apkmirror.com/wp-content/uploads/2017/07/5964f77872598-384x384.png");
        }

        //IMAGE
        ImageView imgLogo = (ImageView) view.findViewById(R.id.imgGameLogo);
        Picasso.get().load(itemGame.getImage().toString()).fit().centerCrop().into((ImageView) view.findViewById(R.id.imgGameLogo));

        //RELEASE_DATE
        ((TextView) view.findViewById(R.id.txtReleaseDate)).setText("Lançamento: "+itemGame.getRelease_date());

        //PLATFORMS
        String listaPlataformas = TextUtils.join(", ", itemGame.getPlatforms());
        ((TextView) view.findViewById(R.id.txtPlatforms)).setText(listaPlataformas);

        //((ImageView) view.findViewById(R.id.imageView1 )).setImageBitmap(LoadImageFromWebOperations("https://at-cdn-s01.audiotool.com/2015/05/26/documents/VXL58qZHo7ydkCHTJSmRY2NOZVUgU/0/cover256x256-94ebc094d4ba4ede99de745fd44725bc.jpg"));
        //Picasso.get().load("https://s-media-cache-ak0.pinimg.com/736x/04/29/d1/0429d1fb5705dd064dcdc346818c03ff.jpg").into((ImageView) view.findViewById(R.id.imageView1));

        return view;
    }
}
