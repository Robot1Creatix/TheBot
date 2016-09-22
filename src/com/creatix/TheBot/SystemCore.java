package com.creatix.TheBot;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import com.creatix.TheBot.audio.BAudioManager;
import com.creatix.TheBot.chat.BMessageManager;
import com.creatix.TheBot.objects.Command;
import com.creatix.langmanager.ExternalLangFile;

import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;

public class SystemCore {
	
	private static String _T;
	
	public static List<Command> commands;

	public static JDA bot;

	public static ExternalLangFile lang;

	public static void main(String[] args) throws IOException, URISyntaxException
	{
		lang = new ExternalLangFile("F:/Bot/lang.lang");
		UserManager.InitializeAssets();
		_T = lang.getLocalizedName("token");
		try {
			bot = new JDABuilder().setBotToken(_T).addListener(new BAudioManager()).addListener(new BMessageManager()).addListener(new UserManager()).addListener(new SystemManager()).buildBlocking();
			commands = new ArrayList<Command>();
			CommandManager.init();
		} catch (LoginException | IllegalArgumentException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}//Hello!