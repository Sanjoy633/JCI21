package com.surflex.jci;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViewPurchaseActivity extends AppCompatActivity {
    TextView textView;
    String txt="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_purchase);
        textView = (TextView) findViewById(R.id.viewPurchase);
        try{

            PurchaseDatabaseHandler purchaseDatabaseHandler = new PurchaseDatabaseHandler(this);
            List<PurchaseModel> purchaseDetails = purchaseDatabaseHandler.getAllPurchases();

            for(PurchaseModel purchase: purchaseDetails){

                txt = txt + purchase.getFormNo()+" "+purchase.getBasis()+" "+purchase.getImgPath()+" "+purchase.getImgPath()+"\r\n\r\n";

            }

        }catch (Exception e){
            Toast.makeText(this, "Message"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        textView.setText(txt);
    }
}
