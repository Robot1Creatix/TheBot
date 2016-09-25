package com.creatix.TheBot;

import com.creatix.TheBot.chat.BMessageManager;
import com.creatix.TheBot.objects.Classification;
import com.creatix.TheBot.objects.IrrelevantNumber;
import com.creatix.TheBot.objects.RelevantNumber;
import com.creatix.TheBot.objects.Subject;
import com.creatix.TheBot.utils.MiscUtils;
import net.dv8tion.jda.OnlineStatus;
import net.dv8tion.jda.entities.*;
import net.dv8tion.jda.entities.impl.UserImpl;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by creatix on 9/24/16.
 */
public class IrrelevantProtocol extends Thread {

    public User user;

    protected Message msg;

    protected Guild guild;

    protected List<String> params;

    public IrrelevantProtocol(User user, Message msg, Guild gUild, String[] args){
        this.setName("irrelevantProtocolThread");
        this.user = user;
        this.msg = msg;
        this.guild = gUild;
        this.params = Arrays.asList(args);
    }


    @Override
    public void run(){
        try {
            BMessageManager.sendMessage(msg.getChannel(), "```Starting irrelevant_protocol <"+ MiscUtils.getArrayAsString(params.toArray(new String[params.size()]), ";")+">```");
            Thread.sleep(1500);
            BMessageManager.sendMessage(msg.getChannel(), "```Searching users...```");
            Thread.sleep(2500);
            List<User> users = guild.getUsers();
            List<Subject> subjs = new ArrayList<>();
            Thread.sleep(1000);
            List<Subject> subjects = Subject.getSubjects(guild.getUsers());
            BMessageManager.sendMessage(msg.getChannel(), "```Assessing threats...```");
            Thread.sleep(3800);
            List<User> threats = subjects.parallelStream().filter((s) -> s.classif.type == Classification.ClassType.Threat && s.getUser().getOnlineStatus() != OnlineStatus.OFFLINE).map((subj) -> subj.getUser()).collect(Collectors.toList());
            List<User> relevants = subjects.parallelStream().filter((s) -> s.classif.type == Classification.ClassType.Contingency && s.getUser().getOnlineStatus() != OnlineStatus.OFFLINE).map((subj) -> subj.getUser()).collect(Collectors.toList());
            List<User> ordinary = subjects.parallelStream().filter((s) -> s.classif.type == Classification.ClassType.Irrelevant && s.getUser().getOnlineStatus() != OnlineStatus.OFFLINE).map((subj) -> subj.getUser()).collect(Collectors.toList());
            if(threats.isEmpty()){
                BMessageManager.sendMessage(msg.getChannel(), "```Threats not detected.```\n");
                return;
            }
            if(ordinary.isEmpty()){
                BMessageManager.sendMessage(msg.getChannel(), "```Ordinary users not found. Ignoring threats.```");
            }
            List<IrrelevantNumber> nums = new ArrayList<>();
            for(User o : ordinary){
                VoiceChannel voice = guild.getVoiceStatusOfUser(o).getChannel();
                List<User> us = voice.getUsers();
                for(User u : us){
                    if(threats.contains(u)) {
                        nums.add(new IrrelevantNumber(o, u));
                        System.out.println(u.getUsername()+" ");
                    }
                    System.out.println(threats.size() + " "+o.getUsername());
                }
            }
            if(nums.isEmpty()){
                BMessageManager.sendMessage(msg.getChannel(), "```No Irrelevant numbers found```");
            }
            List<RelevantNumber> rnums = new ArrayList<>();
            for(User r : relevants){
                VoiceChannel voice = guild.getVoiceStatusOfUser(r).getChannel();
                List<User> us = voice.getUsers();
                for(User u : us){
                    if(threats.contains(u)){
                        if(r.getId().equals(SystemCore.lang.getLocalizedName("admin"))){
                            rnums.add(new RelevantNumber(r, u));
                        }
                    }
                }
            }

            if(!relevants.isEmpty()){
                if(!rnums.isEmpty()){
                    String ret = "";
                    for(RelevantNumber r : rnums){
                        ret += "```";
                        ret += "[ ! ] Relevant Number [ ! ]\n";
                        ret += "Threat for Admin Detected.\n";
                        ret += new Subject((User) r.reason.get(0)).Monitor(guild)+"\n";
                        ret += "Employing Countermeasures...\n";
                        UserManager.SetClassification((User) r.reason.get(0), UserManager.THREAT);
                        User threat = (User) r.reason.get(0);
                        guild.getManager().setNickname(threat, "ADMIN THREAT #0");
                        BMessageManager.sendMessage(msg.getChannel(),ret+"```");
                    }
                }
            }

            String ret = "";
            for(IrrelevantNumber n : nums){
                ret += "";
                ret += "```Irrelevant Number : "+ n.user.getId()+"```";
                if(params.contains("full_info")){
                    ret += "\n```"+new Subject(n.user).Monitor(guild)+"```\n";
                }
                ret += "```Threat: "+((User) n.reason.get(0)).getId()+"```";
                if(params.contains("full_info")){
                    ret += "\n```"+new Subject((User)n.reason.get(0)).Monitor(guild)+"```";
                }
            }
            BMessageManager.sendMessage(msg.getChannel(), ret);
        }catch (Exception e){
            e.printStackTrace();
            BMessageManager.sendMessage(msg.getChannel(), "```Failed start irrelevantProtocol<>. Force shutdowning.```");
            System.exit(-1);
        }
    }

}
