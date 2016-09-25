package com.creatix.TheBot.objects;

import net.dv8tion.jda.entities.User;

/**
 * Created by creatix on 9/25/16.
 */
public class RelevantNumber extends IrrelevantNumber {
    public RelevantNumber(User user, User threat) {
        super(user, threat);
    }
}
