package com.creatix.TheBot.chat;

import static com.creatix.TheBot.SystemCore.bot;
import static com.creatix.TheBot.chat.BMessageManager.reply;

import com.creatix.TheBot.CommandManager;
import com.creatix.TheBot.SystemCore;
import com.creatix.TheBot.UserManager;
import com.creatix.TheBot.objects.Classification;
import com.creatix.TheBot.objects.Command;
import com.creatix.TheBot.utils.MiscUtils;

import net.dv8tion.jda.MessageBuilder;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.entities.VoiceChannel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;
import net.dv8tion.jda.managers.AudioManager;

import java.io.IOException;

import javax.jws.soap.SOAPBinding.Use;

public class BMessageManager extends ListenerAdapter {

	public static final String c = "/";
	
	public void onMessageReceived(MessageReceivedEvent event) {
		
		if(UserManager.getClassification(event.getAuthor()).equals(UserManager.THREAT)){
			event.getMessage().deleteMessage();
			sendMessage(event.getChannel(), new MessageBuilder().appendString("Threat activity detectd...").build());
			return;
		}
		
		Message msg = event.getMessage();
        String text = msg.getContent();
        if (text.startsWith(c)) {
            text = text.substring(c.length()).replaceAll("/\\s\\s+/g", " ");
            String[] t = text.split("\\s", 2);
            String name = t[0];
            String[] args = t.length == 1 ? new String[] {} : t[1].split("\\s");
            Command cm = CommandManager.getCommandByName(name);
            User sender = event.getAuthor();
            if (cm == null) {
                BMessageManager.reply(msg, "Unknown command, enter.... Just enter!");
            } else {
                System.out.printf("Performing command: '%s' with args: [%s]\n", name, MiscUtils.getArrayAsString(args, ", "));
                if (!cm.admin || MiscUtils.canExecuteCommand(cm, sender)) {
                	cm.action.performCommand(msg, args, event.getGuild());
                } else {
                	cm.noadminaction.performCommand(msg, args, event.getGuild());
                }
            }
        }
	}
	public static void sendMessage(MessageChannel cn, String msg){
		sendMessage(cn, new MessageBuilder().appendString(msg).build());
	}
	public static void sendMessage(MessageChannel cn, Message msg) {
		cn.sendMessage(msg);
	}

	public static void reply(MessageChannel cn, User sender, String text) {
		sendMessage(cn, new MessageBuilder().appendMention(sender).appendString(" " + text).build());
	}

	public static void reply(Message orig, String reply) {
		reply(orig.getChannel(), orig.getAuthor(), reply);
        System.out.println("[CHAT] [REPLY] ["+orig.getAuthor()+"] "+reply);
	}
}
