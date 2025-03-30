package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class SoldierTest {
    @Test
    @DisplayName("졸 전진 테스트")
    void choSoldierForwardTest() {
        //given
        Soldier soldier = new Soldier(Team.CHO, new Position(7, 3));
        Position arrivedPosition = new Position(6, 3);
        //when
        Piece movedPiece = soldier.move(arrivedPosition);
        //then
        assertThat(movedPiece.matchesPosition(new Position(6, 3))).isTrue();
    }

    @Test
    @DisplayName("병 전진 테스트")
    void hanSoldierForwardTest() {
        //given
        Soldier soldier = new Soldier(Team.HAN, new Position(4, 3));
        Position arrivedPosition = new Position(5, 3);
        //when
        Piece movedPiece = soldier.move(arrivedPosition);
        //then
        assertThat(movedPiece.matchesPosition(new Position(5, 3))).isTrue();
    }

    @Test
    @DisplayName("졸 오른쪽 이동 테스트")
    void choSoldierRightStepTest() {
        //given
        Soldier soldier = new Soldier(Team.CHO, new Position(7, 3));
        Position arrivedPosition = new Position(7, 4);
        //when
        Piece movedPiece = soldier.move(arrivedPosition);
        //then
        assertThat(movedPiece.matchesPosition(new Position(7, 4))).isTrue();
    }

    @Test
    @DisplayName("졸 왼쪽 이동 테스트")
    void choSoldierLeftStepTest() {
        //given
        Soldier soldier = new Soldier(Team.CHO, new Position(7, 3));
        Position arrivedPosition = new Position(7, 2);
        //when
        Piece movedPiece = soldier.move(arrivedPosition);
        //then
        assertThat(movedPiece.matchesPosition(new Position(7, 2))).isTrue();
    }

    @Test
    @DisplayName("졸 후진 시 이동 불가 예외 발생 테스트")
    void choSoldierBackStepExceptionTest() {
        //given
        Soldier soldier = new Soldier(Team.CHO, new Position(7, 3));
        Position arrivedPosition = new Position(8, 3);
        //when & then
        assertThatThrownBy(() -> soldier.move(arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("병 후진 시 이동 불가 예외 발생 테스트")
    void hanSoldierBackStepExceptionTest() {
        //given
        Soldier soldier = new Soldier(Team.HAN, new Position(4, 3));
        Position arrivedPosition = new Position(3, 3);
        //when & then
        assertThatThrownBy(() -> soldier.move(arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸/병 전진 위치에 아군 존재시 이동 불가 예외 발생 테스트")
    void outOfBoardTest() {
        //given
        Soldier soldier = new Soldier(Team.CHO, new Position(10, 3));
        Position arrivedPosition = new Position(11, 3);
        //when & then
        assertThatThrownBy(() -> soldier.move(arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸/병 궁성 내 이동 테스트")
    void moveSoldierInPalaceTest() {
        //given
        Soldier soldier = new Soldier(Team.CHO, new Position(3, 5));
        Position arrivedPosition = new Position(3, 4);

        //when
        Piece movedPiece = soldier.move(arrivedPosition);
        //then
        assertThat(movedPiece.matchesPosition(new Position(3, 4))).isTrue();
    }
}
