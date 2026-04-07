package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.CannonPiece;
import janggi.domain.piece.ChariotPiece;
import janggi.domain.piece.GeneralPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.SoldierPiece;
import janggi.domain.piece.Team;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        Map<Position, Piece> boards = new LinkedHashMap<>();
        boards.put(new Position(1, 1), new ChariotPiece(Team.HAN));
        boards.put(new Position(9, 1), new ChariotPiece(Team.HAN));
        boards.put(new Position(1, 10), new ChariotPiece(Team.CHO));
        boards.put(new Position(9, 10), new ChariotPiece(Team.CHO));
        board = new Board(boards);
    }

    @ParameterizedTest
    @DisplayName("입력받은 좌표에 기물이 있으면 true를 반환한다.")
    @CsvSource({
            "1, 1",
            "9, 1",
            "1, 10",
            "9, 10"
    })
    void testHasPiecePresentAtPosition(int x, int y) {
        Position position = new Position(x, y);
        assertThat(board.hasPieceAt(position)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("입력받은 좌표에 기물이 없다면 false를 반환한다.")
    @CsvSource({
            "1, 2",
            "9, 2",
            "9, 2",
            "2, 10"
    })
    void testHasPieceNotPresentAtPosition(int x, int y) {
        Position position = new Position(x, y);
        assertThat(board.hasPieceAt(position)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("진영별 남은 기물 점수를 계산한다.")
    @CsvSource({
            "HAN, 27.5",
            "CHO, 26.0"
    })
    void testCalculateScoreByTeam(Team team, double expectedScore) {
        assertThat(board.calculateScore(team)).isEqualTo(expectedScore);
    }

    @Test
    @DisplayName("장은 점수 계산에서 제외된다.")
    void testCalculateScoreExceptGeneral() {
        Map<Position, Piece> boards = new LinkedHashMap<>();
        boards.put(new Position(5, 2), new GeneralPiece(Team.HAN));
        boards.put(new Position(2, 3), new CannonPiece(Team.HAN));
        boards.put(new Position(1, 4), new SoldierPiece(Team.HAN));
        Board board = new Board(boards);

        assertThat(board.calculateScore(Team.HAN)).isEqualTo(10.5);
    }
}
