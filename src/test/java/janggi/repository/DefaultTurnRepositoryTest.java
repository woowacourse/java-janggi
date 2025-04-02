package janggi.repository;

import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.TestFixture;
import janggi.domain.Turn;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DefaultTurnRepositoryTest {

    private final TurnRepository turnRepository = TestFixture.getTurnRepository();

    @BeforeEach
    void setUp() {
        turnRepository.deleteCurrent();
    }

    @DisplayName("턴을 추가한다.")
    @Test
    void addTest() {

        // given
        final Turn turn = new Turn(RED);

        // when
        turnRepository.add(turn);

        // then
        assertThat(turnRepository.findCurrent().get()).isEqualTo(turn);
    }

    @DisplayName("턴을 수정한다.")
    @Test
    void changeTurnTest() {

        // given
        final Turn turn = new Turn(RED);
        turnRepository.add(turn);

        // when
        turn.changeTurn();
        turnRepository.change(turn);

        // then
        assertThat(turnRepository.findCurrent().get()).isEqualTo(turn);
    }

    @DisplayName("턴을 찾는다.")
    @Test
    void findCurrentTest() {

        // given
        final Turn turn = new Turn(RED);
        turnRepository.add(turn);

        // when
        final Optional<Turn> findTurn = turnRepository.findCurrent();

        // then
        assertThat(turn).isEqualTo(findTurn.get());
    }

    @DisplayName("턴을 삭제한다.")
    @Test
    void deleteCurrentTest() {

        // given
        final Turn turn = new Turn(RED);
        turnRepository.add(turn);

        // when
        turnRepository.deleteCurrent();

        // then
        assertThat(turnRepository.findCurrent()).isEmpty();
    }
}
