package server;


import model.Artist;
import model.Concert;
import model.Ticket;
import networking.*;

import java.net.Socket;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 12:17:49 PM
 */
public class SerialConcurrentServer extends ConcurrentServer {
    public SerialConcurrentServer(int port) {
        super(port);
        System.out.println("SerialConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        Worker worker = new Worker(client);
        Thread tw = new Thread(worker);
        return tw;
    }

    class Worker implements Runnable {
        private Socket client;

        Worker(Socket client) {
            this.client = client;
        }

        public void run() {
            try {
                System.out.println("Starting to process request ...  ");
                //opening streams  - mandatory to open first the output and flush, and then the input
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                out.flush();
                ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                //read message from client
                boolean connected = true;
                while (connected) {
                    try {
                        Object obj = in.readObject();
                        if (obj instanceof Request) {
                            System.out.println("Received request ");
                            Request receivedRequest = (Request) obj;
                            if (receivedRequest.getRequestType().equals(RequestType.LOGIN)) {
                                Identifiable identifiable = (Identifiable) receivedRequest.getBody();
                                String body = identifiable.getCredentials();
                                String[] credentials = body.split(" ");
                                String userName = credentials[0];
                                String password = credentials[1];
                                System.out.println("Sending response");
                                Boolean status = loginService.login(userName, password);
                                out.writeObject(status);
                                out.flush();
                            }
                            if (receivedRequest.getRequestType().equals(RequestType.UPDATES)) {
                                clients.add(out);
                            }
                            if (receivedRequest.getRequestType().equals(RequestType.GET_CONCERTS_BY_ARTIST)) {
                                Artist artist = (Artist) receivedRequest.getBody();
                                System.out.println("Sending response");
                                out.writeObject(new Response(concertService.getConcertsByArtist(artist)));
                                out.flush();
                            }
                            if (receivedRequest.getRequestType().equals(RequestType.GET_CONCERTS_BY_DATE)) {
                                String date = (String) receivedRequest.getBody();
                                System.out.println("Sending response");
                                out.writeObject(new Response(concertService.getConcertsByDate(date)));
                                out.flush();
                            }
                            if (receivedRequest.getRequestType().equals(RequestType.GET_CONCERTS_BY_ARTIST_AND_DATE)) {
                                WrapperArtistAndDate wrapperArtistAndDate = (WrapperArtistAndDate) receivedRequest.getBody();
                                Artist artist = wrapperArtistAndDate.getArtist();
                                String date = wrapperArtistAndDate.getDate();
                                System.out.println("Sending response");
                                out.writeObject(new Response(concertService.getConcertsByArtistAndDate(artist, date)));
                                out.flush();
                            }
                            if (receivedRequest.getRequestType().equals(RequestType.SAVE_TICKET)) {
                                Ticket ticket = (Ticket) receivedRequest.getBody();
                                System.out.println("Sending response");
                                ticketService.save(ticket);
                                out.writeObject(new Response(null));
                                out.flush();
                            }
                            if (receivedRequest.getRequestType().equals(RequestType.UPDATE_CONCERT)) {
                                Concert concert = (Concert) receivedRequest.getBody();
                                System.out.println("Sending response");
                                concertService.put(concert);
                                out.writeObject(new Response(null));
                                out.flush();
                                System.out.println("Sending updates");


                                //########## UPDATEING OTHER CLIENTS
                                for (ObjectOutputStream cl : clients) {
                                    System.out.println("Sending update to:"+ cl);
                                    cl.flush();
                                    cl.writeObject(new Response(concert));
                                    cl.flush();
                                }
                            }
                            if (receivedRequest.getRequestType().equals(RequestType.GET_ARTISTS)) {
                                System.out.println("Sending response");
                                out.writeObject(new Response(artistService.getAll()));
                                out.flush();
                            }
                            if (receivedRequest.getRequestType().equals(RequestType.GET_CONCERTS)) {
                                System.out.println("Sending response");
                                out.writeObject(new Response(concertService.getAll()));
                                out.flush();
                            }
                            if (receivedRequest.getRequestType().equals(RequestType.GET_LOCATIONS)) {
                                System.out.println("Sending response");
                                out.writeObject(new Response(locationService.getAll()));
                                out.flush();
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        System.out.println("Error deserializing " + e);
                    }
                }
                //close the streams
                //in.close();
                //out.close();
                //close connection
                //client.close();
                System.out.println("Finished  processing request ...");
            } catch (IOException e) {
                System.out.println("Error in processing client request " + e);
            }
        }

    }
}
