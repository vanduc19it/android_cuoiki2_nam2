package vku.phungduc.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.api.ApiService;

public class updatePassActivity extends AppCompatActivity {
    ProgressDialog loading  = null  ;
    String Semail = "" ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimatkhau);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if( bundle != null){
            Semail = bundle.getString("email") ;

        }
        MultipartBody.Part email = MultipartBody.Part.createFormData("email", Semail) ;

        EditText editPass     = findViewById(R.id.edit_pass) ;
        EditText editRePass   = findViewById(R.id.edit_repass);
        Button btn_updatePass = findViewById(R.id.btn_updatePass);

        btn_updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(v.getContext(),null,"Loading...",false,false);
                String s1 = editPass.getText().toString() ;
                String s2 = editRePass.getText().toString();
                if( s1.equals(s2)){
                    MultipartBody.Part pass =  MultipartBody.Part.createFormData("pass" , s1) ;

                    ApiService.apiService.postUpdatePass(pass , email).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            loading.dismiss();
                            if (response.body().equals("1")){
                                Snackbar.make(v, "Cập nhật mật khẩu thành công", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            }else {
                                Snackbar.make(v, "Cập nhật mật khẩu thất bại", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            loading.dismiss();
                            Snackbar.make(v, "Cập nhật mật khẩu thất bại", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
                }else {
                    loading.dismiss();
                    Snackbar.make(v, "Mật khẩu không trùng khớp ", Snackbar.LENGTH_LONG)
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
