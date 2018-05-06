import model.Artist;
import model.Concert;
import model.Location;
import org.junit.Assert;
import org.junit.Test;

public class ModelTests {
    @Test
    public void oneToManyRelation(){
        Location location=new Location(1,"a",2);
        Artist artist=new Artist(1,"b","b");
        Concert concert=new Concert(artist,"a",location,1,1);
        Assert.assertTrue(concert.getArtist().getId()==1);
    }

}
