package database;

import domain.board.Board;
import domain.board.Position;
import domain.game.Game;
import domain.game.GameStatus;
import domain.game.SavedGame;
import domain.game.TurnManager;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.TeamColor;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class H2GameRepositoryTest {

    @Test
    void 진행중_게임을_저장하고_다시_조회할_수_있다() {
        DatabaseConfig config = DatabaseConfig.inMemory("janggi-repository");
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(new ConnectionManager(config));
        H2GameRepository repository = new H2GameRepository(new ConnectionManager(config));
        databaseInitializer.initialize();

        SavedGame savedGame = repository.save(new Game(
                new Board(Map.of(
                        Position.of(0, 4), Piece.of(TeamColor.HAN, PieceType.KING),
                        Position.of(9, 4), Piece.of(TeamColor.CHO, PieceType.KING),
                        Position.of(6, 0), Piece.of(TeamColor.CHO, PieceType.PAWN)
                )),
                new TurnManager(TeamColor.HAN),
                GameStatus.IN_PROGRESS
        ));

        SavedGame loadedGame = repository.findInProgress().orElseThrow();

        assertThat(loadedGame.id()).isEqualTo(savedGame.id());
        assertThat(loadedGame.game().currentTurn()).isEqualTo(TeamColor.HAN);
        assertThat(loadedGame.game().status()).isEqualTo(GameStatus.IN_PROGRESS);
        assertThat(loadedGame.game().board().findPiece(Position.of(6, 0))).isPresent();
        assertThat(loadedGame.game().board().findPiece(Position.of(6, 0)).orElseThrow().getPieceType()).isEqualTo(PieceType.PAWN);
    }

    @Test
    void 종료된_게임은_진행중_게임으로_조회되지_않는다() {
        DatabaseConfig config = DatabaseConfig.inMemory("janggi-finished");
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(new ConnectionManager(config));
        H2GameRepository repository = new H2GameRepository(new ConnectionManager(config));
        databaseInitializer.initialize();

        SavedGame savedGame = repository.save(new Game(
                new Board(Map.of(
                        Position.of(0, 4), Piece.of(TeamColor.HAN, PieceType.KING),
                        Position.of(9, 4), Piece.of(TeamColor.CHO, PieceType.KING)
                )),
                new TurnManager(),
                GameStatus.IN_PROGRESS
        ));
        savedGame.game().finish();
        repository.save(savedGame);

        assertThat(repository.findInProgress()).isEmpty();
    }

    @Test
    void 진행중_게임을_삭제할_수_있다() {
        DatabaseConfig config = DatabaseConfig.inMemory("janggi-delete");
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(new ConnectionManager(config));
        H2GameRepository repository = new H2GameRepository(new ConnectionManager(config));
        databaseInitializer.initialize();

        repository.save(new Game(
                new Board(Map.of(
                        Position.of(0, 4), Piece.of(TeamColor.HAN, PieceType.KING),
                        Position.of(9, 4), Piece.of(TeamColor.CHO, PieceType.KING)
                )),
                new TurnManager(),
                GameStatus.IN_PROGRESS
        ));

        repository.deleteInProgress();

        assertThat(repository.findInProgress()).isEmpty();
    }
}
