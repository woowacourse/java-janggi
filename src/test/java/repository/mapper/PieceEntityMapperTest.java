package repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Jol;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.piece.strategy.JolMoveStrategy;
import domain.piece.strategy.component.PalaceMoveRule;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.entity.GamePieceEntity;

class PieceEntityMapperTest {

    private final PieceEntityMapper mapper = new PieceEntityMapper();

    @Test
    @DisplayName("GamePieceEntity → Domain(Piece) 변환")
    void toDomain_success() {
        GamePieceEntity entity = new GamePieceEntity(1L, 1L, "JOL", "CHO", 5, 2, true);

        Piece piece = mapper.toDomain(entity);

        assertThat(piece.getPieceType()).isEqualTo(PieceType.JOL);
        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("Piece → 새 GamePieceEntity 변환")
    void toNewEntity_success() {
        PalaceMoveRule palaceMoveRule = new PalaceMoveRule();
        Jol testJol = new Jol(new JolMoveStrategy(palaceMoveRule), Team.CHO);

        GamePieceEntity entity = mapper.toNewEntity(1L, testJol, Position.of(5, 2));

        assertThat(entity.id()).isNull();
        assertThat(entity.pieceType()).isEqualTo("JOL");
        assertThat(entity.team()).isEqualTo("CHO");
        assertThat(entity.row()).isEqualTo(5);
        assertThat(entity.col()).isEqualTo(2);
        assertThat(entity.isActive()).isTrue();
    }
}
