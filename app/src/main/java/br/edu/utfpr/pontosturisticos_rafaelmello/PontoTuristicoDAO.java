package br.edu.utfpr.pontosturisticos_rafaelmello;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PontoTuristicoDAO {
    private Conexao conexao;
    private SQLiteDatabase database;

    public PontoTuristicoDAO(Context context){
        conexao = new Conexao(context);
        database = conexao.getWritableDatabase();
    }

    public long inserir(PontoTuristico pontoTuristico){
        ContentValues values = new ContentValues();
        values.put("titulo",pontoTuristico.getTitulo());
        values.put("latitude",pontoTuristico.getLatitude());
        values.put("longitude",pontoTuristico.getLongitude());
        return database.insert("pontoturistico",null,values);
    }

    public List<PontoTuristico> ObterTodos(){
        List<PontoTuristico> pontoTuristicos = new ArrayList<>();
        Cursor cursor = database.query("pontoturistico", new String[]{"id", "titulo", "latitude", "longitude"},null,null,null,null,null);
        while (cursor.moveToNext()){
            PontoTuristico pt = new PontoTuristico();
            pt.setId(cursor.getInt(0));
            pt.setTitulo(cursor.getString(1));
            pt.setLatitude(cursor.getString(2));
            pt.setLongitude(cursor.getString(3));
            pontoTuristicos.add(pt);
        }
        return pontoTuristicos;
    }

    public void excluir(PontoTuristico p){
        database.delete("pontoturistico", "id = ?", new String[]{p.getId().toString()});
    }

    public void atualizar(PontoTuristico ponto){
        ContentValues values = new ContentValues();
        values.put("titulo",ponto.getTitulo());
        values.put("latitude",ponto.getLatitude());
        values.put("longitude",ponto.getLongitude());
        database.update("pontoturistico", values,"id = ?", new String[]{ponto.getId().toString()});
    }
}
