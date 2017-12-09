package com.example.api.impl;

import com.example.api.IInvoke;

/**
 * @author yangxiuchu
 * @DATE 2017/12/8 15:08
 */
public class DefaultInvokeImpl implements IInvoke {
    @Override
    public void invoke() {
        System.out.println("invoke...");
    }
}
