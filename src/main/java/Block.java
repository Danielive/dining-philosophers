/*
 * Developed by Daniel Chuev on 22.12.18 22:06.
 * Last modified 22.12.18 22:05.
 * Copyright (c) 2018. All right reserved.
 */

import java.text.SimpleDateFormat;
import java.util.Date;

class Block {

    String hash;
    String previousHash;
    private String data; //our data will be a simple message.
    private long timeStamp; //as number of milliseconds since 1/1/1970.
    private int nonce;
    final private static SimpleDateFormat formatDate = new SimpleDateFormat("ss:SSS");

    //Block Constructor.
    Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash(); //Making sure we do this after we set the other values.
    }

    //Calculate new hash based on blocks contents
    String calculateHash() {
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
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block |" + data + "| mined : " + formatDate.format(new Date()) + " | hash: " + hash);
    }
}