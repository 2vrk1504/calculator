package com.vramakanth.calculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    double a; //Operand
    double b; //Operand
    boolean isNum;
    boolean isEqual; //If equal to is pressed.... because we do not want the new numbers to be merged w/ result
    boolean isOp;       //same for operations... we don't want the display to become zero everytime operation is done
    boolean isFirstTime;
    final int PLUS = 1;
    final int MINUS = 2;
    final int INTO = 3;
    final int DIVIDE = 4;
    int operation;
    Button dev;
    Handler mHandler;

    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_main);
        if(s == null){
            initiate();
        }else{
            a = s.getDouble("a");
            b = s.getDouble("b");
            isNum = s.getBoolean("isNum");
            isOp = s.getBoolean("isOp");
            isEqual = s.getBoolean("isEqual");
            isFirstTime = s.getBoolean("isFirstTime");
            ((TextView) findViewById(R.id.display)).setText(s.getString("text"));
        }

    }


    /*This function is for changing the colours of the button using Handler */
    public void initiate(){
        a = 0;
        b = 0;
        TextView display = (TextView) findViewById(R.id.display);
        display.setText("0");
        operation = PLUS;
        isNum = false;
        isEqual = false;
        isOp = false;
        isFirstTime = true;
        //This stuff is for fun!!!! Changing colours!!
        dev = (Button) findViewById(R.id.dev);
        mHandler = new Handler(){
            int i =0;
            public void handleMessage(Message msg){
                if(i<3){
                    i++;
                }else{
                    i = 0;
                }
                switch(i) {
                    case 0:{ dev.setBackgroundColor(getResources().getColor(R.color.red)); break; }
                    case 1:{ dev.setBackgroundColor(getResources().getColor(R.color.yellow)); break; }
                    case 2:{ dev.setBackgroundColor(getResources().getColor(R.color.blue)); break; }
                    case 3:{ dev.setBackgroundColor(getResources().getColor(R.color.green)); break; }
                }
            }
        };
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHandler.obtainMessage(1).sendToTarget();
            }
        }, 1000, 1000);
    }
    @Override
    public void onSaveInstanceState(Bundle s){
        s.putDouble("a", a);
        s.putDouble("b", b);
        s.putBoolean("isNum", isNum);
        s.putBoolean("isOp", isOp);
        s.putBoolean("isEqual", isEqual);
        s.putBoolean("isFirstTime", isFirstTime);
        s.putString("text", ((TextView)findViewById(R.id.display)).getText().toString());
    }

    public void no(View v){

        TextView display = (TextView) findViewById(R.id.display);
        String chSeq;
        if(display.getText()=="0" || (isEqual) || (isOp)){ //New values when zero entered, or when equal button pressed
            chSeq = "";
        }else{
            chSeq = display.getText() + "";
        }
        isNum = true;
        isOp = false;
        isEqual = false;
        int id = v.getId();
        switch(id){
            case R.id.no1:
                display.setText(chSeq +"1"); break;
            case R.id.no2:
                display.setText(chSeq +"2"); break;
            case R.id.no3:
                display.setText(chSeq +"3"); break;
            case R.id.no4:
                display.setText(chSeq +"4"); break;
            case R.id.no5:
                display.setText(chSeq +"5"); break;
            case R.id.no6:
                display.setText(chSeq +"6"); break;
            case R.id.no7:
                display.setText(chSeq +"7"); break;
            case R.id.no8:
                display.setText(chSeq +"8"); break;
            case R.id.no9:
                display.setText(chSeq +"9"); break;
            case R.id.no0:
                display.setText(chSeq +"0"); break;
            case R.id.noDot:
                display.setText(chSeq +"."); break;

        }
    }
    public void clear(View v){
        a = 0;
        b = 0;
        TextView display = (TextView) findViewById(R.id.display);
        display.setText("0");
        operation = PLUS;
        isNum = false;
        isEqual = false;
        isOp = false;
        isFirstTime = true;
    }
    public void equals(View v){
        if(isNum){
            isEqual = true;
            TextView display = (TextView) findViewById(R.id.display);
            a = Double.parseDouble(display.getText() + "");
            actualOp(operation);
            display.setText(b+"");
            operation = 0;
        }
    }
    public void op(View v){
        //This function only receives the button
        //clicked and some things that need to be processed
        //before actually performing the operation!!!
        if(isNum){
            isNum = false;
            isOp = true;
            TextView display = (TextView) findViewById(R.id.display);
            if(!isEqual) {
                double value = Double.parseDouble(display.getText() + "");
                a = value;
                actualOp(operation); //does the previous operation
            }
            isEqual = false;
            if(!isFirstTime){
                display.setText(b+"");
            }
            int id = v.getId();
            switch(id){
                case R.id.opplus:{
                    operation = PLUS;
                    break;
                }
                case R.id.opx:{
                    operation = INTO;
                    break;
                }
                case R.id.opdiv:{
                    operation = DIVIDE;
                    break;
                }
                case R.id.opmin:{
                    operation = MINUS;
                    break;
                }
            }
            isFirstTime = false;
        }
    }
    public void actualOp(int sign){
        switch(sign){
            case PLUS:{ b += a; break;}
            case MINUS:{ b= b-a; break;}
            case INTO: { b *= a; break;}
            case DIVIDE:{ b = b/a; break;}
            default:{b=a;}
        }
        Log.i("CALC", b + "");
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void moreAbout(View v){
        Intent intent = new Intent(this, Activity2.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        timer.cancel();
    }
}

