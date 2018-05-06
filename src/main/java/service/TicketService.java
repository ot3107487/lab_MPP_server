package service;

import model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TicketRepositoryJPA;

import java.util.ArrayList;

@Service
public class TicketService implements IService<Ticket> {
    private final TicketRepositoryJPA ticketRepositoryJPA;

    @Autowired
    public TicketService(TicketRepositoryJPA ticketRepositoryJPA) {
        this.ticketRepositoryJPA = ticketRepositoryJPA;
    }

    @Override
    public void save(Ticket elem) {
        ticketRepositoryJPA.save(elem);
    }

    @Override
    public int size() {
        return (int)ticketRepositoryJPA.count();
    }

    @Override
    public void delete(Ticket elem) {
        ticketRepositoryJPA.delete(elem.getId());
    }

    @Override
    public void put(Ticket elem) {
        ticketRepositoryJPA.save(elem);
    }

    @Override
    public ArrayList<Ticket> getAll() {
        return (ArrayList<Ticket>) ticketRepositoryJPA.findAll();
    }
}
