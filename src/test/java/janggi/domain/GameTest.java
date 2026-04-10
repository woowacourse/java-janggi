package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.Formation;
import janggi.domain.board.FormationCommand;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.player.Name;
import janggi.domain.player.Player;
import janggi.domain.player.Players;
import janggi.domain.space.Position;
import janggi.domain.state.Finished;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
    private Players players;
    private Game game;

    @BeforeEach
    void setUp() {
        Player choPlayer = new Player(new Name("cho"), Side.CHO);
        Player hanPlayer = new Player(new Name("han"), Side.HAN);
        players = new Players(choPlayer, hanPlayer);
        Board board = BoardFactory.create(Formation.from(FormationCommand.FIRST), Formation.from(FormationCommand.FIRST));
        game = Game.startNew(board, players);
    }

    @Test
    void 기물_이동이_완료되면_턴이_상대방_진영으로_변경된다() {
        // Given: 초나라 졸(0,3)을 (0,4)로 전진
        Position source = Position.of(0, 3);
        Position target = Position.of(0, 4);

        // When
        game.move(source, target);

        // Then
        assertThat(game.getCurrentSide()).isEqualTo(Side.HAN);
    }

    @Test
    void 이동할_수_있는_목적지가_전혀_없는_기물을_선택하면_예외가_발생한다() {
        // Given: 사(Guard)를 기물들로 사방을 포위하여 이동 경로가 0개인 상황 연출
        Position guardPos = Position.of(4, 0);
        Map<Position, Piece> blockedMap = Map.of(
                guardPos, PieceFactory.createGuard(Side.CHO),
                Position.of(3, 0), PieceFactory.createChariot(Side.CHO),
                Position.of(5, 0), PieceFactory.createChariot(Side.CHO),
                Position.of(4, 1), PieceFactory.createChariot(Side.CHO)
        );
        Game blockedGame = Game.startNew(new Board(blockedMap), players);

        // When & Then: selectSource 내부에서 movablePositions.isEmpty() 체크 시 예외 발생
        assertThatThrownBy(() -> blockedGame.selectSource(guardPos))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 선택한_기물이_이동할_수_없는_위치를_목적지로_입력하면_예외가_발생한다() {
        // Given: 초나라 졸(0,3) 선택 (졸은 대각선 이동 불가)
        Position source = Position.of(0, 3);
        Position invalidTo = Position.of(1, 4);

        // When & Then: validateDestinations(to) 호출 시 예외 발생
        assertThatThrownBy(() -> game.move(source, invalidTo))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 보드에_궁이_하나만_남게_되면_게임은_종료_상태가_된다() {
        // Given: 한나라 궁(General)이 잡히고 초나라 궁만 남은 보드 상황
        Map<Position, Piece> oneGeneralMap = Map.of(
                Position.of(4, 1), PieceFactory.createGeneral(Side.CHO)
        );
        Game gameOverGame = Game.startNew(new Board(oneGeneralMap), players);

        // When & Then
        assertThat(gameOverGame.isPlaying()).isFalse();
    }

    @Test
    void 목적지에_상대방의_궁이_있어_잡게_되는_순간_게임이_종료된다() {
        // given
        Position generalOfCho = Position.of(4, 1);
        Position generalOfHan = Position.of(4, 8);
        Position soldierOfCho = Position.of(4, 7);
        Board board = new Board(Map.of(
                generalOfCho, PieceFactory.createGeneral(Side.CHO),
                generalOfHan, PieceFactory.createGeneral(Side.HAN),
                soldierOfCho, PieceFactory.createSoldier(Side.CHO)
        ));
        Game actual = Game.startNew(board, players);
        assertThat(actual.isPlaying()).isTrue();

        // when
        actual.move(soldierOfCho, generalOfHan);

        // then
        assertThat(actual.isPlaying()).isFalse();
    }

    @Test
    void 게임이_종료된_상태에서_기물을_이동하려고_시도하면_예외가_발생한다() {
        // given
        Map<Position, Piece> oneGeneralMap = Map.of(
                Position.of(4, 1), PieceFactory.createGeneral(Side.CHO)
        );
        Game gameOverGame = Game.restore(new Finished(new Board(oneGeneralMap), Side.CHO), players);

        assertThat(gameOverGame.isPlaying()).isFalse();

        // when & Then
        assertThatThrownBy(() -> gameOverGame.move(Position.of(4, 1), Position.of(4, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("게임이 이미 종료되었습니다.");
    }


    @Test
    void 현재_보드에_남아있는_양_진영의_기물을_바탕으로_총_점수를_합산하여_올바르게_계산한다() {
        // when
        Score actualOfCho = game.calculateScore(Side.CHO);
        Score actualOfHan = game.calculateScore(Side.HAN);

        // then
        assertThat(actualOfCho.value()).isEqualTo(72.0);
        assertThat(actualOfHan.value()).isEqualTo(73.5);
    }
}
