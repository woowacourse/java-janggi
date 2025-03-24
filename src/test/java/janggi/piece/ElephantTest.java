package janggi.piece;

import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.team.Team;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ElephantTest {
    @Test
    @DisplayName("상 전진 우대각 우대각 이동 테스트")
    void elephantUpRightUpRightUpTest() {
        //given
        Elephant elephant = new Elephant(Team.CHO, new Position(new Row(7), new Column(4)));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(new Row(2), new Column(5))));
        Position arrivedPosition = new Position(new Row(4), new Column(6));
        //when
        elephant.move(arrivedPosition, positioningPiece);
        //then
        assertThat(elephant.matchesPosition(new Position(new Row(4), new Column(6)))).isTrue();
    }

    @Test
    @DisplayName("상 전진 좌대각 좌대각 이동 테스트")
    void elephantUpLeftUpLeftUpTest() {
        //given
        Elephant elephant = new Elephant(Team.CHO, new Position(new Row(7), new Column(4)));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(new Row(2), new Column(5))));
        Position arrivedPosition = new Position(new Row(4), new Column(2));
        //when
        elephant.move(arrivedPosition, positioningPiece);
        //then
        assertThat(elephant.matchesPosition(new Position(new Row(4), new Column(2)))).isTrue();
    }

    @Test
    @DisplayName("상 좌 위대각 위대각 이동 테스트")
    void elephantLeftLeftUpLeftUpTest() {
        //given
        Elephant elephant = new Elephant(Team.CHO, new Position(new Row(7), new Column(4)));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(new Row(2), new Column(5))));
        Position arrivedPosition = new Position(new Row(5), new Column(1));
        //when
        elephant.move(arrivedPosition, positioningPiece);
        //then
        assertThat(elephant.matchesPosition(new Position(new Row(5), new Column(1)))).isTrue();
    }

    @Test
    @DisplayName("상 좌 아래대각 아래대각 이동 테스트")
    void elephantLeftLeftDownLeftDownTest() {
        //given
        Elephant elephant = new Elephant(Team.CHO, new Position(new Row(7), new Column(4)));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(new Row(2), new Column(5))));
        Position arrivedPosition = new Position(new Row(9), new Column(1));
        //when
        elephant.move(arrivedPosition, positioningPiece);
        //then
        assertThat(elephant.matchesPosition(new Position(new Row(9), new Column(1)))).isTrue();
    }

    @Test
    @DisplayName("상 우 위대각 위대각 이동 테스트")
    void elephantRightRightUpRightUpTest() {
        //given
        Elephant elephant = new Elephant(Team.CHO, new Position(new Row(7), new Column(4)));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(new Row(2), new Column(5))));
        Position arrivedPosition = new Position(new Row(5), new Column(7));
        //when
        elephant.move(arrivedPosition, positioningPiece);
        //then
        assertThat(elephant.matchesPosition(new Position(new Row(5), new Column(7)))).isTrue();
    }

    @Test
    @DisplayName("상 우 아래대각 아래대각 이동 테스트")
    void elephantRightRightDownRightDownTest() {
        //given
        Elephant elephant = new Elephant(Team.CHO, new Position(new Row(7), new Column(4)));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(new Row(2), new Column(5))));
        Position arrivedPosition = new Position(new Row(9), new Column(7));
        //when
        elephant.move(arrivedPosition, positioningPiece);
        //then
        assertThat(elephant.matchesPosition(new Position(new Row(9), new Column(7)))).isTrue();
    }

    @Test
    @DisplayName("상 아래 우대각 우대각 이동 테스트")
    void elephantDownRightDownRightDownTest() {
        //given
        Elephant elephant = new Elephant(Team.CHO, new Position(new Row(7), new Column(4)));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(new Row(2), new Column(5))));
        Position arrivedPosition = new Position(new Row(10), new Column(6));
        //when
        elephant.move(arrivedPosition, positioningPiece);
        //then
        assertThat(elephant.matchesPosition(new Position(new Row(10), new Column(6)))).isTrue();
    }

    @Test
    @DisplayName("상 아래 좌대각 좌대각 이동 테스트")
    void elephantDownLeftDownLeftDownTest() {
        //given
        Elephant elephant = new Elephant(Team.CHO, new Position(new Row(7), new Column(4)));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(new Row(2), new Column(5))));
        Position arrivedPosition = new Position(new Row(10), new Column(2));
        //when
        elephant.move(arrivedPosition, positioningPiece);
        //then
        assertThat(elephant.matchesPosition(new Position(new Row(10), new Column(2)))).isTrue();
    }

    @Test
    @DisplayName("상 장기판 밖으로 이동 시 예외 발생")
    void outOfBoardTest() {
        //given
        Elephant elephant = new Elephant(Team.CHO, new Position(new Row(8), new Column(4)));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(new Row(2), new Column(5))));
        Position arrivedPosition = new Position(new Row(11), new Column(2));
        //when
        //then
        assertThatThrownBy(() -> elephant.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상 이동 경로에 장애물이 있는 경우 이동 불가 예외 발생")
    void isObstacleExceptionTest() {
        //given
        Elephant elephant = new Elephant(Team.CHO, new Position(new Row(7), new Column(4)));
        List<Piece> positioningPiece = List.of(new King(Team.CHO, new Position(new Row(9), new Column(3))));
        Position arrivedPosition = new Position(new Row(10), new Column(2));
        //when
        //then
        assertThatThrownBy(() -> elephant.move(arrivedPosition, positioningPiece)).isInstanceOf(IllegalArgumentException.class);
    }
}
