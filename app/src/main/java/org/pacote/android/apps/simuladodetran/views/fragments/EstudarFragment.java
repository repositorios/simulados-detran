package org.pacote.android.apps.simuladodetran.views.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.pacote.android.apps.simuladodetran.databinding.FragmentEstudarBinding;
import org.pacote.android.apps.simuladodetran.models.entities.Placa;
import org.pacote.android.apps.simuladodetran.viewmodel.PlacaViewModel;

import java.util.List;


public class EstudarFragment extends Fragment {

    FragmentEstudarBinding binding;

    private PlacaViewModel placaViewModel;
    private List<Placa> todasPlacas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placaViewModel = new ViewModelProvider(this).get(PlacaViewModel.class);
        observeTodasPlacas();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEstudarBinding.inflate(getLayoutInflater());
        binding.proxima.setOnClickListener(onClickProximaPlaca);
        binding.anterior.setOnClickListener(onClickAnteriorPlaca);

        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void observeTodasPlacas() {
        placaViewModel.getTodasPlacas().observe(this, new Observer<List<Placa>>() {
            @Override
            public void onChanged(@Nullable final List<Placa> placas) {
                todasPlacas = placas;
                atualizaPlaca();
            }
        });

    }

    private void atualizaPlaca() {

        byte[] decodedString = Base64.decode(todasPlacas.get(placaViewModel.getPosicaoAtual()).getImagem(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        binding.placa.setImageBitmap(decodedByte);
        binding.codigo.setText(todasPlacas.get(placaViewModel.getPosicaoAtual()).getCodigo());
        binding.nome.setText(todasPlacas.get(placaViewModel.getPosicaoAtual()).getNome());
        binding.descricao.setText(todasPlacas.get(placaViewModel.getPosicaoAtual()).getDescricao());
        binding.descricao.setMovementMethod(new ScrollingMovementMethod());

    }


    private void atualizaPosicaoAtual(boolean reverse) {
        if (!reverse) {
            if (placaViewModel.getPosicaoAtual() < (todasPlacas.size() - 1)) {
                placaViewModel.setPosicaoAtual((placaViewModel.getPosicaoAtual() + 1));
            } else {
                placaViewModel.setPosicaoAtual(0);
            }
        } else {

            if (placaViewModel.getPosicaoAtual() > 0 ) {
                placaViewModel.setPosicaoAtual((placaViewModel.getPosicaoAtual() - 1));
            } else {
                placaViewModel.setPosicaoAtual(todasPlacas.size() - 1);
            }
        }

    }

    private View.OnClickListener onClickProximaPlaca = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            atualizaPosicaoAtual(false);
            atualizaPlaca();
        }
    };

    private View.OnClickListener onClickAnteriorPlaca = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            atualizaPosicaoAtual(true);
            atualizaPlaca();
        }
    };
}
