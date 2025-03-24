package move;

public class InvalidMovePosition extends IllegalArgumentException {

    public InvalidMovePosition() {
        super("도달할 수 없는 위치입니다.");
    }
}
