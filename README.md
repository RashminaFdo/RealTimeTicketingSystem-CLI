# Real-Time Event Ticketing System CLI

## Overview

The Real-Time Event Ticketing System CLI is a command-line application built to simulate a ticketing system. It follows a producer-consumer pattern, where tickets are released in stages, and customers retrieve them based on availability. This system helps to model ticket sales in real-time, simulating both the production of tickets and their consumption by customers.

## Features

- **Real-Time Ticket Release**: Tickets are released at a specified rate, simulating a live ticketing event.
- **Ticket Retrieval Simulation**: Customers can retrieve tickets at a specified rate, ensuring a realistic flow of ticket sales.
- **Configuration File**: Customize parameters like the total number of tickets, release rate, retrieval rate, and maximum capacity via a JSON configuration file.
- **Producer-Consumer Model**: The system uses a producer-consumer pattern to efficiently handle the flow of tickets.

## Prerequisites

Before you begin, ensure you have the following installed:

- [Java 17+](https://openjdk.java.net/)
- [Maven](https://maven.apache.org/install.html)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/yourusername/real-time-event-ticketing-system-cli.git
cd real-time-event-ticketing-system-cli
```

### Configure the Project

Update the `config.json` file located in the root directory to customize the ticketing parameters:

```json
{
  "totalTickets": 15,
  "ticketReleaseRate": 5,
  "customerRetrievalRate": 2,
  "maxTicketCapacity": 50
}
```

- `totalTickets`: Total number of tickets available.
- `ticketReleaseRate`: The rate at which tickets are released (tickets per cycle).
- `customerRetrievalRate`: The rate at which customers retrieve tickets (customers per cycle).
- `maxTicketCapacity`: The maximum capacity for the event (max tickets available).

### Build the Project

Use Maven to build the project. Run the following command:

```bash
./mvnw clean install
```

### Run the Application

To start the application, use the following command:

```bash
./mvnw spring-boot:run
```

The CLI application will run and simulate the ticket sales process.

## Usage

The application simulates the ticketing system where tickets are released at a specific rate and customers retrieve them based on availability.

### Example Simulation Flow

1. **Tickets are released**: Tickets are made available for customers to purchase at the configured `ticketReleaseRate`.
2. **Customers retrieve tickets**: Customers can retrieve tickets based on the `customerRetrievalRate`. If the maximum capacity is reached, the system will stop further ticket retrievals.

### Example CLI Commands

To start the ticketing simulation, simply run the following command:

```bash
java -jar target/real-time-event-ticketing-system-cli.jar
```

The simulation will show the release and retrieval of tickets in real-time.

## Development

To contribute to this project:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Create a new Pull Request.

## Documentation

For more detailed information on the technologies used in this project, refer to the following:

- [Apache Maven Documentation](https://maven.apache.org/guides/index.html)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Contact

For further questions or inquiries, you can contact the project maintainer:

- **Email**: [fdo.rashmina@gmail.com](mailto:fdo.rashmina@gmail.com)
- **LinkedIn**: [Rashmina Fernando](https://www.linkedin.com/in/rashminafernando)
