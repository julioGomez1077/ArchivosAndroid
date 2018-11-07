package com.example.jcgo1.archivos;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class ActivityEscribir  extends AppCompatActivity {

    RadioButton interna;
    RadioButton externa;
    EditText texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escribir);
        interna = findViewById(R.id.rbnInterna);
        externa = findViewById(R.id.rbnExterna);
        texto = findViewById(R.id.editText);
    }

    public void Guardar(View v){
        if(interna.isChecked()){
            try {
                OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("Datos.txt", Context.MODE_APPEND));
                osw.write(texto.getText().toString());
                osw.close();
                Toast.makeText(getApplicationContext(),"Datos Guardados en la Memoria Interna",Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Log.e("Ficheros","Error en la memoria interna");
            }
        }else{
            checkPermission();
            try{
                String estado = Environment.getExternalStorageState();
               if (estado.equals(Environment.MEDIA_MOUNTED)){
                   File rutaSD = Environment.getExternalStorageDirectory();
                    File f = new File(rutaSD.getAbsolutePath(),"DatoSD.txt");

                   OutputStreamWriter osw =
                           new OutputStreamWriter(
                                   new FileOutputStream(f));
                   osw.write(texto.getText().toString());
                    osw.close();
                   Toast.makeText(getApplicationContext(),"Datos Guardados en la Memoria Externa",Toast.LENGTH_SHORT).show();
               }
            }catch (Exception e){
                Log.d("Salida" , e.getMessage() + " ---------");
            }
        }
        texto.setText("");
    }

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    private void checkPermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            Toast.makeText(this, "This version is not Android 6 or later " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();

        } else {

            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CAMERA);

            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);

                Toast.makeText(this, "Requesting permissions", Toast.LENGTH_LONG).show();

            }else if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED){

                Toast.makeText(this, "The permissions are already granted ", Toast.LENGTH_LONG).show();
                ///// openCamera();

            }

        }

        return;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(REQUEST_CODE_ASK_PERMISSIONS == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "OK Permissions granted ! " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();
                //////openCamera();
            } else {
                Toast.makeText(this, "Permissions are not granted ! " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
