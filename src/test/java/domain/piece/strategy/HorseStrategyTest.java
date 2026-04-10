package domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.board.BoardChecker;
import domain.board.Position;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HorseStrategyTest {

    Map<Position, Piece> dummyBoard;
    BoardChecker boardChecker;

    @BeforeEach
    void setUp() {
        dummyBoard = new HashMap<>();

        dummyBoard.put(
                new Position(3, 3),
                new Piece(Camp.HAN, PieceType.HORSE)
        );
        dummyBoard.put(
                new Position(4, 3),
                new Piece(Camp.HAN, PieceType.SOLDIER)
        );
        dummyBoard.put(
                new Position(4, 4),
                new Piece(Camp.CHO, PieceType.SOLDIER)
        );
        dummyBoard.put(
                new Position(5, 4),
                new Piece(Camp.CHO, PieceType.SOLDIER)
        );
        dummyBoard.put(
                new Position(2, 5),
                new Piece(Camp.CHO, PieceType.SOLDIER)
        );

        boardChecker = new Board(dummyBoard);
    }

    @Test
    @DisplayName("마가 마의 행마법에 따른 이동이 아니면 이동 시 예외가 발생한다.")
    void throwException_When_HorseDoesNotMoveInLShape() {
        Position from = new Position(3, 3);
        Position to = new Position(4, 4);

        Piece horse = dummyBoard.get(from);

        assertThatThrownBy(() -> horse.validateMove(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 마의 이동 방향이 올바르지 않습니다.");
    }

    @Test
    @DisplayName("마의 이동 경로에 멱이 존재하지 않으면 이동 시 예외가 발생하지 않는다.")
    void move_When_HorseLegIsClear() {
        Position from = new Position(3, 3);
        Position to = new Position(2, 5);

        Piece horse = dummyBoard.get(from);

        assertThatCode(() -> horse.validateMove(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("마의 이동 경로에 멱이 존재하면 이동 시 예외가 발생한다.")
    void throwException_When_HorseLegIsBlocked() {
        Position from = new Position(3, 3);
        Position to = new Position(5, 4);

        dummyBoard.put(
                new Position(4, 3),
                new Piece(Camp.CHO, PieceType.SOLDIER)
        );

        Piece horse = dummyBoard.get(from);

        assertThatThrownBy(() -> horse.validateMove(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 멱이 있어 이동할 수 없습니다.");
    }
}
