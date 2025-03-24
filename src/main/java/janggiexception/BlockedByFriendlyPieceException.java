package janggiexception;

public class BlockedByFriendlyPieceException extends RuntimeException {

    public BlockedByFriendlyPieceException() {
        super("도착지에 같은 팀의 기물이 존재하여 움직일 수 없습니다.");
    }
}
