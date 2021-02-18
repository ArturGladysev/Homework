package MultiThread2;

public class GetEntryException extends RuntimeException {      //Собстенное исключение
    public GetEntryException(String message) {
        super(message);
    }
}