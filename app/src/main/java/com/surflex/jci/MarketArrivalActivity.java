package com.surflex.jci;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MarketArrivalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    MarketReport  marketReport;
    Spinner spinner;
    EditText arrivalQuantity;
    EditText minimumRulingMarketRate;
    EditText maximumRulingMarketRate;
    EditText minimumMoistureContent;
    EditText maximumMoistureContent;
    EditText gradeComposition;
    Button saveBtn;
    private static boolean flag;

    String[] juteVariety = { "Select","Tossa", "White", "Mesta", "Bimli" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_arrival);
        //for dropdown
        spinner=(Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,juteVariety);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        //for  Arrived Quantity
        arrivalQuantity=(EditText)findViewById(R.id.arrivalQuantity);


        InputFilter filter = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint=20;
            final int maxDigitsAfterDecimalPoint=2;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([1-9]{1})([0-9]{0,"+(maxDigitsBeforeDecimalPoint-1)+"})?)?(\\.[0-9]{0,"+maxDigitsAfterDecimalPoint+"})?"

                )) {
                    if(source.length()==0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;

            }
        };
        arrivalQuantity.setFilters(new InputFilter[] { filter });


        minimumRulingMarketRate=(EditText)findViewById(R.id.minimumRulingMarketRate);
        maximumRulingMarketRate=(EditText)findViewById(R.id.maximumRulingMarketRate);
        minimumMoistureContent=(EditText)findViewById(R.id.minimumMoistureContent);
        maximumMoistureContent=(EditText)findViewById(R.id.maximumMoistureContent);
        gradeComposition=(EditText)findViewById(R.id.gradeComposition);

        saveBtn=(Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getApplicationContext(),juteVariety[position] ,Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public  void validation(){


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.saveBtn:
                boolean valid=true;
                try {
                    if(spinner.getSelectedItem().toString()=="Select")
                    {
                        Toast.makeText(getApplicationContext(),"Please select Jute Variety.",Toast.LENGTH_LONG).show();
                        valid=false;
                    }
                    else if(arrivalQuantity.getText().toString()==null)
                    {
                        Toast.makeText(getApplicationContext(),"Please enter Arrival Quantity.",Toast.LENGTH_LONG).show();
                        arrivalQuantity.requestFocus();
                        valid=false;
                    }
                    else if(minimumRulingMarketRate.getText().toString()==null){
                        Toast.makeText(getApplicationContext(),"Please enter Maximum Ruling Market Rate.",Toast.LENGTH_LONG).show();
                        minimumRulingMarketRate.requestFocus();
                        valid=false;
                    }
                    else if(minimumMoistureContent.getText().toString()==null){
                        Toast.makeText(getApplicationContext(),"Please enter Minimum Moisture Content.",Toast.LENGTH_LONG).show();
                        minimumMoistureContent.requestFocus();
                        valid=false;
                    }
                    String aq = arrivalQuantity.getText().toString();
                    int minRull = Integer.parseInt(minimumRulingMarketRate.getText().toString());
                    int maxRull = Integer.parseInt(maximumRulingMarketRate.getText().toString());
                    int minMoisture = Integer.parseInt(minimumMoistureContent.getText().toString());
                    int maxMoisture = Integer.parseInt(maximumMoistureContent.getText().toString());
                    int gradeComp = Integer.parseInt(gradeComposition.getText().toString());

                      if(valid) {


                    if (minRull > maxRull) {
                        Toast.makeText(getApplicationContext(), "Maximum Ruling Market Rate must be greater than Minimum Ruling Market Rate.", Toast.LENGTH_LONG).show();
                        maximumRulingMarketRate.requestFocus();
                        valid = false;
                    } else if (minMoisture >= 100) {
                        Toast.makeText(getApplicationContext(), "Minimum Moisture Content must be  lower than equal to 100.", Toast.LENGTH_LONG).show();
                        maximumRulingMarketRate.requestFocus();
                        valid = false;
                    } else if (maxMoisture >= 100 && maximumRulingMarketRate.getText().toString() != null) {
                        Toast.makeText(getApplicationContext(), "Maximum Moisture Content must be lower equal to 100.", Toast.LENGTH_LONG).show();
                        maximumRulingMarketRate.requestFocus();
                        valid = false;
                    } else if (minMoisture > maxMoisture && minimumMoistureContent.getText().toString() != null) {
                        Toast.makeText(getApplicationContext(), "Maximum Moisture Content must be greater than Minimum Moisture Content.", Toast.LENGTH_LONG).show();
                        maximumMoistureContent.requestFocus();
                        valid = false;
                    } else if (gradeComp >= 100) {
                        Toast.makeText(getApplicationContext(), "Grade Composition must be lower than equal to 100.", Toast.LENGTH_LONG).show();
                        gradeComposition.requestFocus();
                        valid = false;
                    }
                }

                if (valid){
                    Toast.makeText(getApplicationContext(),"All Data are vaild",Toast.LENGTH_LONG).show();
                }
                }catch (Exception e){

                    Toast.makeText(getApplicationContext(), "Please Enter the values", Toast.LENGTH_LONG).show();


                }



                break;
        }
    }
}
