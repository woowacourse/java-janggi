package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardInitializerTest {
    private List<List<Piece>> board = BoardInitializer.createBoard();

    @Test
    void 보드_크기는_10개의_행과_9개의_열로_이루어진다() {
        int rowLength = board.size();
        int colLength = board.get(0).size();

        assertThat(rowLength).isEqualTo(10);
        assertThat(colLength).isEqualTo(9);
    }

    @Test
    void 장기판은_32개의_기물과_58개의_빈칸이_있다() {
        int emptyPositionCount = Math.toIntExact(board.stream()
                .flatMap(Collection::stream)
                .filter(Piece::isEmpty)
                .count());

        int pieceCount = Math.toIntExact(board.stream()
                .flatMap(Collection::stream)
                .filter((piece) -> !piece.isEmpty())
                .count());

        assertThat(emptyPositionCount).isEqualTo(58);
        assertThat(pieceCount).isEqualTo(32);
    }

    @ParameterizedTest(name = "{0} 나라의 {1} 개수는 {2}개여야 한다")
    @CsvSource({
            "HAN, SOLDIER, 5",
            "CHO, SOLDIER, 5",
            "HAN, CANNON, 2",
            "CHO, CANNON, 2",
            "HAN, KING, 1",
            "CHO, KING, 1",
            "HAN, TANK, 2",
            "CHO, TANK, 2",
            "HAN, HORSE, 2",
            "CHO, HORSE, 2",
            "HAN, ELEPHANT, 2",
            "CHO, ELEPHANT, 2",
            "HAN, ADVISOR, 2",
            "CHO, ADVISOR, 2"
    })
    void 생성기물_개수_테스트(Team team, PieceType pieceType, int expectedCount) {
        long actualCount = board.stream()
                .flatMap(Collection::stream)
                .filter(piece -> piece.findTeam() == team)
                .filter(piece -> piece.pieceType() == pieceType)
                .count();

        assertThat(actualCount).isEqualTo(expectedCount);
    }
}
