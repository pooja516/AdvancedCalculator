package com.example.pooja.hw_advcalc;

import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView txtScreen;
    String display = "";
    String currentOperator = "";
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtScreen = (TextView)findViewById(R.id.editText);
        txtScreen.setText(display);
    }

    private void updateScreen(){
        txtScreen.setText(display);
    }

    /**
     * The below function will be invoked on the click of the numbers from 0-9 which will display the pressed number.
     * @param v : The parameter takes the view the context the main activity view set at the start of the app.
     */
    public void onClickNumber(View v){
        if(result != ""){
            clear();
            updateScreen();
        }

        Button b  = (Button)v;
        display += b.getText();
        updateScreen();
    }

    /**
     * The method below will return a boolean value based on the operator pressed.
     * @param op : the operator pressed
     * @return
     */
    private boolean isOperator(char op){

        switch (op){
            case '+': return true;
            case '-': return true;
            case '*': return true;
            case '/': return true;
            default: return  false;
        }
    }

    /**
     * The below function will be invoked on the click of the operator which will display the pressed operation.
     * @param v : The parameter takes the view the context the main activity view set at the start of the app.
     */
    public  void onClickOperator(View v){
         if(display == "") return;

        Button b = (Button) v;

        if(result != ""){
            String _display = result;
            clear();
            display = _display;
        }

        if(currentOperator != ""){
            Log.d("CalcX",""+display.charAt(display.length()-1));

            if(isOperator(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1),b.getText().charAt(0));
                updateScreen();
                return;
            }else {
                getResult();
                display = result;
                result = "";
            }
            currentOperator = b.getText().toString();
        }

        display += b.getText();
        currentOperator = b.getText().toString();
        updateScreen();
    }

    /**
     * The clear function is the internal method to clear the values of display variables
     */
    private void  clear(){
        display = "";
        currentOperator = "";
        result = "";

    }

    /**
     * The below function will be invoked on the click of the CLR button which will clear the value step by step by one value at a time.
     * @param v : The parameter takes the view the context the main activity view set at the start of the app.
     */
    public void onClickClear(View v){
        String strTxt = txtScreen.getText().toString();

        strTxt = txtScreen.length() > 1 ? strTxt.substring(0,txtScreen.length() - 1) : "0";

        clear();
        //updateScreen();

        txtScreen.setText(strTxt);
    }

    /**
     * The below method will perform the operation based on the operator provided.
     * @param a : 1'st operand
     * @param b : 2'nd operand
     * @param op : operator
     * @return
     */
    public double operate(String a , String b , String op){

        switch (op){
            case "+" : return Double.valueOf(a) + Double.valueOf(b);
            case "-" : return Double.valueOf(a) - Double.valueOf(b);
            case "*" : return Double.valueOf(a) * Double.valueOf(b);
            case "/" : try{
                return Double.valueOf(a) / Double.valueOf(b);
            }catch (Exception e){
                Log.d("Calc" , e.getMessage());
            }
                default: return -1;
        }
    }

    /**
     * The results will be called from equals method who will actually operate on the two variables
     * @return : store the result in the global variable
     */
    private boolean getResult(){

        if(currentOperator == "") return false;
        String[] operation  = display.split(Pattern.quote(currentOperator));

        if(operation.length < 2) return false;
        result = String.valueOf(operate(operation[0],operation[1],currentOperator));
        return true;
    }

    /**
     * The below function will be invoked on the click of the Equal button which will display the result based on the operation
     * @param v : The parameter takes the view the context the main activity view set at the start of the app.
     */
    public void onClickEqual(View v){
        if(display == "") return;
        if(!getResult()) return;

        txtScreen.setText(display + "\n" + String.valueOf(result));
    }
}

