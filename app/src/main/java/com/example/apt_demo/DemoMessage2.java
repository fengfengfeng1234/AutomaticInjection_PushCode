package com.example.apt_demo;

import android.content.Intent;
import android.util.Log;

import com.example.apt_annotation.RouterPushUri;

import java.util.Map;


@RouterPushUri(
        code = "222",
        codeDescribe = "我的嗨起来"
)
public class DemoMessage2 extends PushRouterBasicMessage {



    public DemoMessage2(Map<String, String> map) {
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
        Log.d("RouterPushUri","DemoMessage2");
    }
}
