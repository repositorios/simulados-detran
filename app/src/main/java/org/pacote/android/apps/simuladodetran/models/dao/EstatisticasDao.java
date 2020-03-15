package org.pacote.android.apps.simuladodetran.models.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import org.pacote.android.apps.simuladodetran.models.entities.Estatisticas;

@Dao
public interface EstatisticasDao {

    @Query("SELECT * FROM estatisticas ORDER BY id")
    LiveData<Estatisticas> getEstatisticas();

    @Update
    void atualiza(Estatisticas estatisticas);
}
