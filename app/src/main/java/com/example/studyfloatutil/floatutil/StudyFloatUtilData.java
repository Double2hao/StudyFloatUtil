package com.example.studyfloatutil.floatutil;

import java.text.SimpleDateFormat;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author: xujiajia
 * created on: 2020/9/4 5:31 PM
 * description:
 */
public class StudyFloatUtilData implements Parcelable {

  //data
  private long time;
  private String msg;

  public StudyFloatUtilData(String msg) {
    time = System.currentTimeMillis();
    this.msg = msg;
  }

  protected StudyFloatUtilData(Parcel in) {
    time = in.readLong();
    msg = in.readString();
  }

  @Override public String toString() {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); //设置格式
    return "[" + sdf.format(time) + "]" + msg;
  }

  public static final Creator<StudyFloatUtilData> CREATOR =
      new Creator<StudyFloatUtilData>() {
        @Override
        public StudyFloatUtilData createFromParcel(Parcel in) {
          return new StudyFloatUtilData(in);
        }

        @Override
        public StudyFloatUtilData[] newArray(int size) {
          return new StudyFloatUtilData[size];
        }
      };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(time);
    dest.writeString(msg);
  }
}
