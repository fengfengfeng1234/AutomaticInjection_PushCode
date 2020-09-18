package com.example.apt_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.apt_annotation.BindView;
import com.example.apt_api.launcher.AutoBind;
import com.example.apt_demo.PushRouterManager;
import com.example.apt_demo.DemoMessage;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @BindView(value = R.id.test_textview)
    public TextView testTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final HashMap<String, String> map= new HashMap<String, String>();

        findViewById(R.id.code2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasicMessage basicMessage= PushRouterManager.getInstance().getGuide("111",map);
                basicMessage.dispose();
            }
        });

        findViewById(R.id.code3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasicMessage basicMessage= PushRouterManager.getInstance().getGuide("222",map);
                basicMessage.dispose();
            }
        });

        
    }
}
