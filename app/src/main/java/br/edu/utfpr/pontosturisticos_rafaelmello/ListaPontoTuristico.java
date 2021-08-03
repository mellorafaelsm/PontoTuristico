package br.edu.utfpr.pontosturisticos_rafaelmello;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class ListaPontoTuristico extends AppCompatActivity {


    private ListView listView;
    private PontoTuristicoDAO dao;
    private List<PontoTuristico> list;
    private List<PontoTuristico> listfiltrado = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ponto_turistico);

        listView = findViewById(R.id.lista_pontos);
        dao = new PontoTuristicoDAO(this);
        list = dao.ObterTodos();
        listfiltrado.addAll(list);

//        ArrayAdapter<PontoTuristico> adaptador = new ArrayAdapter<PontoTuristico>(this, android.R.layout.simple_list_item_1, listfiltrado);
        PontoAdapter adaptador = new PontoAdapter(this,listfiltrado);
        listView.setAdapter(adaptador);

        registerForContextMenu(listView);
}

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procuraPonto(s);
                return false;
            }
        });

        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void procuraPonto(String titulo){
        listfiltrado.clear();
        for (PontoTuristico p : list){
            if (p.getTitulo().toLowerCase().contains(titulo.toLowerCase())){
                listfiltrado.add(p);
            }
        }
        listView.invalidateViews();
    }

    public void excluir(MenuItem item){
        //pega posicao
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final PontoTuristico pontoExcluir = listfiltrado.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Atenção").setMessage("Realmente deseja excluir o registro?").setNegativeButton("NÃO", null).setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listfiltrado.remove(pontoExcluir);
                list.remove(pontoExcluir);
                dao.excluir(pontoExcluir);
                listView.invalidateViews();
            }
        }).create();
        dialog.show();
    }

    public void cadastrar(MenuItem item){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final PontoTuristico pontoAtualizar = listfiltrado.get(menuInfo.position);

        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("pontoturistico", pontoAtualizar);
        startActivity(it);
    }

    @Override
    public void onResume(){
        super.onResume();
        list = dao.ObterTodos();
        listfiltrado.clear();
        listfiltrado.addAll(list);
        listView.invalidateViews();
    }
}