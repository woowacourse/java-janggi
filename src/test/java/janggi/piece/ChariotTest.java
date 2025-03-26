package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ChariotTest {
    @Test
    @DisplayName("차 전진 테스트")
    void chariotUpTest() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(2, 5)));
        Position arrivedPosition = new Position(3, 3);
        //when
        chariot.move(arrivedPosition, positioningPiece);
        //then
        assertThat(chariot.matchesPosition(new Position(3, 3))).isTrue();
    }

    @Test
    @DisplayName("차 전진2 테스트")
    void chariotUp2Test() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new Position(10, 1));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(2, 5)));
        Position arrivedPosition = new Position(7, 1);
        //when
        chariot.move(arrivedPosition, positioningPiece);
        //then
        assertThat(chariot.matchesPosition(new Position(7, 1))).isTrue();
    }

    @Test
    @DisplayName("차 후진 테스트")
    void chariotDownTest() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(2, 5)));
        Position arrivedPosition = new Position(9, 3);
        //when
        chariot.move(arrivedPosition, positioningPiece);
        //then
        assertThat(chariot.matchesPosition(new Position(9, 3))).isTrue();
    }

    @Test
    @DisplayName("차 우측 테스트")
    void chariotRightTest() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(2, 5)));
        Position arrivedPosition = new Position(7, 8);
        //when
        chariot.move(arrivedPosition, positioningPiece);
        //then
        assertThat(chariot.matchesPosition(new Position(7, 8))).isTrue();
    }

    @Test
    @DisplayName("차 좌측 테스트")
    void chariotLeftTest() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(2, 5)));
        Position arrivedPosition = new Position(7, 1);
        //when
        chariot.move(arrivedPosition, positioningPiece);
        //then
        assertThat(chariot.matchesPosition(new Position(7, 1))).isTrue();
    }

    @Test
    @DisplayName("차가 장기판 범위 밖 좌표로 이동할 경우 예외 발생")
    void outOfBoardTest() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new Position(7, 6));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(3, 7)));
        Position arrivedPosition = new Position(7, 11);
        //when & then
        assertThatThrownBy(() -> chariot.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("차 이동 경로에 장애물이 존재하는 경우 예외 발생")
    void isObstacleExceptionTest() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new Position(7, 6));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(7, 3)));
        Position arrivedPosition = new Position(7, 1);
        //when & then
        assertThatThrownBy(() -> chariot.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }

}
