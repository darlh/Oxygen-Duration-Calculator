package com.darlheidenreich.o2cylinderdurationcalculator;

import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
//import android.util.DisplayMetrics;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.ViewGroup;
//import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
import android.widget.Spinner;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener{
    Double factor;
    private Spinner sizes_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sizes_spinner = (Spinner) findViewById(R.id.sizes_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sizes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizes_spinner.setAdapter(adapter);
        sizes_spinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        parent.getItemAtPosition(position);
        switch (position) {
            case 0:
                factor = 0.16;
                break;
            case 1:
                factor = 0.28;
                break;
            case 2:
                factor = 2.41;
                break;
            case 3:
                factor = 3.14;
                break;
            case 4:
                factor = 1.56;
                break;

        }
    }

    public void onNothingSelected(AdapterView<?> parent) {}

    //public void psiPopup(View view) {
        //LinearLayout viewGroup = (LinearLayout)findViewById(R.id.popupPsi);
        //LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View layout = layoutInflater.inflate(R.layout.popup_psi, viewGroup);
        //final PopupWindow psiPopupWindow = new PopupWindow();
        //psiPopupWindow.setContentView(layout);
        //psiPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //psiPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //psiPopupWindow.setFocusable(true);
        //psiPopupWindow.showAtLocation(layout, Gravity.NO_GRAVITY, 0, 0);

        //public boolean onTouchEvent(MotionEvent event){
            //if MotionEvent.ACTION_UP==1
                //psiPopupWindow.dismiss();
        //}

    //}

    public void calculateOxygen(View view){
        TextView result = (TextView)findViewById(R.id.result);
        TextView psiLabel = (TextView)findViewById(R.id.psiLabel);
        TextView litersLabel = (TextView)findViewById(R.id.litersLabel);
        EditText psi=(EditText)findViewById(R.id.psi);
        EditText liters=(EditText)findViewById(R.id.liters);
        String strPsi = psi.getText().toString();
        String strLiters = liters.getText().toString();
        litersLabel.setError(null);
        psiLabel.setError(null);
        if (TextUtils.isEmpty(strPsi)){
            psiLabel.requestFocus();
            //psiLabel.setError("This field cannot be left blank.");
            psiLabel.setError(getResources().getString(R.string.blank_validation));
            return;
        }
        else if (TextUtils.isEmpty(strLiters)){
            litersLabel.requestFocus();
            litersLabel.setError("This field cannot be left blank.");
            return;
        }
        else{
            Double pressure=Double.parseDouble(String.valueOf(psi.getText()));
            Double rate=Double.parseDouble(String.valueOf(liters.getText()));
            if (pressure<=200){
                result.setText(String.valueOf(0));
                psiLabel.requestFocus();
                psiLabel.setError("This field must be above the safe residual pressure of 200 psi.");
                return;
            }
            else if (rate==0){
                litersLabel.requestFocus();
                litersLabel.setError("This field cannot be set to zero.");
                return;
            }
            else{
                Double min = factor * (pressure - 200) / rate;
                String str = String.valueOf(min);
                result.setText(str);
            }
        }
    }

    public void resetCalculator(View view){
        TextView result=(TextView)findViewById(R.id.result);
        TextView psiLabel = (TextView)findViewById(R.id.psiLabel);
        TextView litersLabel = (TextView)findViewById(R.id.litersLabel);
        EditText psi=(EditText)findViewById(R.id.psi);
        EditText liters=(EditText)findViewById(R.id.liters);
        litersLabel.setError(null);
        psiLabel.setError(null);
        result.setText("");
        psi.setText("");
        psi.setHint("");
        liters.setText("");
        liters.setHint("");
    }



}

