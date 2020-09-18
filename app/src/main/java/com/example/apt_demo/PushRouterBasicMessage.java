package com.example.apt_demo;

import java.util.Map;

/**
 * Created by yhj on 2015/9/18.
 */
public abstract class PushRouterBasicMessage extends BasicMessage {

    Map<String, String> mMap;

    public PushRouterBasicMessage(Map<String, String> map) {
        this.mMap = map;
    }

}

