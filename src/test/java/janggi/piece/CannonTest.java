package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CannonTest {
    @Test
    @DisplayName("포 전진 테스트")
    void cannonUpTest() {
        //given
        Cannon cannon = new Cannon(Team.CHO, new Position(7, 3));
        Position arrivedPosition = new Position(3, 3);
        //when
        Piece movedPiece = cannon.move(arrivedPosition);
        //then
        assertThat(movedPiece.matchesPosition(new Position(3, 3))).isTrue();
    }

    @Test
    @DisplayName("포 후진 테스트")
    void cannonDownTest() {
        //given
        Cannon cannon = new Cannon(Team.CHO, new Position(7, 3));
        Position arrivedPosition = new Position(9, 3);
        //when
        Piece movedPiece = cannon.move(arrivedPosition);
        //then
        assertThat(movedPiece.matchesPosition(new Position(9, 3))).isTrue();
    }

    @Test
    @DisplayName("포 우측 테스트")
    void CannonRightTest() {
        //given
        Cannon cannon = new Cannon(Team.CHO, new Position(7, 3));
        Position arrivedPosition = new Position(7, 5);
        //when
        Piece movedPiece = cannon.move(arrivedPosition);
        //then
        assertThat(movedPiece.matchesPosition(new Position(7, 5))).isTrue();
    }

    @Test
    @DisplayName("포 좌측 테스트")
    void cannonLeftTest() {
        //given
        Cannon cannon = new Cannon(Team.CHO, new Position(7, 3));
        Position arrivedPosition = new Position(7, 1);
        //when
        Piece movedPiece = cannon.move(arrivedPosition);
        //then
        assertThat(movedPiece.matchesPosition(new Position(7, 1))).isTrue();
    }

    @Test
    @DisplayName("포가 장기판 범위 밖 좌표로 이동할 경우 예외 발생")
    void outOfBoardTest() {
        //given
        Cannon cannon = new Cannon(Team.CHO, new Position(7, 6));
        Position arrivedPosition = new Position(7, 11);
        //when & then
        assertThatThrownBy(() -> cannon.move(arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

}
