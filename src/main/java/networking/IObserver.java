package networking;


import model.Concert;
import model.Ticket;

import java.io.Serializable;

public interface IObserver {
     void concertUpdated(Concert concert);
}
