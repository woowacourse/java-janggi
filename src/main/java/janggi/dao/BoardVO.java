package janggi.dao;

import janggi.domain.board.Board;
import janggi.exception.DataAccessException;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
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
        if (cells == null) {
            throw new DataAccessException("보드판은 null이 될 수 없습니다.");
        }
        return new Board(
                cells,
                Camp.valueOf(currentCamp)
        );
    }
}
