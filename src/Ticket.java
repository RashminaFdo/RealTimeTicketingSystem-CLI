public class Ticket {
    private int ticketId;
    private String eventName;

    public Ticket(int ticketId, String eventName) {
        this.ticketId = ticketId;
        this.eventName = eventName;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getEventName() {
        return eventName;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", eventName='" + eventName + '\'' +
                '}';
    }
}
