package networking;

import java.io.Serializable;
import java.util.ArrayList;

public class Response implements Serializable {
    private Object data;

    public Response(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
