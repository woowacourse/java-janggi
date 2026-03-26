package domain.board;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.piece.Cha;
import domain.piece.Jol;
import domain.piece.None;
import domain.piece.Piece;
import domain.piece.Sang;
import domain.player.Team;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Map<Position, Piece> createEmptyBoard() {
        Map<Position, Piece> boardMap = new HashMap<>();
        for (int y = 0; y <= 9; y++) {
            for (int x = 0; x <= 8; x++) {
                boardMap.put(new Position(x, y), new None());
            }
        }

        return boardMap;
    }

    @Test
    void 이동할_수_없는_경우는_canMove가_false_반환() {
        Map<Position, Piece> boardMap = createEmptyBoard();
        boardMap.put(new Position(0, 0), new Cha(Team.CHO));
        boardMap.put(new Position(1, 0), new Sang(Team.CHO));

        Board board = new Board(boardMap);
        assertFalse(board.canMove(new Position(0, 0), new Position(8, 0)));
    }

    @Test
    void 이동할_수_있는_경우는_canMove가_true() {
        Map<Position, Piece> boardMap = createEmptyBoard();
        boardMap.put(new Position(0, 3), new Jol(Team.CHO));

        Board board = new Board(boardMap);
        assertTrue(board.canMove(new Position(0, 3), new Position(0, 4)));
    }
}
