package com.unscramble.transactions.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
public class Util {

    private Environment environment;

    public Util() {

    }

    @Autowired
    public Util(Environment environment) {
        this.environment = environment;
    }

    public int compareDate(String timestamp) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(environment.getProperty("dateFormat"), Locale.ENGLISH);
        Date date = dateFormat.parse(timestamp);
        long difference = System.currentTimeMillis()- date.getTime();
        float daysBetween = (float) Math.ceil((difference / (1000*60*60*24)));
        return (int)daysBetween;
    }
}
