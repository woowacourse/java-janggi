package janggi.model;

import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class OccupiedPositionsTest {

    @Test
    void 특정_위치가_점유되었는지_확인한다() {
        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of(
                new Position(1, 5), new PieceIdentity(Color.RED, PieceType.CHARIOT)
        ));

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(occupiedPositions.existPosition(new Position(1, 5))).isTrue();
        softly.assertThat(occupiedPositions.existPosition(new Position(1, 4))).isFalse();
        softly.assertAll();
    }

    @Test
    void 특정_위치에_같은_색의_기물이_존재하는지_확인한다() {
        OccupiedPositions occupiedPositions = new OccupiedPositions(Map.of(
                new Position(1, 5), new PieceIdentity(Color.RED, PieceType.CHARIOT)
        ));

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(occupiedPositions.existSameColor(new Position(1, 5), Color.RED)).isTrue();
        softly.assertThat(occupiedPositions.existSameColor(new Position(1, 5), Color.BLUE)).isFalse();
        softly.assertAll();
    }

}
