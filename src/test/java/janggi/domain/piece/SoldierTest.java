package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.piece.strategy.ChariotStrategy;
import janggi.domain.piece.strategy.SoldierStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class SoldierTest {

    @DisplayName("병의 경로에 기물이 있는지 확인하는 메소드는 항상 true를 반환한다.")
    @Test
    void moveRoute_Always_ReturnTrue() {
        Camp camp = Camp.CHO;
        Soldier soldier = new Soldier(camp, new SoldierStrategy(camp.direction()));
        assertThat(soldier.moveRoute(new HashMap<>())).isTrue();
    }

    @DisplayName("병의 진영과 도착지점에 있는 기물의 진영이 다르면 true, 같으면 false를 반환한다")
    @ParameterizedTest
    @CsvSource({
            "CHO, false",
            "HAN, true"
    })
    void moveDestination_compareDestinationPiece_ReturnBoolean(Camp destinationPieceCamp, boolean expected) {
        Camp camp = Camp.CHO;
        Soldier soldier = new Soldier(camp, new SoldierStrategy(camp.direction()));
        Soldier destinationSoldier = new Soldier(destinationPieceCamp, new SoldierStrategy(destinationPieceCamp.direction()));
        assertThat(soldier.moveDestination(Position.of(0, 0), destinationSoldier)).isEqualTo(expected);
    }

    @DisplayName("병이 포인지 확인하는 테스트 (항상 false)")
    @Test
    void isCannon_Always_ReturnFalse() {
        Piece piece = new Soldier(Camp.CHO, new SoldierStrategy(Camp.CHO.direction()));
        assertThat(piece.isCannon()).isFalse();
    }
}
