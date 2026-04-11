package model;

import static org.assertj.core.api.Assertions.assertThat;

import model.board.Army;
import model.board.Board;
import model.board.Country;
import model.board.Status;
import model.board.strategy.InnerElephant;
import model.board.strategy.OuterElephant;
import model.move.Move;
import model.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class JanggiGameTest {

    private Board board;

    @BeforeEach()
    void setUp() {
        board = new Board();
        Army cho = new Army(new InnerElephant());
        Army han = new Army(new OuterElephant());
        cho.deployTo(board, Country.CHO);
        han.deployTo(board, Country.HAN);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5})
        // 3회 이상일 때
    void 동일_상황_반복_횟수가_3회_이상이면_게임_상태가_DRAW로_변한다(int count) {
        // given
        JanggiGame game = new JanggiGame(1, board, Country.CHO, Status.PLAYING);
        Move move = new Move(Position.of(1, 1), Position.of(2, 1));

        // when
        game.move(move, count);

        // then
        assertThat(game.isDraw()).isTrue();
        assertThat(game.status()).isEqualTo(Status.DRAW);
    }

    @Test
    void 왕이_잡히면_게임은_더_이상_진행_중이_아니다() {
        // given
        board.remove(Position.of(2, 5));

        JanggiGame game = new JanggiGame(1, board, Country.CHO, Status.PLAYING);

        // then
        assertThat(game.isProgressing()).isFalse();
    }

    @Test
    void 점수가_높은_진영이_승리_진영으로_계산된다() {
        // given
        board.remove(Position.of(1, 1));
        JanggiGame game = new JanggiGame(1, board, Country.CHO, Status.PLAYING);

        // when
        Country winner = game.scoreWinnerCountry();

        // then
        assertThat(winner).isEqualTo(Country.CHO);
    }
}