package br.com.nucleosti.bollyfilmes;

       import androidx.appcompat.app.AppCompatActivity;
       import androidx.fragment.app.FragmentManager;
       import androidx.fragment.app.FragmentTransaction;

       import android.content.Intent;
       import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback {

    public static final String KEY_FILME = "FILME";

    private boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_filme_detalhe) != null) {
            if (savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_filme_detalhe, new FilmeDetalheFragment())
                        .commit();
            }
            isTablet = true;
        } else {
            isTablet = false;
        }
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        assert mainFragment != null;
        mainFragment.setUseFilmeDestaque(!isTablet);
    }

    @Override
    public void onItemSelected(ItemFilme itemFilme) {
        if (isTablet){
            //para tablet troca o fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            FilmeDetalheFragment detalheFragment = new FilmeDetalheFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(MainActivity.KEY_FILME, itemFilme);
            detalheFragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.fragment_filme_detalhe, detalheFragment);
            fragmentTransaction.commit();
        } else {
            //para celular abre outra activity
            Intent intent = new Intent(this, FilmeDetalheActivity.class);
            intent.putExtra(MainActivity.KEY_FILME, itemFilme);
            startActivity(intent);
        }
    }
}