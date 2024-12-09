public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final Object lock;

    public Vendor(TicketPool ticketPool, int totalTickets, int ticketReleaseRate, Object lock) {
        this.ticketPool = ticketPool;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                lock.wait();  // Wait until the event name is entered by the customer
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        int ticketId = 1;
        while (ticketId <= totalTickets) {
            Ticket ticket = new Ticket(ticketId++, "Event Name");
            ticketPool.addTicket(ticket);
            System.out.println("[Vendor] Ticket ID " + ticket.getTicketId() + " added to the pool.");

            try {
                Thread.sleep(1000 / ticketReleaseRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
