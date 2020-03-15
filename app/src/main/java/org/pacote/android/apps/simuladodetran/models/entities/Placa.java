package org.pacote.android.apps.simuladodetran.models.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "placas")
public class Placa {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @NonNull
    @ColumnInfo(name = "imagem")
    private String imagem;

    @NonNull
    @ColumnInfo(name = "codigo")
    private String codigo;

    @NonNull
    @ColumnInfo(name = "nome")
    private String nome;

    @NonNull
    @ColumnInfo(name = "descricao")
    private String descricao;

    public Placa(@NonNull String imagem, @NonNull String codigo, @NonNull String nome, @NonNull String descricao) {
        this.imagem = imagem;
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getImagem() {
        return imagem;
    }

    public void setImagem(@NonNull String imagem) {
        this.imagem = imagem;
    }

    @NonNull
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(@NonNull String codigo) {
        this.codigo = codigo;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    @NonNull
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NonNull String descricao) {
        this.descricao = descricao;
    }
}
