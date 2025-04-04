package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ElephantTest {
    @Test
    @DisplayName("상 전진 우대각 우대각 이동 테스트")
    void elephantUpRightUpRightUpTest() {
        //given
        Position startPosition = new Position(7, 4);
        Position arrivedPosition = new Position(4, 6);
        Piece elephant = new DefaultPiece(Team.CHO, PieceType.ELEPHANT);
        //when
        Position movedPosition = elephant.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("상 전진 좌대각 좌대각 이동 테스트")
    void elephantUpLeftUpLeftUpTest() {
        //given
        Position startPosition = new Position(7, 4);
        Position arrivedPosition = new Position(4, 2);
        Piece elephant = new DefaultPiece(Team.CHO, PieceType.ELEPHANT);
        //when
        Position movedPosition = elephant.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("상 좌 위대각 위대각 이동 테스트")
    void elephantLeftLeftUpLeftUpTest() {
        //given
        Position startPosition = new Position(7, 4);
        Position arrivedPosition = new Position(5, 1);
        Piece elephant = new DefaultPiece(Team.CHO, PieceType.ELEPHANT);
        //when
        Position movedPosition = elephant.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("상 좌 아래대각 아래대각 이동 테스트")
    void elephantLeftLeftDownLeftDownTest() {
        //given
        Position startPosition = new Position(7, 4);
        Position arrivedPosition = new Position(9, 1);
        Piece elephant = new DefaultPiece(Team.CHO, PieceType.ELEPHANT);
        //when
        Position movedPosition = elephant.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("상 우 위대각 위대각 이동 테스트")
    void elephantRightRightUpRightUpTest() {
        //given
        Position startPosition = new Position(7, 4);
        Position arrivedPosition = new Position(5, 7);
        Piece elephant = new DefaultPiece(Team.CHO, PieceType.ELEPHANT);
        //when
        Position movedPosition = elephant.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("상 우 아래대각 아래대각 이동 테스트")
    void elephantRightRightDownRightDownTest() {
        //given
        Position startPosition = new Position(7, 4);
        Position arrivedPosition = new Position(9, 7);
        Piece elephant = new DefaultPiece(Team.CHO, PieceType.ELEPHANT);
        //when
        Position movedPosition = elephant.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("상 아래 우대각 우대각 이동 테스트")
    void elephantDownRightDownRightDownTest() {
        //given
        Position startPosition = new Position(7, 4);
        Position arrivedPosition = new Position(10, 6);
        Piece elephant = new DefaultPiece(Team.CHO, PieceType.ELEPHANT);
        //when
        Position movedPosition = elephant.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("상 아래 좌대각 좌대각 이동 테스트")
    void elephantDownLeftDownLeftDownTest() {
        //given
        Position startPosition = new Position(7, 4);
        Position arrivedPosition = new Position(10, 2);
        Piece elephant = new DefaultPiece(Team.CHO, PieceType.ELEPHANT);
        //when
        Position movedPosition = elephant.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("상 궁성 이동 테스트")
    void moveInPalaceElephantTest() {
        //given
        Position startPosition = new Position(8, 3);
        Position arrivedPosition = new Position(10, 6);
        Piece elephant = new DefaultPiece(Team.CHO, PieceType.ELEPHANT);
        //when
        Position movedPosition = elephant.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("상 장기판 밖으로 이동 시 예외 발생")
    void outOfBoardElephantTest() {
        //given
        Position startPosition = new Position(9, 3);
        Position arrivedPosition = new Position(12, 1);
        Piece elephant = new DefaultPiece(Team.CHO, PieceType.ELEPHANT);
        //when & then
        Assertions.assertThatThrownBy(() -> elephant.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }
}
