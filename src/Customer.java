import java.util.Scanner;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalRate;
    private String eventName;
    private final Object lock;

    public Customer(TicketPool ticketPool, int retrievalRate, Object lock) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
        this.lock = lock;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        synchronized (lock) {
            while (true) {
                System.out.println("Please enter the event name for this session:");
                eventName = scanner.nextLine().trim();
                if (!eventName.isEmpty()) {
                    lock.notifyAll();
                    break;
                } else {
                    System.out.println("Error: Event name cannot be empty. Please enter a valid event name.");
                }
            }
        }

        while (true) {
            Ticket ticket = ticketPool.consumeTicket();
            System.out.println("[Customer] Ticket ID: " + ticket.getTicketId() + " was bought for event: " + eventName);

            try {
                Thread.sleep(1000 / retrievalRate);  // Simulate the time delay between customer actions
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
