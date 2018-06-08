package com.example.developer.newnote.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import static com.example.developer.newnote.Helper.*;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.developer.newnote.Note;
import com.example.developer.newnote.NoteApp;
import com.example.developer.newnote.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    Context context =this;
    ImageButton btnBack, btnSave;
    AppCompatEditText edtTitle, edtDesc;
    NoteApp noteApp;
    TextInputLayout inputTitle;
    boolean isShow = false;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setInto();

        setActionEditText();

        isShow = getIntent().getBooleanExtra(IS_SHOW_KEY,false);
        if (isShow){
        int id= getIntent().getIntExtra(SHOW_KEY,0);
        edtTitle.setText(noteApp.getNotes().get(id).getTitle());
        edtDesc.setText(noteApp.getNotes().get(id).getDesc());

        edtTitle.requestFocus();
           // btnSave.setText(R.string.change_save);
        }

    }


    private void setActionEditText(){
        edtTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (edtTitle.getText().toString().isEmpty()) {
                    inputTitle.setErrorEnabled(true);
                    inputTitle.setError(getString(R.string.txt_error_edtTitle));
                }else{
                    inputTitle.setErrorEnabled(false);
                }
            }
        });
        edtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtTitle.getText().toString().isEmpty()) {
                    inputTitle.setErrorEnabled(true);
                    inputTitle.setError(getString(R.string.txt_error_edtTitle));
                }else{
                    inputTitle.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputTitle.setCounterEnabled(true);
        inputTitle.setCounterMaxLength(40);


    }

    private void setInto() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_second);
        toolbar.setTitle(R.string.new_note);
        setSupportActionBar(toolbar);
        inputTitle = (TextInputLayout) findViewById(R.id.titleInputLayout);
        isShow =false;
        fab = (FloatingActionButton) findViewById(R.id.fab_add_alertd);
        fab.setAnimation(anim_fade_in());
        noteApp = (NoteApp) getApplication();
        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnSave = (ImageButton) findViewById(R.id.btn_save);
        edtTitle = (AppCompatEditText) findViewById(R.id.edt_title);
        edtDesc = (AppCompatEditText) findViewById(R.id.edt_desc);
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }
    private Animation anim_fade_in(){
        Animation fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(1500);
        return fadeIn;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                AlertDialog.Builder builder =new AlertDialog.Builder(context);
                builder.setTitle(R.string.sure_to_back);
                builder.setPositiveButton(R.string.txt_positive,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                         startActivity(new Intent(context,MainActivity.class));
                          finish();
                         dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton(R.string.txt_negative,
                        new DialogInterface.OnClickListener() {
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

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd   HH:mm");
        String date = df.format(c.getTime());
        boolean isShow = getIntent().getBooleanExtra(IS_SHOW_KEY,false);

        if (title.equals("")){
            Toast.makeText(context, R.string.toast_title_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (desc.equals("")){
            Toast.makeText(context, R.string.toast_desc_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        if (isShow == false) {
            noteApp.getNotes().add(new Note(title, desc,date));
            Note note = new Note(edtTitle.getText().toString(), edtDesc.getText().toString(),date);
            noteApp.create(note);
            finish();
        }else {

            int i= noteApp.getNotes().get(id).getId();
            noteApp.getNotes().set(id ,new Note(title,desc,date));
            noteApp.update(new Note(title,desc,date, i));
            isShow =false;
            finish();
        }
            Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.sure_to_back);
        builder.setPositiveButton(R.string.txt_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(SecondActivity.this, MainActivity.class));
                finish();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(R.string.txt_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                 dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}