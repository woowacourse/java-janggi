package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class HorseTest {

    @ParameterizedTest
    @DisplayName("상하좌우 한 칸과 대각선 한 칸 이동하면 true를 반환한다.")
    @CsvSource(value = {
            "4, 7",
            "6, 7",
            "3, 6",
            "3, 4",
            "4, 3",
            "6, 3",
            "7, 4",
            "7, 6"
    })
    void shouldReturnTrueWhenFollowMovingRule(int destX, int destY) {
        // given
        Horse horse = new Horse(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        boolean canMove = horse.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @DisplayName("상하좌우 한 칸과 대각선 한 칸 이동하지 않으면 false를 반환한다.")
    @CsvSource(value = {
            "5, 5",
            "5, 6",
            "4, 5",
            "6, 5",
            "5, 4",
            "5, 7",
            "5, 3",
            "7, 5",
            "3, 5",
            "7, 7",
            "3, 3",
            "7, 3",
            "3, 7"
    })
    void shouldReturnFalseWhenUnfollowMovingRule(int destX, int destY) {
        // given
        Horse horse = new Horse(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        boolean canMove = horse.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isFalse();
    }

    @ParameterizedTest
    @DisplayName("이동 경로에 말이 존재한다면 false를 반환한다.")
    @CsvSource(value = {
            "4, 7, 5, 6",
            "6, 7, 5, 6",
            "3, 6, 4, 5",
            "3, 4, 4, 5",
            "4, 3, 5, 4",
            "6, 3, 5, 4",
            "7, 4, 6, 5",
            "7, 6, 6, 5"
    })
    void shouldReturnFalseWhenPieceOnPath(int destX, int destY, int pathX, int pathY) {
        // given
        Horse horse = new Horse(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);
        Map<Position, Piece> existsPieceOnPathBoard = Map.of(new Position(pathX, pathY), new Horse(Side.RED));

        // when
        boolean canMove = horse.canMove(start, end, existsPieceOnPathBoard);

        // then
        assertThat(canMove).isFalse();
    }
}
