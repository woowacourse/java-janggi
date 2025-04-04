package domain.position;

import static org.assertj.core.api.Assertions.assertThat;

import domain.chesspiece.ChessPiece;
import domain.chesspiece.Pawn;
import domain.type.ChessTeam;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessPiecePositionsTest {

    @Disabled
    @Test
    @DisplayName("이동 위치에 적팀 기물이 있으면, 해당 기물을 제거한다")
    void test1() {
        //given
        final ChessPosition enemyPosition = new ChessPosition(4, 4);
        final Pawn enemy = new Pawn(ChessTeam.RED, enemyPosition);
        final ChessPosition position = new ChessPosition(4, 5);
        final Pawn pawn = new Pawn(ChessTeam.BLUE, position);
        final Pawn expectedPawn = new Pawn(ChessTeam.BLUE, enemyPosition);

        //when
        final ChessPiecePositions chessPiecePositions = new ChessPiecePositions(List.of(enemy, pawn));
        chessPiecePositions.move(pawn, enemyPosition);
        final List<ChessPiece> chessPieces = chessPiecePositions.getChessPieces();

        //then
        assertThat(chessPieces).doesNotContain(enemy)
                .contains(expectedPawn);
    }
}
