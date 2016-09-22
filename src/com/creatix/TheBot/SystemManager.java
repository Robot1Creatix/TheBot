package com.creatix.TheBot;

import java.util.List;

import com.creatix.TheBot.objects.Subject;
import com.creatix.TheBot.utils.MiscUtils;

import net.dv8tion.jda.OnlineStatus;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.entities.VoiceChannel;
import net.dv8tion.jda.events.ReadyEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

public class SystemManager extends ListenerAdapter{
	@Override
	 public void onReady(ReadyEvent event) {
		VoiceChannel chan = event.getJDA().getVoiceChannels().parallelStream().filter((ch) -> ch.getUsers().parallelStream().anyMatch(MiscUtils::isAdmin)).toArray(VoiceChannel[]::new)[0];
		event.getJDA().getAudioManager(chan.getGuild()).openAudioConnection(chan);
	 }
	
}
