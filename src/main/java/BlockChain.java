/*
 * Developed by Daniel Chuev.
 * Last modified 24.12.18 1:37.
 * Copyright (c) 2018. All Right Reserved.
 */

import java.util.ArrayList;

/**
 * @author Daniel Chuev
 */
class BlockChain {
    static ArrayList<Block> blockchain = new ArrayList<>();
    private static String hashBlockChain = "";

    synchronized static void addBlock(Block newBlock) {
        blockchain.add(newBlock);
        Manager.getPhilosophers().get(newBlock.getNumbPhil()).setDined(true);
        hashBlockChain = StringUtil.applySha256(blockchain.toString());

        System.out.println("*********************CURRENT*BLOCKCHAIN*****************");
        print();
        System.out.println("*************************THE*END************************");
    }

    static void clearBlockChain() {
        blockchain.clear();
    }

    synchronized static String getHashBlockChain() {
        return hashBlockChain;
    }

    private static void print() {
        for (int i = 0; i < blockchain.size(); i++) {
            blockchain.get(i).print();
            if (i < blockchain.size()-1) System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
        }
    }
}
