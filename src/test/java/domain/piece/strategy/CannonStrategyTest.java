package domain.piece.strategy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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

public class CannonStrategyTest {

    Map<Position, Piece> dummyBoard;
    BoardChecker boardChecker;

    @BeforeEach
    void setUp() {
        dummyBoard = new HashMap<>();
    }

    @Test
    @DisplayName("경로상에 넘을 기물이 없을 경우 예외를 발생한다.")
    void throwException_When_NoPieceExistsInPath() {
        dummyBoard.put(
                new Position(1, 1),
                new Piece(Camp.HAN, PieceType.CANNON)
        );
        boardChecker = new Board(dummyBoard);

        Position from = new Position(1, 1);
        Position to = new Position(1, 5);

        Piece cannon = dummyBoard.get(from);

        assertThatThrownBy(() -> cannon.move(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경로상에 넘을 기물이 두 개 이상일 경우 예외를 발생한다.")
    void throwException_When_MultiplePiecesExistInPath() {
        dummyBoard.put(
                new Position(1, 1),
                new Piece(Camp.HAN, PieceType.CANNON)
        );
        dummyBoard.put(
                new Position(1, 2),
                new Piece(Camp.HAN, PieceType.SOLDIER)
        );
        dummyBoard.put(
                new Position(1, 4),
                new Piece(Camp.CHO, PieceType.SOLDIER)
        );
        boardChecker = new Board(dummyBoard);

        Position from = new Position(1, 1);
        Position to = new Position(1, 5);

        Piece cannon = dummyBoard.get(from);

        assertThatThrownBy(() -> cannon.move(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경로상에 포가 존재할 경우 예외를 발생한다.")
    void throwException_When_CannonIsExists() {
        dummyBoard.put(
                new Position(1, 1),
                new Piece(Camp.HAN, PieceType.CANNON)
        );
        dummyBoard.put(
                new Position(1, 3),
                new Piece(Camp.CHO, PieceType.CANNON)
        );
        boardChecker = new Board(dummyBoard);

        Position from = new Position(1, 1);
        Position to = new Position(1, 5);

        Piece cannon = dummyBoard.get(from);

        assertThatThrownBy(() -> cannon.move(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경로상에 포가 아닌 기물이 정확히 한 개 존재할 경우 정상 이동한다.")
    void moveSuccessfully_When_NoCannonInPathAndExactlyOnePiece() {
        dummyBoard.put(
                new Position(1, 1),
                new Piece(Camp.HAN, PieceType.CANNON)
        );
        dummyBoard.put(
                new Position(1, 3),
                new Piece(Camp.CHO, PieceType.SOLDIER)
        );
        boardChecker = new Board(dummyBoard);

        Position from = new Position(1, 1);
        Position to = new Position(1, 5);

        Piece cannon = dummyBoard.get(from);

        assertThatCode(() -> cannon.move(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("도착 위치에 존재하는 기물이 포일 경우 움직일 수 없다.")
    void throwException_When_CannonIsOnDestination() {
        dummyBoard.put(
                new Position(1, 1),
                new Piece(Camp.HAN, PieceType.CANNON)
        );
        dummyBoard.put(
                new Position(1, 3),
                new Piece(Camp.CHO, PieceType.SOLDIER)
        );
        dummyBoard.put(
                new Position(1, 5),
                new Piece(Camp.CHO, PieceType.CANNON)
        );
        boardChecker = new Board(dummyBoard);

        Position from = new Position(1, 1);
        Position to = new Position(1, 5);

        Piece cannon = dummyBoard.get(from);

        assertThatThrownBy(() -> cannon.move(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
