package com.creatix.TheBot.objects;

import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Message;

import java.io.IOException;

public interface ICommandAction {
	public void performCommand(Message sender, String[] args, Guild giuld);
}
