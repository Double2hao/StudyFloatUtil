package com.example.studyfloatutil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.studyfloatutil.floatutil.StudyFloatUtilData;
import com.example.studyfloatutil.floatutil.StudyFloatUtilDelegate;
import com.example.studyfloatutil.floatutil.StudyFloatUtilHelper;
import com.example.studyfloatutil.floatutil.StudyFloatUtilManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  //ui
  private Button btnInit;
  private Button btnOpen;
  private Button btnClose;
  private Button btnShow;
  private Button btnRemove;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initViews();
  }

  private void initViews() {
    btnInit = findViewById(R.id.btn_init_main);
    btnOpen = findViewById(R.id.btn_open_main);
    btnClose = findViewById(R.id.btn_close_main);
    btnShow = findViewById(R.id.btn_show_main);
    btnRemove = findViewById(R.id.btn_remove_main);

    btnInit.setOnClickListener(this);
    btnOpen.setOnClickListener(this);
    btnClose.setOnClickListener(this);
    btnShow.setOnClickListener(this);
    btnRemove.setOnClickListener(this);
  }

  @Override protected void onResume() {
    super.onResume();
    StudyFloatUtilHelper.sendBroadcast(this, new StudyFloatUtilData("页面进入前台"));
  }

  @Override protected void onPause() {
    super.onPause();
    StudyFloatUtilHelper.sendBroadcast(this, new StudyFloatUtilData("页面进入后台"));
  }

  @Override public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.btn_init_main) {
      StudyFloatUtilManager.getInstance().init(this, false, new StudyFloatUtilDelegate() {
        @Override public void log(String msg) {
          Log.i("test", msg); //自己的log系统
        }
      });
    } else if (id == R.id.btn_open_main) {
      StudyFloatUtilManager.getInstance().setOpenDebugControl(true);
    } else if (id == R.id.btn_close_main) {
      StudyFloatUtilManager.getInstance().setOpenDebugControl(false);
    } else if (id == R.id.btn_show_main) {
      StudyFloatUtilManager.getInstance().showDebugViewOnUiThread(MainActivity.this);
    } else if (id == R.id.btn_remove_main) {
      StudyFloatUtilManager.getInstance().removeDebugViewOnUiThread(MainActivity.this);
    }
  }
}