package br.com.nucleosti.bollyfilmes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class FilmeDetalheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_detalhe);

        //pego o itemFilme que veio do fragement
        Intent intent = getIntent();
        ItemFilme itemFilme = (ItemFilme) intent.getSerializableExtra(MainActivity.KEY_FILME);

        //usa o FragmentManager e inicia a transação
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //passa para o fragment de detalhe
        FilmeDetalheFragment fragment = new FilmeDetalheFragment(); //cria o fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.KEY_FILME, itemFilme); //seta
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_filme_detalhe, fragment);

        fragmentTransaction.commit();
    }
}