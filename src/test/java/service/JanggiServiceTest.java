package service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.Board;
import domain.JanggiGame;
import domain.Position;
import domain.enums.Country;
import domain.enums.PieceType;
import service.dto.PositionDto;
import testUtil.BoardTestUtil;

class JanggiServiceTest {

    @BeforeEach
    void setUp() {
//        System.setProperty("db.url", "jdbc:h2:~/testDb;"); // Test DB도 확인이 필요하다면, 해당 옵션 선택
        System.setProperty("db.url", "jdbc:h2:mem:testDb;DB_CLOSE_DELAY=-1");
    }

    @Test
    void 게임_초기생성후_불러오기() {
        JanggiService janggiService = new JanggiService();
        List<PieceType> maSang = BoardTestUtil.createMasangSangMa();
        JanggiGame game = new JanggiGame(new Board(maSang));
        int gameId = janggiService.initializeData(game);

        janggiService.validateExistGame(gameId);
        Country turn = janggiService.getNowTurnCountry(gameId);

        assertDoesNotThrow(() -> janggiService.validateExistGame(gameId));
        assertThat(turn).isEqualTo(Country.CHO);
    }

    @Test
    void 게임_저장후_불러오기_순서() {
        JanggiService janggiService = new JanggiService();

        List<PieceType> maSang = BoardTestUtil.createMasangSangMa();
        JanggiGame game = new JanggiGame(new Board(maSang));

        int gameId = janggiService.initializeData(game);

        janggiService.applyMove(Position.create(1, 1), Position.create(2, 1), gameId);

        janggiService.validateExistGame(gameId);
        Country turn = janggiService.getNowTurnCountry(gameId);
        assertThat(turn).isEqualTo(Country.HAN);
    }

    @Test
    void 기물_이동_후_상태_결과_저장() {
        JanggiService janggiService = new JanggiService();
        List<PieceType> maSang = BoardTestUtil.createMasangSangMa();
        JanggiGame game = new JanggiGame(new Board(maSang));
        int gameId = janggiService.initializeData(game);

        janggiService.applyMove(Position.create(1, 1), Position.create(2, 1), gameId);

        Country turn = janggiService.getNowTurnCountry(gameId);
        boolean isGameOver = janggiService.isGameOver(gameId);


        assertThat(turn).isEqualTo(Country.HAN);
        assertThat(isGameOver).isFalse();
    }

    @Test
    void 기물_이동_후_기물위치_저장() {
        JanggiService janggiService = new JanggiService();
        List<PieceType> maSang = BoardTestUtil.createMasangSangMa();
        JanggiGame game = new JanggiGame(new Board(maSang));
        int gameId = janggiService.initializeData(game);

        janggiService.applyMove(Position.create(1, 1), Position.create(2, 1), gameId);
        janggiService.applyMove(Position.create(10, 1), Position.create(9, 1), gameId);

        Country turn = janggiService.getNowTurnCountry(gameId);
        boolean isGameOver = janggiService.isGameOver(gameId);


        assertThat(turn).isEqualTo(Country.CHO);
        assertThat(isGameOver).isFalse();
        assertThat(janggiService.getPiecePositions(gameId, PieceType.CHA))
                .contains(new PositionDto(2, 1));
    }

}