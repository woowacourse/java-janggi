package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
    private Player choPlayer;
    private Player hanPlayer;

    @BeforeEach
    void setUp() {
        choPlayer = new Player(new Name("cho"), new CurrentTurn());
        hanPlayer = new Player(new Name("han"), new NotCurrentTurn());
    }

    @Test
    void nextTurn은_플레이어_턴을_교체하고_다음_진영명을_반환한다() {
        Game game = new Game(
                new Board(new HashMap<>()),
                choPlayer,
                hanPlayer
        );

        String first = game.nextTurn();
        String second = game.nextTurn();

        assertThat(first).isEqualTo("한");
        assertThat(second).isEqualTo("초");
    }

    @Test
    void movePiece는_보드의_기물을_이동시킨다() {
        Position from = new Position(0, 3);
        Position to = new Position(0, 4);
        Soldier soldier = new Soldier(Side.CHO);
        Game game = createGameWithPieces(Map.of(from, soldier));

        game.movePiece(from, to);

        assertThat(game.getBoard()).doesNotContainKey(from);
        assertThat(game.getBoard().get(to)).isSameAs(soldier);
    }

    @Test
    void 올바른_범위가_아닌_위치를_입력한_경우() {
        Position from = new Position(0, 3);
        Position to = new Position(0, 4);
        Soldier soldier = new Soldier(Side.CHO);
        Game game = createGameWithPieces(Map.of(from, soldier));

        assertThatThrownBy(() -> game.getPossibleDestinations(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 장군이_하나면_게임은_종료상태다() {
        Game game = createGameWithPieces(Map.of(new Position(4, 1), new General(Side.CHO)));

        assertThat(game.isOver()).isTrue();
    }

    private Game createGameWithPieces(Map<Position, Piece> pieces) {
        return new Game(
                new Board(new HashMap<>(pieces)),
                new Player(new Name("cho"), new CurrentTurn()),
                new Player(new Name("han"), new NotCurrentTurn())
        );
    }
}
