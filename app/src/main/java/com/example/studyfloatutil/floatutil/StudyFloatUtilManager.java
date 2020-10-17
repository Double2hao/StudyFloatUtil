package com.example.studyfloatutil.floatutil;

import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.AnyThread;
import androidx.annotation.UiThread;

import static android.content.Context.WINDOW_SERVICE;

/**
 * author: xujiajia
 * created on: 2020/9/4 5:27 PM
 * description:
 */
public class StudyFloatUtilManager {
  //constants
  private static final String TAG = "StudyFloatUtilManager";
  //data
  private Context context;
  private LinkedList<StudyFloatUtilData> dataList = new LinkedList<>();
  private StudyFloatUtilView studyFloatUtilView;//输出data的view
  private StudyFloatUtilReceiver studyFloatUtilReceiver;
  private StudyFloatUtilDelegate delegate;
  private boolean isOpenFloatUtil = false;

  private static class HOST {
    private static final StudyFloatUtilManager instance = new StudyFloatUtilManager();
  }

  private StudyFloatUtilManager() {

  }

  @AnyThread
  public void init(Context context, boolean isOpenDebugControl,
      StudyFloatUtilDelegate delegate) {
    if (context == null || delegate == null) {
      return;
    }
    this.context = context;
    this.delegate = delegate;
    setDelegate(delegate);
    setOpenDebugControl(isOpenDebugControl);
  }

  public void setDelegate(StudyFloatUtilDelegate delegate) {
    this.delegate = delegate;
  }

  public StudyFloatUtilDelegate getDelegate() {
    return delegate;
  }

  @AnyThread
  public void setOpenDebugControl(boolean openDebugControl) {
    isOpenFloatUtil = openDebugControl;
    if (isOpenFloatUtil) {
      if (studyFloatUtilReceiver == null && context != null) {
        studyFloatUtilReceiver = new StudyFloatUtilReceiver();
        context.registerReceiver(studyFloatUtilReceiver,
            new IntentFilter(StudyFloatUtilHelper.FILTER_BROADCAST));//注册广播
      }
    } else {
      if (studyFloatUtilReceiver != null && context != null) {
        context.unregisterReceiver(studyFloatUtilReceiver);//反注册广播
        studyFloatUtilReceiver = null;
      }
    }
  }

  public boolean isOpenDebugControl() {
    return isOpenFloatUtil;
  }

  //在测试环境或者debug包的时候会显示
  @UiThread
  public void showDebugViewOnUiThread(Context context) {
    if (studyFloatUtilView != null || delegate == null || !isOpenFloatUtil) {
      return;
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
      Toast.makeText(context, "SoGameDebug功能需要打开悬浮窗权限才能使用", Toast.LENGTH_LONG).show();
      Intent intent = new Intent();
      intent.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
      intent.setData(Uri.parse("package:" + context.getPackageName()));
      context.startActivity(intent);
    }
    //宽高设置为屏幕宽度。（游戏存在横屏与竖屏）
    WindowManager.LayoutParams layoutParams =
        new WindowManager.LayoutParams();
    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
    } else {
      layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
    }
    layoutParams.format = PixelFormat.RGBA_8888;
    layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
    layoutParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;

    studyFloatUtilView = new StudyFloatUtilView(context);
    WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
    if (windowManager == null) {
      return;
    }
    windowManager.addView(studyFloatUtilView, layoutParams);
  }

  @UiThread
  public void removeDebugViewOnUiThread(Context context) {
    if (studyFloatUtilView == null) {
      return;
    }
    WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
    if (windowManager == null) {
      return;
    }
    windowManager.removeViewImmediate(studyFloatUtilView);
    studyFloatUtilView = null;
  }

  public static StudyFloatUtilManager getInstance() {
    return HOST.instance;
  }

  @UiThread
  void pushData(StudyFloatUtilData data) {
    if (data == null || delegate == null || !isOpenFloatUtil) {
      return;
    }
    dataList.push(data);
    syncDataToView();
  }

  @UiThread
  private void syncDataToView() {
    if (studyFloatUtilView == null || delegate == null || !isOpenFloatUtil) {
      return;
    }
    StudyFloatUtilData data;
    try {
      for (; ; ) {
        if (dataList.size() < 1) {
          return;
        }
        data = dataList.pop();
        String dataString = data.toString();
        this.delegate.log(dataString);
        if (studyFloatUtilView != null) {
          studyFloatUtilView.appendInfo(dataString + "\n");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
