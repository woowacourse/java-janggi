package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GuardTest {
    @Test
    @DisplayName("사 전진 테스트")
    void guardForwardTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(10, 4));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(9, 5)));
        Position arrivedPosition = new Position(9, 4);
        //when
        guard.move(arrivedPosition, positioningPiece);
        //then
        assertThat(guard.matchesPosition(new Position(9, 4))).isTrue();
    }

    @Test
    @DisplayName("사 후진 테스트")
    void guardBackTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(9, 4));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(9, 5)));
        Position arrivedPosition = new Position(10, 4);
        //when
        guard.move(arrivedPosition, positioningPiece);
        //then
        assertThat(guard.matchesPosition(new Position(10, 4))).isTrue();
    }

    @Test
    @DisplayName("사 오른쪽 이동 테스트")
    void guardRightTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(10, 4));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(9, 5)));
        Position arrivedPosition = new Position(10, 5);
        //when
        guard.move(arrivedPosition, positioningPiece);
        //then
        assertThat(guard.matchesPosition(new Position(10, 5))).isTrue();
    }

    @Test
    @DisplayName("사 왼쪽 이동 테스트")
    void guardLeftTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(10, 6));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(9, 5)));
        Position arrivedPosition = new Position(10, 5);
        //when
        guard.move(arrivedPosition, positioningPiece);
        //then
        assertThat(guard.matchesPosition(new Position(10, 5))).isTrue();
    }

    @Test
    @DisplayName("사 오른쪽 위 이동 테스트")
    void guardRightUpTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(10, 4));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(9, 4)));
        Position arrivedPosition = new Position(9, 5);
        //when
        guard.move(arrivedPosition, positioningPiece);
        //then
        assertThat(guard.matchesPosition(new Position(9, 5))).isTrue();
    }

    @Test
    @DisplayName("사 오른쪽 아래 이동 테스트")
    void guardRightDownTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(8, 4));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(9, 4)));
        Position arrivedPosition = new Position(9, 5);
        //when
        guard.move(arrivedPosition, positioningPiece);
        //then
        assertThat(guard.matchesPosition(new Position(9, 5))).isTrue();
    }

    @Test
    @DisplayName("사 왼쪽 위 이동 테스트")
    void guardLeftUpTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(10, 6));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(9, 4)));
        Position arrivedPosition = new Position(9, 5);
        //when
        guard.move(arrivedPosition, positioningPiece);
        //then
        assertThat(guard.matchesPosition(new Position(9, 5))).isTrue();
    }

    @Test
    @DisplayName("사 왼쪽 아래 이동 테스트")
    void guardLeftDownTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(8, 6));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(9, 4)));
        Position arrivedPosition = new Position(9, 5);
        //when
        guard.move(arrivedPosition, positioningPiece);
        //then
        assertThat(guard.matchesPosition(new Position(9, 5))).isTrue();
    }

    @Test
    @DisplayName("사 전진 위치에 아군 존재시 이동 불가 예외 발생 테스트")
    void guardStepExceptionTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(10, 4));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(9, 5)));
        Position arrivedPosition = new Position(9, 5);
        //when & then
        assertThatThrownBy(() -> guard.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("사 장기판 밖으로 이동 시 예외 발생 테스트")
    void outOfBoardExceptionTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(10, 5));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(9, 6)));
        Position arrivedPosition = new Position(11, 6);
        //when & then
        assertThatThrownBy(() -> guard.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }
}
