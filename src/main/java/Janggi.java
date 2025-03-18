import java.util.List;

public class Janggi {
    private final Board board;

    public Janggi(final Board board) {
        this.board = board;
    }

    public void initializeBoard() {
        for(var pieceType : PieceType.values()) {
            if(pieceType == PieceType.EMPTY) {
                continue;
            }
            for(var teamType : TeamType.values()) {
                if(teamType == TeamType.NONE) {
                    continue;
                }
                initialize(pieceType, teamType);
            }
        }
    }

    private void initialize(PieceType pieceType, TeamType teamType) {
        List<Position> initialPositions = pieceType.getInitialPositions(teamType);
        for(Position initialPosition : initialPositions) {
            board.putPiece(initialPosition, new Piece(pieceType, teamType));
        }
    }
}
