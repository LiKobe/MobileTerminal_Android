package ros.message;

/**
 * Created by xxhong on 16-11-17.
 */
@MessageType(string = "std_msgs/String")
public class SemanticRequest extends Message {
    public String jsonStr;

    public SemanticRequest(String args) {
        jsonStr = args;
    }
}
