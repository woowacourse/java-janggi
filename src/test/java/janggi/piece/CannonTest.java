package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CannonTest {
    @Test
    @DisplayName("포 전진 테스트")
    void cannonUpTest() {
        //given
        Cannon cannon = new Cannon(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(5, 3)));
        Position arrivedPosition = new Position(3, 3);
        //when
        cannon.move(arrivedPosition, positioningPiece);
        //then
        assertThat(cannon.matchesPosition(new Position(3, 3))).isTrue();
    }

    @Test
    @DisplayName("포 후진 테스트")
    void cannonDownTest() {
        //given
        Cannon cannon = new Cannon(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(8, 3)));
        Position arrivedPosition = new Position(9, 3);
        //when
        cannon.move(arrivedPosition, positioningPiece);
        //then
        assertThat(cannon.matchesPosition(new Position(9, 3))).isTrue();
    }

    @Test
    @DisplayName("포 우측 테스트")
    void CannonRightTest() {
        //given
        Cannon cannon = new Cannon(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(7, 4)));
        Position arrivedPosition = new Position(7, 5);
        //when
        cannon.move(arrivedPosition, positioningPiece);
        //then
        assertThat(cannon.matchesPosition(new Position(7, 5))).isTrue();
    }

    @Test
    @DisplayName("포 좌측 테스트")
    void cannonLeftTest() {
        //given
        Cannon cannon = new Cannon(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(7, 2)));
        Position arrivedPosition = new Position(7, 1);
        //when
        cannon.move(arrivedPosition, positioningPiece);
        //then
        assertThat(cannon.matchesPosition(new Position(7, 1))).isTrue();
    }

    @Test
    @DisplayName("포가 장기판 범위 밖 좌표로 이동할 경우 예외 발생")
    void outOfBoardTest() {
        //given
        Cannon cannon = new Cannon(Team.CHO, new Position(7, 6));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(7, 7)));
        Position arrivedPosition = new Position(7, 11);
        //when & then
        assertThatThrownBy(() -> cannon.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("포 이동 경로에 장애물이 없는 경우 예외 발생")
    void isObstacleExceptionTest() {
        //given
        Cannon cannon = new Cannon(Team.CHO, new Position(7, 6));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(1, 7)));
        Position arrivedPosition = new Position(7, 1);
        //when & then
        assertThatThrownBy(() -> cannon.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("포 이동 경로에 장애물이 두개 있는 경우 예외 발생")
    void isMultiObstacleExceptionTest() {
        //given
        Cannon cannon = new Cannon(Team.CHO, new Position(7, 6));
        List<Piece> positioningPiece = List.of(
                new Soldier(Team.CHO, new Position(7, 5)),
                new Soldier(Team.CHO, new Position(7, 4))
        );
        Position arrivedPosition = new Position(7, 1);
        //when & then
        assertThatThrownBy(() -> cannon.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("포 이동 경로에 장애물이 넘을 수 없는 대상(포)인 경우 예외 발생")
    void canNorJumpExceptionTest() {
        //given
        Cannon cannon = new Cannon(Team.CHO, new Position(7, 6));
        List<Piece> positioningPiece = List.of(
                new Cannon(Team.CHO, new Position(7, 4))
        );
        Position arrivedPosition = new Position(7, 1);
        //when & then
        assertThatThrownBy(() -> cannon.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }

}
