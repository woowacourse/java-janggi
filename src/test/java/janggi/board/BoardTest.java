package janggi.board;

import janggi.dao.BoardDao;
import janggi.dao.DatabaseConnector;
import janggi.piece.*;
import janggi.position.Position;
import janggi.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class BoardTest {

    private static final PieceGenerator pieceGenerator = new PieceGenerator();
    private static final BoardDao boardDao = new BoardDao(new DatabaseConnector());

    @Test
    @DisplayName("보드에서 선택한 기물을 다른 기물로 이동 시킬 수 있는지 확인")
    void boardMoveTest() {
        //given
        Board board = new Board(pieceGenerator.generateInitialPieces(TableOption.EHHE, TableOption.HEEH));
        Position startPosition = new Position(10, 1);
        Position arrivedPosition = new Position(8, 1);
        //when
        board.movePiece(new Turn(Team.CHO), startPosition, arrivedPosition);
        Map<Position, Piece> positionedPieces = board.getLocatedPieces();
        Piece findPiece = positionedPieces.get(arrivedPosition);
        //then
        Assertions.assertThat(findPiece).isEqualTo(new DefaultPiece(Team.CHO, PieceType.CHARIOT));
    }

    @Test
    @DisplayName("현재 턴에 맞지 않은 기물 선택 시 예외 발생")
    void checkTurnTest() {
        //given
        Board board = new Board(pieceGenerator.generateInitialPieces(TableOption.EHHE, TableOption.HEEH));
        Position startPosition = new Position(10, 1);
        Position arrivedPosition = new Position(8, 1);
        //when & then
        Assertions.assertThatThrownBy(() -> board.movePiece(new Turn(Team.HAN), startPosition, arrivedPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("기물이 없는 위치를 선택할 경우 예외 발생")
    void notExistsPieceTest() {
        //given
        Board board = new Board(pieceGenerator.generateInitialPieces(TableOption.EHHE, TableOption.HEEH));
        Position startPosition = new Position(9, 1);
        Position arrivedPosition = new Position(8, 1);
        //when & then
        Assertions.assertThatThrownBy(() -> board.movePiece(new Turn(Team.CHO), startPosition, arrivedPosition));
    }

    @Test
    @DisplayName("포 이동 경로에 넘을 수 있는 장애물이 존재하는 경우 이동")
    void cannonMoveTest() {
        Board board = new Board(new HashMap<>(Map.of(
                 new Position(8, 2), new DefaultPiece(Team.CHO, PieceType.CANNON),
                 new Position(8, 3), new DefaultPiece(Team.CHO, PieceType.ELEPHANT))));

        assertThatCode(
                () -> board.movePiece(new Turn(Team.CHO), new Position(8, 2), new Position(8, 5))
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("포 이동 경로에 넘을 수 없는 장애물이 존재하는 경우 예외 발생")
    void cannonMoveExceptionTest() {
        Board board = new Board(new HashMap<>(Map.of(
                new Position(8, 2), new DefaultPiece(Team.CHO, PieceType.CANNON),
                new Position(8, 3), new DefaultPiece(Team.CHO, PieceType.CANNON))));

        assertThatThrownBy(
                () -> board.movePiece(new Turn(Team.CHO), new Position(8, 2), new Position(8, 5))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("포 이동 경로에 장애물이 여러개 존재하는 경우 예외 발생")
    void hasManyObstacleExceptionTest() {
        Board board = new Board(new HashMap<>(Map.of(
                new Position(8, 2), new DefaultPiece(Team.CHO, PieceType.CANNON),
                new Position(8, 3), new DefaultPiece(Team.CHO, PieceType.ELEPHANT),
                new Position(8, 4), new DefaultPiece(Team.CHO, PieceType.ELEPHANT))));

        assertThatThrownBy(
                () -> board.movePiece(new Turn(Team.CHO), new Position(8, 2), new Position(8, 5))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("makeCannonInPalace")
    @DisplayName("포 궁성 내 대각선 이동 테스트")
    void moveCrossWithinPalaceTest(Position cannonPosition, Position abstaclePosition, Position arrivedPosition) {
        //given
        Board board = new Board(new HashMap<>(Map.of(
                cannonPosition, new DefaultPiece(Team.CHO, PieceType.CANNON),
                abstaclePosition, new PalacePiece(Team.CHO, PieceType.KING))));

        //when & then
        assertThatCode(
                () -> board.movePiece(new Turn(Team.CHO), cannonPosition, arrivedPosition)
        ).doesNotThrowAnyException();
    }

    static Stream<Arguments> makeCannonInPalace() {
        return Stream.of(
                Arguments.arguments(new Position(8, 4), new Position(9, 5), new Position(10, 6)),
                Arguments.arguments(new Position(10, 4), new Position(10, 5), new Position(10, 9)),
                Arguments.arguments(new Position(8, 4), new Position(9, 5), new Position(10, 6))
        );
    }

    @ParameterizedTest
    @MethodSource("makeExceptionCannonInPalace")
    @DisplayName("포 궁성 내 대각선 이동 불가 테스트")
    void moveCrossWithinPalaceExceptionTest(Position cannonPosition, Position arrivedPosition, Piece abstaclePiece) {
        //given
        Board board = new Board(new HashMap<>(Map.of(
                cannonPosition, new DefaultPiece(Team.CHO, PieceType.CANNON),
                arrivedPosition,abstaclePiece
        )));
        //when & then
        assertThatThrownBy(
                () -> board.movePiece(new Turn(Team.CHO), cannonPosition, arrivedPosition)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> makeExceptionCannonInPalace() {
        return Stream.of(
                Arguments.arguments(new Position(10, 4),new Position(9, 5), new PalacePiece(Team.CHO, PieceType.KING)),
                Arguments.arguments(new Position(10, 4),new Position(7, 7), new DefaultPiece(Team.CHO,PieceType.CANNON)),
                Arguments.arguments(new Position(10, 4),new Position(9, 3), new PalacePiece(Team.CHO, PieceType.KING))
        );
    }

    @Test
    @DisplayName("차 궁성 내 대각선 이동 테스트")
    void moveCrossWithinPalaceTest() {
        //given
        Board board = new Board(new HashMap<>(Map.of(
                new Position(10, 4), new DefaultPiece(Team.CHO, PieceType.CHARIOT)
        )));

        //when & then
        assertThatCode(
                () -> board.movePiece(new Turn(Team.CHO), new Position(10, 4), new Position(8, 6))
        ).doesNotThrowAnyException();
    }
}
