package org.pacote.android.apps.simuladodetran.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.pacote.android.apps.simuladodetran.models.DataRepository;
import org.pacote.android.apps.simuladodetran.models.entities.Estatisticas;

public class EstatisticasViewModel extends AndroidViewModel {
    private DataRepository dataRepository;
    private LiveData<Estatisticas> estatisticas;

    public EstatisticasViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        estatisticas = dataRepository.getEstatisticas();
    }

    public void atualiza(Estatisticas estatisticas) {
        dataRepository.atualiza(estatisticas);
    }

    public LiveData<Estatisticas> getEstatisticas() {
        return estatisticas;
    }
}
