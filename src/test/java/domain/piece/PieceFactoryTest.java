package domain.piece;

import domain.piece.strategy.HorseElephantSetupStrategy;
import domain.piece.strategy.LeftElephantStrategy;
import domain.piece.strategy.RightElephantStrategy;
import domain.position.Position;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceFactoryTest {

    @Test
    @DisplayName("전략마다 다른 말들의 위치를 반환한다.")
    void createAllPiecesTest() {
        PieceFactory pieceFactory = new PieceFactory();
        HorseElephantSetupStrategy first = new RightElephantStrategy();
        HorseElephantSetupStrategy second = new LeftElephantStrategy();
        Map<Position, Piece> allPieces = pieceFactory.createAllPieces(first, second);
        Assertions.assertThat(allPieces).hasSize(32);
    }
}
