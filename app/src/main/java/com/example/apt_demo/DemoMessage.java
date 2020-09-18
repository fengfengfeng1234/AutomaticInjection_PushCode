package com.example.apt_demo;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.example.apt_annotation.RouterPushUri;

import java.util.Map;


@RouterPushUri(
        code = "111",
        codeDescribe = "我的出价 价格确认中"
)
public class DemoMessage extends PushRouterBasicMessage {



    public DemoMessage(Map<String, String> map) {
        super(map);
    }

    @Override
    public String getJumpPath() {
        return null;
    }

    @Override
    public Intent getIntent(boolean customMessageStyle) {
        return null;
    }

    @Override
    public void dispose() {
        Log.d("RouterPushUri","DemoMessage");
    }
}
