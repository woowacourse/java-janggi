package janggi.util;

import static janggi.domain.piece.PieceType.CANNON;
import static janggi.domain.piece.PieceType.CHARIOT;
import static janggi.domain.piece.PieceType.ELEPHANT;
import static janggi.domain.piece.PieceType.GENERAL;
import static janggi.domain.piece.PieceType.GUARD;
import static janggi.domain.piece.PieceType.HORSE;
import static janggi.domain.piece.PieceType.SOLDIER;
import static janggi.util.PieceMapper.CANNON_DISPLAY_NAME;
import static janggi.util.PieceMapper.CHARIOT_DISPLAY_NAME;
import static janggi.util.PieceMapper.ELEPHANT_DISPLAY_NAME;
import static janggi.util.PieceMapper.GENERAL_DISPLAY_NAME;
import static janggi.util.PieceMapper.GUARD_DISPLAY_NAME;
import static janggi.util.PieceMapper.HORSE_DISPLAY_NAME;
import static janggi.util.PieceMapper.SOLDIER_DISPLAY_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PieceMapperTest {

    @ParameterizedTest
    @MethodSource("기물_타입별로_매핑되는_이름_테스트_케이스")
    public void 기물_타입별로_매핑되는_이름_테스트(Piece piece, String pieceName) {
        // when & then
        assertThat(PieceMapper.from(piece)).isEqualTo(pieceName);
    }

    private static Stream<Arguments> 기물_타입별로_매핑되는_이름_테스트_케이스() {
        return Stream.of(
                Arguments.of(new Piece(Dynasty.HAN, CANNON), CANNON_DISPLAY_NAME),
                Arguments.of(new Piece(Dynasty.HAN, CHARIOT), CHARIOT_DISPLAY_NAME),
                Arguments.of(new Piece(Dynasty.HAN, ELEPHANT), ELEPHANT_DISPLAY_NAME),
                Arguments.of(new Piece(Dynasty.HAN, GENERAL), GENERAL_DISPLAY_NAME),
                Arguments.of(new Piece(Dynasty.HAN, GUARD), GUARD_DISPLAY_NAME),
                Arguments.of(new Piece(Dynasty.HAN, HORSE), HORSE_DISPLAY_NAME),
                Arguments.of(new Piece(Dynasty.HAN, SOLDIER), SOLDIER_DISPLAY_NAME)
        );
    }

}
