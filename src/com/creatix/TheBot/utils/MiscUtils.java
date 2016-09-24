package com.creatix.TheBot.utils;

import com.creatix.TheBot.UserManager;
import com.creatix.TheBot.objects.Command;
import com.creatix.TheBot.objects.Subject;

import net.dv8tion.jda.entities.User;

public class MiscUtils {
	public static String getArrayAsString(Object[] array, String format)
    {
        if(array.length == 0)
        {
            return "";
        }
        String ret = "";
        for(Object o : array)
        {
            ret += o + format;
        }
        return ret.substring(0, ret.length() - format.length());
    }
	public static boolean canExecuteCommand(Command cmd, User sender){
		return !cmd.admin || UserManager.getClassification(sender).accessLevel >= 0.5F;
	}
	public static boolean isAdmin(User user){
		return UserManager.getClassification(user).accessLevel == 1.0F;
	}

	public static String formatTimeSeconds(int seconds){
        String ret = "";
        if(seconds < 60)
            return ret = seconds+" seconds";
        int mins = 0, secs = seconds;
        while(secs / 60 >= 1.0F){
            mins++;
            secs-=60;
            System.out.println(mins);
        }
        ret += mins+" min "+secs+" seconds";
        return ret;
    }
    public static String formatTimeMinutes(int munutes){
        String ret = "";
        if(munutes < 60)
            return ret = munutes+" minutes";
        int hours = 0, mins = munutes;
        while(mins / 60 >= 1.0F){
            hours++;
            mins-=60;
            System.out.println(mins);
        }
        ret += hours+" hours "+mins+" minutes";
        return ret;
    }
}
