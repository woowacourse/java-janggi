package janggi.domain.game;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.game.Game.NO_AVAILABLE_MOVES_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.BoardDesignPolicy;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.ChariotMoveStrategy;
import janggi.domain.piece.HorseMoveStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.piece.SoldierMoveStrategy;
import janggi.domain.position.Position;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    @DisplayName("선택된 기물이 움직일 수 있는 위치가 없다면 오류를 일으킨다")
    public void canMovePositions_fail_no_position() {
        // given
        Position from = Position.from(1, 1);
        BoardDesignPolicy boardDesignPolicy = () -> Map.of(
                from, new Piece(CHO, new ChariotMoveStrategy()),
                Position.from(1, 2), new Piece(CHO, new HorseMoveStrategy()),
                Position.from(2, 1), new Piece(CHO, new SoldierMoveStrategy())
        );
        Game game = Game.initGame(boardDesignPolicy);

        // when & then
        assertThatThrownBy(() -> game.canMovePosition(from))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(NO_AVAILABLE_MOVES_MESSAGE);
    }

    @Test
    @DisplayName("기물을 움직인 후에는 턴이 변경되어야 한다.")
    public void movePiece_success_change_turn() throws Exception {
        // given
        Position from = Position.from(1, 1);
        BoardDesignPolicy boardDesignPolicy = () -> Map.of(
                from, new Piece(CHO, new ChariotMoveStrategy())
        );

        Game game = Game.initGame(boardDesignPolicy);
        Dynasty currentTurn = game.currentTurn();

        // when
        game.movePiece(from, Position.from(1, 2));
        
        // then
        assertThat(game.currentTurn()).isEqualTo(currentTurn.next());
    }
}
