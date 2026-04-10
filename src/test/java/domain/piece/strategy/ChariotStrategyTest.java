package domain.piece.strategy;

import domain.board.Board;
import domain.board.BoardChecker;
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
    BoardChecker boardChecker;

    @BeforeEach
    void setUp() {
        dummyBoard = new HashMap<>();

        dummyBoard.put(
                new Position(1, 1),
                new Piece(Camp.HAN, PieceType.CHARIOT)
        );
        dummyBoard.put(
                new Position(1, 2),
                new Piece(Camp.HAN, PieceType.SOLDIER)
        );
        dummyBoard.put(
                new Position(1, 5),
                new Piece(Camp.CHO, PieceType.SOLDIER)
        );

        boardChecker = new Board(dummyBoard);
    }

    @Test
    @DisplayName("경로상의 기물 리스트가 비어있지 않은 경우 예외를 발생한다.")
    void throwException_When_PiecesAreNotEmpty() {
        Position from = new Position(1, 1);
        Position to = new Position(1, 5);

        Piece chariot = dummyBoard.get(from);

        assertThatThrownBy(() -> chariot.validateMove(from, to, boardChecker))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차 이동 경로 상에 기물이 존재하여 움직일 수 없습니다.");
    }

    @Test
    @DisplayName("경로상의 기물 리스트가 비어있는 경우 정상 이동한다.")
    void moveSuccessfully_When_PiecesIsEmpty() {
        Position from = new Position(1, 1);
        Position to = new Position(7, 1);

        Piece chariot = dummyBoard.get(from);

        assertThatCode(() -> chariot.validateMove(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("차는 궁성 대각선으로 이동할 수 있다.")
    void moveSuccessfully_When_PalaceDiagonalMove() {
        Position from = new Position(4, 1);
        Position to = new Position(6, 3);

        dummyBoard.clear();
        dummyBoard.put(from, new Piece(Camp.HAN, PieceType.CHARIOT));
        boardChecker = new Board(dummyBoard);

        Piece chariot = dummyBoard.get(from);

        assertThatCode(() -> chariot.validateMove(from, to, boardChecker))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("차의 궁성 대각선 경로가 막혀 있으면 이동할 수 없다.")
    void throwException_When_PalaceDiagonalPathBlocked() {
        Position from = new Position(4, 1);
        Position to = new Position(6, 3);

        dummyBoard.clear();
        dummyBoard.put(from, new Piece(Camp.HAN, PieceType.CHARIOT));
        dummyBoard.put(new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL));
        boardChecker = new Board(dummyBoard);

        Piece chariot = dummyBoard.get(from);

        assertThatThrownBy(() -> chariot.validateMove(from, to, boardChecker))
                .hasMessage("[ERROR] 차 이동 경로 상에 기물이 존재하여 움직일 수 없습니다.");
    }
}
