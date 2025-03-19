package janggi;

import java.util.List;
import janggi.piece.General;
import janggi.piece.Piece;
import janggi.piece.TeamType;

public class Janggi {
    private final Board board;

    public Janggi(final Board board) {
        this.board = board;
    }

    public void initializeBoard() {
        initializeGeneral();
    }

    private void initializeGeneral() {
        List<General> generals = General.createWithInitialPositions(TeamType.BLUE);
    }

    private void putInBoard(List<Piece> pieces) {
        for(Piece piece : pieces) {
            board.putPiece(piece.getPosition(), piece);
        }
    }
}
