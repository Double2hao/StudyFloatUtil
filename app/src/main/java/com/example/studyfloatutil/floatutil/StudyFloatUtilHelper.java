package com.example.studyfloatutil.floatutil;

import android.content.Context;
import android.content.Intent;

/**
 * author: xujiajia
 * created on: 2020/9/5 11:20 AM
 * description:
 */
public class StudyFloatUtilHelper {
  //broadcast
  public static String FILTER_BROADCAST = "FloatUtilData";
  public static String EXTRA_FLOAT_UTIL_DATA = "extra_float_util_data";

  //这个方法可能在多个不同的进程调用
  public static void sendBroadcast(Context context, StudyFloatUtilData data) {
    if (StudyFloatUtilManager.getInstance().isOpenDebugControl()) {
      Intent intent = new Intent(FILTER_BROADCAST);
      intent.putExtra(EXTRA_FLOAT_UTIL_DATA, data);
      context.sendBroadcast(intent);
    }
  }
}
