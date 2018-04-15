package networking;

import java.io.Serializable;

public class Identifiable implements Serializable {
    private String credentials;

    public Identifiable(String credentials) {
        this.credentials = credentials;
    }

    public String getCredentials() {
        return credentials;
    }
}
