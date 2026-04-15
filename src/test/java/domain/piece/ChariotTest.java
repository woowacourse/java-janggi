package domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static fixture.PiecePathFinder.piecesOnPath;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import fixture.BoardFixtureFactory;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChariotTest {

    @ParameterizedTest
    @CsvSource(value = {
            "2,9",
            "4,9"
    })
    void 출발점을_기준으로_도착점이_수직_수평_위치에_있지_않다면_에러를_반환한다(int column, int row) {
        Chariot chariot = new Chariot(Team.CHO);
        Coordination from = Coordination.of(1, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> chariot.validateRule(from, to))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,9",
            "1,8"
    })
    void 출발점을_기준으로_도착점이_수직_수평_위치에_있다면_에러를_반환하지_않는다(int column, int row) {
        Chariot chariot = new Chariot(Team.CHO);
        Coordination from = Coordination.of(1, 10);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> chariot.validateRule(from, to))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,6",
            "3,10"
    })
    void 움직이는_위치_사이에_기물이_있다면_에러를_반환한다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .map();

        Chariot chariot = new Chariot(Team.CHO);
        Coordination from = Coordination.of(1, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> chariot.validatePath(piecesOnPath(chariot, from, to, board)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,9",
            "1,8"
    })
    void 움직이는_위치_사이에_기물이_없다면_예러를_반환하지_않는다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .map();

        Chariot chariot = new Chariot(Team.CHO);
        Coordination from = Coordination.of(1, 10);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> chariot.validatePath(piecesOnPath(chariot, from, to, board)))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,7",
            "2,10"
    })
    void 도착지의_기물이_아군이라면_에러를_반환한다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .map();

        Chariot chariot = new Chariot(Team.CHO);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> chariot.validateNotSameTeam(board.get(to)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6,8",
            "5,9"
    })
    void 궁성_대각선_방향으로_이동할_수_있다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(1, 10), Coordination.of(4, 10))
                .moveIgnoringValidation(Coordination.of(5, 9), Coordination.of(5, 8))
                .map();

        Chariot chariot = new Chariot(Team.CHO);
        Coordination from = Coordination.of(4, 10);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> {
            chariot.validateRule(from, to);
            chariot.validatePath(piecesOnPath(chariot, from, to, board));
        })
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6,8"
    })
    void 궁성_대각선_경로에_기물이_있다면_이동할_수_없다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(1, 10), Coordination.of(4, 10))
                .map();

        Chariot chariot = new Chariot(Team.CHO);
        Coordination from = Coordination.of(4, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> chariot.validatePath(piecesOnPath(chariot, from, to, board)))
                .isInstanceOf(PieceException.class);
    }
}
