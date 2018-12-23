/*
 * Developed by Daniel Chuev.
 * Last modified 23.12.18 13:41.
 * Copyright (c) 2018. All Right Reserved.
 */

import java.util.ArrayList;

/**
 * @author Daniel Chuev
 */
public class BlockChain {
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

    synchronized static ArrayList<Block> getBlockchain() {
        return blockchain;
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

    //    static Boolean isChainValid() {
//        Block currentBlock;
//        Block previousBlock;
//        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
//
//        //loop through blockchain to check hashes:
//        for(int i=1; i < blockchain.size(); i++) {
//            currentBlock = blockchain.get(i);
//            previousBlock = blockchain.get(i-1);
//            //compare registered hash and calculated hash:
//            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
//                System.out.println("Current Hashes not equal");
//                return false;
//            }
//            //compare previous hash and registered previous hash
//            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
//                System.out.println("Previous Hashes not equal");
//                return false;
//            }
//            //check if hash is solved
//            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
//                System.out.println("This block hasn't been mined");
//                return false;
//            }
//
//        }
//        return true;
//    }

}
