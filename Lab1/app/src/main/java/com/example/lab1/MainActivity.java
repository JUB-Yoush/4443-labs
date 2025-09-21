package com.example.lab1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    String userName = "admin";
    String pass = "1234";

    String test = "WORKING0";

    SharedPreferences sp;
    EditText userInput;
    EditText passInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText userInput = findViewById(R.id.userInput);
        EditText passInput = findViewById(R.id.passInput);

        SharedPreferences sharedpref = getApplicationContext().getSharedPreferences("userPrefs",Context.MODE_PRIVATE);
        userInput.setText(sharedpref.getString("username",""));
        passInput.setText(sharedpref.getString("password",""));

        CheckBox PassToggle = findViewById(R.id.checkBox);
        CheckBox rememberBox = findViewById(R.id.rememberMeCheck);




        PassToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

          @Override
              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    EditText passInput = findViewById(R.id.passInput);
              Log.d("MainActivity",Integer.toString(passInput.getInputType()));
                    //InputType oldone = (InputType) passInput.getInputType();
                    if (isChecked) {
                        passInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    } else {
                        passInput.setInputType(129); // The enum to switch back to string form is 129, which does not exist in the documentation

                    }
                  }
              }
        );
    }

    public void ValidateInput(View view) {
        EditText userInput = findViewById(R.id.userInput);
        EditText passInput = findViewById(R.id.passInput);
        TextView displayMsg = findViewById(R.id.displayMsg);
        CheckBox rememberBox = findViewById(R.id.rememberMeCheck);

        String invalidDisplayMsg = "Invalid username/password";
        String validDisplayMsg = "Login Successful";

        ArrayList<String> users = readFromFile(this);
        boolean valid = false;

        Log.d("MainActivity",Integer.toString(users.size()));
        for (String user:
             users) {
            Log.d("MainActivity",user);
        }
        Log.d("MainActivity",users.toString());
        for (int i = 0; i < users.size();i+=2) {
            String user = users.get(i);
            Log.d("MainActivity",user);
            Log.d("MainActivity",userInput.getText().toString());

            String pass = users.get(i+1);
            Log.d("MainActivity",pass);

            if (userInput.getText().toString().equals(user) && passInput.getText().toString().equals(pass)){
                valid = true;
                break;
            }
        }


        if (!valid){
            displayMsg.setText(invalidDisplayMsg);
        } else {
            displayMsg.setText(validDisplayMsg);

            if(rememberBox.isChecked()){
                sp = getSharedPreferences("userPrefs",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username",userInput.getText().toString());
                editor.putString("password",passInput.getText().toString());
                editor.commit();
                Log.d("MainActivity","info saved");
            }
            sndMsg(view);

        }


    }

    public void CancelLogin(View view) {
        EditText userInput = findViewById(R.id.userInput);
        EditText passInput = findViewById(R.id.passInput);
        userInput.setText("");
        passInput.setText("");


    }

    public void sndMsg(View view){
        Intent intent = new Intent(this,WelcomeActivity.class);

        EditText myMsg = (EditText) findViewById(R.id.userInput);
        String msg = myMsg.getText().toString();

        intent.putExtra("MESSAGE",msg);

        startActivity(intent);
    }

    public void onRegisterClicked(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private ArrayList<String> readFromFile(Context context) {

        String ret = "";
        ArrayList<String> out = new ArrayList<>();
        try {
            InputStream inputStream = context.openFileInput("users.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null ) {
                    out.add(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return out;
    }
}