package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HorseTest {
    @Test
    @DisplayName("마 전진 우대각 이동 테스트")
    void horseUpRightUpTest() {
        //given
        Position startPosition = new Position(7, 3);
        Position arrivedPosition = new Position(5, 4);
        DefaultPiece horse = new DefaultPiece(Team.CHO, PieceType.HORSE);
        //when
        Position movedPosition = horse.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("마 전진 좌대각 이동 테스트")
    void horseUpLeftUpTest() {
        //given
        Position startPosition = new Position(7, 3);
        Position arrivedPosition = new Position(5, 2);
        DefaultPiece horse = new DefaultPiece(Team.CHO, PieceType.HORSE);
        //when
        Position movedPosition = horse.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("마 좌 위대각 이동 테스트")
    void horseLeftLeftUpTest() {
        //given
        Position startPosition = new Position(7, 3);
        Position arrivedPosition = new Position(6, 1);
        DefaultPiece horse = new DefaultPiece(Team.CHO, PieceType.HORSE);
        //when
        Position movedPosition = horse.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("마 좌 아래대각 이동 테스트")
    void horseLeftLeftDownTest() {
        //given
        Position startPosition = new Position(7, 3);
        Position arrivedPosition = new Position(8, 1);
        DefaultPiece horse = new DefaultPiece(Team.CHO, PieceType.HORSE);
        //when
        Position movedPosition = horse.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("마 우 위대각 이동 테스트")
    void horseRightRightUpTest() {
        //given
        Position startPosition = new Position(7, 3);
        Position arrivedPosition = new Position(6, 5);
        DefaultPiece horse = new DefaultPiece(Team.CHO, PieceType.HORSE);
        //when
        Position movedPosition = horse.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("마 우 아래대각 이동 테스트")
    void horseRightRightDownTest() {
        //given
        Position startPosition = new Position(7, 3);
        Position arrivedPosition = new Position(8, 5);
        DefaultPiece horse = new DefaultPiece(Team.CHO, PieceType.HORSE);
        //when
        Position movedPosition = horse.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("마 아래 우대각 이동 테스트")
    void horseDownRightDownTest() {
        //given
        Position startPosition = new Position(7, 3);
        Position arrivedPosition = new Position(9, 4);
        DefaultPiece horse = new DefaultPiece(Team.CHO, PieceType.HORSE);
        //when
        Position movedPosition = horse.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("마 아래 좌대각 이동 테스트")
    void horseDownLeftDownTest() {
        //given
        Position startPosition = new Position(7, 3);
        Position arrivedPosition = new Position(9, 2);
        DefaultPiece horse = new DefaultPiece(Team.CHO, PieceType.HORSE);
        //when
        Position movedPosition = horse.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("마 궁성 이동 테스트")
    void moveInPalaceHorseTest() {
        //given
        Position startPosition = new Position(8, 3);
        Position arrivedPosition = new Position(9, 5);
        DefaultPiece horse = new DefaultPiece(Team.CHO, PieceType.HORSE);
        //when
        Position movedPosition = horse.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("마 장기판 밖으로 이동 시 예외 발생")
    void outOfBoardHorseTest() {
        //given
        Position startPosition = new Position(9, 3);
        Position arrivedPosition = new Position(11, 2);
        DefaultPiece horse = new DefaultPiece(Team.CHO, PieceType.HORSE);
        //when & then
        Assertions.assertThatThrownBy(() -> horse.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }
}
