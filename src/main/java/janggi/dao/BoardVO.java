package janggi.dao;

import janggi.board.Board;
import janggi.piece.Camp;
import janggi.piece.Piece;
import janggi.position.Position;
import java.util.Map;

public record BoardVO(Long id, String teamCode, String currentCamp, String winnerCamp) {

    public static BoardVO of(Board board, String teamCode) {
        return new BoardVO(
                null,
                teamCode,
                board.getCurrentCamp().name(),
                board.determineWinner().name()
        );
    }

    public Board toBoard(Map<Position, Piece> cells) {
        return new Board(
                cells,
                Camp.valueOf(currentCamp)
        );
    }
}
