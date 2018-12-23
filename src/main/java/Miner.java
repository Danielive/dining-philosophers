/*
 * Developed by Daniel Chuev.
 * Last modified 24.12.18 0:30.
 * Copyright (c) 2018. All Right Reserved.
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * @author Daniel Chuev
 */
class Miner {

    private int numbMiner;
    private ArrayList<Block> minerChain = new ArrayList<>();
    private int difficulty = 5;
    private String hashBlockChain = "";

    Miner(int number) {
        this.numbMiner = number+1;
    }

    void creation(Philosopher philosopher) {
        if (Objects.equals(hashBlockChain, BlockChain.getHashBlockChain()))
            addition(philosopher);
        else {
            blockChainToMinerChain();
            addition(philosopher);
        }

        checkStateBlockChain();
    }

    private void checkStateBlockChain() {
        if (Objects.equals(hashBlockChain, BlockChain.getHashBlockChain())) {
            BlockChain.addBlock(minerChain.get(minerChain.size() - 1));
            hashBlockChain = BlockChain.getHashBlockChain();
        }
        else {
            blockChainToMinerChain();
            System.out.println("Miner#" + numbMiner + " removed block");
        }
    }

    /**
     * 1. Chain is <strong>not</strong> empty, philosopher has <strong>not</strong> last number in list
     * 2. Chain is empty, philosopher has <strong>not</strong> last number in list
     * 3. Chain is <strong>not</strong> empty, philosopher has last number in list
     * 4. Chain is empty, philosopher has last number in list
     * @param philosopher
     */
    private void addition(Philosopher philosopher) {
        if (minerChain.size() != 0 && (philosopher.getNumber() + 1) < Manager.getForks().size()) {
            System.out.println("Miner#" + numbMiner + " creating block |" + philosopher.getName() + " takes forks " + (philosopher.getNumber() + 1) + " and " + (philosopher.getNumber() + 2) + "| : " + Philosopher.getFormatDate().format(new Date()));
            addBlock(new Block(philosopher.getName() + " takes forks " + (philosopher.getNumber() + 1) + " and " + (philosopher.getNumber() + 2), philosopher.getNumber(), BlockChain.blockchain.get(BlockChain.blockchain.size() - 1).hash));
        } else if ((philosopher.getNumber() + 1) < Manager.getForks().size()) {
            System.out.println("Miner#" + numbMiner + " creating block |" + philosopher.getName() + " takes forks " + (philosopher.getNumber() + 1) + " and " + (philosopher.getNumber() + 2) + "| : " + Philosopher.getFormatDate().format(new Date()));
            addBlock(new Block(philosopher.getName() + " takes forks " + (philosopher.getNumber() + 1) + " and " + (philosopher.getNumber() + 2), philosopher.getNumber(), "0"));
        } else if (minerChain.size() != 0) {
            System.out.println("Miner#" + numbMiner + " creating block |" + philosopher.getName() + " takes forks " + (philosopher.getNumber() + 1) + " and " + (1) + "| : " + Philosopher.getFormatDate().format(new Date()));
            addBlock(new Block(philosopher.getName() + " takes forks " + (philosopher.getNumber() + 1) + " and " + (1), philosopher.getNumber(), BlockChain.blockchain.get(BlockChain.blockchain.size() - 1).hash));
        } else {
            System.out.println("Miner#" + numbMiner + " creating block |" + philosopher.getName() + " takes forks " + (philosopher.getNumber() + 1) + " and " + (1) + "| : " + Philosopher.getFormatDate().format(new Date()));
            addBlock(new Block(philosopher.getName() + " takes forks " + (philosopher.getNumber() + 1) + " and " + (1), philosopher.getNumber(), "0"));
        }
    }

    private void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        minerChain.add(newBlock);
        System.out.println("Miner#" + numbMiner + " created block ↓ : " + Philosopher.getFormatDate().format(new Date()));
        newBlock.print();
    }

    private void blockChainToMinerChain() {
        minerChain.clear();
        minerChain.addAll(BlockChain.blockchain);
        hashBlockChain = BlockChain.getHashBlockChain();
    }

    void clearMinerChain() {
        minerChain.clear();
    }
}
