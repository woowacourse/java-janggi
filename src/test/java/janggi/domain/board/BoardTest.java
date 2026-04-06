package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.ChariotPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
}
