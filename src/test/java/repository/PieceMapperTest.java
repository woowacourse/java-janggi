package repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.board.Intersection;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Piece 매핑 테스트")
class PieceMapperTest {

    @DisplayName("도메인 객체를 엔티티로 변환한다")
    @Test
    void 도메인_객체를_엔티티로_변환() {
        // given
        Piece piece = Piece.of(PieceType.CANNON, Side.HAN);
        Intersection pos = new Intersection(1, 4);
        long gameId = 1L;

        // when
        PieceEntity entity = PieceMapper.toEntity(gameId, pos, piece);

        // then
        assertAll(
                () -> assertThat(entity.type()).isEqualTo("CANNON"),
                () -> assertThat(entity.side()).isEqualTo("HAN"),
                () -> assertThat(entity.row()).isEqualTo(1),
                () -> assertThat(entity.file()).isEqualTo(4)
        );
    }
}
