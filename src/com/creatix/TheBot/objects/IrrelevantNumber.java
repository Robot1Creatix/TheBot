package com.creatix.TheBot.objects;

import net.dv8tion.jda.entities.User;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by creatix on 9/24/16.
 */
public class IrrelevantNumber  {

    public List<Object> reason;
    public User user;

    public String time;

    public IrrelevantNumber(User user, Object ...reason){
        this.reason = Arrays.asList(reason);
        this.user = user;
        this.time = new SimpleDateFormat().format(Calendar.getInstance().getTime());
    }

}
