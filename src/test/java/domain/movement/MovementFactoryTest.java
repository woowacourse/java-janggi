package domain.movement;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MovementFactory 클래스 테스트")
class MovementFactoryTest {

    @Test
    @DisplayName("GENERAL 기물은 GeneralMovement를 생성한다")
    void createGeneralMovement() {
        Piece piece = new Piece(Team.HAN, PieceType.GENERAL);

        Movement movement = MovementFactory.create(piece);

        assertThat(movement).isInstanceOf(PalaceMovement.class);
    }

    @Test
    @DisplayName("GUARD 기물은 FourDirectionMovement 생성한다")
    void createGuardMovement() {
        Piece piece = new Piece(Team.HAN, PieceType.GUARD);

        Movement movement = MovementFactory.create(piece);

        assertThat(movement).isInstanceOf(PalaceMovement.class);
    }

    @Test
    @DisplayName("CHARIOT 기물은 StepPieceMovement를 생성한다")
    void createChariotMovement() {
        Piece piece = new Piece(Team.HAN, PieceType.CHARIOT);

        Movement movement = MovementFactory.create(piece);

        assertThat(movement).isInstanceOf(LinearMovement.class);
    }

    @Test
    @DisplayName("CANNON 기물은 StepPieceMovement를 생성한다")
    void createCannonMovement() {
        Piece piece = new Piece(Team.CHO, PieceType.CANNON);

        Movement movement = MovementFactory.create(piece);

        assertThat(movement).isInstanceOf(LinearMovement.class);
    }

    @Test
    @DisplayName("HORSE 기물은 HorseMovement를 생성한다")
    void createHorseMovement() {
        Piece piece = new Piece(Team.HAN, PieceType.HORSE);

        Movement movement = MovementFactory.create(piece);

        assertThat(movement).isInstanceOf(HorseMovement.class);
    }

    @Test
    @DisplayName("ELEPHANT 기물은 ElephantMovement를 생성한다")
    void createElephantMovement() {
        Piece piece = new Piece(Team.CHO, PieceType.ELEPHANT);

        Movement movement = MovementFactory.create(piece);

        assertThat(movement).isInstanceOf(ElephantMovement.class);
    }

    @Test
    @DisplayName("HAN SOLDIER 기물은 SoldierMovement를 생성한다")
    void createHanSoldierMovement() {
        Piece piece = new Piece(Team.HAN, PieceType.SOLDIER);

        Movement movement = MovementFactory.create(piece);

        assertThat(movement).isInstanceOf(SoldierMovement.class);
    }

    @Test
    @DisplayName("CHO SOLDIER 기물은 SoldierMovement를 생성한다")
    void createChoSoldierMovement() {
        Piece piece = new Piece(Team.CHO, PieceType.SOLDIER);

        Movement movement = MovementFactory.create(piece);

        assertThat(movement).isInstanceOf(SoldierMovement.class);
    }
}
