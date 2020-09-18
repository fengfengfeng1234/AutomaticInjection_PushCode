package com.example.apt_demo;

import android.content.Intent;

/**
 * Created by yhj on 2015/9/18.
 */
public abstract class BasicMessage {


    public void dispose() {
    }

    public abstract String getJumpPath();

    public abstract Intent getIntent(boolean customMessageStyle);

    public void afterLogin() {

    }
}

