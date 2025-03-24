package janggi.piece;

import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.team.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class SoldierTest {
    @Test
    @DisplayName("졸 전진 테스트")
    void choSoldierForwardTest() {
        //given
        Soldier soldier = new Soldier(Team.CHO, new Position(new Row(7), new Column(3)));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(new Row(7), new Column(2))));
        Position arrivedPosition = new Position(new Row(6), new Column(3));
        //when
        soldier.move(arrivedPosition, positioningPiece);
        //then
        assertThat(soldier.matchesPosition(new Position(new Row(6), new Column(3)))).isTrue();
    }

    @Test
    @DisplayName("병 전진 테스트")
    void hanSoldierForwardTest() {
        //given
        Soldier soldier = new Soldier(Team.HAN, new Position(new Row(4), new Column(3)));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(new Row(4), new Column(2))));
        Position arrivedPosition = new Position(new Row(5), new Column(3));
        //when
        soldier.move(arrivedPosition, positioningPiece);
        //then
        assertThat(soldier.matchesPosition(new Position(new Row(5), new Column(3)))).isTrue();
    }

    @Test
    @DisplayName("졸 오른쪽 이동 테스트")
    void choSoldierRightStepTest() {
        //given
        Soldier soldier = new Soldier(Team.CHO, new Position(new Row(7), new Column(3)));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(new Row(7), new Column(2))));
        Position arrivedPosition = new Position(new Row(7), new Column(4));
        //when
        soldier.move(arrivedPosition, positioningPiece);
        //then
        assertThat(soldier.matchesPosition(new Position(new Row(7), new Column(4)))).isTrue();
    }

    @Test
    @DisplayName("졸 왼쪽 이동 테스트")
    void choSoldierLeftStepTest() {
        //given
        Soldier soldier = new Soldier(Team.CHO, new Position(new Row(7), new Column(3)));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(new Row(7), new Column(4))));
        Position arrivedPosition = new Position(new Row(7), new Column(2));
        //when
        soldier.move(arrivedPosition, positioningPiece);
        //then
        assertThat(soldier.matchesPosition(new Position(new Row(7), new Column(2)))).isTrue();
    }

    @Test
    @DisplayName("졸 후진 시 이동 불가 예외 발생 테스트")
    void choSoldierBackStepExceptionTest() {
        //given
        Soldier soldier = new Soldier(Team.CHO, new Position(new Row(7), new Column(3)));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(new Row(7), new Column(4))));
        Position arrivedPosition = new Position(new Row(8), new Column(3));
        //when
        //then
        assertThatThrownBy(() -> soldier.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("병 후진 시 이동 불가 예외 발생 테스트")
    void hanSoldierBackStepExceptionTest() {
        //given
        Soldier soldier = new Soldier(Team.HAN, new Position(new Row(4), new Column(3)));
        List<Piece> positioningPiece = List.of(new Soldier(Team.HAN, new Position(new Row(4), new Column(4))));
        Position arrivedPosition = new Position(new Row(3), new Column(3));
        //when
        //then
        assertThatThrownBy(() -> soldier.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸/병 전진 위치에 아군 존재시 이동 불가 예외 발생 테스트")
    void soldierStepExceptionTest() {
        //given
        Soldier soldier = new Soldier(Team.CHO, new Position(new Row(7), new Column(3)));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(new Row(7), new Column(2))));
        Position arrivedPosition = new Position(new Row(7), new Column(2));
        //when
        //then
        assertThatThrownBy(() -> soldier.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸/병 전진 위치에 아군 존재시 이동 불가 예외 발생 테스트")
    void outOfBoardTest() {
        //given
        Soldier soldier = new Soldier(Team.CHO, new Position(new Row(10), new Column(3)));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(new Row(7), new Column(2))));
        Position arrivedPosition = new Position(new Row(11), new Column(3));
        //when
        //then
        assertThatThrownBy(() -> soldier.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }
}
