package com.example.threads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickYashwinButton(View view){

//      With handler we can specify the code that we want to run later. This way we will wait for 10 seconds where we will be able to press the button and then this code will implement
        final Handler handle = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                TextView yashwinText = (TextView) findViewById(R.id.yashwinText);
                yashwinText.setText("Nice job bruh");
            }
        };

        Runnable r = new Runnable() {
            @Override
            public void run() {     //Contains the code that we want to run in the background
                long futureTime = System.currentTimeMillis() + 10000;
                while(System.currentTimeMillis() < futureTime){
                    synchronized (this){        //Prevents multiple threads from bumping into each other or trying to do the same thing
                        try{
                            wait(futureTime - System.currentTimeMillis());
                        }
                        catch(Exception e){}
                    }
                }
                handle.sendEmptyMessage(0);
            }
        };

//      On using a thread the button will not freeze as before for 10 seconds
        Thread yashwinThread = new Thread(r);
        yashwinThread.start();



/* This part should not be inside the runnable object because the rule is when creating an app you never want to change any interface element to update the interface at all within a thread */
// That is done in a handler
//        TextView yashwinText = (TextView) findViewById(R.id.yashwinText);
//        yashwinText.setText("Nice job bruh");
    }

}
