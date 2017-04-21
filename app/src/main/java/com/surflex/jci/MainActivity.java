package com.surflex.jci;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText ed1;
    Button logIn;
    Spinner spinnerIMEI;
    String allIMEI="";
    String[] imeiArr,imeiArrNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1=(EditText)findViewById(R.id.editText);
        logIn = (Button)findViewById(R.id.logIn);
        logIn.setOnClickListener(this);

        imeiArr = getMyIMEI().split(",");

        spinnerIMEI = (Spinner)findViewById(R.id.spinnerIMEI);
        spinnerIMEI.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,imeiArr);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIMEI.setAdapter(aa);

        InputFilter phFilter = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint=10;
            final int maxDigitsAfterDecimalPoint=2;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches("(([0-9]{0,"+(maxDigitsBeforeDecimalPoint)+"})?)?")) {
                    if(source.length()==0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;

            }
        };

        ed1.setFilters(new InputFilter[] { phFilter });

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logIn:
                try{
                    if (ed1.getText().toString().isEmpty() || ed1.getText().toString().trim().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Enter the phone number.", Toast.LENGTH_SHORT).show();

                        ed1.requestFocus();
                    }
                    else if (ed1.getText().toString().length()<10) {
                        Toast.makeText(getApplicationContext(), "Please Enter a valid phone number.", Toast.LENGTH_SHORT).show();

                        ed1.requestFocus();
                    }
                    else if(ed1.getText().toString() != null) {
                        //Toast.makeText(getApplicationContext(), ed1.getText().toString(), Toast.LENGTH_SHORT).show();


                        Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
                        startActivity(i);
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Please Enter the phone number", Toast.LENGTH_SHORT).show();

                }

                break;




        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private String getMyIMEI() {
        TelephonyManager telephony = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String first="",firstNumber="";
        String second="",secondNumber="";
        String all="";
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
        return first+","+second;
    }
}
