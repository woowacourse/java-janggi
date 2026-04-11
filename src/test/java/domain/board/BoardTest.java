package domain.board;

import common.exception.JanggiException;
import domain.piece.*;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static common.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Player choPlayer;

    @BeforeEach
    void setUp() {
        choPlayer = new Player(new Name("초나라"), Team.CHO);
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

    @Nested
    class GeneralMovementTest {
        @Test
        void 이동할_수_없는_경우_Exception_던진다() {
            Map<Position, BasicPiece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Cha(Team.CHO));
            boardMap.put(new Position(0, 1), new Sang(Team.CHO));

            Board board = new Board(boardMap);
            assertThrows(JanggiException.class, () ->
                    board.move(new Position(0, 0), new Position(0, 8), choPlayer));
        }

        @Test
        void 이동할_수_있는_경우_Exception_던지지_않는다() {
            Map<Position, BasicPiece> boardMap = createEmptyBoard();
            boardMap.put(new Position(6, 0), new Jol(Team.CHO));

            Board board = new Board(boardMap);
            assertDoesNotThrow(() ->
                    board.move(new Position(6, 0), new Position(5, 0), choPlayer));
        }
    }

    @Nested
    class PoMovementTest {
        @Test
        void 포가_넘을_기물이_없는_경우_이동할_수_없어_Exception_던진다() {
            Map<Position, BasicPiece> boardMap = createEmptyBoard();
            boardMap.put(new Position(5, 0), new Po(Team.CHO));

            Board board = new Board(boardMap);
            assertThrows(JanggiException.class, () ->
                    board.move(new Position(5, 0), new Position(8, 0), choPlayer));
        }

        @Test
        void 포가_기물을_넘어_이동할_수_있는_경우_Exception_던지지_않는다() {
            Map<Position, BasicPiece> boardMap = createEmptyBoard();
            boardMap.put(new Position(8, 1), new Po(Team.CHO));
            boardMap.put(new Position(7, 1), new Cha(Team.HAN)); // 징검다리 기물

            Board board = new Board(boardMap);
            assertDoesNotThrow(() ->
                    board.move(new Position(8, 1), new Position(6, 1), choPlayer));
        }

        @Test
        void 포_이동시_목적지가_포면_Exception_던진다() {
            Map<Position, BasicPiece> boardMap = createEmptyBoard();
            boardMap.put(new Position(8, 1), new Po(Team.CHO));
            boardMap.put(new Position(7, 1), new Cha(Team.HAN)); // 징검다리
            boardMap.put(new Position(6, 1), new Po(Team.HAN)); // 목적지가 포

            Board board = new Board(boardMap);
            assertThrows(JanggiException.class, () ->
                    board.move(new Position(8, 1), new Position(6, 1), choPlayer));
        }
    }

    @Nested
    class MoveResultTest {
        @Test
        void 도착_위치에_상대편_기물이_있는_경우_도착칸으로_이동하고_원래자리는_비워진다() {
            Map<Position, BasicPiece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Cha(Team.CHO));
            boardMap.put(new Position(0, 3), new Cha(Team.HAN));

            Board board = new Board(boardMap);
            board.move(new Position(0, 0), new Position(0, 3), choPlayer);

            assertTrue(board.findPiece(new Position(0, 0)).isNone());
            assertEquals(new Cha(Team.CHO), board.findPiece(new Position(0, 3)));
        }

        @Test
        void 도착_위치에_상대편_기물이_없는_경우에도_도착칸으로_이동한다() {
            Map<Position, BasicPiece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Cha(Team.CHO));

            Board board = new Board(boardMap);
            board.move(new Position(0, 0), new Position(0, 3), choPlayer);

            assertTrue(board.findPiece(new Position(0, 0)).isNone());
            assertFalse(board.findPiece(new Position(0, 3)).isNone());
            assertEquals(new Cha(Team.CHO), board.findPiece(new Position(0, 3)));
        }
    }

    @Nested
    class ScoreTest {
        @Test
        void 팀별_원점수를_계산한다() {
            Map<Position, BasicPiece> boardMap = createEmptyBoard();
            boardMap.put(new Position(0, 0), new Cha(Team.CHO));
            boardMap.put(new Position(0, 1), new Ma(Team.CHO));
            boardMap.put(new Position(9, 8), new Jol(Team.HAN));
            boardMap.put(new Position(9, 7), new Jang(Team.HAN));

            Board board = new Board(boardMap);

            assertThat(board.calculateRawScore(Team.CHO)).isEqualTo(18);
            assertThat(board.calculateRawScore(Team.HAN)).isEqualTo(2);
        }
    }

}
