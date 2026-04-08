package repository;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.Position;
import domain.board.SetUp;
import domain.game.Game;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcGameRepositoryTest {
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        String url = "jdbc:h2:mem:janggi-" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1";
        gameRepository = new JdbcGameRepository(url);
    }

    @Test
    @DisplayName("저장한 진행 중인 게임을 다시 조회할 수 있다")
    void findInProgressGame() {
        Game game = Game.start(SetUp.INNER_ELEPHANT, SetUp.LEFT_ELEPHANT);

        gameRepository.save(game);

        Game savedGame = gameRepository.findInProgressGame().orElseThrow();

        assertThat(savedGame.currentTurn()).isEqualTo(game.currentTurn());
        assertThat(savedGame.isFinished()).isEqualTo(game.isFinished());
        assertThat(savedGame.board().findBy(new Position(5, 9)).type()).isEqualTo(PieceType.GENERAL);
        assertThat(savedGame.board().findBy(new Position(5, 2)).type()).isEqualTo(PieceType.GENERAL);
        assertThat(savedGame.board().findBy(new Position(1, 7)).type()).isEqualTo(PieceType.SOLDIER);
        assertThat(savedGame.board().findBy(new Position(1, 4)).type()).isEqualTo(PieceType.SOLDIER);
    }

    @Test
    @DisplayName("종료된 게임은 조회되지 않는다")
    void cannotFindFinishedGame() {
        Board board = new Board(Map.of(
                new Position(5, 5), new Piece(Camp.CHO, PieceType.CHARIOT),
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL)
        ));
        Game finishedGame = Game.restore(board, Camp.CHO, false);
        finishedGame.move(new Position(5, 5), new Position(5, 2));

        gameRepository.save(finishedGame);

        assertThat(gameRepository.findInProgressGame()).isEmpty();
    }

    @Test
    @DisplayName("같은 게임을 다시 저장하면 최신 상태가 조회된다")
    void saveLatestGameState() {
        Game game = Game.start(SetUp.LEFT_ELEPHANT, SetUp.RIGHT_ELEPHANT);

        gameRepository.save(game);

        game.move(new Position(1, 7), new Position(1, 6));
        gameRepository.save(game);

        Game savedGame = gameRepository.findInProgressGame().orElseThrow();

        assertThat(savedGame.currentTurn()).isEqualTo(Camp.HAN);
        assertThat(savedGame.board().findPiece(new Position(1, 7))).isEmpty();
        assertThat(savedGame.board().findBy(new Position(1, 6)).type()).isEqualTo(PieceType.SOLDIER);
        assertThat(savedGame.board().findBy(new Position(1, 6)).camp()).isEqualTo(Camp.CHO);
    }
}
