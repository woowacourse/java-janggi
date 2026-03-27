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

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SoldierStrategyTest {
    Map<Position, Piece> dummyBoard;
    PathChecker pathChecker;

    @BeforeEach
    void setUp() {
        dummyBoard = new HashMap<>();

        dummyBoard.put(
                new Position(1, 4),
                new Piece(Camp.HAN, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy())
        );
        dummyBoard.put(
                new Position(1, 7),
                new Piece(Camp.CHO, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy())
        );

        pathChecker = new Board(dummyBoard);
    }

    @Test
    @DisplayName("졸은 y축의 양의 방향으로 이동 시 예외를 발생한다.")
    void throwException_When_SoldierMove_Y_PlusDirection() {
        Position from = new Position(1, 7);
        Position to = new Position(1, 8);

        Piece soldier = dummyBoard.get(from);

        assertThatThrownBy(() -> soldier.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("병은 y축의 음의 방향으로 이동 시 예외를 발생한다.")
    void throwException_When_SoldierMove_Y_MinusDirection() {
        Position from = new Position(1, 4);
        Position to = new Position(1, 3);

        Piece soldier = dummyBoard.get(from);

        assertThatThrownBy(() -> soldier.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
