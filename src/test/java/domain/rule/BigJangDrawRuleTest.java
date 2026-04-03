package domain.rule;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.board.Board;
import domain.piece.BasicPiece;
import domain.piece.Jang;
import domain.piece.Jol;
import domain.piece.None;
import domain.player.Team;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BigJangDrawRuleTest {

    private final DrawRule drawRule = new BigJangDrawRule();

    @Test
    void 두_장의_사이에_기물이_없고_같은_열이면_빅장이다() {
        Map<Position, BasicPiece> boardMap = createEmptyBoard();
        boardMap.put(new Position(0, 4), new Jang(Team.HAN));
        boardMap.put(new Position(9, 4), new Jang(Team.CHO));

        Board board = new Board(boardMap);

        assertTrue(drawRule.isDraw(board));
    }

    @Test
    void 두_장의_사이에_기물이_있으면_빅장이_아니다() {
        Map<Position, BasicPiece> boardMap = createEmptyBoard();
        boardMap.put(new Position(0, 4), new Jang(Team.HAN));
        boardMap.put(new Position(9, 4), new Jang(Team.CHO));
        boardMap.put(new Position(4, 4), new Jol(Team.CHO));

        Board board = new Board(boardMap);

        assertFalse(drawRule.isDraw(board));
    }

    private Map<Position, BasicPiece> createEmptyBoard() {
        Map<Position, BasicPiece> board = new HashMap<>();
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                board.put(new Position(row, column), None.getInstance());
            }
        }
        return board;
    }
}

