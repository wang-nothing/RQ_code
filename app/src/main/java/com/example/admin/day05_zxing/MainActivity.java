package com.example.admin.day05_zxing;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_open;
    private Button btn_parsing;
    private Button btn_generate;
    private Button btn_generatelogo;
    private ImageView image_generate;
    private EditText et_generate;
    final int REQUEST_CODE = 1;
    final int REQUEST_IMAGE = 2;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initListener();
        initServices();
    }

    private void initServices() {
        String[] checkPermission = CheckPermissionUtils.checkPermission(this);
        if (checkPermission.length == 0){

        }else{
            ActivityCompat.requestPermissions(this,checkPermission,100);
        }
    }

    private void initListener() {
        btn_open.setOnClickListener(this);
        btn_parsing.setOnClickListener(this);
        btn_generate.setOnClickListener(this);
        btn_generatelogo.setOnClickListener(this);
    }

    private void initViews() {
        btn_open = findViewById(R.id.btn_open);
        btn_parsing = findViewById(R.id.btn_parsing);
        btn_generate = findViewById(R.id.btn_generate);
        btn_generatelogo = findViewById(R.id.btn_generatelogo);
        image_generate = findViewById(R.id.image_generate);
        et_generate = findViewById(R.id.et_generate);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE:
                if (requestCode == REQUEST_CODE) {
                    //处理扫描结果（在界面上显示）
                    if (null != data) {
                        Bundle bundle = data.getExtras();
                        if (bundle == null) {
                            return;
                        }
                        if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                            String result = bundle.getString(CodeUtils.RESULT_STRING);
//                            Toast.makeText(MainActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                            intent.putExtra("text",result);
                            startActivity(intent);
                        } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                            Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
            case REQUEST_IMAGE:
                if (requestCode == REQUEST_IMAGE) {
                    if (data != null) {
                        Uri uri = data.getData();
                        try {
                            CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                                @Override
                                public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//                                    Toast.makeText(MainActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
//                                    Log.i("ZxingActivity.this", "二维码图片识别==: " + result);
                                    Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                                    intent.putExtra("text",result);
                                    startActivity(intent);
                                }
                                @Override
                                public void onAnalyzeFailed() {
                                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();


                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_open:
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
                break;
            case R.id.btn_parsing:
                Intent intent_generate = new Intent(Intent.ACTION_GET_CONTENT);
                intent_generate.addCategory(Intent.CATEGORY_OPENABLE);
                intent_generate.setType("image/*");
                startActivityForResult(intent_generate,REQUEST_IMAGE);
                break;
            case R.id.btn_generate:
                String json = et_generate.getText().toString();
                if (TextUtils.isEmpty(json)){
                    Toast.makeText(MainActivity.this,"输入为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                et_generate.setText("");
                bitmap = CodeUtils.createImage(json,400,400,null);
                image_generate.setImageBitmap(bitmap);
                break;
            case R.id.btn_generatelogo:
                String textContent = et_generate.getText().toString();
                if (TextUtils.isEmpty(textContent)) {
                    Toast.makeText(MainActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                et_generate.setText("");
                bitmap = CodeUtils.createImage(textContent, 400, 400, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background));
                image_generate.setImageBitmap(bitmap);
                break;
        }
    }

}
