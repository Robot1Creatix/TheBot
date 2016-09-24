package com.creatix.TheBot;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import com.creatix.TheBot.chat.BMessageManager;
import com.creatix.TheBot.objects.Classification;
import com.creatix.TheBot.objects.Command;
import com.creatix.TheBot.objects.Subject;

import com.creatix.TheBot.utils.MiscUtils;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;

public class CommandManager {

	public static void registerCommand(Command command) {
		if (command.name.length() <= 0)
			return;
		if (!SystemCore.commands.contains(command)) {
			SystemCore.commands.add(command);
			System.out.println("Command '" + command.name + "' was success added.");
		}
	}
	public static void init() {

		registerCommand(new Command("monitor", "mon", "Monitor subject by id or name", true, (msg, args, guild) -> {
			if (args.length >= 1) {
				if (args[0].startsWith("*")) {
					//MonitorOptions
					String option = args[0].substring(1);
					System.out.println("options" + option);
					switch (option) {
						case ("channel"):
						case ("chan"): {
							List<User> u = guild.getVoiceStatusOfUser(msg.getAuthor()).getChannel().getUsers();
							System.out.println("chan");
							String ret = "";
							for (User us : u) {
								Subject subj = new Subject(us);
								ret += "```" + subj.Monitor(guild) + "```";
							}
							BMessageManager.sendMessage(msg.getChannel(), ret);
							break;
						}
						case ("server"):
						case ("serv"): {
							break;
						}
						case ("registered"):
						case ("reg"): {
							break;
						}
						default: {
						}
					}
				} else {
					List<User> list = new ArrayList<User>();
					list.addAll(guild.getUsersByName(args[0]));
					list.add(guild.getUserById(args[0]));
					if (list.isEmpty()) {
						BMessageManager.reply(msg, "User not fount with ID/Name '" + args[0] + "'.");
						return;
					} else {
						Subject subj = new Subject(list.get(0));
						BMessageManager.sendMessage(msg.getChannel(), "```" + subj.Monitor(guild) + "```");
					}
				}
			} else {
				return;
			}
		}));
		registerCommand(new Command("classification", "class", "Show all users with [args[0]] classification", true, (msg, args, guild) -> {
			if (args.length >= 1) {
				if (UserManager.getClassByName(args[0]) == null)
					return;
				List<String> users = new ArrayList<>();
				for (String id : UserManager.Users) {
					if (UserManager._Humans.get(id).equals(UserManager.getClassByName(args[0])))
						users.add(id);
				}
				if (users.isEmpty()) {
					BMessageManager.reply(msg, "Not fount users.");
					return;
				}
				String ret = "Searching...\n";
				ret += "Search arguments: Classification[" + args[0] + "]\n";
				for (String id : users) {
					User _s = guild.getUserById(id);
					ret += "  - " + _s.getUsername() + " : " + _s.getOnlineStatus().name() + "\n";
				}
				BMessageManager.sendMessage(msg.getChannel(), "```" + ret + "```");
			} else {
				return;
			}
		}));
		registerCommand(new Command("commands", "help", "Show all commands", false, (msg, args, guild) -> {
			if (args.length >= 1) {
				if (getCommandByName(args[0]) == null) {
					commandList(msg);
					return;
				} else {
					Command c = getCommandByName(args[0]);
					String ret = "Searching information about command '" + args[0] + "'...\n";
					ret += "Execute Name: " + c.name + "\n";
					ret += "Short Execute Name: " + c.auxname + "\n";
					ret += "AccessLevel:  " + (c.admin ? 0.5F : 0.0F) + "[" + (c.admin ? "ADMIN" : "USERS") + "]\n";
					ret += "Description: \n" + "  " + c.desc + "\n";
					BMessageManager.sendMessage(msg.getChannel(), "```" + ret + "```");
				}
			} else {
				commandList(msg);
			}
		}));
		registerCommand(new Command("status", "Show bot status", true, (msg, args, guild) -> {
			int mb = 1024 * 1024;
			Runtime runtime = Runtime.getRuntime();
			long used = (runtime.totalMemory() - runtime.freeMemory()) / mb;
			long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory
					.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
			BMessageManager.reply(msg, "I\'am  alive\nI\'am using " + used + "mb RAM of " + memorySize / mb + "mb!\nI\'am working with " + Thread.currentThread().getId() + " thread");
		}));
		registerCommand(new Command("shutdown", "kill", "Kill the bot", true, (msg, args, guild) -> {
			if (!UserManager.getClassification(msg.getAuthor()).equals(UserManager.ADMIN)) {
				BMessageManager.reply(msg, "[ ! ] Shutdown Imminent\nEmploying Countermeasures...");
				UserManager.SetClassification(msg.getAuthor(), UserManager.THREAT);
				guild.getUserById(SystemCore.lang.getLocalizedName("admin")).getPrivateChannel().sendMessage("!!Shutdown attempt detected!!\n" + new Subject(msg.getAuthor()).Monitor(guild));
				return;
			} else {
				SystemCore.bot.shutdown();
			}
		}));
		registerCommand(new Command("reorganize", "reorg", "Reorganize classification(args[1]) for user(args[0])", true, (msg, args, guild) -> {
			if (args.length < 2)
				return;
			Classification cl = UserManager.getClassByName(args[1]);
			User us = guild.getUsersByName(args[0]).get(0);
			User usr = guild.getUserById(args[0]);
			User user;
			if (cl == null)
				return;
			if (us != null) {
				user = us;
			} else if (usr != null) {
				user = usr;
			} else {
				return;
			}
			if (cl.accessLevel > UserManager.getClassification(msg.getAuthor()).accessLevel)
				return;
			if (UserManager.getClassification(msg.getAuthor()).accessLevel < UserManager.getClassification(user).accessLevel)
				return;
			UserManager.SetClassification(user, cl);
			BMessageManager.sendMessage(msg.getChannel(), "```Classification was edited for " + guild.getEffectiveNameForUser(user) + " to " + cl.className + " by " + guild.getEffectiveNameForUser(msg.getAuthor()) + "```");
		}));
		registerCommand(new Command("godmod", "gm", "Get admin access to 1 minute by a secret key", false, (msg, args, guild) -> {
			if (args.length < 1)
				return;
			if (UserManager.getClassification(msg.getAuthor()).equals(UserManager.ADMIN))
				return;
			if (args[0].equals(SystemCore.key)) {
				Classification old = UserManager.getClassification(msg.getAuthor());
				UserManager.SetClassification(msg.getAuthor(), UserManager.ADMIN);
				BMessageManager.sendMessage(msg.getChannel(), "```Godmode turned on to " + msg.getAuthor().getUsername() + " on 1 minute...```");
				MiscUtils.generateSecretKey();
				new Thread(() -> {
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					UserManager.SetClassification(msg.getAuthor(), old);
					BMessageManager.sendMessage(msg.getChannel(), "```Godmode turned off for " + msg.getAuthor().getUsername() + ".```");
				}).start();
			} else {
				System.out.println("Not valid key.");
			}
		}));
		registerCommand(new Command("sl", "sl", "The secret command, you never know what it doing!", true, (msg, args, guild) -> {
			if (!MiscUtils.isAdmin(msg.getAuthor()))
				return;
			if (SystemCore.lang.getLocalizedName("sm").equals("sm"))
				return;
			BMessageManager.sendMessage(msg.getChannel(), SystemCore.lang.getLocalizedName("sm"));
		}));
		registerCommand(new Command("irrelevant_protocol", "Execute protocol", true, (msg, args, guild) -> {
			IrrelevantProtocol protocol = new IrrelevantProtocol(msg.getAuthor(),msg, guild, args);
			protocol.start();
		}));
	}


	private static void commandList(Message msg) {
		String ret = "";
		if (SystemCore.commands.isEmpty()) {
			ret += "No commands registered.";
		} else {
			ret += "Commands: \n";
			for (Command cmd : SystemCore.commands) {
				ret += " - " + cmd.name + "\n";
			}
			BMessageManager.sendMessage(msg.getChannel(), "```" + ret + "```");
		}
	}

	public static Command getCommandByName(String name) {
		for (Command c : SystemCore.commands) {
			if (name.toLowerCase().equals(c.name) || name.toLowerCase().equals(c.auxname))
				return c;
		}
		return null;
	}
}
