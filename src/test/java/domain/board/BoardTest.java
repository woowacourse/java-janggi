package domain.board;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.piece.Cha;
import domain.piece.Jol;
import domain.piece.None;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sang;
import domain.player.Team;
import domain.position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Map<Position, Piece> createEmptyBoard() {
        Map<Position, Piece> boardMap = new HashMap<>();
        for (int row = 0; row <= 9; row++) {
            for (int column = 0; column <= 8; column++) {
                boardMap.put(new Position(row, column), new None());
            }
        }

        return boardMap;
    }

    @Nested
    class 포_이외의_기물_이동_테스트 {
        @Test
        void 이동할_수_없는_경우는_Exception_던진다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Cha(Team.CHO));
            boardMap.put(new Position(0, 1), new Sang(Team.CHO));

            Board board = new Board(boardMap);
            assertThrows(IllegalArgumentException.class, () -> board.move(new Position(0, 0), new Position(0, 8)));
        }

        @Test
        void 이동할_수_있는_경우는_Exception_던지지_않는다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(6, 0), new Jol(Team.CHO));

            Board board = new Board(boardMap);
            assertDoesNotThrow(() -> board.move(new Position(6, 0), new Position(5, 0)));
        }
    }

    @Nested
    class 포_기물_이동_테스트 {
        @Test
        void 포가_이동할_수_없는_경우는_Exception_던진다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(5, 0), new Po(Team.CHO));

            Board board = new Board(boardMap);
            assertThrows(IllegalArgumentException.class, () -> board.move(new Position(5, 0), new Position(8, 0)));
        }

        @Test
        void 포가_이동할_수_있는_경우는_Exception_던지지_않는다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(8, 1), new Po(Team.CHO));
            boardMap.put(new Position(7, 1), new Cha(Team.HAN));
            boardMap.put(new Position(6, 1), new None());

            Board board = new Board(boardMap);
            assertDoesNotThrow(() -> board.move(new Position(8, 1), new Position(6, 1)));
        }

        @Test
        void 포가_도착지에_상대편_포가_있으면_Exception_던진다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(8, 1), new Po(Team.CHO));
            boardMap.put(new Position(7, 1), new Cha(Team.HAN));
            boardMap.put(new Position(6, 1), new Po(Team.HAN));

            Board board = new Board(boardMap);
            assertThrows(IllegalArgumentException.class, () -> board.move(new Position(8, 1), new Position(6, 1)));
        }
    }

    @Nested
    class 기물_이동_테스트 {
        @Test
        void 도착_위치에_상대편_기물이_있는_경우_해당_기물을_반환한다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Cha(Team.CHO));
            boardMap.put(new Position(0, 3), new Cha(Team.HAN));

            Board board = new Board(boardMap);

            assertEquals(new Cha(Team.HAN), board.move(new Position(0, 0), new Position(0, 3)));
        }

        @Test
        void 도착_위치에_상대편_기물이_없는_경우_None_기물을_반환한다() {
            Map<Position, Piece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Cha(Team.CHO));

            Board board = new Board(boardMap);

            assertEquals(new None(), board.move(new Position(0, 0), new Position(0, 3)));
        }
    }
}
