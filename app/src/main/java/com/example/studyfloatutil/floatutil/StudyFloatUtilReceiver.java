package com.example.studyfloatutil.floatutil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * author: xujiajia
 * created on: 2020/9/5 11:09 AM
 * description:
 */
public class StudyFloatUtilReceiver extends BroadcastReceiver {
  @Override public void onReceive(Context context, Intent intent) {
    StudyFloatUtilData data =
        intent.getParcelableExtra(StudyFloatUtilHelper.EXTRA_FLOAT_UTIL_DATA);
    if (data != null) {
      StudyFloatUtilManager.getInstance().pushData(data);
    }
  }
}
