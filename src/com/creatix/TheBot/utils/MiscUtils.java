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
}
