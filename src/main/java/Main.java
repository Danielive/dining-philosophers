/*
 * Developed by Daniel Chuev on 22.12.18 22:06.
 * Last modified 22.12.18 16:39.
 * Copyright (c) 2018. All right reserved.
 */

import java.util.Date;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 * @author Daniel Chuev
 */
public class Main {

    private static Supplier<Integer> scanText = () -> {
        System.out.print("Enter number of philosophers: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    };

    public static void main(String args[]) throws InterruptedException {
        // new Manager().execute(scanText.get());

        //add our blocks to the blockchain ArrayList:

        System.out.println("Trying to Mine block 1... " + Philosopher.getFormatDate().format(new Date()));
        BlockChain.addBlock(new Block("Hi im the first block", "0"));

        System.out.println("Trying to Mine block 2... " + Philosopher.getFormatDate().format(new Date()));
        BlockChain.addBlock(new Block("Yo im the second block", BlockChain.blockchain.get(BlockChain.blockchain.size()-1).hash));

        System.out.println("Trying to Mine block 3... " + Philosopher.getFormatDate().format(new Date()));
        BlockChain.addBlock(new Block("Hey im the third block", BlockChain.blockchain.get(BlockChain.blockchain.size()-1).hash));

        System.out.println("\nBlockchain is Valid: " + BlockChain.isChainValid());

        String blockchainJson = StringUtil.getJson(BlockChain.blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);
    }
}