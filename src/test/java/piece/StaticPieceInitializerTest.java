package piece;

import static org.assertj.core.api.Assertions.assertThat;

import game.Country;
import game.StartingPosition;
import java.util.Map;
import org.junit.jupiter.api.Test;
import position.Position;

class StaticPieceInitializerTest {

    @Test
    void 초나라의_기본_배치를_생성할_수_있다() {
        // given
        StaticPieceInitializer initializer = new StaticPieceInitializer();

        // when
        Map<Position, Piece> board = initializer.init(StartingPosition.RIGHT_ELEPHANT_SETUP, Country.CHO);

        // then
        assertThat(board).hasSize(16);
        assertThat(board.values()).anyMatch(piece -> piece.getType() == PieceType.CHO_SOLDIER);

    }

    @Test
    void 한나라의_기본_배치를_생성할_수_있다() {
        // given
        StaticPieceInitializer initializer = new StaticPieceInitializer();

        // when
        Map<Position, Piece> board = initializer.init(StartingPosition.LEFT_ELEPHANT_SETUP, Country.HAN);

        // then
        assertThat(board).hasSize(16);
        assertThat(board.values()).anyMatch(piece -> piece.getType() == PieceType.HAN_SOLDIER);
    }
}
