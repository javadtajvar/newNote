package com.example.developer.newnote.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.developer.newnote.adapter.MyAdapter;
import com.example.developer.newnote.Note;
import com.example.developer.newnote.NoteApp;
import com.example.developer.newnote.R;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.developer.newnote.Helper.*;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Context context = this;
    MyAdapter myAdapter;
    ListView listView;
    NoteApp noteApp;
    FloatingActionButton fab_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInto();



        myAdapter = new MyAdapter(context, noteApp.getNotes());
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra(SHOW_KEY, i);
                intent.putExtra(IS_SHOW_KEY, true);
                finish();
                startActivity(intent);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int item, long l) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle(R.string.delete_note);
                alertDialog.setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int id = noteApp.getNotes().get(item).getId();
                        //noteApp.delete(noteApp.getNotes().get(item));
                        noteApp.getNotes().remove(item);
                        noteApp.delete(id);
                        myAdapter.notifyDataSetChanged();
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
                return true;
            }
        });
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SecondActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(R.string.exit);
        dialog.setMessage(R.string.are_you_sure);
        dialog.setPositiveButton(getString(R.string.yes_sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                dialogInterface.dismiss();
            }
        });
        dialog.setNegativeButton(getString(R.string.not_sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    private Animation anim_fade_in(){
        Animation fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(1500);
        return fadeIn;
    }

    private void setInto() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        noteApp = (NoteApp) getApplication();
        listView = (ListView) findViewById(R.id.list);
        fab_add =findViewById(R.id.fab_add);
        fab_add.setAnimation(anim_fade_in());

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
