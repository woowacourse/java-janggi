package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.infrastructure.FakeGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("게임 저장소(GameRepository) 테스트")
class GameRepositoryTest {

    private FakeGameRepository gameRepository;
    private Board board;

    @BeforeEach
    void setUp() {
        gameRepository = new FakeGameRepository();
        board = BoardFactory.create("4", "4");
    }

    @DisplayName("게임을 저장하면 고유한 게임 id를 반환한다.")
    @Test
    void save_ReturnsGameId() {
        long gameId = gameRepository.save(board, Team.CHO);

        assertThat(gameId).isPositive();
    }

    @DisplayName("게임을 저장하고 id로 보드를 조회할 수 있다.")
    @Test
    void save_AndFindBoardById() {
        long gameId = gameRepository.save(board, Team.CHO);

        assertThat(gameRepository.findBoardById(gameId)).isPresent();
    }

    @DisplayName("게임을 저장하고 id로 현재 턴을 조회할 수 있다.")
    @Test
    void save_AndFindTurnById() {
        long gameId = gameRepository.save(board, Team.CHO);

        assertThat(gameRepository.findTurnById(gameId)).isEqualTo(Team.CHO);
    }

    @DisplayName("게임 정보를 업데이트하면 턴이 변경된다.")
    @Test
    void update_ChangesTurn() {
        long gameId = gameRepository.save(board, Team.CHO);

        gameRepository.update(gameId, board, Team.HAN);

        assertThat(gameRepository.findTurnById(gameId)).isEqualTo(Team.HAN);
    }

    @DisplayName("존재하지 않는 게임 id로 보드를 조회하면 빈 Optional을 반환한다.")
    @Test
    void findBoardById_NotFound_ReturnsEmpty() {
        assertThat(gameRepository.findBoardById(999L)).isEmpty();
    }

    @DisplayName("존재하지 않는 게임 id로 턴을 조회하면 예외가 발생한다.")
    @Test
    void findTurnById_NotFound_ThrowsException() {
        assertThatThrownBy(() -> gameRepository.findTurnById(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 게임을 찾을 수 없습니다: 999");
    }
}
