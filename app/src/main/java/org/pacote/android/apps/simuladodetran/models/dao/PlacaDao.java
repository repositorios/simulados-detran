package org.pacote.android.apps.simuladodetran.models.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import org.pacote.android.apps.simuladodetran.models.entities.Placa;

import java.util.List;

@Dao
public interface PlacaDao {

    @Query("SELECT * FROM placas ORDER BY RANDOM()")
    LiveData<List<Placa>> getTodasPlacas();
}
