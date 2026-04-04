package repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.board.Position;
import domain.board.SetUp;
import domain.game.Game;
import domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InMemoryGameRepositoryTest {
    GameRepository gameRepository = new InMemoryGameRepository();

    @Test
    @DisplayName("저장한 게임을 다시 조회할 수 있다")
    void findInProgressGame() {
        Game game = new Game(SetUp.INNER_ELEPHANT, SetUp.LEFT_ELEPHANT);

        gameRepository.save(game);
        Game savedGame = gameRepository.findInProgressGame().orElseThrow();

        assertThat(savedGame.currentTurn()).isEqualTo(game.currentTurn());
        assertThat(savedGame.isFinished()).isEqualTo(game.isFinished());

        assertThat(savedGame.board().findBy(new Position(5, 9)).type())
                .isEqualTo(PieceType.GENERAL);
        assertThat(savedGame.board().findBy(new Position(5, 2)).type())
                .isEqualTo(PieceType.GENERAL);
        assertThat(savedGame.board().findBy(new Position(1, 7)).type())
                .isEqualTo(PieceType.SOLDIER);
        assertThat(savedGame.board().findBy(new Position(1, 4)).type())
                .isEqualTo(PieceType.SOLDIER);
    }
}
