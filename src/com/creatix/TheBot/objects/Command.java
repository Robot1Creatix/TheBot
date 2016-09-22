package com.creatix.TheBot.objects;

import com.creatix.TheBot.SystemCore;
import com.creatix.TheBot.chat.BMessageManager;

public class Command {
	
	public String name, auxname, desc;
	public boolean admin;
	public ICommandAction action;
	public ICommandAction noadminaction;
	
	public Command(String name, String auxName, String desc, boolean admin, ICommandAction action)
	{
		this.name = name;
		this.auxname = auxName;
		this.desc = SystemCore.lang.getLocalizedName(desc);
		this.admin = admin;
		this.action = action;
		this.noadminaction = (msg, args, guild) -> {
			BMessageManager.reply(msg, SystemCore.lang.getLocalizedName("notadmin.text"));
		};
	}
	public Command(String name, String desc, boolean admin, ICommandAction action)
	{
		this(name, name, desc, admin, action);
	}
	public Command(String name, boolean admin, ICommandAction action)
	{
		this(name, name, "", admin,action);
	}
	
}
