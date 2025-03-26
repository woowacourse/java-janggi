package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class KingTest {
    @Test
    @DisplayName("왕 전진 테스트")
    void kingForwardTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(7, 2)));
        Position arrivedPosition = new Position(8, 5);
        //when
        king.move(arrivedPosition, positioningPiece);
        //then
        assertThat(king.matchesPosition(new Position(8, 5))).isTrue();
    }

    @Test
    @DisplayName("왕 후진 테스트")
    void kingBackTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(7, 2)));
        Position arrivedPosition = new Position(10, 5);
        //when
        king.move(arrivedPosition, positioningPiece);
        //then
        assertThat(king.matchesPosition(new Position(10, 5))).isTrue();
    }

    @Test
    @DisplayName("왕 오른쪽 이동 테스트")
    void kingRightStepTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(7, 2)));
        Position arrivedPosition = new Position(9, 6);
        //when
        king.move(arrivedPosition, positioningPiece);
        //then
        assertThat(king.matchesPosition(new Position(9, 6))).isTrue();
    }

    @Test
    @DisplayName("왕 왼쪽 이동 테스트")
    void kingLeftStepTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(7, 2)));
        Position arrivedPosition = new Position(9, 4);
        //when
        king.move(arrivedPosition, positioningPiece);
        //then
        assertThat(king.matchesPosition(new Position(9, 4))).isTrue();
    }

    @Test
    @DisplayName("왕 오른쪽 위 이동 테스트")
    void kingRightUpStepTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(7, 2)));
        Position arrivedPosition = new Position(8, 6);
        //when
        king.move(arrivedPosition, positioningPiece);
        //then
        assertThat(king.matchesPosition(new Position(8, 6))).isTrue();
    }

    @Test
    @DisplayName("왕 오른쪽 아래 이동 테스트")
    void kingRightDownStepTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(7, 2)));
        Position arrivedPosition = new Position(10, 6);
        //when
        king.move(arrivedPosition, positioningPiece);
        //then
        assertThat(king.matchesPosition(new Position(10, 6))).isTrue();
    }

    @Test
    @DisplayName("왕 왼쪽 위 이동 테스트")
    void kingLeftUpStepTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(7, 2)));
        Position arrivedPosition = new Position(8, 4);
        //when
        king.move(arrivedPosition, positioningPiece);
        //then
        assertThat(king.matchesPosition(new Position(8, 4))).isTrue();
    }

    @Test
    @DisplayName("왕 왼쪽 아래 이동 테스트")
    void kingLeftDownStepTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(7, 2)));
        Position arrivedPosition = new Position(10, 4);
        //when
        king.move(arrivedPosition, positioningPiece);
        //then
        assertThat(king.matchesPosition(new Position(10, 4))).isTrue();
    }

    @Test
    @DisplayName("왕 전진 위치에 아군 존재시 이동 불가 예외 발생 테스트")
    void kingStepExceptionTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(9, 6)));
        Position arrivedPosition = new Position(9, 6);
        //when & then
        assertThatThrownBy(() -> king.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("왕 장기판 밖으로 이동 시 예외 발생 테스트")
    void outOfBoardExceptionTest() {
        //given
        King king = new King(Team.CHO, new Position(10, 5));
        List<Piece> positioningPiece = List.of(new Soldier(Team.CHO, new Position(9, 6)));
        Position arrivedPosition = new Position(11, 5);
        //when & then
        assertThatThrownBy(() -> king.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }
}
