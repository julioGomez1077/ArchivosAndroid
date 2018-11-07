package com.example.jcgo1.archivos;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ActivityLeer  extends AppCompatActivity {

    RadioButton interna;
    RadioButton externa;
    EditText texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer);
        interna = findViewById(R.id.rbnInterna);
        externa = findViewById(R.id.rbnExterna);
        texto = findViewById(R.id.editText);
        texto.setEnabled(false);
    }

    public void Guardar(View v){

        if(interna.isChecked()){
            try{
                BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("Datos.txt")));
                String datos = br.readLine();
                texto.setText(datos);
                br.close();
            }catch (Exception e){

            }
        }else{
            try{
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)){
                    File rutaSD = Environment.getExternalStorageDirectory();
                    File f = new File(rutaSD.getAbsolutePath(),"DatoSD.txt");
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                    String datos = br.readLine();
                    texto.setText(datos);
                    br.close();
                }
            }catch (Exception e){

            }
        }
    }

}
