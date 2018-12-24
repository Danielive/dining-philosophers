/*
 * Developed by Daniel Chuev.
 * Last modified 24.12.18 1:38.
 * Copyright (c) 2018. All Right Reserved.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Daniel Chuev
 */
final class Manager {

    private static List<Philosopher> philosophers = new ArrayList<>();
    private static List<Philosopher> previewList = new ArrayList<>();
    private static List<Fork> forks = new ArrayList<>();
    private static List<Miner> miners = new ArrayList<>();
    private static boolean stateDined;

    void execute(final int count) {
        System.out.println("Number of philosophers: " + count);

        // set list philosophers
        for (int i = 0; i < count; i++) {
            philosophers.add(new Philosopher(i));
            forks.add(new Fork(i));
        }

        // set list miners
        for (int i = 0; i < 3; i++) {
            miners.add(new Miner(i));
        }

        while (true) {
            setStateDined(false);
            BlockChain.clearBlockChain();
            philosophers.forEach(Philosopher::clear);
            forks.forEach(Fork::clear);
            miners.forEach(Miner::clearMinerChain);

            while (!stateDined) {
                previewList.clear();

                for (Philosopher philosopher : philosophers)
                    if (choicePhilosopher(philosopher))
                        previewList.add(philosopher);

                Collections.shuffle(previewList);

                miners.stream().parallel().forEach(this::creation);

                philosophers.forEach(this::choiceRestorePhilosopher);

                checkDined(count);
            }
            System.out.println("**************************FINAL*************************");
        }
    }

    private void creation(Miner miner) {
        Philosopher philosopher;

        synchronized (this) {
            if (!Manager.getPreviewList().isEmpty()) {
                philosopher = Manager.getPreviewList().get(0);
                Manager.getPreviewList().remove(0);
            }
            else {
                for (Philosopher phil : philosophers) {
                    if (phil.getState() && phil.getTake())
                        previewList.add(phil);
                }
                Collections.shuffle(previewList);
                philosopher = previewList.get(0);
                previewList.clear();
            }
        }

        if (philosopher != null) miner.creation(philosopher);
    }

    private void checkDined(final int count) {
        int i = 0;

        for (Philosopher phil : Manager.getPhilosophers()) {
            if (phil.getDined()) {
                i++;
            }
        }

        if (i == count) setStateDined(true);
    }

    private static void setStateDined(boolean stateDined) {
        Manager.stateDined = stateDined;
    }

    private StatePhilosopher state = (one, two) -> {
        synchronized (this) {
            if ((Manager.getForks().get(one).isState() && Manager.getForks().get(two).isState()) &&
                    !(Manager.getPhilosophers().get(one).getState() && Manager.getPhilosophers().get(two).getState()) &&
                    !Manager.getPhilosophers().get(one).getDined() && !Manager.getPhilosophers().get(one).getTake()) {
                Manager.getForks().get(one).setState(false);
                Manager.getForks().get(two).setState(false);
                Manager.getPhilosophers().get(one).setState(true);
                Manager.getPhilosophers().get(two).setState(true);
                Manager.getPhilosophers().get(one).setTake(true);
                return true;
            }
            return false;
        }
    };

    private StatePhilosopher restore = (one, two) -> {
        if (!(Manager.getForks().get(one).isState() && Manager.getForks().get(two).isState()) &&
                (Manager.getPhilosophers().get(one).getState() && Manager.getPhilosophers().get(two).getState()) &&
                Manager.getPhilosophers().get(one).getTake()) {
            Manager.getForks().get(one).setState(true);
            Manager.getForks().get(two).setState(true);
            Manager.getPhilosophers().get(one).setState(false);
            Manager.getPhilosophers().get(two).setState(false);
            Manager.getPhilosophers().get(one).setTake(false);
            return true;
        }
        return false;
    };

    private Boolean isState(Integer oneFork, Integer twoFork) {return state.isReady(oneFork, twoFork);}

    private void doRestore(Integer oneFork, Integer twoFork) {restore.isReady(oneFork, twoFork);}

    private Boolean choicePhilosopher(Philosopher philosopher) {
        if ((philosopher.getNumber()+1) < Manager.getPhilosophers().size())
            return isState(philosopher.getNumber(), (philosopher.getNumber() + 1));
        else return isState(philosopher.getNumber(), 0);
    }

    private void choiceRestorePhilosopher(Philosopher philosopher) {
        if (philosopher.getState()) {
            if (philosopher.getNumber()+1 < Manager.getPhilosophers().size())
                doRestore(philosopher.getNumber(), philosopher.getNumber()+1);
            else
                doRestore(philosopher.getNumber(), 0);
        }
    }

    private synchronized static List<Philosopher> getPreviewList() {
        return previewList;
    }

    static List<Fork> getForks() {
        return forks;
    }

    static List<Philosopher> getPhilosophers() {
        return philosophers;
    }
}