package domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.board.formation.OutsideMaFormation;
import domain.coordinate.Position;
import domain.piece.Piece;
import domain.state.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

class BasicBoardInitializerTest {

    @Test
    @DisplayName("보드 초기화 시 배치된 기물 수는 32개이다.")
    void boardInitializeTest() {
        // given
        BasicBoardInitializer basicBoardInitializer = new BasicBoardInitializer(new OutsideMaFormation(Side.HAN), new OutsideMaFormation(Side.CHU));
        int count = 0;

        // when
        for (Map.Entry<Position, Piece> positionPieceEntry : basicBoardInitializer.initialize().entrySet()) {
            if (!positionPieceEntry.getValue().isNeutral()) count++;
        }
        // then
        assertThat(count).isEqualTo(32);
    }
}
