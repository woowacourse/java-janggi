package janggi.util;

import static janggi.util.PieceMapper.CANNON_DISPLAY_NAME;
import static janggi.util.PieceMapper.CHARIOT_DISPLAY_NAME;
import static janggi.util.PieceMapper.ELEPHANT_DISPLAY_NAME;
import static janggi.util.PieceMapper.GENERAL_DISPLAY_NAME;
import static janggi.util.PieceMapper.GUARD_DISPLAY_NAME;
import static janggi.util.PieceMapper.HORSE_DISPLAY_NAME;
import static janggi.util.PieceMapper.SOLDIER_DISPLAY_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.CannonMoveStrategy;
import janggi.domain.piece.ChariotMoveStrategy;
import janggi.domain.piece.ElephantMoveStrategy;
import janggi.domain.piece.GeneralMoveStrategy;
import janggi.domain.piece.GuardMoveStrategy;
import janggi.domain.piece.HorseMoveStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.piece.SoldierMoveStrategy;
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
                Arguments.of(new Piece(Dynasty.HAN, new CannonMoveStrategy()), CANNON_DISPLAY_NAME),
                Arguments.of(new Piece(Dynasty.HAN, new ChariotMoveStrategy()), CHARIOT_DISPLAY_NAME),
                Arguments.of(new Piece(Dynasty.HAN, new ElephantMoveStrategy()), ELEPHANT_DISPLAY_NAME),
                Arguments.of(new Piece(Dynasty.HAN, new GeneralMoveStrategy()), GENERAL_DISPLAY_NAME),
                Arguments.of(new Piece(Dynasty.HAN, new GuardMoveStrategy()), GUARD_DISPLAY_NAME),
                Arguments.of(new Piece(Dynasty.HAN, new HorseMoveStrategy()), HORSE_DISPLAY_NAME),
                Arguments.of(new Piece(Dynasty.HAN, new SoldierMoveStrategy()), SOLDIER_DISPLAY_NAME)
        );
    }

}
