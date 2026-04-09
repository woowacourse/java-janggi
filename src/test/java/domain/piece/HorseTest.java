package domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static fixture.PiecePathFinder.piecesOnPath;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import fixture.BoardFixtureFactory;
import fixture.MoveContextFactory;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HorseTest {

    @ParameterizedTest
    @CsvSource(value = {
            "3,8",
            "4,7",
            "5,8",
            "4,9",
    })
    void 이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        Horse horse = new Horse(Team.CHO);
        Coordination from = Coordination.of(3, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> horse.validateRule(MoveContextFactory.create(from, to)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2,5",
            "2,7",
            "3,4",
            "3,8",
            "5,4",
            "5,8",
            "6,5",
            "6,7",
    })
    void 이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        Horse horse = new Horse(Team.CHO);
        Coordination from = Coordination.of(4, 6);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> horse.validateRule(MoveContextFactory.create(from, to)))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,2",
            "3,2",
            "4,3",
            "4,5",
    })
    void 움직이는_위치_사이에_기물이_있다면_에러를_반환한다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(2, 1), Coordination.of(2, 4))
                .map();

        Horse horse = new Horse(Team.HAN);
        Coordination from = Coordination.of(2, 4);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> horse.validatePath(piecesOnPath(horse, from, to, board)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,6",
            "3,6"
    })
    void 움직이는_위치_사이에_기물이_없다면_예러를_반환하지_않는다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(2, 1), Coordination.of(2, 4))
                .map();

        Horse horse = new Horse(Team.HAN);
        Coordination from = Coordination.of(2, 4);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> horse.validatePath(piecesOnPath(horse, from, to, board)))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "3,4",
            "7,4"
    })
    void 도착지의_기물이_아군이라면_에러를_반환한다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(2, 1), Coordination.of(5, 3))
                .map();

        Horse horse = new Horse(Team.HAN);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> horse.validateNotSameTeam(board.get(to)))
                .isInstanceOf(PieceException.class);
    }
}
