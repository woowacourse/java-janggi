package janggi.board;

import janggi.piece.*;
import janggi.position.Position;
import janggi.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BoardTest {

    private static final PieceGenerator pieceGenerator = new PieceGenerator();

    @Test
    @DisplayName("보드에서 선택한 기물을 다른 기물로 이동 시킬 수 있는지 확인")
    void boardMoveTest() {
        //given
        Board board = new Board(pieceGenerator.generateInitialPieces(TableOption.EHHE, TableOption.HEEH));
        Position startPosition = new Position(10, 1);
        Position arrivedPosition = new Position(8, 1);
        //when
        board.move(Team.CHO, startPosition, arrivedPosition);
        List<Piece> positionedPieces = board.getLocatedPieces();
        Piece findPiece = positionedPieces.stream()
                .filter(piece -> piece.matchesPosition(arrivedPosition))
                .findFirst()
                .orElseThrow();
        //then
        Assertions.assertThat(findPiece).isEqualTo(new Chariot(Team.CHO,new Position(8, 1)));
    }

    @Test
    @DisplayName("현재 턴에 맞지 않은 기물 선택 시 예외 발생")
    void checkTurnTest() {
        //given
        Board board = new Board(pieceGenerator.generateInitialPieces(TableOption.EHHE, TableOption.HEEH));
        Position startPosition = new Position(10, 1);
        Position arrivedPosition = new Position(8, 1);
        //when & then
        Assertions.assertThatThrownBy(() -> board.move(Team.HAN, startPosition, arrivedPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("기물이 없는 위치를 선택할 경우 예외 발생")
    void notExistsPieceTest() {
        //given
        Board board = new Board(pieceGenerator.generateInitialPieces(TableOption.EHHE, TableOption.HEEH));
        Position startPosition = new Position(9, 1);
        Position arrivedPosition = new Position(8,1);
        //when & then
        Assertions.assertThatThrownBy(() -> board.move(Team.CHO, startPosition, arrivedPosition));
    }

    @Test
    @DisplayName("포 이동 경로에 넘을 수 있는 장애물이 존재하는 경우 이동")
    void cannonMoveTest() {
        Board board = new Board(List.of(
                new Cannon(Team.CHO,new Position(8,2)),
                new Elephant(Team.CHO,new Position(8,3))
        ));

        assertThatCode(
                ()-> board.move(Team.CHO,new Position(8,2),new Position(8,5))
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("포 이동 경로에 넘을 수 없는 장애물이 존재하는 경우 예외 발생")
    void cannonMoveExceptionTest() {
        Board board = new Board(List.of(
                new Cannon(Team.CHO,new Position(8,2)),
                new Cannon(Team.CHO,new Position(8,3))
        ));

        assertThatThrownBy(
                ()-> board.move(Team.CHO,new Position(8,2),new Position(8,5))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("포 이동 경로에 장애물이 여러개 존재하는 경우 예외 발생")
    void hasManyObstacleExceptionTest() {
        Board board = new Board(List.of(
                new Cannon(Team.CHO,new Position(8,2)),
                new Elephant(Team.CHO,new Position(8,3)),
                new Elephant(Team.CHO,new Position(8,4))
        ));

        assertThatThrownBy(
                ()-> board.move(Team.CHO,new Position(8,2),new Position(8,5))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    // todo 기물 이동 경로에 장애물 존재 시 예외 발생 테스트 작성
    // todo 포 관련 예외 발생 테스트
    // todo 기물 이동 위치에 아군 말 있는 경우 예외 테스트 작성
}
