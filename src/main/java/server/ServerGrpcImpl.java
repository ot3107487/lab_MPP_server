package server;

import io.grpc.stub.StreamObserver;
import model.Artist;
import model.Concert;
import model.Location;
import model.Ticket;
import service.ConcertService;
import service.LoginService;
import service.Service;
import ursus.GrpcProtos;
import ursus.IServerGrpc;

public class ServerGrpcImpl extends IServerGrpc.IServerImplBase {
    private final LoginService loginService;
    private final Service<Integer, Artist> artistService;
    private final Service<Integer, Location> locationService;
    private final ConcertService concertService;
    private final Service<Integer, Ticket> ticketService;

    public ServerGrpcImpl(LoginService loginService, Service<Integer, Artist> artistService, Service<Integer, Location> locationService, ConcertService concertService, Service<Integer, Ticket> ticketService) {

        this.loginService = loginService;
        this.artistService = artistService;
        this.locationService = locationService;
        this.concertService = concertService;
        this.ticketService = ticketService;
    }

    @Override
    public void login(GrpcProtos.CredentialsGrpc request, StreamObserver<GrpcProtos.GrpcReply> responseObserver) {
        String password = request.getPassword();
        String username = request.getUsername();
        if (loginService.login(username, password)) {
            responseObserver.onNext(GrpcProtos.GrpcReply.newBuilder().setStatus("OK").build());
            responseObserver.onCompleted();
        } else {
            responseObserver.onNext(GrpcProtos.GrpcReply.newBuilder().setStatus("FAILED").build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getArtists(GrpcProtos.RequestGrpc request, StreamObserver<GrpcProtos.ArtistList> responseObserver) {
        GrpcProtos.ArtistList.Builder artistList = GrpcProtos.ArtistList.newBuilder();
        for (Artist artist : artistService.getAll()) {
            GrpcProtos.ArtistGrpc.Builder artistBuilder = GrpcProtos.ArtistGrpc.newBuilder();
            artistBuilder.setFirstname(artist.getFirstName());
            artistBuilder.setLastname(artist.getLastName());
            artistBuilder.setId(artist.getId());
            artistList.addArtists(artistBuilder.build());
        }
        responseObserver.onNext(artistList.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getConcerts(GrpcProtos.RequestGrpc request, StreamObserver<GrpcProtos.ConcertList> responseObserver) {
        GrpcProtos.ConcertList.Builder concertList = GrpcProtos.ConcertList.newBuilder();
        for (Concert concert : concertService.getAll()) {
            GrpcProtos.ConcertGrpc.Builder concertBuilder = GrpcProtos.ConcertGrpc.newBuilder();
            concertBuilder.setDate(concert.getDate());
            concertBuilder.setId(concert.getId());
            concertBuilder.setIdArtist(concert.getIdArtist());
            concertBuilder.setIdLocation(concert.getIdLocation());
            concertBuilder.setNumberOfTickets(concert.getNumberOfTickets());
            concertBuilder.setSoldTickets(concert.getSoldTickets());
            concertList.addConcerts(concertBuilder.build());
        }
        responseObserver.onNext(concertList.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getLocation(GrpcProtos.RequestGrpc request, StreamObserver<GrpcProtos.LocationList> responseObserver) {
        GrpcProtos.LocationList.Builder locationList = GrpcProtos.LocationList.newBuilder();
        for (Location location : locationService.getAll()) {
            GrpcProtos.LocationGrpc.Builder locationBuilder = GrpcProtos.LocationGrpc.newBuilder();
            locationBuilder.setId(location.getId());
            locationBuilder.setName(location.getName());
            locationBuilder.setPlaces(location.getPlaces());
            locationList.addLocations(locationBuilder.build());
        }
        responseObserver.onNext(locationList.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getConcertsByArtist(GrpcProtos.ArtistGrpc request, StreamObserver<GrpcProtos.ConcertList> responseObserver) {
        Artist artist = new Artist(request.getId(), request.getLastname(), request.getFirstname());
        GrpcProtos.ConcertList.Builder concertList = GrpcProtos.ConcertList.newBuilder();
        for (Concert concert : concertService.getConcertsByArtist(artist)) {
            GrpcProtos.ConcertGrpc.Builder concertBuilder = GrpcProtos.ConcertGrpc.newBuilder();
            concertBuilder.setDate(concert.getDate());
            concertBuilder.setId(concert.getId());
            concertBuilder.setIdArtist(concert.getIdArtist());
            concertBuilder.setIdLocation(concert.getIdLocation());
            concertBuilder.setNumberOfTickets(concert.getNumberOfTickets());
            concertBuilder.setSoldTickets(concert.getSoldTickets());
            concertList.addConcerts(concertBuilder.build());
        }
        responseObserver.onNext(concertList.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getConcertsByDate(GrpcProtos.RequestGrpc request, StreamObserver<GrpcProtos.ConcertList> responseObserver) {
        String date = request.getRequestType();
        GrpcProtos.ConcertList.Builder concertList = GrpcProtos.ConcertList.newBuilder();
        for (Concert concert : concertService.getConcertsByDate(date)) {
            GrpcProtos.ConcertGrpc.Builder concertBuilder = GrpcProtos.ConcertGrpc.newBuilder();
            concertBuilder.setDate(concert.getDate());
            concertBuilder.setId(concert.getId());
            concertBuilder.setIdArtist(concert.getIdArtist());
            concertBuilder.setIdLocation(concert.getIdLocation());
            concertBuilder.setNumberOfTickets(concert.getNumberOfTickets());
            concertBuilder.setSoldTickets(concert.getSoldTickets());
            concertList.addConcerts(concertBuilder.build());
        }
        responseObserver.onNext(concertList.build());
        responseObserver.onCompleted();
    }

    @Override
    public void saveTicket(GrpcProtos.TicketGrpc request, StreamObserver<GrpcProtos.GrpcReply> responseObserver) {
        Ticket ticket = new Ticket(request.getId(), request.getIdConcert(), request.getPlacesBought(), request.getBuyer());
        ticketService.save(ticket);
        responseObserver.onNext(GrpcProtos.GrpcReply.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateConcert(GrpcProtos.ConcertGrpc request, StreamObserver<GrpcProtos.GrpcReply> responseObserver) {
        Concert concert=new Concert(request.getId(),request.getIdArtist()
                ,request.getDate(),request.getIdLocation(),request.getNumberOfTickets(),request.getSoldTickets());
        concertService.put(concert);
        responseObserver.onNext(GrpcProtos.GrpcReply.newBuilder().build());
        responseObserver.onCompleted();
    }
}
