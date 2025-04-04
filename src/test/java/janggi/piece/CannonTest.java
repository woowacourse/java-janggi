package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CannonTest {
    @Test
    @DisplayName("포 전진 테스트")
    void cannonUpTest() {
        //given
        Position startPosition = new Position(7, 3);
        Position arrivedPosition = new Position(3, 3);
        Piece cannon = new DefaultPiece(Team.CHO, PieceType.CANNON);
        //when
        Position movedPosition = cannon.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("포 후진 테스트")
    void cannonDownTest() {
        //given
        Position startPosition = new Position(3, 3);
        Position arrivedPosition = new Position(7, 3);
        Piece cannon = new DefaultPiece(Team.CHO, PieceType.CANNON);
        //when
        Position movedPosition = cannon.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("포 오른쪽 이동 테스트")
    void cannonRightTest() {
        //given
        Position startPosition = new Position(3, 5);
        Position arrivedPosition = new Position(3, 9);
        Piece cannon = new DefaultPiece(Team.CHO, PieceType.CANNON);
        //when
        Position movedPosition = cannon.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("포 왼쪽 이동 테스트")
    void cannonLeftTest() {
        //given
        Position startPosition = new Position(3, 5);
        Position arrivedPosition = new Position(3, 1);
        Piece cannon = new DefaultPiece(Team.CHO, PieceType.CANNON);
        //when
        Position movedPosition = cannon.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("포 궁성 내 좌측 상단 대각선 이동 테스트")
    void moveCannonLeftUpCrossInPalaceTest() {
        //given
        Position startPosition = new Position(10, 6);
        Position arrivedPosition = new Position(8, 4);
        Piece cannon = new DefaultPiece(Team.CHO, PieceType.CANNON);
        //when
        Position movedPosition = cannon.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }


    @Test
    @DisplayName("포 궁성 내 우측 상단 대각선 이동 테스트")
    void moveCannonRightUpCrossInPalaceTest() {
        //given
        Position startPosition = new Position(10, 4);
        Position arrivedPosition = new Position(8, 6);
        Piece cannon = new DefaultPiece(Team.CHO, PieceType.CANNON);
        //when
        Position movedPosition = cannon.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("포가 장기판 범위 밖 좌표로 이동할 경우 예외 발생")
    void outOfBoardTest() {
        //given
        Position startPosition = new Position(10, 5);
        Position arrivedPosition = new Position(10, 0);
        Piece cannon = new DefaultPiece(Team.CHO, PieceType.CANNON);
        //when & then
        Assertions.assertThatThrownBy(() -> cannon.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

}
