package br.com.nucleosti.bollyfilmes;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private int posicaoItem = ListView.INVALID_POSITION;
    private static final String KEY_POSICAO = "SELECIONADO";
    private ListView list;
    private boolean useFilmeDestaque = false;
    private FilmesAdapter adapter;

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //seta uma flag para avisar o fragment que o menu vai aparecer
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //recupera a lista criada no layout
        list = (ListView) view.findViewById(R.id.list_filmes);

        //cria um arraylist
        final ArrayList<ItemFilme> arrayList = new ArrayList<>();
        arrayList.add(new ItemFilme("Homem Aranha", "Filme de herói picado por uma aranha", "10/07/2020", 4));
        arrayList.add(new ItemFilme("Homem Cobra", "Filme de herói picado por uma cobra", "12/05/2020", 2));
        arrayList.add(new ItemFilme("Homem Javali", "Filme de herói picado por um javali", "30/08/2020", 3));
        arrayList.add(new ItemFilme("Homem Pássaro", "Filme de herói picado por um pássaro", "23/05/2020", 5));
        arrayList.add(new ItemFilme("Homem Cachorro", "Filme de herói picado por um cachorro", "10/07/2020", 3.5f));
        arrayList.add(new ItemFilme("Homem Gato", "Filme de herói picado por um gato", "11/04/2019", 2.5f));

        //cria o adapter
        adapter = new FilmesAdapter(getContext(), arrayList);
        adapter.setUseFilmeDestaque(useFilmeDestaque);

        list.setAdapter(adapter); //seta o adapter na list

        //click no item da list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemFilme itemFilme = arrayList.get(position);
                Callback callback = (Callback) getActivity();
                callback.onItemSelected(itemFilme);
                posicaoItem = position;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_POSICAO)) {
            posicaoItem = savedInstanceState.getInt(KEY_POSICAO);
        }
        return view;
    }

    //quando salva o estado do fragment ou da activity
    @Override
    public void onSaveInstanceState(Bundle outState) {

       if (posicaoItem != ListView.INVALID_POSITION) {
           outState.putInt(KEY_POSICAO, posicaoItem);
       }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (posicaoItem != ListView.INVALID_POSITION && list != null) {
            list.smoothScrollToPosition(posicaoItem);
        }
    }

    //criando o menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    //criando item de menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_atualizar:
                Toast.makeText(getContext(), "Atualizando os filmes..", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUseFilmeDestaque(boolean useFilmeDestaque) {
        this.useFilmeDestaque = useFilmeDestaque;

        if (adapter != null) {
            adapter.setUseFilmeDestaque(useFilmeDestaque);
        }
    }

    public interface Callback {
       void onItemSelected(ItemFilme itemFilme);
   }
}