package janggiexception;

public class OutOfBoardException extends RuntimeException {

    public OutOfBoardException() {
        super("장기판을 넘어서 이동할 수 없습니다.");
    }
}
