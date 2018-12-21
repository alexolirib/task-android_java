package com.alexolirib.tasks.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alexolirib.tasks.R;
import com.alexolirib.tasks.infra.operation.OperationListener;
import com.alexolirib.tasks.manager.PersonManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private Context mContext;
    private PersonManager mPersonManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.mContext = this;
        mPersonManager = new PersonManager(this);

        // Inicializa elementos
        this.mViewHolder.editEmail = (EditText) this.findViewById(R.id.edit_email);
        this.mViewHolder.editPassword = (EditText) this.findViewById(R.id.edit_password);
        this.mViewHolder.buttonLogin = (Button) this.findViewById(R.id.button_login);
        this.mViewHolder.textRegister = (TextView) this.findViewById(R.id.text_register);

        // Inicializa eventos
        this.mViewHolder.buttonLogin.setOnClickListener(this);
        this.mViewHolder.textRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_login) {

            this.handleLogin();

        } else if (id == R.id.text_register) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

    private void handleLogin() {
        String email = this.mViewHolder.editEmail.getText().toString();
        String pass = this.mViewHolder.editPassword.getText().toString();
        if(!email.replace(" ", "").equals("") && !pass.replace(" ", "").equals("")){
            mPersonManager.login(email, pass, registerListenerLogin());
        } else{
            Toast.makeText(this, "Verifica login ou senha", Toast.LENGTH_SHORT).show();
        }
    }

    private OperationListener<Boolean> registerListenerLogin(){
        return new OperationListener<Boolean>(){
            @Override
            public void onSuccess(Boolean result) {
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
            }
        };
    }

    /**
     * ViewHolder
     */
    private static class ViewHolder {
        private EditText editEmail;
        private EditText editPassword;
        private Button buttonLogin;
        private TextView textRegister;
    }
}
