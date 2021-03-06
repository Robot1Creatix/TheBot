package com.creatix.TheBot.objects;

import com.creatix.TheBot.SystemCore;
import com.creatix.TheBot.UserManager;

import com.sun.istack.internal.NotNull;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;


import java.util.ArrayList;
import java.util.List;

public class Subject {

	protected User user;
	public String meta;
	public Classification classif;
	public String un, id;
	public Number number;
	
	public Subject(@NotNull User user){
		this.user = user;
		this.classif = UserManager.getClassification(user);
		this.meta = SystemCore.lang.getLocalizedName("meta." + user.getId() + ".text").equals("meta." + user.getId() + ".text") ? "MISSING DATA" : SystemCore.lang.getLocalizedName("meta." + user.getId() + ".text");
		this.id = (classif.equals(UserManager.ADMIN) ||  classif.equals(UserManager.ASSET) ? "Redacted" : user.getId());
		this.un = (classif.equals(UserManager.ADMIN) ||  classif.equals(UserManager.ASSET)? "Redacted" : user.getUsername());
		this.number = new Number(user.getId());
	}

	public User getUser() {
		return user;
	}
	
	public String Monitor(Guild guild){
		String ret = "Monitoring subject....\n";
		switch(classif.className.toLowerCase()){
			case ("irrelevant"): break;
			case ("asset"): ret += "Asset Identified"; break;
			case ("admin"): ret += "Admin Identified"; break;
			case ("threat"): ret += "Threat Confirmed"; break;
			case ("system"): ret += "Private Inforamtion Request Detected. Application Response..."; return ret;
			default: ret += "Identification failed. Internal Error"; return ret;
		}
		ret += "\n";
		ret += "-----------------------------------\n";
		ret += "Alias:          "+guild.getEffectiveNameForUser(user)+"\n";
		ret += "-----------------------------------\n";
		ret += "UserID:         "+id+"\n";
		ret += "Username:       "+un+"\n";
		ret += "UIN:            "+ number.getCoded()+"\n";
		ret += "Projection:     "+classif.className+"\n";
		ret += "AccessLevel:    "+classif.accessLevel+"\n\n";
		ret += "Conclusion:     "+classif.conclusion+"\n\n";
		ret += "OnlineStatus:   "+user.getOnlineStatus().name()+"\n";
		ret += "Location:       "+(UserManager.getChannelByUser(user, guild) == null ? "Locating failed" : UserManager.getChannelByUser(user, guild).getName());
		return ret;
	}
	
	public static Subject getSubjectByUser(User user){
		return new Subject(user);
	}
	public static List<Subject> getSubjects(List<User> user){
		List<Subject> list = new ArrayList<>();
		for(User u : user){
			list.add(getSubjectByUser(u));
		}
		return list;
	}
}
