package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HorseTest {
    @Test
    @DisplayName("마 전진 우대각 이동 테스트")
    void horseUpRightUpTest() {
        //given
        Horse horse = new Horse(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(2, 5)));
        Position arrivedPosition = new Position(5, 4);
        //when
        horse.move(arrivedPosition, positioningPiece);
        //then
        assertThat(horse.matchesPosition(new Position(5, 4))).isTrue();
    }

    @Test
    @DisplayName("마 전진 좌대각 이동 테스트")
    void horseUpLeftUpTest() {
        //given
        Horse horse = new Horse(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(2, 5)));
        Position arrivedPosition = new Position(5, 2);
        //when
        horse.move(arrivedPosition, positioningPiece);
        //then
        assertThat(horse.matchesPosition(new Position(5, 2))).isTrue();
    }

    @Test
    @DisplayName("마 좌 위대각 이동 테스트")
    void horseLeftLeftUpTest() {
        //given
        Horse horse = new Horse(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(2, 5)));
        Position arrivedPosition = new Position(6, 1);
        //when
        horse.move(arrivedPosition, positioningPiece);
        //then
        assertThat(horse.matchesPosition(new Position(6, 1))).isTrue();
    }

    @Test
    @DisplayName("마 좌 아래대각 이동 테스트")
    void horseLeftLeftDownTest() {
        //given
        Horse horse = new Horse(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(2, 5)));
        Position arrivedPosition = new Position(8, 1);
        //when
        horse.move(arrivedPosition, positioningPiece);
        //then
        assertThat(horse.matchesPosition(new Position(8, 1))).isTrue();
    }

    @Test
    @DisplayName("마 우 위대각 이동 테스트")
    void horseRightRightUpTest() {
        //given
        Horse horse = new Horse(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(2, 5)));
        Position arrivedPosition = new Position(6, 5);
        //when
        horse.move(arrivedPosition, positioningPiece);
        //then
        assertThat(horse.matchesPosition(new Position(6, 5))).isTrue();
    }

    @Test
    @DisplayName("마 우 아래대각 이동 테스트")
    void horseRightRightDownTest() {
        //given
        Horse horse = new Horse(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(2, 5)));
        Position arrivedPosition = new Position(8, 5);
        //when
        horse.move(arrivedPosition, positioningPiece);
        //then
        assertThat(horse.matchesPosition(new Position(8, 5))).isTrue();
    }

    @Test
    @DisplayName("마 아래 우대각 이동 테스트")
    void horseDownRightDownTest() {
        //given
        Horse horse = new Horse(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(2, 5)));
        Position arrivedPosition = new Position(9, 4);
        //when
        horse.move(arrivedPosition, positioningPiece);
        //then
        assertThat(horse.matchesPosition(new Position(9, 4))).isTrue();
    }

    @Test
    @DisplayName("마 아래 좌대각 이동 테스트")
    void horseDownLeftDownTest() {
        //given
        Horse horse = new Horse(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(2, 5)));
        Position arrivedPosition = new Position(9, 2);
        //when
        horse.move(arrivedPosition, positioningPiece);
        //then
        assertThat(horse.matchesPosition(new Position(9, 2))).isTrue();
    }

    @Test
    @DisplayName("마 장기판 밖으로 이동 시 예외 발생")
    void outOfBoardTest() {
        //given
        Horse horse = new Horse(Team.CHO, new Position(9, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(2, 5)));
        Position arrivedPosition = new Position(11, 2);
        //when & then
        assertThatThrownBy(() -> horse.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("마 이동 경로에 장애물이 있는 경우 이동 불가 예외 발생")
    void isObstacleExceptionTest() {
        //given
        Horse horse = new Horse(Team.CHO, new Position(7, 3));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(6, 3)));
        Position arrivedPosition = new Position(5, 2);
        //when & then
        assertThatThrownBy(() -> horse.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }
}
