package vku.phungduc.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.api.ApiService;


public class forgotPassActivity extends AppCompatActivity {

    public static String code_pass = ""  ;
    ProgressDialog loading  = null  ;
    String email = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogot_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btn_xacNhan = findViewById(R.id.btn_xacnhan) ;
        Button btn_sendMail = findViewById(R.id.btn_sendMail);
        EditText edit_email = findViewById(R.id.edit_mail);
        PinView code_updatePass = findViewById(R.id.pinview_code);
        btn_sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(v.getContext(),null,"Loading...",false,false);

                ApiService.apiService.getCodePass(edit_email.getText().toString()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        loading.dismiss();

                        String s = response.body();
                         email = edit_email.getText().toString() ;
                        try {
                            int code = Integer.valueOf(s) ;
                            code_pass = s ;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(180000);
                                        code_pass = "";
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                            Snackbar.make(v, "Gửi mã thành công ", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }catch (Exception e){
                            Snackbar.make(v,  "Gửi mã thất bại :((", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        loading.dismiss();

                        Snackbar.make(v, "Đăng nhập thất bại ", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }
        });
        btn_xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( code_pass.equals(code_updatePass.getText().toString()) && code_pass != ""){
                    Snackbar.make(v, "Xác nhận thành công" , Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent intent = new Intent(getApplicationContext(), updatePassActivity.class);
                    intent.putExtra("email", email);

                    startActivity(intent);
                    finish();
                }else {
                    Snackbar.make(v, "Mời bạn nhập mã lại:((" , Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
