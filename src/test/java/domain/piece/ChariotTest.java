package domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .map();

        Chariot chariot = new Chariot(Team.CHO);
        Coordination from = Coordination.of(1, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> chariot.validateMovable(from, to, board))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,9",
            "1,8"
    })
    void 출발점을_기준으로_도착점이_수직_수평_위치에_있다면_에러를_반환하지_않는다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .map();

        Chariot chariot = new Chariot(Team.CHO);
        Coordination from = Coordination.of(1, 10);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> chariot.validateMovable(from, to, board))
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

        assertThatThrownBy(() -> chariot.validateMovable(from, to, board))
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

        assertThatCode(() -> chariot.validateMovable(from, to, board))
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
        Coordination from = Coordination.of(1, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> chariot.validateMovable(from, to, board))
                .isInstanceOf(PieceException.class);
    }
}
