package com.surflex.jci;

import android.content.Intent;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class PurchaseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText formNo,binNo,grossQuantity,deductionQuantity,netQuantity,avgRate,juteValue,estGradeComp,gEd1,gEd2,gEd3,gEd4,gEd5,gEd6,gEd7,gEd8;
    TextView gText1,gText2,gText3,gText4,gText5,gText6,gText7,gText8;
    Button nextBtn;
    String allGrade="";


    String[] basis = { "select", "MSP", "Commercial", };
    String[] juteVariety = { "select", "Tossa", "White", "Tossa (New)","White (New)", "Mesta", "Bimli" };
    String juteVarietySelected = "" , basicSelected = "";
    int totalEstimatedGrade = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        formNo = (EditText)findViewById(R.id.form_no) ;
        binNo = (EditText)findViewById(R.id.binNo);
        grossQuantity = (EditText)findViewById(R.id.gross_quantity);
        deductionQuantity = (EditText)findViewById(R.id.deduction_quantity);
        netQuantity = (EditText)findViewById(R.id.netQuantity);
        netQuantity.setEnabled(false);
        juteValue = (EditText)findViewById(R.id.juteValue);
        avgRate = (EditText)findViewById(R.id.avgRate);
        avgRate.setEnabled(false);
        estGradeComp = (EditText)findViewById(R.id.estimatedGrade);
        estGradeComp.setEnabled(false);
        nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Please Value.", Toast.LENGTH_LONG).show();
                validationInput();
                autoCalculatedFields();
                if(validationInput()){
                    if(calculateGrade()!=100){
                        Toast.makeText(getApplicationContext(), "Sum of grade entered value should equal to 100", Toast.LENGTH_SHORT).show();

                    }else{
                        Calendar c = Calendar.getInstance();
                        String entryDate = c.get(Calendar.DAY_OF_MONTH)+"-"+ c.get(Calendar.MONTH)+"-"+c.get(Calendar.YEAR);

                   Intent i = new Intent(getApplicationContext(), Purchase2Activity.class);
                    i.putExtra("formNo",formNo.getText().toString());
                    i.putExtra("basis",basicSelected);
                    i.putExtra("binNo",binNo.getText().toString());
                    i.putExtra("juteVariety",juteVarietySelected);
                    i.putExtra("grossQuantity",grossQuantity.getText().toString());
                    i.putExtra("deductionQuantity",deductionQuantity.getText().toString());
                    i.putExtra("netQuantity",netQuantity.getText().toString());
                    i.putExtra("avgRate",avgRate.getText().toString());
                    i.putExtra("estGradeComp",estGradeComp.getText().toString());
                    i.putExtra("allGrade",allGrade);
                    i.putExtra("entryDate",entryDate) ;
                    startActivity(i);
                }}


            }
        });


        gText1 = (TextView)findViewById(R.id.grade1Text);
        gText2 = (TextView)findViewById(R.id.grade2Text);
        gText3 = (TextView)findViewById(R.id.grade3Text);
        gText4 = (TextView)findViewById(R.id.grade4Text);
        gText5 = (TextView)findViewById(R.id.grade5Text);
        gText6 = (TextView)findViewById(R.id.grade6Text);
        gText7 = (TextView)findViewById(R.id.grade7Text);
        gText8 = (TextView)findViewById(R.id.grade8Text);

        gEd1 = (EditText)findViewById(R.id.garde1EditText);
        gEd2 = (EditText)findViewById(R.id.garde2EditText);
        gEd3 = (EditText)findViewById(R.id.garde3EditText);
        gEd4 = (EditText)findViewById(R.id.garde4EditText);
        gEd5 = (EditText)findViewById(R.id.garde5EditText);
        gEd6 = (EditText)findViewById(R.id.garde6EditText);
        gEd7 = (EditText)findViewById(R.id.garde7EditText);
        gEd8 = (EditText)findViewById(R.id.garde8EditText);




        //Getting the instance of basis Spinner and applying OnItemSelectedListener on it
        Spinner basisSpin = (Spinner) findViewById(R.id.spinner_basis);
        basisSpin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the basis list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,basis);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the basis Spinner
        basisSpin.setAdapter(aa);
        basisSpin.setOnItemSelectedListener(this);

        //Getting the instance of juteVariety Spinner and applying OnItemSelectedListener on it
        Spinner juteVarietySpin = (Spinner) findViewById(R.id.spinner_jute_variety);


        //Creating the ArrayAdapter instance having the juteVariety list
        ArrayAdapter aa1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,juteVariety);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the juteVariety Spinner
        juteVarietySpin.setAdapter(aa1);
        juteVarietySpin.setOnItemSelectedListener(this);





        InputFilter filter0To100 = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint=5;
            final int maxDigitsAfterDecimalPoint=2;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches("((100|[1-9]?[0-9])?)?")) {
                    if(source.length()==0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;

            }
        };

        InputFilter formFilter = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint=5;
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
        formNo.setFilters(new InputFilter[] { formFilter });


        InputFilter grossQuantityFilter = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint=20;
            final int maxDigitsAfterDecimalPoint=2;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([0-9]{0,"+(maxDigitsBeforeDecimalPoint)+"})?)?(\\.[0-9]{0,"+maxDigitsAfterDecimalPoint+"})?"

                )) {
                    if(source.length()==0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;

            }
        };

        grossQuantity.setFilters(new InputFilter[] { grossQuantityFilter });
        deductionQuantity.setFilters(new InputFilter[] { grossQuantityFilter });
        gEd1.setFilters(new InputFilter[] {filter0To100});
        gEd2.setFilters(new InputFilter[] {filter0To100});
        gEd3.setFilters(new InputFilter[] {filter0To100});
        gEd4.setFilters(new InputFilter[] {filter0To100});
        gEd5.setFilters(new InputFilter[] {filter0To100});
        gEd6.setFilters(new InputFilter[] {filter0To100});
        gEd7.setFilters(new InputFilter[] {filter0To100});
        gEd8.setFilters(new InputFilter[] {filter0To100});




    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        Spinner spinner = (Spinner) adapterView;
        if(spinner.getId() == R.id.spinner_basis)
        {
            //do this

            //Toast.makeText(getApplicationContext(),juteVariety[position].toString(),Toast.LENGTH_LONG).show();

            basicSelected=basis[position].toString();

            //Toast.makeText(getApplicationContext(),"Hello 1",Toast.LENGTH_LONG).show();
        }
        else if(spinner.getId() == R.id.spinner_jute_variety)
        {
            //do this


            if(juteVariety[position].toString().equals("Tossa") || juteVariety[position].toString().equals("White"))
            {
                gText6.setVisibility(View.VISIBLE);gEd6.setText("0");gEd6.setVisibility(View.VISIBLE);
                gText7.setVisibility(View.VISIBLE);gEd7.setText("0");gEd7.setVisibility(View.VISIBLE);
                gText8.setVisibility(View.VISIBLE);gEd8.setText("0");gEd8.setVisibility(View.VISIBLE);
                juteVarietySelected = juteVariety[position].toString();
            }else if(juteVariety[position].toString().equals("Mesta") || juteVariety[position].toString().equals("Bimli"))
            {
                gText6.setVisibility(View.VISIBLE);gEd6.setText("0");gEd6.setVisibility(View.VISIBLE);
                gText7.setVisibility(View.GONE);gEd7.setText("0");gEd7.setVisibility(View.GONE);
                gText8.setVisibility(View.GONE);gEd8.setText("0");gEd8.setVisibility(View.GONE);
                juteVarietySelected = juteVariety[position].toString();

            }else {
                juteVarietySelected = juteVariety[position].toString();
                gText6.setVisibility(View.GONE);gEd6.setText("0");gEd6.setVisibility(View.GONE);
                gText7.setVisibility(View.GONE);gEd7.setText("0");gEd7.setVisibility(View.GONE);
                gText8.setVisibility(View.GONE);gEd8.setText("0");gEd8.setVisibility(View.GONE);
            }
            //Toast.makeText(getApplicationContext(),juteVariety[position].toString(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean validationInput(){
        if (binNo.getText().toString().isEmpty() || binNo.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the bill number.", Toast.LENGTH_LONG).show();

            binNo.requestFocus();
            return false;
        }
        else

        if (formNo.getText().toString().isEmpty() || formNo.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the form number.", Toast.LENGTH_LONG).show();

            formNo.requestFocus();
            return false;
        }
        else if (formNo.getText().toString().length()<5) {
            Toast.makeText(getApplicationContext(), "form number should contain 5 digit.", Toast.LENGTH_LONG).show();

            binNo.requestFocus();
            return false;
        }else if (grossQuantity.getText().toString().isEmpty() || grossQuantity.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the gross quantity.", Toast.LENGTH_LONG).show();

            grossQuantity.requestFocus();
            return false;
        }else if (deductionQuantity.getText().toString().isEmpty() || deductionQuantity.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the deduction quantity.", Toast.LENGTH_LONG).show();

            deductionQuantity.requestFocus();
            return false;
        }
        else if (juteValue.getText().toString().isEmpty() || juteValue.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the jute value.", Toast.LENGTH_LONG).show();

            juteValue.requestFocus();
            return false;
        }
        else if (gEd1.getText().toString().isEmpty() || gEd1.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the value of Grade 1.", Toast.LENGTH_LONG).show();

            gEd1.requestFocus();
            return false;
        }
        else if (gEd2.getText().toString().isEmpty() || gEd2.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the value of Grade 2.", Toast.LENGTH_LONG).show();

            gEd2.requestFocus();
            return false;
        }
        else if (gEd3.getText().toString().isEmpty() || gEd3.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the value of Grade 3.", Toast.LENGTH_LONG).show();

            gEd3.requestFocus();
            return false;
        }
        else if (gEd4.getText().toString().isEmpty() || gEd4.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the value of Grade 4.", Toast.LENGTH_LONG).show();

            gEd4.requestFocus();
            return false;
        }
        else if (gEd5.getText().toString().isEmpty() || gEd5.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the value of Grade 5.", Toast.LENGTH_LONG).show();

            gEd5.requestFocus();
            return false;
        }

        else if ((gEd6.getText().toString().isEmpty() || gEd6.getText().toString().trim().equals(""))
                && (juteVarietySelected.equals("Tossa") || juteVarietySelected.equals("White") ||
                juteVarietySelected.equals("Bimli")|| juteVarietySelected.equals("Mesta")) ) {
            Toast.makeText(getApplicationContext(), "Please Enter the value of Grade 6.", Toast.LENGTH_LONG).show();

            gEd6.requestFocus();
            return false;
        }
        else if ((gEd7.getText().toString().isEmpty() || gEd7.getText().toString().trim().equals(""))
                && (juteVarietySelected.equals("Tossa") || juteVarietySelected.equals("White") ) ) {
            Toast.makeText(getApplicationContext(), "Please Enter the value of Grade 7.", Toast.LENGTH_LONG).show();

            gEd7.requestFocus();
            return false;
        }
        else if ((gEd8.getText().toString().isEmpty() || gEd8.getText().toString().trim().equals(""))
                && (juteVarietySelected.equals("Tossa") || juteVarietySelected.equals("White") ) ) {
            Toast.makeText(getApplicationContext(), "Please Enter the value of Grade 8.", Toast.LENGTH_LONG).show();

            gEd8.requestFocus();
            return false;
        }

        return  true;
    }


    public  void autoCalculatedFields(){
        boolean flag = false;
        double netQ=0.0;
        try{

            netQ= (Double)( Double.parseDouble(grossQuantity.getText().toString())- Double.parseDouble(deductionQuantity.getText().toString()) );

            netQuantity.setText(String.valueOf(netQ));
            //Toast.makeText(getApplicationContext(), "hi flag"+flag, Toast.LENGTH_SHORT).show();
           //Toast.makeText(getApplicationContext(), "hi flag 1"+flag+netQ, Toast.LENGTH_SHORT).show();
           double avgR= (Double)( Double.parseDouble(juteValue.getText().toString())/netQ );
           avgRate.setText(String.valueOf(avgR));
           //Toast.makeText(getApplicationContext(), "hi flag"+flag, Toast.LENGTH_SHORT).show();


        estGradeComp.setText(String.valueOf(calculateGrade()));
    }catch (Exception e){
        Toast.makeText(getApplicationContext(), e.getMessage()+"merror"+netQ, Toast.LENGTH_SHORT).show();

    }

    }
    public int calculateGrade(){
        int totalGrade=0;
try {

    if ((juteVarietySelected.equals("Bimli") || juteVarietySelected.equals("Mesta"))) {

        totalGrade = (Integer) (totalGrade
                + Integer.parseInt(gEd1.getText().toString()) + Integer.parseInt(gEd2.getText().toString())
                + Integer.parseInt(gEd3.getText().toString()) + Integer.parseInt(gEd4.getText().toString())
                + Integer.parseInt(gEd5.getText().toString()) + Integer.parseInt(gEd6.getText().toString())

        );
        allGrade= gEd1.getText().toString()+","+gEd2.getText().toString()+","+gEd3.getText().toString()+","
                +gEd4.getText().toString()+","+gEd5.getText().toString()+"," +gEd6.getText().toString();


    } else if ((juteVarietySelected.equals("Tossa") || juteVarietySelected.equals("White"))) {

        totalGrade = (Integer) (totalGrade
                + Integer.parseInt(gEd1.getText().toString()) + Integer.parseInt(gEd2.getText().toString())
                + Integer.parseInt(gEd3.getText().toString()) + Integer.parseInt(gEd4.getText().toString())
                + Integer.parseInt(gEd5.getText().toString()) + Integer.parseInt(gEd6.getText().toString())
                + Integer.parseInt(gEd7.getText().toString()) + Integer.parseInt(gEd8.getText().toString())

        );
        allGrade= gEd1.getText().toString()+","+gEd2.getText().toString()+","+gEd3.getText().toString()+","
                +gEd4.getText().toString()+","+gEd5.getText().toString()+","+gEd6.getText().toString()+","
                +gEd7.getText().toString()+","+gEd8.getText().toString();
    } else {
        totalGrade = (Integer) (totalGrade
                + Integer.parseInt(gEd1.getText().toString()) + Integer.parseInt(gEd2.getText().toString())
                + Integer.parseInt(gEd3.getText().toString()) + Integer.parseInt(gEd4.getText().toString())
                + Integer.parseInt(gEd5.getText().toString())

        );
        allGrade= gEd1.getText().toString()+","+gEd2.getText().toString()+","+gEd3.getText().toString()+","
                +gEd4.getText().toString()+","+gEd5.getText().toString();
       /* Toast.makeText(getApplicationContext(), juteVarietySelected.equals("Bimli")+"k"+juteVarietySelected+"gd1"+gEd1.getText().toString()+","+"gd2"+gEd2.getText().toString()+","
                +"gd3"+gEd3.getText().toString()+","
                +"gd4"+gEd4.getText().toString()+","+"gd5"+gEd5.getText().toString()+","+ "gd6"+gEd6.getText().toString(), Toast.LENGTH_SHORT).show();
*/
    }
}catch (Exception e){
    Toast.makeText(getApplicationContext(), "erroir"+e.getMessage(), Toast.LENGTH_SHORT).show();}
        //Toast.makeText(getApplicationContext(), "hi flag"+totalGrade, Toast.LENGTH_SHORT).show();

        return totalGrade;
    }


}

