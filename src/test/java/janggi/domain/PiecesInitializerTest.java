package janggi.domain;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import janggi.domain.position.Position;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecesInitializerTest {

    @DisplayName("장기말의 내상차림을 초기화한다.")
    @Test
    void initializeInnerElephantTest() {
        // given

        // when & then
        SoftAssertions softly = new SoftAssertions();

        softly.assertThatCode(() -> PiecesInitializer.initializePieces(
                InitialElephantSetting.INNER_ELEPHANT)).doesNotThrowAnyException();

        softly.assertThat(
                        PiecesInitializer.initializePieces(InitialElephantSetting.INNER_ELEPHANT).getPieces())
                .contains(
                        new Elephant(new Position(2, 9), RED),
                        new Elephant(new Position(6, 9), RED),
                        new Horse(new Position(1, 9), RED),
                        new Horse(new Position(7, 9), RED),
                        new Elephant(new Position(2, 0), BLUE),
                        new Elephant(new Position(6, 0), BLUE),
                        new Horse(new Position(1, 0), BLUE),
                        new Horse(new Position(7, 0), BLUE)
                );
    }

    @DisplayName("장기말의 외상차림을 초기화한다.")
    @Test
    void initializeOuterElephantTest() {
        // given

        // when & then
        SoftAssertions softly = new SoftAssertions();

        softly.assertThatCode(() -> PiecesInitializer.initializePieces(
                InitialElephantSetting.OUTER_ELEPHANT)).doesNotThrowAnyException();

        softly.assertThat(
                        PiecesInitializer.initializePieces(InitialElephantSetting.OUTER_ELEPHANT).getPieces())
                .contains(
                        new Elephant(new Position(1, 9), RED),
                        new Elephant(new Position(7, 9), RED),
                        new Horse(new Position(2, 9), RED),
                        new Horse(new Position(6, 9), RED),
                        new Elephant(new Position(1, 0), BLUE),
                        new Elephant(new Position(7, 0), BLUE),
                        new Horse(new Position(2, 0), BLUE),
                        new Horse(new Position(6, 0), BLUE)
                );
    }

    @DisplayName("장기말의 좌상차림을 초기화한다.")
    @Test
    void initializeLeftElephantTest() {
        // given

        // when & then
        SoftAssertions softly = new SoftAssertions();

        softly.assertThatCode(() -> PiecesInitializer.initializePieces(
                InitialElephantSetting.LEFT_ELEPHANT)).doesNotThrowAnyException();

        softly.assertThat(
                        PiecesInitializer.initializePieces(InitialElephantSetting.INNER_ELEPHANT).getPieces())
                .contains(
                        new Elephant(new Position(1, 9), RED),
                        new Elephant(new Position(6, 9), RED),
                        new Horse(new Position(2, 9), RED),
                        new Horse(new Position(7, 9), RED),
                        new Elephant(new Position(1, 0), BLUE),
                        new Elephant(new Position(6, 0), BLUE),
                        new Horse(new Position(2, 0), BLUE),
                        new Horse(new Position(7, 0), BLUE)
                );
    }

    @DisplayName("장기말의 우상차림을 초기화한다.")
    @Test
    void initializeRightElephantTest() {
        // given

        // when & then
        SoftAssertions softly = new SoftAssertions();

        softly.assertThatCode(() -> PiecesInitializer.initializePieces(
                InitialElephantSetting.RIGHT_ELEPHANT)).doesNotThrowAnyException();

        softly.assertThat(
                        PiecesInitializer.initializePieces(InitialElephantSetting.INNER_ELEPHANT).getPieces())
                .contains(
                        new Elephant(new Position(2, 9), RED),
                        new Elephant(new Position(7, 9), RED),
                        new Horse(new Position(1, 9), RED),
                        new Horse(new Position(6, 9), RED),
                        new Elephant(new Position(2, 0), BLUE),
                        new Elephant(new Position(7, 0), BLUE),
                        new Horse(new Position(1, 0), BLUE),
                        new Horse(new Position(6, 0), BLUE)
                );
    }
}
