package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.game.SetUp;
import domain.player.Team;
import java.util.List;
import org.junit.jupiter.api.Test;

class PieceInitializerTest {

    @Test
    void 한_영역의_기물_리스트를_반환한다() {
        // when
        List<Piece> pieces = PieceInitializer.createTeamPieces(Team.HAN, SetUp.INNER_ELEPHANT);

        // then
        assertAll(() -> {
            assertThat(pieces.stream().filter(p -> p.isEqualType(PieceType.GENERAL)).count()).isEqualTo(1);
            assertThat(pieces.stream().filter(p -> p.isEqualType(PieceType.CHARIOT)).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p.isEqualType(PieceType.CANNON)).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p.isEqualType(PieceType.HORSE)).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p.isEqualType(PieceType.ELEPHANT)).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p.isEqualType(PieceType.GUARD)).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p.isEqualType(PieceType.SOLDIER)).count()).isEqualTo(5);
        });
    }

    @Test
    void 초_영역의_기물_리스트를_반환한다() {
        // when
        List<Piece> pieces = PieceInitializer.createTeamPieces(Team.CHO, SetUp.INNER_ELEPHANT);

        // then
        assertAll(() -> {
            assertThat(pieces.stream().filter(p -> p.isEqualType(PieceType.GENERAL)).count()).isEqualTo(1);
            assertThat(pieces.stream().filter(p -> p.isEqualType(PieceType.CHARIOT)).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p.isEqualType(PieceType.CANNON)).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p.isEqualType(PieceType.HORSE)).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p.isEqualType(PieceType.ELEPHANT)).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p.isEqualType(PieceType.GUARD)).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p.isEqualType(PieceType.SOLDIER)).count()).isEqualTo(5);
        });
    }
}
