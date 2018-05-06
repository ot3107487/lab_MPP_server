package controller;

import model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.interfaces.ITicketService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/tickets")
public class TicketController {
    private final ITicketService ticketService;

    @Autowired
    public TicketController(ITicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public void saveTicket(@RequestBody Ticket ticket, HttpServletResponse response) {
        Ticket dbTicket = ticketService.findById(ticket.getId());
        if (dbTicket == null)
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        else {
            ticketService.save(ticket);
        }
    }
}
