package repository;

import model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepositoryJPA extends JpaRepository<Ticket,Integer> {
}
