package com.creatix.TheBot.audio;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.creatix.TheBot.SystemCore;

import net.dv8tion.jda.audio.player.FilePlayer;
import net.dv8tion.jda.audio.player.URLPlayer;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.entities.VoiceChannel;
import net.dv8tion.jda.events.ReadyEvent;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.events.voice.VoiceJoinEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;
import net.dv8tion.jda.managers.AudioManager;

import static com.creatix.TheBot.SystemCore.bot;
import static com.creatix.TheBot.chat.BMessageManager.reply;

public class BAudioManager extends ListenerAdapter{
	
    public void onReady(ReadyEvent event) {
    	
    }
    public void onVoiceJoin(VoiceJoinEvent event) {
    	if(event.getUser().getId() == SystemCore.bot.getSelfInfo().getId())
    	{
			try {
	    		URLPlayer player;
				player = new URLPlayer(SystemCore.bot, new URL("http://projectbronze.comli.com/test/cyhm.mp3"));
				event.getGuild().getAudioManager().setSendingHandler(player);
				player.setVolume(0.025F);
	    		player.play();
			} catch (IOException | UnsupportedAudioFileException e) {
				e.printStackTrace();
			}
    	}
    }
    
}
