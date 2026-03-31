package domain.board;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import common.exception.JanggiException;
import domain.piece.Cha;
import domain.piece.Jol;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sang;
import domain.player.Team;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Map<Position, Piece> createEmptyBoard() {
        return new HashMap<>();
    }

    @Nested
    class GeneralMovementTest {
        @Test
        void 이동할_수_없는_경우_Exception_던진다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Cha(Team.CHO));
            boardMap.put(new Position(0, 1), new Sang(Team.CHO));

            Board board = new Board(boardMap);
            assertThrows(JanggiException.class, () -> board.move(new Position(0, 0), new Position(0, 8), Team.CHO));
        }

        @Test
        void 이동할_수_있는_경우_Exception_던지지_않는다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(6, 0), new Jol(Team.CHO));

            Board board = new Board(boardMap);
            assertDoesNotThrow(() -> board.move(new Position(6, 0), new Position(5, 0), Team.CHO));
        }
    }

    @Nested
    class PoMovementTest {
        @Test
        void 포가_이동할_수_없는_경우_Exception_던진다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(5, 0), new Po(Team.CHO));

            Board board = new Board(boardMap);
            assertThrows(JanggiException.class, () -> board.move(new Position(5, 0), new Position(8, 0), Team.CHO));
        }

        @Test
        void 포가_이동할_수_있는_경우_Exception_던지지_않는다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(8, 1), new Po(Team.CHO));
            boardMap.put(new Position(7, 1), new Cha(Team.HAN));

            Board board = new Board(boardMap);
            assertDoesNotThrow(() -> board.move(new Position(8, 1), new Position(6, 1), Team.CHO));
        }

        @Test
        void 포_이동시_목적지가_포면_Exception_던진다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(8, 1), new Po(Team.CHO));
            boardMap.put(new Position(7, 1), new Cha(Team.HAN));
            boardMap.put(new Position(6, 1), new Po(Team.HAN));

            Board board = new Board(boardMap);
            assertThrows(JanggiException.class, () -> board.move(new Position(8, 1), new Position(6, 1), Team.CHO));
        }
    }

    @Nested
    class MoveResultTest {
        @Test
        void 도착_위치에_상대편_기물이_있는_경우_도착칸으로_이동한다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Cha(Team.CHO));
            boardMap.put(new Position(0, 3), new Cha(Team.HAN));

            Board board = new Board(boardMap);
            board.move(new Position(0, 0), new Position(0, 3), Team.CHO);

            assertFalse(board.hasPiece(new Position(0, 0)));
            assertEquals(new Cha(Team.CHO), board.findPiece(new Position(0, 3)));
        }

        @Test
        void 도착_위치에_상대편_기물이_없는_경우에도_도착칸으로_이동한다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Cha(Team.CHO));

            Board board = new Board(boardMap);
            board.move(new Position(0, 0), new Position(0, 3), Team.CHO);

            assertFalse(board.hasPiece(new Position(0, 0)));
            assertTrue(board.hasPiece(new Position(0, 3)));
            assertEquals(new Cha(Team.CHO), board.findPiece(new Position(0, 3)));
        }
    }
}
