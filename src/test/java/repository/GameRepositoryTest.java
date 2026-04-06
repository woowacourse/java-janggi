package repository;

import static org.assertj.core.api.Assertions.assertThat;

import database.InitTable;
import domain.Board;
import domain.Game;
import domain.PieceType;
import domain.Position;
import domain.Team;
import domain.piece.Pawn;
import domain.piece.Piece;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameRepositoryTest {
    private GameRepository gameRepository;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
                "sa",
                "");
        InitTable.schemaInit(connection);
        gameRepository = new GameRepository(connection);
    }

    @Test
    void 게임이_저장_된다() {
        Board board = new Board();
        Game game = new Game(board);

        gameRepository.save(game, board);
        Game found = gameRepository.findByGameId(game.id());

        assertThat(found.board().getPieces()).hasSize(board.getPieces().size());
    }

    @Test
    void 저장된_마자막_게임이_조회된다() {
        Board board = new Board();
        Game game = new Game(board);

        gameRepository.save(game, board);
        Game latelyGame = gameRepository.findLatest();

        assertThat(latelyGame.id()).isEqualTo(game.id());
        assertThat(latelyGame.turn()).isEqualTo(game.turn());
        assertThat(latelyGame.board().getPieces()).hasSize(board.getPieces().size());
    }

    @Test
    void 업데이트하면_보드가_변경된다() {
        Piece pawn = new Pawn(Team.CHO);

        Position from = Position.from(9, 5);
        Position to = Position.from(9, 6);
        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Pawn(Team.CHO));
        testPiece.put(to, new Pawn(Team.HAN));

        Board board = new Board(testPiece);
        Game game = new Game(board);

        gameRepository.save(game, board);

        board.move(from, to, PieceType.PAWN, Team.CHO);
        game.changeTurn();
        gameRepository.update(game, board, from, to);

        Game latelyGame = gameRepository.findLatest();

        assertThat(latelyGame.board().getPieces()).hasSize(1);
        assertThat(latelyGame.turn()).isEqualTo(Team.HAN);
    }
}
