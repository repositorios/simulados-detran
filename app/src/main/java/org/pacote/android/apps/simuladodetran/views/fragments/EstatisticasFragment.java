package org.pacote.android.apps.simuladodetran.views.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.pacote.android.apps.simuladodetran.databinding.FragmentEstatisticasBinding;
import org.pacote.android.apps.simuladodetran.models.entities.Estatisticas;
import org.pacote.android.apps.simuladodetran.viewmodel.EstatisticasViewModel;

import java.util.ArrayList;
import java.util.List;


public class EstatisticasFragment extends Fragment {

    FragmentEstatisticasBinding binding;
    EstatisticasViewModel estatisticasViewModel;
    Estatisticas estatisticasDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        estatisticasViewModel = new ViewModelProvider(this).get(EstatisticasViewModel.class);
        observerEstatisticas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEstatisticasBinding.inflate(getLayoutInflater());
        binding.zerar.setOnClickListener(onClickZerarEstatisticas);


        return binding.getRoot();
    }

    private void observerEstatisticas() {
        estatisticasViewModel.getEstatisticas().observe(this, new Observer<Estatisticas>() {
            @Override
            public void onChanged(@Nullable final Estatisticas dadosDb) {
                estatisticasDatabase = dadosDb;
                geraGrafico();

            }
        });
    }

    public void geraGrafico() {

        ArrayList<PieEntry> estatisticas = new ArrayList<>();
        estatisticas.add(new PieEntry(estatisticasDatabase.getAcertos(), "Acertos"));
        estatisticas.add(new PieEntry(estatisticasDatabase.getErros(), "Erros"));

        PieDataSet pieDataSet = new PieDataSet(estatisticas, "Estatísticas");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value));
            }
        });


        PieData pieData = new PieData(pieDataSet);

        binding.pieChart.setNoDataText("Sem dados");
        binding.pieChart.setData(pieData);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.setCenterText("Estatísticas");
        binding.pieChart.animate();
        binding.pieChart.getLegend().setEnabled(false);
        binding.pieChart.invalidate();



    }


    private View.OnClickListener onClickZerarEstatisticas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            estatisticasDatabase.setAcertos(0);
            estatisticasDatabase.setErros(0);
            estatisticasViewModel.atualiza(estatisticasDatabase);
            Toast.makeText(getContext(), "Estatísticas zeradas. Comece uma nova rodada de simulados.", Toast.LENGTH_LONG).show();
        }
    };
}
