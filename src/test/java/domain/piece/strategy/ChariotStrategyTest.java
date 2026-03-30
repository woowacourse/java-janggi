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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class ChariotStrategyTest {

    Map<Position, Piece> dummyBoard;
    PathChecker pathChecker;

    @BeforeEach
    void setUp() {
        dummyBoard = new HashMap<>();

        dummyBoard.put(new Position(1, 1), new Piece(Camp.HAN, PieceType.CHARIOT));
        dummyBoard.put(new Position(1, 2), new Piece(Camp.HAN, PieceType.SOLDIER));
        dummyBoard.put(new Position(1, 5), new Piece(Camp.CHO, PieceType.SOLDIER));

        pathChecker = new Board(dummyBoard);
    }

    @Test
    @DisplayName("경로상의 기물 리스트가 비어있지 않은 경우 예외를 발생한다.")
    void throwException_When_PiecesAreNotEmpty() {
        Position from = new Position(1, 1);
        Position to = new Position(1, 5);

        Piece chariot = dummyBoard.get(from);

        assertThatThrownBy(() -> chariot.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경로상의 기물 리스트가 비어있는 경우 정상 이동한다.")
    void moveSuccessfully_When_PiecesIsEmpty() {
        Position from = new Position(1, 1);
        Position to = new Position(7, 1);

        Piece chariot = dummyBoard.get(from);

        assertThatCode(() -> chariot.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }
}
