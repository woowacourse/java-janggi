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

    @DisplayName("병을 넘어갈 수 있는지 확인하는 테스트 (항상 True)")
    @Test
    void canBeJumpedOver_Always_ReturnTrue() {
        Piece piece = new Soldier(Camp.CHO, new SoldierStrategy(Camp.CHO.forward()));
        assertThat(piece.canBeJumpedOver()).isTrue();
    }

    @DisplayName("포가 병을 잡을 수 있는지 확인하는 테스트 (항상 True)")
    @Test
    void canBeCapturedByCannon_Always_ReturnTrue() {
        Piece piece = new Soldier(Camp.CHO, new SoldierStrategy(Camp.CHO.forward()));
        assertThat(piece.canBeCapturedByCannon()).isTrue();
    }

    @DisplayName("병의 경로에 기물이 있는지 확인하는 메소드는 항상 true를 반환한다.")
    @Test
    void canPassRoute_Always_ReturnTrue() {
        Camp camp = Camp.CHO;
        Soldier soldier = new Soldier(camp, new SoldierStrategy(camp.forward()));
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
        Soldier soldier = new Soldier(camp, new SoldierStrategy(camp.forward()));
        Soldier destinationSoldier = new Soldier(destinationPieceCamp, new SoldierStrategy(destinationPieceCamp.forward()));
        assertThat(soldier.canCatch(destinationSoldier)).isEqualTo(expected);
    }
}
