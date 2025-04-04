package service;

import static org.assertj.core.api.Assertions.assertThat;

import dao.JanggiDao;
import dao.PieceDao;
import domain.Board;
import domain.fake.FakeJanggiDao;
import domain.fake.FakePieceDao;
import domain.fake.InitialChessPiecePositionsWithOutKingGenerator;
import domain.position.ChessPiecePositions;
import domain.position.InitialChessPiecePositionsGenerator;
import game.Janggi;
import game.Turn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanngiServiceTest {

    @Test
    @DisplayName("왕이 죽으면 게임이 종료 된다.")
    void test1() {
        //given
        final ChessPiecePositions chessPiecePositions = ChessPiecePositions.from(new InitialChessPiecePositionsWithOutKingGenerator());
        final Turn turn = Turn.create();
        final Board board = new Board(chessPiecePositions);
        final Janggi janggi = new Janggi(board, turn);
        final PieceDao fakePieceDao = new FakePieceDao();
        final JanggiDao fakeJanggiDao = new FakeJanggiDao();

        //when
        final JanngiService janngiService = new JanngiService(fakePieceDao, fakeJanggiDao);
        final boolean result = janngiService.canContinueGame(janggi);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("두 팀의 왕이 모두 살아있어 게임을 계속 이어나간다.")
    void test2() {
        //given
        final ChessPiecePositions chessPiecePositions = ChessPiecePositions.from(new InitialChessPiecePositionsGenerator());
        final Turn turn = Turn.create();
        final Board board = new Board(chessPiecePositions);
        final Janggi janggi = new Janggi(board, turn);
        final PieceDao fakePieceDao = new FakePieceDao();
        final JanggiDao fakeJanggiDao = new FakeJanggiDao();

        //when
        final JanngiService janngiService = new JanngiService(fakePieceDao, fakeJanggiDao);
        final boolean result = janngiService.canContinueGame(janggi);

        //then
        assertThat(result).isTrue();

    }

}
