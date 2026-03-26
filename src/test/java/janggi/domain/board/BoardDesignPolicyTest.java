package janggi.domain.board;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.ElephantMoveStrategy;
import janggi.domain.piece.HorseMoveStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class BoardDesignPolicyTest {

    @ParameterizedTest
    @EnumSource(HorseElephantPosition.class)
    @DisplayName("기물을 주어진 상차림법에 맞게 보드 위에 배치한다.")
    public void 보드_초기화(HorseElephantPosition horseElephantPosition) {
        // given
        Map<Dynasty, HorseElephantPosition> horseElephantPositionMap = Map.of(
                Dynasty.CHO, horseElephantPosition,
                Dynasty.HAN, horseElephantPosition
        );
        BoardDesignPolicy boardDesignPolicy = new BoardDesignPolicy(horseElephantPositionMap);

        // when
        Map<Position, Piece> board = boardDesignPolicy.initBoard();

        // then
        assertThat(board).hasSize(32);
        assertHorseElephantPositionByRow(horseElephantPosition, board, 1);
        assertHorseElephantPositionByRow(horseElephantPosition, board, 10);
    }

    private static void assertHorseElephantPositionByRow(HorseElephantPosition horseElephantPosition, Map<Position, Piece> board, int row) {
        assertThat(board.get(Position.from(row, horseElephantPosition.leftHorseColumn())).moveStrategy())
                .isInstanceOf(HorseMoveStrategy.class);
        assertThat(board.get(Position.from(row, horseElephantPosition.leftElephantColumn())).moveStrategy())
                .isInstanceOf(ElephantMoveStrategy.class);
        assertThat(board.get(Position.from(row, horseElephantPosition.rightHorseColumn())).moveStrategy())
                .isInstanceOf(HorseMoveStrategy.class);
        assertThat(board.get(Position.from(row, horseElephantPosition.rightElephantColumn())).moveStrategy())
                .isInstanceOf(ElephantMoveStrategy.class);
    }

}
