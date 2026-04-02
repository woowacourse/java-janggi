package domain.piece.strategy;

import domain.board.Board;
import domain.board.PathChecker;
import domain.board.Position;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ElephantStrategyTest {

    Map<Position, Piece> dummyBoard;
    PathChecker pathChecker;

    @BeforeEach
    void setUp() {
        dummyBoard = new HashMap<>();

        dummyBoard.put(new Position(3, 3), new Piece(Camp.HAN, PieceType.ELEPHANT));
        dummyBoard.put(new Position(4, 4), new Piece(Camp.HAN, PieceType.ELEPHANT));
        dummyBoard.put(new Position(4, 5), new Piece(Camp.HAN, PieceType.SOLDIER));
        dummyBoard.put(new Position(5, 6), new Piece(Camp.HAN, PieceType.SOLDIER));
        dummyBoard.put(new Position(6, 7), new Piece(Camp.CHO, PieceType.SOLDIER));
        dummyBoard.put(new Position(7, 2), new Piece(Camp.CHO, PieceType.SOLDIER));

        pathChecker = new Board(dummyBoard);
    }

    @Test
    @DisplayName("상은 상의 행마법에 따른 이동이 아니면 이동 시 예외가 발생한다.")
    void throwException_When_ElephantDoesNotMoveInThreeByTwoShape() {
        Position from = new Position(4, 4);
        Position to = new Position(3, 3);

        Piece elephant = dummyBoard.get(from);

        assertThatThrownBy(() -> elephant.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상이 이동 경로의 두 멱이 모두 비어 있으면 이동 시 예외가 발생하지 않는다.")
    void move_When_BothElephantLegsAreClear() {
        Position from = new Position(4, 4);
        Position to = new Position(7, 2);

        Piece elephant = dummyBoard.get(from);

        assertThatCode(() -> elephant.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("상이 이동 경로의 첫 번째 멱 위치에 기물이 있으면 이동 시 예외가 발생한다.")
    void throwException_When_FirstElephantLegIsBlocked() {
        Position from = new Position(4, 4);
        Position to = new Position(6, 7);

        dummyBoard.put(new Position(4, 5), new Piece(Camp.CHO, PieceType.SOLDIER));

        Piece elephant = dummyBoard.get(from);

        assertThatThrownBy(() -> elephant.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상이 이동 경로의 두 번째 멱 위치에 기물이 있으면 이동 시 예외가 발생한다.")
    void throwException_When_SecondElephantLegIsBlocked() {
        Position from = new Position(4, 4);
        Position to = new Position(6, 7);

        dummyBoard.put(new Position(5, 6), new Piece(Camp.CHO, PieceType.SOLDIER));

        Piece elephant = dummyBoard.get(from);

        assertThatThrownBy(() -> elephant.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
