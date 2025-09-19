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

import java.io.PrintStream;


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

        if (!userInput.getText().toString().equals(userName) || !passInput.getText().toString().equals(pass)) {
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

}
