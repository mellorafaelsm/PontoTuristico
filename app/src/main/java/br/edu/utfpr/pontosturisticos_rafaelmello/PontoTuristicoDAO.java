package br.edu.utfpr.pontosturisticos_rafaelmello;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PontoTuristicoDAO {
    private final SQLiteDatabase database;

    public PontoTuristicoDAO(Context context) {
        database =  new Conexao(context).getWritableDatabase();
    }

    private ContentValues getContentValues(PontoTuristico pontoTuristico) {
        ContentValues values = new ContentValues();
        values.put("titulo", pontoTuristico.getTitulo());
        values.put("latitude", pontoTuristico.getLatitude());
        values.put("longitude", pontoTuristico.getLongitude());
        values.put("foto", pontoTuristico.getFoto());
        return values;
    }

    public long inserir(PontoTuristico ponto) {
        return database.insert("pontoturistico", null, getContentValues(ponto));
    }

    public List<PontoTuristico> ObterTodos() {
        List<PontoTuristico> pontoTuristicos = new ArrayList<>();
        Cursor cursor = database.query("pontoturistico", new String[]{"id", "titulo", "latitude", "longitude", "foto"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            PontoTuristico pt = new PontoTuristico();
            pt.setId(cursor.getInt(0));
            pt.setTitulo(cursor.getString(1));
            pt.setLatitude(cursor.getString(2));
            pt.setLongitude(cursor.getString(3));
            pt.setFoto(cursor.getBlob(4));
            pontoTuristicos.add(pt);
        }
        return pontoTuristicos;
    }

    public void excluir(PontoTuristico p) {
        database.delete("pontoturistico", "id = ?", new String[]{p.getId().toString()});
    }

    public void atualizar(PontoTuristico ponto) {
        database.update("pontoturistico", getContentValues(ponto), "id = ?", new String[]{ponto.getId().toString()});
    }
}
