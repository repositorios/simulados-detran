package org.pacote.android.apps.simuladodetran.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.pacote.android.apps.simuladodetran.models.DataRepository;
import org.pacote.android.apps.simuladodetran.models.entities.Placa;

import java.util.List;

public class PlacaViewModel extends AndroidViewModel {

    private DataRepository dataRepository;
    private LiveData<List<Placa>> todasPlacas;
    private int posicaoAtual = 0;
    private String respostaCerta = null;

    public PlacaViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        todasPlacas = dataRepository.getTodasPlacas();
    }

    public LiveData<List<Placa>> getTodasPlacas() {
        return todasPlacas;
    }

    public int getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(@NonNull int posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public String getRespostaCerta() {
        return respostaCerta;
    }

    public void setRespostaCerta(@NonNull String respostaCerta) {
        this.respostaCerta = respostaCerta;
    }
}