import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private Queue<Ticket> ticketQueue;
    private int maxTicketCapacity;

    public TicketPool(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
        ticketQueue = new LinkedList<>();
    }

    // Synchronized method to add tickets to the pool (by Vendor)
    public synchronized void addTicket(Ticket ticket) {
        while (ticketQueue.size() == maxTicketCapacity) {
            try {
                wait();  // Wait if the pool is full
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        ticketQueue.add(ticket);
        notifyAll();  // Notify all threads when a new ticket is added
    }

    // Synchronized method to consume tickets from the pool (by Customer)
    public synchronized Ticket consumeTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                wait();  // Wait if the pool is empty
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Ticket ticket = ticketQueue.poll();
        notifyAll();  // Notify all threads when a ticket is consumed
        return ticket;
    }

    public int getSize() {
        return ticketQueue.size();
    }
}
