package org.pacote.android.apps.simuladodetran.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

import org.pacote.android.apps.simuladodetran.R;
import org.pacote.android.apps.simuladodetran.databinding.ActivityMainBinding;
import org.pacote.android.apps.simuladodetran.views.fragments.EstatisticasFragment;
import org.pacote.android.apps.simuladodetran.views.fragments.EstudarFragment;
import org.pacote.android.apps.simuladodetran.views.fragments.SimuladoFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.bottomNavigation.setItemIconTintList(null); // Força os icones do menu ficarem coloridos
        binding.bottomNavigation.setOnItemSelectedListener(navListener);


    }

    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected = null;

            int itemId = item.getItemId();
            if (itemId == R.id.estudar) {
                selected = new EstudarFragment();
            } else if (itemId == R.id.simulado) {
                selected = new SimuladoFragment();
            } else if (itemId == R.id.estatisticas) {
                selected = new EstatisticasFragment();
            } else {
                Toast.makeText(MainActivity.this, "Opção inválida", Toast.LENGTH_LONG).show();
            }

            binding.fragmentContainer.removeAllViews();
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentContainer.getId(), selected).commit();

            return true;
        }
    };
}
