package domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.board.PathChecker;
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
    PathChecker pathChecker;

    @BeforeEach
    void setUp() {
        dummyBoard = new HashMap<>();

        dummyBoard.put(
                new Position(3, 3),
                new Piece(Camp.HAN, PieceType.HORSE, PieceType.HORSE.createStrategy())
        );
        dummyBoard.put(
                new Position(4, 3),
                new Piece(Camp.HAN, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy())
        );
        dummyBoard.put(
                new Position(4, 4),
                new Piece(Camp.CHO, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy())
        );
        dummyBoard.put(
                new Position(5, 4),
                new Piece(Camp.CHO, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy())
        );
        dummyBoard.put(
                new Position(2, 5),
                new Piece(Camp.CHO, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy())
        );

        pathChecker = new Board(dummyBoard);
    }

    @Test
    @DisplayName("마가 마의 행마법에 따른 이동이 아니면 이동 시 예외가 발생한다.")
    void throwException_When_HorseDoesNotMoveInLShape() {
        Position from = new Position(3, 3);
        Position to = new Position(4, 4);

        Piece horse = dummyBoard.get(from);

        assertThatThrownBy(() -> horse.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("마의 이동 경로에 멱이 존재하지 않으면 이동 시 예외가 발생하지 않는다.")
    void move_When_HorseLegIsClear() {
        Position from = new Position(3, 3);
        Position to = new Position(2, 5);

        Piece horse = dummyBoard.get(from);

        assertThatCode(() -> horse.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("마의 이동 경로에 멱이 존재하면 이동 시 예외가 발생한다.")
    void throwException_When_HorseLegIsBlocked() {
        Position from = new Position(3, 3);
        Position to = new Position(5, 4);

        dummyBoard.put(
                new Position(4, 3),
                new Piece(Camp.CHO, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy())
        );

        Piece horse = dummyBoard.get(from);

        assertThatThrownBy(() -> horse.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
