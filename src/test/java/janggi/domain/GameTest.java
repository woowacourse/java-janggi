package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.PieceType;
import java.util.List;
import org.junit.jupiter.api.Test;

public class GameTest {
    @Test
    void 새로_생성된_게임_보드에_대해서_첫_번째_턴을_초로_설정한다() {
        Game game = new Game();
        game.init(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG);

        assertThat(game.getCurrentSide()).isEqualTo(Side.CHO);
        assertThat(game.getCurrentTurn()).isEqualTo(0);
    }

    @Test
    void 불러온_게임_보드에_대해서_현재_진영과_턴을_가져올_수_있다() {
        Game game = new Game();
        List<PieceInitInfo> pieceInfos = List.of(
                new PieceInitInfo(new Position(2, 5), Side.HAN, PieceType.GUNG),
                new PieceInitInfo(new Position(9, 5), Side.CHO, PieceType.GUNG)

        );
        game.init(pieceInfos, Side.HAN, 12);

        assertThat(game.getCurrentSide()).isEqualTo(Side.HAN);
        assertThat(game.getCurrentTurn()).isEqualTo(12);
    }
}
