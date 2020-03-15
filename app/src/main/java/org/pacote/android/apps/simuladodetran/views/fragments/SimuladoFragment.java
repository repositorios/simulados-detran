package org.pacote.android.apps.simuladodetran.views.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.pacote.android.apps.simuladodetran.databinding.FragmentSimuladoBinding;
import org.pacote.android.apps.simuladodetran.models.entities.Estatisticas;
import org.pacote.android.apps.simuladodetran.models.entities.Placa;
import org.pacote.android.apps.simuladodetran.utils.NumeroAleatorio;
import org.pacote.android.apps.simuladodetran.viewmodel.EstatisticasViewModel;
import org.pacote.android.apps.simuladodetran.viewmodel.PlacaViewModel;

import java.util.List;


public class SimuladoFragment extends Fragment {

    FragmentSimuladoBinding binding;
    private PlacaViewModel placaViewModel;
    private EstatisticasViewModel estatisticasViewModel;

    private List<Placa> todasPlacas;
    Estatisticas estatisticas;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placaViewModel = new ViewModelProvider(this).get(PlacaViewModel.class);
        estatisticasViewModel = new ViewModelProvider(this).get(EstatisticasViewModel.class);

        observeTodasPlacas();
        observeEstatisticas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSimuladoBinding.inflate(getLayoutInflater());

        binding.responder.setOnClickListener(onClickResponder);
        binding.proxima.setOnClickListener(onClickNovaPergunta);

        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        estatisticasViewModel.atualiza(estatisticas);
    }

    private void observeTodasPlacas() {
        placaViewModel.getTodasPlacas().observe(this, new Observer<List<Placa>>() {
            @Override
            public void onChanged(@Nullable final List<Placa> placas) {
                todasPlacas = placas;
                atualizaPergunta();
            }
        });
    }

    private void observeEstatisticas() {
        estatisticasViewModel.getEstatisticas().observe(this, new Observer<Estatisticas>() {
            @Override
            public void onChanged(@Nullable final Estatisticas dadosDb) {
                estatisticas = dadosDb;
            }
        });
    }


    private void atualizaPergunta() {
        binding.radioGroup.clearCheck();
        binding.proxima.setVisibility(View.INVISIBLE);
        binding.responder.setVisibility(View.VISIBLE);
        binding.titulo.setText("");
        binding.texto.setText("");


        byte[] decodedString = Base64.decode(todasPlacas.get(placaViewModel.getPosicaoAtual()).getImagem(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        binding.simuladoPlaca.setImageBitmap(decodedByte);

        int posicaoCerta = NumeroAleatorio.geraInteiroDentroRange(1, 3);
        int primeraOpcaoErrada = NumeroAleatorio.geraInteiroDentroRange(0, todasPlacas.size() - 1);
        int SegundaOpcaoErrada = NumeroAleatorio.geraInteiroDentroRange(0, todasPlacas.size() - 1);

        placaViewModel.setRespostaCerta(todasPlacas.get(placaViewModel.getPosicaoAtual()).getNome());

        switch (posicaoCerta) {
            case 1:
                binding.respostaA.setText(todasPlacas.get(placaViewModel.getPosicaoAtual()).getNome());
                binding.respostaB.setText(todasPlacas.get(primeraOpcaoErrada).getNome());
                binding.respostaC.setText(todasPlacas.get(SegundaOpcaoErrada).getNome());
                break;
            case 2:
                binding.respostaA.setText(todasPlacas.get(primeraOpcaoErrada).getNome());
                binding.respostaB.setText(todasPlacas.get(placaViewModel.getPosicaoAtual()).getNome());
                binding.respostaC.setText(todasPlacas.get(SegundaOpcaoErrada).getNome());
                break;
            case 3:
                binding.respostaA.setText(todasPlacas.get(SegundaOpcaoErrada).getNome());
                binding.respostaB.setText(todasPlacas.get(primeraOpcaoErrada).getNome());
                binding.respostaC.setText(todasPlacas.get(placaViewModel.getPosicaoAtual()).getNome());
                break;
        }
    }

    private void atualizaPosicaoPergunta() {

        if (placaViewModel.getPosicaoAtual() < (todasPlacas.size() - 1)) {
            placaViewModel.setPosicaoAtual((placaViewModel.getPosicaoAtual() + 1));
        } else {
            placaViewModel.setPosicaoAtual(0);
        }
    }

    private View.OnClickListener onClickResponder = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String respostaEscolhida = "";
            boolean proxima = false;

            if (binding.respostaA.isChecked()) {
                respostaEscolhida = (String) binding.respostaA.getText();
            } else if (binding.respostaB.isChecked()) {
                respostaEscolhida = (String) binding.respostaB.getText();
            } else if (binding.respostaC.isChecked()) {
                respostaEscolhida = (String) binding.respostaC.getText();
            }

            if (respostaEscolhida.equals("")) {
                Toast.makeText(getContext(), "Escolha uma alternativa", Toast.LENGTH_SHORT).show();

            } else if (respostaEscolhida.equals(placaViewModel.getRespostaCerta())) {
                binding.titulo.setText("✔️ Resposta Correta");
                binding.texto.setText("");
                proxima = true;
                estatisticas.setAcertos(estatisticas.getAcertos()+1);

            } else {
                binding.titulo.setText("❌ Resposta Errada");
                binding.texto.setText("O correto seria: " + placaViewModel.getRespostaCerta());
                proxima = true;
                estatisticas.setErros(estatisticas.getErros()+1);
            }

            if (proxima) {
                binding.responder.setVisibility(View.INVISIBLE);
                binding.proxima.setVisibility(View.VISIBLE);
                binding.radioGroup.clearCheck();
            }
        }
    };


    private View.OnClickListener onClickNovaPergunta = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            atualizaPosicaoPergunta();
            atualizaPergunta();
        }
    };
}
