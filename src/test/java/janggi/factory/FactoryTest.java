package janggi.factory;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.factory.horse_elephant.HorseElephantFactory;
import janggi.view.horseElephantPosition;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FactoryTest {

    @DisplayName("초기화된 기물은 32개가 나와야한다")
    @Test
    void test1() {
        for (horseElephantPosition horseElephantPositionByCho : horseElephantPosition.values()) {
            for (horseElephantPosition horseElephantPositionByHan : horseElephantPosition.values()) {
                Map<Position, Piece> initializeBoard = PieceInitFactory.initialize();

                initializeBoard.putAll(HorseElephantFactory.create(horseElephantPositionByCho, Team.CHO));
                initializeBoard.putAll(HorseElephantFactory.create(horseElephantPositionByHan, Team.HAN));

                assertThat(initializeBoard).hasSize(32);
            }
        }

    }
}
