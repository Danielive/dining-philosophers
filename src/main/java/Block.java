/*
 * Developed by Daniel Chuev.
 * Last modified 23.12.18 13:41.
 * Copyright (c) 2018. All Right Reserved.
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

class Block {

    String hash;
    private String previousHash;
    private String data; //our data will be a simple message.
    private Integer numbPhil;
    private long timeStamp; //as number of milliseconds since 1/1/1970.
    private int nonce;
    final private static SimpleDateFormat formatDate = new SimpleDateFormat("ss:SSS");

    //Block Constructor.
    Block(String data, Integer numbPhil, String previousHash) {
        this.data = data;
        this.numbPhil = numbPhil;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash(); //Making sure we do this after we set the other values.
    }

    //Calculate new hash based on blocks contents
    private String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        data
        );
        return calculatedhash;
    }

    //Increases nonce value until hash target is reached.
    void mineBlock(int difficulty) {
        String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0"
        while(!hash.substring(0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
    }

    void print() {
        System.out.println(
                "________________________________________________________" +
                "\nBlock |" + data + "| mined : " + formatDate.format(new Date()) +
                "\nprevious hash : " + (!Objects.equals(previousHash, "0") ? previousHash.substring(55) : previousHash) +
                "\ncurrent hash  : " + hash.substring(55) +
                "\n________________________________________________________");
    }

    public Integer getNumbPhil() {
        return numbPhil;
    }
}