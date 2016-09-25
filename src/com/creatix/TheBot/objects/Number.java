package com.creatix.TheBot.objects;

/**
 * Created by creatix on 9/25/16.
 */
public class Number {

    protected String part1, part2, part3, part2_codded, part3_codded, full, coded;

    public Number(String id){
        this.part1 = id.substring(0, 3);
        this.part2 = id.substring(3, 5);
        this.part3 = id.substring(5, 9);
        this.part2_codded = "XX";
        this.part3_codded = "XXXX";
        this.full = part1+"-"+part2+"-"+part3;
        this.coded = part1+"-"+part2_codded+"-"+part3_codded;
    }

    public String getCoded() {
        return coded;
    }

    public String getFull() {
        return full;
    }
}
