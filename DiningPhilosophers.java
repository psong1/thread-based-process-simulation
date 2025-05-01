import java.util.concurrent.locks.*;

class Philosopher extends Thread {
    private final int id;
    private final Lock leftFork;
    private final Lock rightFork;

    public Philosopher(int id, Lock leftFork, Lock rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();
                pickUpForks();
                eat();
                putDownForks();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("[Philosopher " + id + "] is thinking.");
        Thread.sleep(1000); // Simulate thinking time
    }

    private void pickUpForks() {
        System.out.println("[Philosopher " + id + "] is waiting for forks...");
        leftFork.lock();
        System.out.println("[Philosopher " + id + "] picked up left fork.");

        rightFork.lock();
        System.out.println("[Philosopher " + id + "] picked up right fork.");
    }

    private void eat() throws InterruptedException {
        System.out.println("[Philosopher " + id + "] is eating.");
        Thread.sleep(2000); // Simulate eating time
    }

    private void putDownForks() {
        leftFork.unlock();
        System.out.println("[Philosopher " + id + "] released left fork.");
        rightFork.unlock();
        System.out.println("[Philosopher " + id + "] released right fork.");
    }
}

public class DiningPhilosophers {
    public static void main(String[] args) {
        Lock[] forks = new Lock[5];
        for (int i = 0; i < 5; i++) {
            forks[i] = new ReentrantLock();
        }

        Philosopher[] philosophers = new Philosopher[5];
        for (int i = 0; i < 5; i++) {
            // Each philosopher takes two forks
            philosophers[i] = new Philosopher(i + 1, forks[i], forks[(i + 1) % 5]);
            philosophers[i].start();
        }
    }
}
