package janggi.domain.game;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.game.Game.NO_AVAILABLE_MOVES_MESSAGE;
import static janggi.domain.piece.PieceType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.exception.DomainException;
import janggi.domain.board.BoardDesignPolicy;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.*;
import janggi.domain.position.Position;

import java.time.LocalDateTime;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    @DisplayName("선택된 기물이 움직일 수 있는 위치가 없다면 오류를 일으킨다")
    public void findMovablePositions() {
        // given
        Position from = Position.from(1, 1);
        BoardDesignPolicy boardDesignPolicy = () -> Map.of(
                from, new Piece(CHO, CHARIOT),
                Position.from(1, 2), new Piece(CHO, HORSE),
                Position.from(2, 1), new Piece(CHO, SOLDIER)
        );
        Game game = Game.initGame(boardDesignPolicy, "room", LocalDateTime.now());

        // when & then
        assertThatThrownBy(() -> game.findMovablePositions(from))
                .isInstanceOf(DomainException.class)
                .hasMessage(String.format(NO_AVAILABLE_MOVES_MESSAGE, from.row().row(), from.column().column()));
    }

    @Test
    @DisplayName("기물을 움직인 후에는 턴이 변경되어야 한다.")
    public void movePiece_success_change_turn() {
        // given
        Position from = Position.from(1, 1);
        BoardDesignPolicy boardDesignPolicy = () -> Map.of(
                from, new Piece(CHO, CHARIOT)
        );

        Game game = Game.initGame(boardDesignPolicy, "room", LocalDateTime.now());
        Dynasty currentTurn = game.currentTurn();

        // when
        game.movePiece(from, Position.from(1, 2), LocalDateTime.now());
        
        // then
        assertThat(game.currentTurn()).isEqualTo(currentTurn.next());
    }

    @Test
    @DisplayName("기물을 움직인 후에는 최근 플레이 시간이 업데이트 돼야 한다.")
    public void movePiece_success_chang_lastPlayedAt() {
        // given
        Position from = Position.from(1, 1);
        BoardDesignPolicy boardDesignPolicy = () -> Map.of(
                from, new Piece(CHO, CHARIOT)
        );

        LocalDateTime initTime = LocalDateTime.of(2026, 4, 5, 10, 15);
        Game game = Game.initGame(boardDesignPolicy, "room", initTime);
        Dynasty currentTurn = game.currentTurn();

        // when
        LocalDateTime playedAt = LocalDateTime.of(2026, 4, 5, 12, 44);
        game.movePiece(from, Position.from(1, 2), playedAt);

        // then
        assertThat(game.lastPlayedAt()).isEqualTo(playedAt);
    }
}
