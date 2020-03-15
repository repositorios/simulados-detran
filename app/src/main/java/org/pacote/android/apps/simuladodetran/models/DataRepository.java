package org.pacote.android.apps.simuladodetran.models;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.pacote.android.apps.simuladodetran.models.dao.EstatisticasDao;
import org.pacote.android.apps.simuladodetran.models.dao.PlacaDao;
import org.pacote.android.apps.simuladodetran.models.entities.Estatisticas;
import org.pacote.android.apps.simuladodetran.models.entities.Placa;

import java.util.List;

public class DataRepository {
    private PlacaDao placaDao;
    private EstatisticasDao estatisticasDao;

    private LiveData<List<Placa>> todasPlacas;
    private LiveData<Estatisticas> estatisticas;


    public DataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        placaDao = db.placaDao();
        estatisticasDao = db.estatisticasDao();
        todasPlacas = placaDao.getTodasPlacas();
        estatisticas = estatisticasDao.getEstatisticas();
    }

    public LiveData<List<Placa>> getTodasPlacas() {
        return todasPlacas;
    }

    public LiveData<Estatisticas> getEstatisticas() {
        return estatisticas;
    }

   public void atualiza(final Estatisticas estatisticas) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                estatisticasDao.atualiza(estatisticas);
            }
        });
    }
}
