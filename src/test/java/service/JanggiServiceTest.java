package service;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.BoardFixture;
import dto.GameSummary;
import dto.GameWrapper;
import java.io.IOException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.JanggiGameRepository;
import repository.MemoryDBConnectionUtil;
import support.TransactionTemplate;

@DisplayName("서비스 계층 테스트")
class JanggiServiceTest {

    private JanggiService janggiService;

    @BeforeEach
    void setUp() throws IOException {
        DataSource dataSource = MemoryDBConnectionUtil.getDataSource();
        janggiService = new JanggiService(new TransactionTemplate(dataSource), new JanggiGameRepository(dataSource));
        janggiService.clear();
    }

    @AfterEach
    void tearDown() {
        janggiService.clear();
    }

    @DisplayName("새로운 게임을 생성한다")
    @Test
    void 새로운_게임_생성() {
        GameWrapper gameWrapper = janggiService.createGame(BoardFixture.create());

        GameWrapper loaded = janggiService.loadGame(gameWrapper.gameId());

        assertThat(loaded)
                .usingRecursiveComparison()
                .isEqualTo(gameWrapper);
    }

    @DisplayName("존재하는 게임들의 요약 정보를 조회한다")
    @Test
    void 게임_목록_조회() {
        GameWrapper gameWrapperA = janggiService.createGame(BoardFixture.create());
        GameWrapper gameWrapperB = janggiService.createGame(BoardFixture.create());

        List<GameSummary> gameSummaries = janggiService.loadAllGameSummaries();

        assertThat(gameSummaries)
                .hasSize(2)
                .extracting(GameSummary::id)
                .containsExactlyInAnyOrder(gameWrapperA.gameId(), gameWrapperB.gameId());
    }
}
