package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.common.Position;
import domain.common.Side;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.player.Name;
import domain.player.Players;
import domain.score.ScorePolicy;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
    private Players players;
    private Game game;

    @BeforeEach
    void setUp() {
        players = Players.createInitial(new Name("cho"), new Name("han"));
        Board board = BoardFactory.create(Formation.LEFT_ELEPHANT, Formation.LEFT_ELEPHANT);
        game = new Game(board, players, new ScorePolicy());
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
        Game blockedGame = new Game(new Board(blockedMap), players, new ScorePolicy());

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
        Game gameOverGame = new Game(new Board(oneGeneralMap), players, new ScorePolicy());

        // When & Then
        assertThat(gameOverGame.isOver()).isTrue();
    }

    @Test
    void 게임은_진영별_현재_점수를_조회할_수_있다() {
        assertThat(game.getScore(Side.CHO)).isEqualTo(72.0);
        assertThat(game.getScore(Side.HAN)).isEqualTo(73.5);
    }
}
