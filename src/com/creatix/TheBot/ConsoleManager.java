package com.creatix.TheBot;

import com.creatix.TheBot.utils.MiscUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by creatix on 9/24/16.
 */
public class ConsoleManager extends Thread{

    @Override
    public void run() {
        while(isAlive()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String b = null;
            try {
                b = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String command = b.split("\\s+")[0];
            String[] args;
            if (b.split("\\s+").length == 1) {
                args = new String[]{};
            } else {
                args = b.substring(command.length() + 1).split("\\s+");
            }
            System.out.println("'" + command + "', args '" + MiscUtils.getArrayAsString(args, ",")+"'");
            switch(command){
                case ("class"):case ("reorg"):{
                    if(args.length < 2){
                        return;
                    }
                    if(!UserManager._Humans.containsKey(args[0])){
                        return;
                    }
                    if(UserManager.getClassByName(args[1]) == null){
                        return;
                    }
                    UserManager.SetClassification(args[0], UserManager.getClassByName(args[1]));
                    break;
                }
            }
        }
    }
}
