package Board;

public class Exception extends RuntimeException {

    // Unique identifier for serialization (required by the Serializable interface)
    private static final long serialVersionUID = 1L;

    // Constructor that takes a message as a parameter and passes it to the superclass constructor
    public Exception(String msg) {
        super(msg);
    }

}
