package domain;

import domain.boardgenerator.BoardGenerator;
import domain.piece.Piece;
import java.util.List;

public class FakeBoardGenerator implements BoardGenerator {

    private final List<Piece> board;

    public FakeBoardGenerator(List<Piece> board) {
        this.board = board;
    }

    @Override
    public List<Piece> generateBoard() {
        return board;
    }
}
