package domain;

import domain.boardgenerator.BoardGenerator;
import domain.piece.Piece;
import java.util.Map;

public class FakeBoardGenerator implements BoardGenerator {

    private final Map<Position, Piece> board;

    public FakeBoardGenerator(Map<Position, Piece> board) {
        this.board = board;
    }

    @Override
    public Map<Position, Piece> generateBoard() {
        return board;
    }
}
