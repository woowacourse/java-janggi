package janggi;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiGameTest {

    @DisplayName("초기 초기배치를 할 수 있다.")
    @Test
    void test1() {
        //given
        JanggiGame janggiGame = new JanggiGame(AssignType.IN_TOP, AssignType.LEFT_TOP);

        //when
        List<Piece> choWorldPieces = janggiGame.getChoPieces();

        List<Position> maPiecePositions = choWorldPieces.stream()
                .filter(piece -> piece.getPieceType() == PieceType.MA)
                .map(Piece::getPosition)
                .toList();
        assertThat(maPiecePositions)
                .containsExactlyInAnyOrder(new Position(1, 9 ), new Position(7, 9));
        List<Position> sangPiecePositions = choWorldPieces.stream()
                .filter(piece -> piece.getPieceType() == PieceType.SANG)
                .map(Piece::getPosition)
                .toList();
        assertThat(sangPiecePositions)
                .containsExactlyInAnyOrder(new Position(2, 9), new Position(6,9));
    }
}
