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

class ElephantTest {

    @ParameterizedTest
    @CsvSource(value = {
            "2,9",
            "3,9",
            "4,9",
            "3,8",
            "4,8",
            "4,7"
    })
    void 이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        Elephant elephant = new Elephant(Team.CHO);
        Coordination from = Coordination.of(3, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> elephant.validateRule(MoveContextFactory.create(from, to)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,4",
            "7,4",
            "2,9",
            "6,9"
    })
    void 이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        Elephant elephant = new Elephant(Team.CHO);
        Coordination from = Coordination.of(4, 6);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> elephant.validateRule(MoveContextFactory.create(from, to)))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,6",
            "7,6",
            "6,1"
    })
    void 움직이는_위치_사이에_기물이_있다면_에러를_반환한다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(3, 1), Coordination.of(4, 4))
                .map();

        Elephant elephant = new Elephant(Team.HAN);
        Coordination from = Coordination.of(4, 4);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> elephant.validatePath(piecesOnPath(elephant, from, to, board)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "7,3",
            "7,7"
    })
    void 움직이는_위치_사이에_기물이_없다면_에러를_반환하지_않는다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(3, 1), Coordination.of(4, 5))
                .map();

        Elephant elephant = new Elephant(Team.HAN);
        Coordination from = Coordination.of(4, 5);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> elephant.validatePath(piecesOnPath(elephant, from, to, board)))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "3,4",
            "7,4"
    })
    void 도착지의_기물이_아군이라면_에러를_반환한다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(3, 1), Coordination.of(5, 7))
                .map();

        Elephant elephant = new Elephant(Team.HAN);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> elephant.validateNotSameTeam(board.get(to)))
                .isInstanceOf(PieceException.class);
    }
}
