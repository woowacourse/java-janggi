package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class PieceEntityFactoryTest {

    @ParameterizedTest
    @EnumSource(value = PieceType.class, names = {"EMPTY"}, mode = EnumSource.Mode.EXCLUDE)
    @DisplayName("EMPTY를 제외한 모든 기물 타입을 생성할 수 있다")
    void create_nonEmptyTypes(PieceType type) {
        Piece cho = PieceFactory.create(type, Team.CHO);
        Piece han = PieceFactory.create(type, Team.HAN);

        assertThat(cho.getPieceType()).isEqualTo(type);
        assertThat(cho.getTeam()).isEqualTo(Team.CHO);
        assertThat(han.getTeam()).isEqualTo(Team.HAN);
        assertThat(cho.isEmpty()).isFalse();
        assertThat(han.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("EMPTY 타입은 EmptyPiece로 생성된다")
    void create_empty() {
        Piece empty = PieceFactory.create(PieceType.EMPTY, Team.UNDEFINED);

        assertThat(empty.isEmpty()).isTrue();
        assertThat(empty.getPieceType()).isEqualTo(PieceType.EMPTY);
    }
}
