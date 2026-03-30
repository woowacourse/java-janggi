package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.SoldierStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class SoldierTest {
    @DisplayName("병이 포에 의해 잡힐 수 있다.")
    @Test
    void canBeCaughtByCannon() {
        Piece piece = new Soldier(Camp.CHO, new SoldierStrategy(Camp.CHO.direction()));
        assertThat(piece.canBeCaughtByCannon()).isTrue();
    }

    @DisplayName("병은 넘을 수 있다.")
    @Test
    void canBeJumpedOver() {
        Piece piece = new Soldier(Camp.CHO, new SoldierStrategy(Camp.CHO.direction()));
        assertThat(piece.canBeJumpedOver()).isTrue();
    }

    @DisplayName("병의 경로에 기물이 있는지 확인하는 메소드는 항상 true를 반환한다.")
    @Test
    void canPassRoute_Always_ReturnTrue() {
        Camp camp = Camp.CHO;
        Soldier soldier = new Soldier(camp, new SoldierStrategy(camp.direction()));
        assertThat(soldier.canPassRoute(new HashMap<>())).isTrue();
    }

    @DisplayName("병의 진영과 도착지점에 있는 기물의 진영이 다르면 true, 같으면 false를 반환한다")
    @ParameterizedTest
    @CsvSource({
            "CHO, false",
            "HAN, true"
    })
    void canCatchPiece_ReturnBoolean(Camp destinationPieceCamp, boolean expected) {
        Camp camp = Camp.CHO;
        Soldier soldier = new Soldier(camp, new SoldierStrategy(camp.direction()));
        Soldier destinationSoldier = new Soldier(destinationPieceCamp, new SoldierStrategy(destinationPieceCamp.direction()));
        assertThat(soldier.canCatch(destinationSoldier)).isEqualTo(expected);
    }
}
