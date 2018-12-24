/*
 * Developed by Daniel Chuev.
 * Last modified 24.12.18 1:38.
 * Copyright (c) 2018. All Right Reserved.
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

class Block {

    String hash;
    private String previousHash;
    private String miner;
    private String data;
    private Integer numbPhil;
    private String timeStamp;
    private int nonce;

    Block(String miner, String data, Integer numbPhil, String previousHash) {
        this.miner = miner;
        this.data = data;
        this.numbPhil = numbPhil;
        this.previousHash = previousHash;
        this.timeStamp = new SimpleDateFormat("ss:SSS").format(new Date());
        this.hash = calculateHash();
    }

    // calculate new hash based on blocks contents
    private String calculateHash() {
        return StringUtil.applySha256(
                previousHash +
                        numbPhil +
                        timeStamp +
                        Integer.toString(nonce) +
                        miner +
                        data
        );
    }

    // increases nonce value until hash target is reached.
    void mineBlock(int difficulty) {
        String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0"
        while(!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
    }

    void print() {
        System.out.println(
                "__________________________BLOCK_________________________" +
                "\n|" + data + "|" +
                "\nprevious hash : " + (!Objects.equals(previousHash, "0") ? previousHash.substring(55) : previousHash) +
                "\ncurrent hash  : " + hash.substring(55) +
                "\ncreated : " + miner +
                "\n________________________________________________________");
    }

    Integer getNumbPhil() {
        return numbPhil;
    }
}