package factory;

import domain.PieceProperty;
import domain.PieceType;
import domain.Team;
import domain.strategy.CannonMoveStrategy;
import domain.strategy.ChariotMoveStrategy;
import domain.strategy.ElephantMoveStrategy;
import domain.strategy.GreenSoldierMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.MoveStrategy;
import domain.strategy.PalaceMoveStrategy;
import domain.strategy.RedSoldierMoveStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoveStrategyFactoryTest {

    private final MoveStrategyFactory moveStrategyFactory = new MoveStrategyFactory();

    @Test
    @DisplayName("기물 속성이 졸, GREEN팀(초나라)이라면 이동 전략은 DownwardMoveStrategy를 반환해야 한다.")
    void pieceType_soldier_green_team_return_DownwardMoveStrategy() {
        PieceProperty greenSoldier = new PieceProperty(PieceType.SOLDIER, Team.GREEN);

        MoveStrategy downwardMoveStrategy = moveStrategyFactory.createMoveStrategy(greenSoldier);

        Assertions.assertThat(downwardMoveStrategy).isInstanceOf(GreenSoldierMoveStrategy.class);
    }

    @Test
    @DisplayName("기물 속성이 졸, RED팀(한나라)이라면 이동 전략은 UpwardMoveStrategy를 반환해야 한다.")
    void pieceType_soldier_red_team_return_UpwardMoveStrategy() {
        PieceProperty redSoldier = new PieceProperty(PieceType.SOLDIER, Team.RED);

        MoveStrategy upwardMoveStrategy = moveStrategyFactory.createMoveStrategy(redSoldier);

        Assertions.assertThat(upwardMoveStrategy).isInstanceOf(RedSoldierMoveStrategy.class);
    }

    @Test
    @DisplayName("기물 속성이 사 기물이라면 이동 전략은 PalaceMoveStrategy를 반환해야 한다.")
    void pieceType_guard_return_PalaceMoveStrategy() {
        PieceProperty guard = new PieceProperty(PieceType.GUARD, Team.RED);

        MoveStrategy guardMoveStrategy = moveStrategyFactory.createMoveStrategy(guard);

        Assertions.assertThat(guardMoveStrategy).isInstanceOf(PalaceMoveStrategy.class);
    }

    @Test
    @DisplayName("기물 속성이 궁 기물이라면 이동 전략은 PalaceMoveStrategy를 반환해야 한다.")
    void pieceType_general_return_PalaceMoveStrategy() {
        PieceProperty general = new PieceProperty(PieceType.GENERAL, Team.RED);

        MoveStrategy generalMoveStrategy = moveStrategyFactory.createMoveStrategy(general);

        Assertions.assertThat(generalMoveStrategy).isInstanceOf(PalaceMoveStrategy.class);
    }

    @Test
    @DisplayName("기물 속성이 마 기물이라면 이동 전략은 HorseMoveStrategy를 반환해야 한다.")
    void pieceType_horse_return_HorseMoveStrategy() {
        PieceProperty horse = new PieceProperty(PieceType.HORSE, Team.RED);

        MoveStrategy horseMoveStrategy = moveStrategyFactory.createMoveStrategy(horse);

        Assertions.assertThat(horseMoveStrategy).isInstanceOf(HorseMoveStrategy.class);
    }

    @Test
    @DisplayName("기물 속성이 상 기물이라면 이동 전략은 ElephantMoveStrategy를 반환해야 한다.")
    void pieceType_elephant_return_ElephantMoveStrategy() {
        PieceProperty elephant = new PieceProperty(PieceType.ELEPHANT, Team.RED);

        MoveStrategy elephantMoveStrategy = moveStrategyFactory.createMoveStrategy(elephant);

        Assertions.assertThat(elephantMoveStrategy).isInstanceOf(ElephantMoveStrategy.class);
    }

    @Test
    @DisplayName("기물 속성이 차 기물이라면 이동 전략은 ChariotMoveStrategy를 반환해야 한다.")
    void pieceType_chariot_return_ChariotMoveStrategy() {
        PieceProperty chariot = new PieceProperty(PieceType.CHARIOT, Team.RED);

        MoveStrategy chariotMoveStrategy = moveStrategyFactory.createMoveStrategy(chariot);

        Assertions.assertThat(chariotMoveStrategy).isInstanceOf(ChariotMoveStrategy.class);
    }

    @Test
    @DisplayName("기물 속성이 포 기물이라면 이동 전략은 CannonMoveStrategy를 반환해야 한다.")
    void pieceType_cannon_return_CannonMoveStrategy() {
        PieceProperty cannon = new PieceProperty(PieceType.CANNON, Team.RED);

        MoveStrategy cannonMoveStrategy = moveStrategyFactory.createMoveStrategy(cannon);

        Assertions.assertThat(cannonMoveStrategy).isInstanceOf(CannonMoveStrategy.class);
    }

}
