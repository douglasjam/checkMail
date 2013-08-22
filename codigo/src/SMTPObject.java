
import java.util.ArrayList;

public class SMTPObject {

    String server;
    ArrayList<String> tags;

    public SMTPObject() {
        tags = new ArrayList<String>();
    }

    public SMTPObject(String server, ArrayList<String> tags) {
        this.server = server;
        this.tags = tags;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    @Override
    public String toString() {
        return "SMTPObject{" + "server=" + server
                + ", tags=" + tags + '}';
    }
}
