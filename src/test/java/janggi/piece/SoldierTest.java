package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SoldierTest {
    @Test
    @DisplayName("졸 전진 테스트")
    void soldierChoUpTest() {
        //given
        Position startPosition = new Position(5, 3);
        Position arrivedPosition = new Position(4, 3);
        Piece soldier = new DefaultPiece(Team.CHO, PieceType.SOLDIER);
        //when
        Position movedPosition = soldier.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("병 전진 테스트")
    void soldierHanUpTest() {
        //given
        Position startPosition = new Position(4, 3);
        Position arrivedPosition = new Position(5, 3);
        Piece soldier = new DefaultPiece(Team.HAN, PieceType.SOLDIER);
        //when
        Position movedPosition = soldier.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("졸/병 오른쪽 이동 테스트")
    void soldierRightTest() {
        //given
        Position startPosition = new Position(4, 3);
        Position arrivedPosition = new Position(4, 4);
        Piece soldier = new DefaultPiece(Team.CHO, PieceType.SOLDIER);
        //when
        Position movedPosition = soldier.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("졸/병 왼쪽 이동 테스트")
    void soldierLeftTest() {
        //given
        Position startPosition = new Position(4, 3);
        Position arrivedPosition = new Position(4, 2);
        Piece soldier = new DefaultPiece(Team.CHO, PieceType.SOLDIER);
        //when
        Position movedPosition = soldier.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("졸 후진 시 예외 테스트")
    void soldierChoDownExceptionTest() {
        //given
        Position startPosition = new Position(7, 3);
        Position arrivedPosition = new Position(8, 3);
        Piece soldier = new DefaultPiece(Team.CHO, PieceType.SOLDIER);
        //when & then
        Assertions.assertThatThrownBy(() -> soldier.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("병 후진 시 예외 테스트")
    void soldierHanDownExceptionTest() {
        //given
        Position startPosition = new Position(4, 3);
        Position arrivedPosition = new Position(3, 3);
        Piece soldier = new DefaultPiece(Team.HAN, PieceType.SOLDIER);
        //when & then
        Assertions.assertThatThrownBy(() -> soldier.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸 궁성 내 우측 상단 대각선 이동 테스트")
    void soldierChoRightUpCrossTest() {
        //given
        Position startPosition = new Position(2, 5);
        Position arrivedPosition = new Position(1, 6);
        Piece soldier = new DefaultPiece(Team.CHO, PieceType.SOLDIER);
        //when
        Position movedPosition = soldier.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("졸 궁성 내 좌측 상단 대각선 이동 테스트")
    void soldierChoLeftUpCrossTest() {
        //given
        Position startPosition = new Position(2, 5);
        Position arrivedPosition = new Position(1, 4);
        Piece soldier = new DefaultPiece(Team.CHO, PieceType.SOLDIER);
        //when
        Position movedPosition = soldier.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("졸 궁성 내 좌측 하단 대각선 이동 시 예외 테스트")
    void soldierChoLeftDownCrossExceptionTest() {
        //given
        Position startPosition = new Position(2, 5);
        Position arrivedPosition = new Position(3, 4);
        Piece soldier = new DefaultPiece(Team.CHO, PieceType.SOLDIER);
        //when & then
        Assertions.assertThatThrownBy(() -> soldier.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸 궁성 내 우측 하단 대각선 이동 시 예외 테스트")
    void soldierChoRightDownCrossExceptionTest() {
        //given
        Position startPosition = new Position(2, 5);
        Position arrivedPosition = new Position(3, 6);
        Piece soldier = new DefaultPiece(Team.CHO, PieceType.SOLDIER);
        //when & then
        Assertions.assertThatThrownBy(() -> soldier.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    // todo han
    @Test
    @DisplayName("한 궁성 내 우측 하단 대각선 이동 테스트")
    void soldierHanRightDownCrossTest() {
        //given
        Position startPosition = new Position(9, 5);
        Position arrivedPosition = new Position(10, 6);
        Piece soldier = new DefaultPiece(Team.HAN, PieceType.SOLDIER);
        //when
        Position movedPosition = soldier.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("한 궁성 내 좌측 하단 대각선 이동 테스트")
    void soldierHanLeftDownCrossTest() {
        //given
        Position startPosition = new Position(9, 5);
        Position arrivedPosition = new Position(10, 4);
        Piece soldier = new DefaultPiece(Team.HAN, PieceType.SOLDIER);
        //when
        Position movedPosition = soldier.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("한 궁성 내 좌측 상단 대각선 이동 시 예외 테스트")
    void soldierHanLeftUpCrossExceptionTest() {
        //given
        Position startPosition = new Position(9, 5);
        Position arrivedPosition = new Position(8, 4);
        Piece soldier = new DefaultPiece(Team.HAN, PieceType.SOLDIER);
        //when & then
        Assertions.assertThatThrownBy(() -> soldier.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("한 궁성 내 우측 상단 대각선 이동 시 예외 테스트")
    void soldierHanRightUpCrossExceptionTest() {
        //given
        Position startPosition = new Position(9, 5);
        Position arrivedPosition = new Position(8, 6);
        Piece soldier = new DefaultPiece(Team.HAN, PieceType.SOLDIER);
        //when & then
        Assertions.assertThatThrownBy(() -> soldier.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("졸 궁성 밖 대각선 이동 시 예외 테스트")
    void soldierChoCrossExceptionTest() {
        //given
        Position startPosition = new Position(4, 3);
        Position arrivedPosition = new Position(3, 4);
        Piece soldier = new DefaultPiece(Team.CHO, PieceType.SOLDIER);
        //when & then
        Assertions.assertThatThrownBy(() -> soldier.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("한 궁성 밖 대각선 이동 시 예외 테스트")
    void soldierHanCrossExceptionTest() {
        //given
        Position startPosition = new Position(4, 3);
        Position arrivedPosition = new Position(3, 4);
        Piece soldier = new DefaultPiece(Team.HAN, PieceType.SOLDIER);
        //when & then
        Assertions.assertThatThrownBy(() -> soldier.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸 궁성 내 대각선 불가 방향 이동 시 예외 테스트")
    void soldierChoUnavailableCrossExceptionTest() {
        //given
        Position startPosition = new Position(3, 5);
        Position arrivedPosition = new Position(2, 6);
        Piece soldier = new DefaultPiece(Team.CHO, PieceType.SOLDIER);
        //when & then
        Assertions.assertThatThrownBy(() -> soldier.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("한 궁성 내 대각선 불가 방향 이동 시 예외 테스트")
    void soldierHanUnavailableCrossExceptionTest() {
        //given
        Position startPosition = new Position(8, 5);
        Position arrivedPosition = new Position(9, 6);
        Piece soldier = new DefaultPiece(Team.HAN, PieceType.SOLDIER);
        //when & then
        Assertions.assertThatThrownBy(() -> soldier.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸 장기판 밖으로 이동 시 예외 발생")
    void soldierOutOfBoardExceptionTest() {
        //given
        Position startPosition = new Position(10, 1);
        Position arrivedPosition = new Position(11, 1);
        Piece soldier = new DefaultPiece(Team.HAN, PieceType.SOLDIER);
        //when & then
        Assertions.assertThatThrownBy(() -> soldier.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }
}
