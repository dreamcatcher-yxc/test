package com.example.logback;

import ch.qos.logback.core.PropertyDefinerBase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampPropertyDefiner extends PropertyDefinerBase {

    private String pattern;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPropertyValue() {
        return new SimpleDateFormat(this.pattern).format(new Date());
    }
}
