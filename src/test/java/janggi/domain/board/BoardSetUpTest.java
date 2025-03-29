package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("보드 셋업 테스트")
class BoardSetUpTest {

    @DisplayName("한나라의 마상상마 상차림을 반환한다.")
    @Test
    void getDynastySetUp_HanInner() {
        // given
        BoardSetUp boardSetUp = BoardSetUp.INNER_ELEPHANT;

        // when
        Map<Point, Piece> dynastySetUp = boardSetUp.getDynastySetUp(Dynasty.HAN);

        // then
        assertThat(dynastySetUp)
                .containsEntry(new Point(1, 2), new Horse(Dynasty.HAN))
                .containsEntry(new Point(1, 3), new Elephant(Dynasty.HAN))
                .containsEntry(new Point(1, 7), new Elephant(Dynasty.HAN))
                .containsEntry(new Point(1, 8), new Horse(Dynasty.HAN));
    }

    @DisplayName("한나라의 상마마상 상차림을 반환한다.")
    @Test
    void getDynastySetUp_HanOuter() {
        // given
        BoardSetUp boardSetUp = BoardSetUp.OUTER_ELEPHANT;

        // when
        Map<Point, Piece> dynastySetUp = boardSetUp.getDynastySetUp(Dynasty.HAN);

        // then
        assertThat(dynastySetUp)
                .containsEntry(new Point(1, 2), new Elephant(Dynasty.HAN))
                .containsEntry(new Point(1, 3), new Horse(Dynasty.HAN))
                .containsEntry(new Point(1, 7), new Horse(Dynasty.HAN))
                .containsEntry(new Point(1, 8), new Elephant(Dynasty.HAN));
    }

    @DisplayName("한나라의 마상마상 상차림을 반환한다.")
    @Test
    void getDynastySetUp_HanRight() {
        // given
        BoardSetUp boardSetUp = BoardSetUp.RIGHT_ELEPHANT;

        // when
        Map<Point, Piece> dynastySetUp = boardSetUp.getDynastySetUp(Dynasty.HAN);

        // then
        assertThat(dynastySetUp)
                .containsEntry(new Point(1, 2), new Horse(Dynasty.HAN))
                .containsEntry(new Point(1, 3), new Elephant(Dynasty.HAN))
                .containsEntry(new Point(1, 7), new Horse(Dynasty.HAN))
                .containsEntry(new Point(1, 8), new Elephant(Dynasty.HAN));
    }

    @DisplayName("한나라의 상마상마 상차림을 반환한다.")
    @Test
    void getDynastySetUp_HanLeft() {
        // given
        BoardSetUp boardSetUp = BoardSetUp.LEFT_ELEPHANT;

        // when
        Map<Point, Piece> dynastySetUp = boardSetUp.getDynastySetUp(Dynasty.HAN);

        // then
        assertThat(dynastySetUp)
                .containsEntry(new Point(1, 2), new Elephant(Dynasty.HAN))
                .containsEntry(new Point(1, 3), new Horse(Dynasty.HAN))
                .containsEntry(new Point(1, 7), new Elephant(Dynasty.HAN))
                .containsEntry(new Point(1, 8), new Horse(Dynasty.HAN));
    }

    @DisplayName("초나라의 마상상마 상차림을 반환한다.")
    @Test
    void getDynastySetUp_ChuInner() {
        // given
        BoardSetUp boardSetUp = BoardSetUp.INNER_ELEPHANT;

        // when
        Map<Point, Piece> dynastySetUp = boardSetUp.getDynastySetUp(Dynasty.CHU);

        // then
        assertThat(dynastySetUp)
                .containsEntry(new Point(10, 2), new Horse(Dynasty.CHU))
                .containsEntry(new Point(10, 3), new Elephant(Dynasty.CHU))
                .containsEntry(new Point(10, 7), new Elephant(Dynasty.CHU))
                .containsEntry(new Point(10, 8), new Horse(Dynasty.CHU));
    }

    @DisplayName("초나라의 상마마상 상차림을 반환한다.")
    @Test
    void getDynastySetUp_ChuOuter() {
        // given
        BoardSetUp boardSetUp = BoardSetUp.OUTER_ELEPHANT;

        // when
        Map<Point, Piece> dynastySetUp = boardSetUp.getDynastySetUp(Dynasty.CHU);

        // then
        assertThat(dynastySetUp)
                .containsEntry(new Point(10, 2), new Elephant(Dynasty.CHU))
                .containsEntry(new Point(10, 3), new Horse(Dynasty.CHU))
                .containsEntry(new Point(10, 7), new Horse(Dynasty.CHU))
                .containsEntry(new Point(10, 8), new Elephant(Dynasty.CHU));
    }

    @DisplayName("초나라의 마상마상 상차림을 반환한다.")
    @Test
    void getDynastySetUp_ChuRight() {
        // given
        BoardSetUp boardSetUp = BoardSetUp.RIGHT_ELEPHANT;

        // when
        Map<Point, Piece> dynastySetUp = boardSetUp.getDynastySetUp(Dynasty.CHU);

        // then
        assertThat(dynastySetUp)
                .containsEntry(new Point(10, 2), new Horse(Dynasty.CHU))
                .containsEntry(new Point(10, 3), new Elephant(Dynasty.CHU))
                .containsEntry(new Point(10, 7), new Horse(Dynasty.CHU))
                .containsEntry(new Point(10, 8), new Elephant(Dynasty.CHU));
    }

    @DisplayName("초나라의 상마상마 상차림을 반환한다.")
    @Test
    void getDynastySetUp_ChuLeft() {
        // given
        BoardSetUp boardSetUp = BoardSetUp.LEFT_ELEPHANT;

        // when
        Map<Point, Piece> dynastySetUp = boardSetUp.getDynastySetUp(Dynasty.CHU);

        // then
        assertThat(dynastySetUp)
                .containsEntry(new Point(10, 2), new Elephant(Dynasty.CHU))
                .containsEntry(new Point(10, 3), new Horse(Dynasty.CHU))
                .containsEntry(new Point(10, 7), new Elephant(Dynasty.CHU))
                .containsEntry(new Point(10, 8), new Horse(Dynasty.CHU));
    }
}