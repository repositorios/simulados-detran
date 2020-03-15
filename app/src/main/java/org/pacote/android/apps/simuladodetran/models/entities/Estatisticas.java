package org.pacote.android.apps.simuladodetran.models.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "estatisticas")
public class Estatisticas {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @NonNull
    @ColumnInfo(name = "acertos")
    private Integer acertos;

    @NonNull
    @ColumnInfo(name = "erros")
    private Integer erros;

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public Integer getAcertos() {
        return acertos;
    }

    public void setAcertos(@NonNull Integer acertos) {
        this.acertos = acertos;
    }

    @NonNull
    public Integer getErros() {
        return erros;
    }

    public void setErros(@NonNull Integer erros) {
        this.erros = erros;
    }
}
