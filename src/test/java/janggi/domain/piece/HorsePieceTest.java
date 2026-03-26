package janggi.domain.piece;

import janggi.domain.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HorsePieceTest {

    @ParameterizedTest
    @DisplayName("마는 직선 한 칸 대각선으로 한 칸 이동 가능하다.")
    @CsvSource({
            "5, 4, 4, 2", "5, 4, 6, 2",
            "5, 4, 3, 3", "5, 4, 7, 3",
            "5, 4, 3, 5", "5, 4, 7, 5",
            "5, 4, 4, 6", "5, 4, 6, 6",
    })
    void testMovableHorse(int preX, int preY, int nextX, int nextY) {
        HorsePiece horsePiece = new HorsePiece(Team.HAN);
        assertThat(horsePiece.canMove(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }


    @ParameterizedTest
    @DisplayName("마는 직선 한 칸 대각선으로 한 칸 이외에 이동이 불가능하다.")
    @CsvSource({
            "5, 4, 4, 3", "5, 4, 6, 1",
            "5, 4, 3, 4", "5, 4, 7, 2",
            "5, 4, 3, 6", "5, 4, 7, 4",
            "5, 4, 4, 7", "5, 4, 6, 5",
    })
    void testNotMovableHorse(int preX, int preY, int nextX, int nextY) {
        HorsePiece horsePiece = new HorsePiece(Team.HAN);
        assertThat(horsePiece.canMove(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("도착 좌표까지의 경로를 반환한다.")
    @CsvSource({
            "5, 4, 4, 2, 5, 3",
            "5, 4, 6, 2, 5, 3",
            "5, 4, 3, 3, 4, 4",
            "5, 4, 7, 3, 6, 4",
            "5, 4, 3, 5, 4, 4",
            "5, 4, 7, 5, 6, 4",
            "5, 4, 4, 6, 5, 5",
            "5, 4, 6, 6, 5, 5",
    })
    void testFindDestinationPath(int preX, int preY, int nextX, int nextY,
                                 int pathX1, int pathY1) {
        HorsePiece horsePiece = new HorsePiece(Team.HAN);
        List<Position> path = horsePiece.findPath(new Position(preX, preY), new Position(nextX, nextY));
        assertThat(path).containsExactly(new Position(pathX1, pathY1), new Position(nextX, nextY));
    }
}
