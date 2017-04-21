package com.surflex.jci;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

public class IMEIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imei);
        String number = getMyPhoneNO();
        Toast.makeText(getApplicationContext(), "My Phone Number is: "
                + number, Toast.LENGTH_SHORT).show();

        TextView textView = (TextView) findViewById(R.id.phone_number_view);
        textView.setText("My Phone Nnumber is: " + number);


    }

    private String getMyPhoneNO() {
        TelephonyManager telephony = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String first="",firstNumber="";
        String second="",secondNumber="";
        try {

            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getFirstMethod = telephonyClass.getMethod("getDeviceId", parameter);

            Log.d("SimData", getFirstMethod.toString());
            Object[] obParameter = new Object[1];
            obParameter[0] = 0;
            TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            first = (String) getFirstMethod.invoke(telephony, obParameter);
            firstNumber=telephony.getLine1Number();
            Log.d("SimData", "first :" + first);
            obParameter[0] = 1;
            second = (String) getFirstMethod.invoke(telephony, obParameter);

            Log.d("SimData", "Second :" + second);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return " 1st IMEI "+first+" and "+secondNumber+"2nd IMEI"+second;
    }
}