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
        dummyBoard.put(new Position(6, 1), new Piece(Camp.CHO, PieceType.CHARIOT));
        dummyBoard.put(new Position(6, 2), new Piece(Camp.CHO, PieceType.CHARIOT));
        dummyBoard.put(new Position(4, 8), new Piece(Camp.CHO, PieceType.CHARIOT));
        dummyBoard.put(new Position(5, 9), new Piece(Camp.CHO, PieceType.CHARIOT));

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
        Position to = new Position(3, 1);

        Piece chariot = dummyBoard.get(from);

        assertThatCode(() -> chariot.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("출발 지점과 도착 지점이 궁성 내부이고 중앙을 포함한 대각선 1칸 이동이 가능하다")
    void moveSuccess_When_ChariotIsInPalaceAndDiagonalOneSpaceMove() {
        Position from = new Position(6, 1);
        Position to = new Position(5, 2);

        Piece chariot = dummyBoard.get(from);

        assertThatCode(() -> chariot.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }


    @Test
    @DisplayName("출발 지점과 도착 지점이 궁성 내부이고 중앙을 포함하지 않은 대각선 1칸 이동은 예외가 발생한다")
    void throwException_When_ChariotIsInPalaceButNoIncludeCenterDiagonalOneSpaceMove() {
        Position from = new Position(6, 2);
        Position to = new Position(5, 3);

        Piece chariot = dummyBoard.get(from);

        assertThatThrownBy(() -> chariot.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("출발 지점과 도착 지점이 궁성 내부이고 중앙이 비어있는 대각선 2칸 이동이 가능하다")
    void moveSuccess_When_ChariotIsInPalaceAndDiagonalTwoSpaceMove() {
        Position from = new Position(6, 1);
        Position to = new Position(4, 3);

        Piece chariot = dummyBoard.get(from);

        assertThatCode(() -> chariot.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("출발 지점과 도착 지점이 궁성 내부이고 중앙이 비어있지 않은 대각선 2칸 이동은 예외가 발생한다")
    void throwException_When_ChariotIsInPalaceButNoIncludeCenterDiagonalTwoSpaceMove() {
        Position from = new Position(4, 8);
        Position to = new Position(6, 10);

        Piece chariot = dummyBoard.get(from);

        assertThatThrownBy(() -> chariot.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁성 밖에서의 대각선 이동은 예외가 발생한다")
    void throwException_When_DiagonalMoveOutOfPalace() {
        Position from = new Position(1, 2);
        Position to = new Position(2, 3);

        Piece chariot = dummyBoard.get(from);

        assertThatThrownBy(() -> chariot.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
