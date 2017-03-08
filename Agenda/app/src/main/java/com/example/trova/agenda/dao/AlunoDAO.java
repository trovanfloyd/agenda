package com.example.trova.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.trova.agenda.modelo.Aluno;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trova on 1/11/2017.
 */

public class AlunoDAO extends SQLiteOpenHelper {

    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL, caminhoFoto TEXT);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
       /* String sql = "DROP TABLE IF EXISTS Alunos";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);*/
        String sql = "";
        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT";
        }
    }

    public void insere(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getContentValuesAluno(aluno);

        db.insert("Alunos", null, dados);
    }

    @NonNull
    private ContentValues getContentValuesAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());

        return dados;
    }

    public List<Aluno> buscarAlunos() {
        String sql = "SELECT * FROM Alunos";
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(sql, null);
        List<Aluno> alunos = new ArrayList<Aluno>();
        while(c.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);
        }

        c.close();

        return alunos;
    }

    public void delete(Aluno aluno) {

        SQLiteDatabase db = getWritableDatabase();

        String[] params = {aluno.getId().toString()};
        db.delete("Alunos", "id = ?", params);
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getContentValuesAluno(aluno);


        String[] params = {aluno.getId().toString()};
        db.update("Alunos", dados, "id = ?", params);
    }
}
