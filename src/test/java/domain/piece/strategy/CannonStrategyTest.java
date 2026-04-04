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

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class CannonStrategyTest {

    Map<Position, Piece> dummyBoard;
    PathChecker pathChecker;

    @BeforeEach
    void setUp() {
        dummyBoard = new HashMap<>();
    }

    @Test
    @DisplayName("경로상에 기물이 정확히 한 개가 아닐 경우 예외를 발생한다.")
    void throwException_When_PiecesAreNotExactlyOne() {
        dummyBoard.put(new Position(1, 1), new Piece(Camp.HAN, PieceType.CANNON));
        dummyBoard.put(new Position(1, 2), new Piece(Camp.HAN, PieceType.SOLDIER));
        dummyBoard.put(new Position(1, 4), new Piece(Camp.CHO, PieceType.SOLDIER));
        pathChecker = new Board(dummyBoard);

        Position from = new Position(1, 1);
        Position to = new Position(1, 5);

        Piece cannon = dummyBoard.get(from);

        assertThatThrownBy(() -> cannon.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경로상에 포가 존재할 경우 예외를 발생한다.")
    void throwException_When_CannonIsExists() {
        dummyBoard.put(new Position(1, 1), new Piece(Camp.HAN, PieceType.CANNON));
        dummyBoard.put(new Position(1, 3), new Piece(Camp.CHO, PieceType.CANNON));
        pathChecker = new Board(dummyBoard);

        Position from = new Position(1, 1);
        Position to = new Position(1, 5);

        Piece cannon = dummyBoard.get(from);

        assertThatThrownBy(() -> cannon.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경로상에 포가 아닌 기물이 정확히 한 개 존재할 경우 정상 이동한다.")
    void moveSuccessfully_When_NoCannonInPathAndExactlyOnePiece() {
        dummyBoard.put(new Position(1, 1), new Piece(Camp.HAN, PieceType.CANNON));
        dummyBoard.put(new Position(1, 3), new Piece(Camp.CHO, PieceType.SOLDIER));
        pathChecker = new Board(dummyBoard);

        Position from = new Position(1, 1);
        Position to = new Position(1, 5);

        Piece cannon = dummyBoard.get(from);

        assertThatCode(() -> cannon.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("도착 위치에 존재하는 기물이 포일 경우 움직일 수 없다.")
    void throwException_When_CannonIsOnDestination() {
        dummyBoard.put(new Position(1, 1), new Piece(Camp.HAN, PieceType.CANNON));
        dummyBoard.put(new Position(1, 3), new Piece(Camp.CHO, PieceType.SOLDIER));
        dummyBoard.put(new Position(1, 5), new Piece(Camp.CHO, PieceType.CANNON));
        pathChecker = new Board(dummyBoard);

        Position from = new Position(1, 1);
        Position to = new Position(1, 5);

        Piece cannon = dummyBoard.get(from);

        assertThatThrownBy(() -> cannon.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("출발 지점과 도착 지점이 궁성 내부일 경우 도착 위치가 궁성의 외곽이 아니면 예외가 발생한다")
    void throwException_When_FromAndToIsInPalaceButNotOuterPerimeter() {
        dummyBoard.put(new Position(4, 1), new Piece(Camp.HAN, PieceType.CANNON));
        pathChecker = new Board(dummyBoard);

        Position from = new Position(4, 1);
        Position to = new Position(5, 1);

        Piece cannon = dummyBoard.get(from);

        assertThatThrownBy(() -> cannon.move(from, to, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("출발 지점과 도착 지점이 궁성 내부일 경우 대각선 이동이고 도착 지점에 포가 없다면 정상 이동한다")
    void moveSuccess_When_FromAndToIsInPalaceAndOuterPerimeter() {
        dummyBoard.put(new Position(4, 1), new Piece(Camp.HAN, PieceType.CANNON));
        dummyBoard.put(new Position(5, 2), new Piece(Camp.HAN, PieceType.SOLDIER));
        pathChecker = new Board(dummyBoard);

        Position from = new Position(4, 1);
        Position to = new Position(6, 3);

        Piece cannon = dummyBoard.get(from);

        assertThatCode(() -> cannon.move(from, to, pathChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("출발 지점과 도착 지점이 궁성 내부일 경우 도착 위치가 궁성의 외곽이고 경로에 포가 있다면 예외가 발생한다")
    void throwException_When_FromAndToIsInPalaceAndOuterPerimeterButCannonInPath() {
        dummyBoard.put(new Position(4, 1), new Piece(Camp.HAN, PieceType.CANNON));
        dummyBoard.put(new Position(4, 2), new Piece(Camp.CHO, PieceType.CANNON));
        dummyBoard.put(new Position(5, 2), new Piece(Camp.HAN, PieceType.CANNON));
        pathChecker = new Board(dummyBoard);

        Position from = new Position(4, 1);
        Position to1 = new Position(4, 3);
        Position to2 = new Position(6, 3);

        Piece cannon = dummyBoard.get(from);

        assertThatThrownBy(() -> cannon.move(from, to1, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> cannon.move(from, to2, pathChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
