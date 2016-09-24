package com.creatix.TheBot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.creatix.TheBot.objects.Classification;
import com.creatix.TheBot.objects.Classification.ClassType;

import com.sun.istack.internal.NotNull;
import net.dv8tion.jda.entities.*;
import net.dv8tion.jda.events.ReadyEvent;
import net.dv8tion.jda.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

public class UserManager extends ListenerAdapter{
	
	public static HashMap<String, Classification> _Humans;
	public static List<String> Users;
	public static final void InitializeAssets(){
		_Humans = new HashMap<String, Classification>();
		Users = new ArrayList<String>();
		Add("146275563022188544", ADMIN);
		Add("225611033509756928", SYSTEM);
		Add("123471451750793219", ASSET);
		Add("222052803013509131", IRRELEVANT);
		Add("179137236783071232", ASSET);
	}

	public static final Classification IRRELEVANT = new Classification("Irrelevant", 0.0F, Classification.ClassType.Irrelevant, "Ignore."),
										ASSET = new Classification("Asset", 0.5F, ClassType.Contingency, "Protection"),
										ADMIN = new Classification("Admin", 1F, ClassType.Contingency, "Protection"),
										SYSTEM = new Classification("System", 1.5F, ClassType.Contingency, "Redacted"),
										THREAT = new Classification("Threat", 0.0F, ClassType.Threat, "Eliminate");
										
	
	protected static void Add(User user, Classification c){
		if(!_Humans.containsKey(user.getId())){
			_Humans.put(user.getId(), c);
			Users.add(user.getId());
			System.out.println("User '" + user.getUsername() + "' added to list with classification "+c.className+"; Access Level : "+c.accessLevel);
		}
	}
	protected static void Add(String id, Classification c){
		if(!_Humans.containsKey(id)){
			_Humans.put(id, c);
			Users.add(id);
			System.out.println("User '" + id + "' added to list with classification "+c.className+"; Access Level : "+c.accessLevel);
		}
	}
	public static void SetClassification(User user, Classification classification){
		if(!_Humans.containsKey(user.getId())) {
			_Humans.put(user.getId(), classification);
		}else{
			_Humans.replace(user.getId(), classification);

		}

	}
	@Override
    public void onReady(ReadyEvent event) {
        List<User> _l = event.getJDA().getUsers();
        for(User r : _l) {
            Add(r, (r.isBot() ? THREAT : IRRELEVANT));
        }
    }
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event)
    {
        User u = event.getUser();
        Add(u, (u.isBot() ? THREAT : IRRELEVANT));
    }
    public static Classification getClassification(User user){
    	return _Humans.getOrDefault(user.getId(), IRRELEVANT);
    }
    public static Classification getClassByName(String name){
    	name = name.toLowerCase();
    	if(name.equals("irrelevant"))
    		return IRRELEVANT;
    	if(name.equals("asset"))
    		return ASSET;
    	if(name.equals("admin"))
    		return ADMIN;
    	if(name.equals("system"))
    		return SYSTEM;
    	if(name.equals("threat"))
    		return THREAT;
    	return null;
    	
    }
    public static Channel getChannelByUser(@NotNull User user, Guild guild){
		List<VoiceChannel> channels = guild.getVoiceChannels();
		List<Channel> ret = new ArrayList<>();
		for(VoiceChannel chan : channels){
			if(chan.getUsers().contains(user))
				ret.add(chan);
		}
		if(ret.size() != 0)
			return ret.get(0);
		List<TextChannel> texts = guild.getTextChannels();
		for(TextChannel chan : texts){
			if(chan.getUsers().contains(user))
				ret.add(chan);
		}
		if(ret.size() != 0)
			return ret.get(0);
		return null;
	}
}
