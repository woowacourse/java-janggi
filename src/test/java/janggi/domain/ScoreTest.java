package janggi.domain;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreTest {

    @DisplayName("보드에 있는 기물들의 점수를 계산한다.")
    @Test
    void calculateScoreTest() {
        Piece soldier1 = new Soldier(new Position(1, 1), RED); // 2점
        Piece elephant = new Elephant(new Position(1, 3), RED); // 3점
        Piece chariot = new Chariot(new Position(1, 4), RED); // 13점
        Piece soldier2 = new Soldier(new Position(1, 2), BLUE); // 2점
        Piece guard = new Guard(new Position(1, 5), BLUE); // 3점
        Piece horse = new Horse(new Position(1, 6), BLUE); // 5점

        Score score = new Score(List.of(soldier1, soldier2, elephant, chariot, guard, horse));

        assertAll(
                () -> assertThat(score.calculateTeamScore(RED)).isEqualTo(19.5),
                () -> assertThat(score.calculateTeamScore(BLUE)).isEqualTo(10)
        );
    }
}
