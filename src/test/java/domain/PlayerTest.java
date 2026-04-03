package domain;

import static org.assertj.core.api.Assertions.assertThat;


import domain.piece.Pawn;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import strategy.InitializeStrategy;
import strategy.OuterElephantFormationStrategy;

public class PlayerTest {
    @Test
    void 보드_초기_생성시_점수_확인() {
        // given
        InitializeStrategy strategy = new OuterElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        assertThat(board.calculateScore(Team.CHO)).isEqualTo(72);
        assertThat(board.calculateScore(Team.HAN)).isEqualTo(73.5);
    }

    @Test
    void 상대편_기물을_잡았을때_점수_반영_테스트() {
        // given
        Map<Position, Piece> testPieces = new HashMap<>();
        Position from = Position.from(6,1);
        Position to = Position.from(5,1);

        testPieces.put(from, new Pawn(Team.CHO));
        testPieces.put(to, new Pawn(Team.HAN));
        Board testBoard = new Board(testPieces);

        // when
        testBoard.move(from, to, PieceType.PAWN, Team.CHO);

        // then
        assertThat(testBoard.calculateScore(Team.CHO)).isEqualTo(2);
        assertThat(testBoard.calculateScore(Team.HAN)).isEqualTo(1.5);
    }
}
