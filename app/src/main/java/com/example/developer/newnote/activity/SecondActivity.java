package com.example.developer.newnote.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import static com.example.developer.newnote.Helper.*;
import android.widget.EditText;
import android.widget.Toast;

import com.example.developer.newnote.Note;
import com.example.developer.newnote.NoteApp;
import com.example.developer.newnote.R;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    Context context =this;
    Button btnBack, btnSave;
    EditText edtTitle, edtDesc;
    NoteApp noteApp;
    boolean isShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setInto();

        isShow = getIntent().getBooleanExtra(IS_SHOW_KEY,false);
        if (isShow){
        int id= getIntent().getIntExtra(SHOW_KEY,0);
        edtTitle.setText(noteApp.getNotes().get(id).getTitle());
        edtDesc.setText(noteApp.getNotes().get(id).getDesc());
        edtTitle.requestFocus();
            btnSave.setText(R.string.change_save);
        }

    }



    private void setInto() {
        isShow =false;
        noteApp = (NoteApp) getApplication();
        btnBack = (Button) findViewById(R.id.btn_back);
        btnSave = (Button) findViewById(R.id.btn_save);
        edtTitle = (EditText) findViewById(R.id.edt_title);
        edtDesc = (EditText) findViewById(R.id.edt_desc);
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                AlertDialog.Builder builder =new AlertDialog.Builder(context);
                builder.setTitle("برای برگشت مطمئنی؟");
                builder.setPositiveButton("آره", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                         startActivity(new Intent(context,MainActivity.class));
                        finish();
                         dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                     dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.btn_save:
                saveNote();

                break;
        }
    }

    private void saveNote() {
        String title = edtTitle.getText().toString();
        String desc = edtDesc.getText().toString();
        int id = getIntent().getIntExtra(SHOW_KEY,-1);
        boolean isShow = getIntent().getBooleanExtra(IS_SHOW_KEY,false);

        if (title.equals("")){
            Toast.makeText(context, "عنوان را وارد کنید", Toast.LENGTH_SHORT).show();
            return;
        }
        if (desc.equals("")){
            Toast.makeText(context, "یادداشت خود را وارد کنید", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isShow == false) {
            noteApp.getNotes().add(new Note(title, desc));
            Note note = new Note(edtTitle.getText().toString(), edtDesc.getText().toString());
            noteApp.create(note);
            finish();
        }else {
           int i= noteApp.getNotes().get(id).getId();
            noteApp.getNotes().set(id ,new Note(title,desc));
            noteApp.update(new Note(title,desc, i));
            isShow =false;
            finish();
        }
            Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
        finish();
    }
}