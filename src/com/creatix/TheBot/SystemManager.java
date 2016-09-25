package com.creatix.TheBot;

import java.text.Format;
import java.util.*;

import com.creatix.TheBot.chat.BMessageManager;
import com.creatix.TheBot.objects.Subject;
import com.creatix.TheBot.utils.MiscUtils;

import net.dv8tion.jda.MessageBuilder;
import net.dv8tion.jda.OnlineStatus;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.entities.VoiceChannel;
import net.dv8tion.jda.events.ReadyEvent;
import net.dv8tion.jda.events.guild.GuildJoinEvent;
import net.dv8tion.jda.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.events.user.UserOnlineStatusUpdateEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

public class SystemManager extends ListenerAdapter {
	@Override
	public void onReady(ReadyEvent event) {
		VoiceChannel chan = event.getJDA().getVoiceChannels().parallelStream().filter((ch) -> ch.getUsers().parallelStream().anyMatch(MiscUtils::isAdmin)).toArray(VoiceChannel[]::new)[0];
		event.getJDA().getAudioManager(chan.getGuild()).openAudioConnection(chan);
	}

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {

		BMessageManager.sendMessage(event.getUser().getPrivateChannel(), "```" +
				"Привет, "+event.getGuild().getEffectiveNameForUser(event.getUser())+"! Ты зашёл на сервер 'Pentagon', а значит ты был преглашён моим админом, в противном случае я тебя забаню!\n" +
				"Если ты и впредь хочешь здесь находиться, то тебе предётся усвоить несколько правил, что бы не стать мне или кому-либо ещё угрозой!\n" +
				"1. Не чавкай в микрофон, если с тобой в канале есть ещё кто-то!\n" +
				"2. Не пытайся спорить с Ботами, которых здесь два, я и PBBot.\n" +
				"3. Если вдруг ты попробуешь нанести какой то вред системе либо админу - ты будешь накан!\n" +
				"4. Не давать заведома ложные советы/информацию." +
				"И да, список комманд,которые ты сможешь выполнять, можно узнать набраз /help!" +
				"Ну и напоследок, теперь ты зарегестрирован в моей системе, тебе присвоен номер и классификация, если админ не нашаманил в моём коде либо в консоли - ты Irrelevant.\n" +
				"Вот немного информации и тебе...```"
				+"```"+new Subject(event.getUser())+"```");

		if(UserManager.getClassification(event.getUser()).equals(UserManager.THREAT)){
			event.getGuild().getUserById(SystemCore.lang.getLocalizedName("admin")).getPrivateChannel().sendMessage(new Subject(event.getUser()).Monitor(event.getGuild()));
		}
	}
}
