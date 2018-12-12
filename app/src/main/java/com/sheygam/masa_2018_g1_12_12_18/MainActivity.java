package com.sheygam.masa_2018_g1_12_12_18;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressBar myProgress;
    private EditText inputEmail, inputPassword;
    private Button regBtn, loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myProgress = findViewById(R.id.myProgress);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        regBtn = findViewById(R.id.regBtn);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(this);
        regBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.regBtn){
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            try {
                checkEmail(email);
                checkPassword(password);
                //TODO send request
            }catch (EmailValidException e){
                showEmailError(e.getMessage());
            }catch (PasswordValidException e){
                showPasswordError(e.getMessage());
            }
        }else if(v.getId() == R.id.loginBtn){

        }
    }

    private void showProgress(){
        myProgress.setVisibility(View.VISIBLE);
        inputEmail.setVisibility(View.INVISIBLE);
        inputPassword.setVisibility(View.INVISIBLE);
        loginBtn.setVisibility(View.INVISIBLE);
        regBtn.setVisibility(View.INVISIBLE);
    }

    private void hideProgress(){
        myProgress.setVisibility(View.GONE);
        inputEmail.setVisibility(View.VISIBLE);
        inputPassword.setVisibility(View.VISIBLE);
        regBtn.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.VISIBLE);
    }

    private void showEmailError(String error){
        inputEmail.setError(error);
    }

    private void showPasswordError(String error){
        inputPassword.setError(error);
    }

    private void showError(String error){
        new AlertDialog.Builder(this)
                .setMessage(error)
                .setTitle("Error!")
                .setPositiveButton("Ok",null)
                .setCancelable(false)
                .create()
                .show();
    }

    private void showNextView(){
        Toast.makeText(this, "Next Activity", Toast.LENGTH_SHORT).show();
    }


    private void checkEmail(String email) throws EmailValidException {
        if(email.isEmpty()){
            throw new EmailValidException("Email can't be empty!");
        }

        int at = email.indexOf("@");
        if(at < 0) {
            throw new EmailValidException("Wrong email format! Example: name@mail.com");
        }

        if(email.lastIndexOf("@") != at) {
            throw new EmailValidException("Wrong email format! Example: name@mail.com");
        }

        int dot = email.lastIndexOf(".");
        if(dot < 0 || dot < at) {
            throw new EmailValidException("Wrong email format! Example: name@mail.com");
        }

        if(email.length() - 1 - dot <= 1) {
            throw new EmailValidException("Wrong email format! Example: name@mail.com");
        }
    }

    private void checkPassword(String password) throws PasswordValidException {
        if(password.length() < 8) {
            throw new PasswordValidException("Password length need be 8 or more symbols");
        }
        boolean[] tests = new boolean[4];
        char[] arr = password.toCharArray();
        for (char anArr : arr) {
            if (Character.isUpperCase(anArr)) {
                tests[0] = true;
            }

            if (Character.isLowerCase(anArr)) {
                tests[1] = true;
            }

            if (Character.isDigit(anArr)) {
                tests[2] = true;
            }

            if (isSpecSymbol(anArr)) {
                tests[3] = true;
            }
        }

        if(!tests[0]){
            throw new PasswordValidException("Password must contain at least one uppercase letter!");
        }
        if(!tests[1]){
            throw new PasswordValidException("Password must contain at least one lowercase letter!");
        }
        if(!tests[2]){
            throw new PasswordValidException("Password must contain at least one digit!");
        }
        if(!tests[3]){
            throw new PasswordValidException("Password must contain at least one special symbol from ['$','~','-','_']!");
        }
    }

    private boolean isSpecSymbol(char c) {
        char[] arr = {'$','~','-','_'};
        for (char anArr : arr) {
            if (anArr == c) {
                return true;
            }
        }
        return false;
    }


}