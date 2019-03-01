package go.app.mdb.android.go.lixian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import go.app.mdb.android.go.R;
import static go.app.mdb.android.go.lixian.LixinaMainActivity.tiaoxingma_zhi;

/**
 * 用于获取条形码的时候使用这个页面
 */
public class EmptyActivity extends AppCompatActivity {

    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");

        IntentIntegrator integrator = new  IntentIntegrator(EmptyActivity.this);
        // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("扫描条形码");//底部的提示文字，设为""可以置空
        integrator.setCameraId(0);//前置或者后置摄像头
        integrator.setBeepEnabled(false);//扫描成功的「哔哔」声，默认开启
        integrator.initiateScan();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "扫码取消！", Toast.LENGTH_LONG).show();

                this.finish();
            } else {
                //Toast.makeText(this, "扫描成功，条码值: " + result.getContents(), Toast.LENGTH_LONG).show();
                //获取条形码值
                 tiaoxingma_zhi = result.getContents();
//                //注册广播
//                Intent intent = new Intent();
//                intent.setAction("cn.programmer.CUSTOM_INTENT");
//                sendBroadcast(intent);

                this.finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
