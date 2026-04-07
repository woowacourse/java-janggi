package repository;

import static org.assertj.core.api.Assertions.assertThat;

import config.DatabaseConfig;
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
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameRepositoryTest {
    private GameRepository gameRepository;

    @BeforeEach
    public void setUp() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
                "sa",
                "");

        try (Statement stmt = connection.createStatement()){
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS game (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    turn varchar(10) NOT NULL,
                    is_finished boolean NOT NULL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS piece (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    game_id BIGINT NOT NULL ,
                    row_index INT NOT NULL,
                    column_index INT  NOT NULL,
                    piece_type varchar(20)  NOT NULL,
                    team varchar(10) NOT NULL,
                    FOREIGN KEY (game_id) REFERENCES game(id)
                )
            """);

        } catch (Exception e) {
            System.out.println("테이블을 생성하지 못했습니다" + e.getMessage());
            throw new RuntimeException(e);
        }

        gameRepository = new GameRepository();
    }

    @Test
    void 게임이_저장_된다() {
        Connection connection = DatabaseConfig.createConnection();

        Board board = new Board();
        Game game = new Game(board);

        gameRepository.save(game, connection);
        Game found = gameRepository.findByGameId(game.id(), connection);

        assertThat(found.board().getPieces()).hasSize(board.getPieces().size());
    }

    @Test
    void 저장된_마자막_게임이_조회된다() {
        Connection connection = DatabaseConfig.createConnection();

        Board board = new Board();
        Game game = new Game(board);

        gameRepository.save(game, connection);
        Game latelyGame = gameRepository.findLatest(connection);

        assertThat(latelyGame.id()).isEqualTo(game.id());
        assertThat(latelyGame.turn()).isEqualTo(game.turn());
        assertThat(latelyGame.board().getPieces()).hasSize(board.getPieces().size());
    }

    @Test
    void 업데이트하면_보드가_변경된다() {
        Connection connection = DatabaseConfig.createConnection();

        Piece pawn = new Pawn(Team.CHO);

        Position from = Position.from(9, 5);
        Position to = Position.from(9, 6);
        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Pawn(Team.CHO));
        testPiece.put(to, new Pawn(Team.HAN));

        Board board = new Board(testPiece);
        Game game = new Game(board);

        gameRepository.save(game, connection);

        board.move(from, to, PieceType.PAWN, Team.CHO);
        game.changeTurn();
        gameRepository.update(game, from, to, connection);

        Game latelyGame = gameRepository.findLatest(connection);

        assertThat(latelyGame.board().getPieces()).hasSize(1);
        assertThat(latelyGame.turn()).isEqualTo(Team.HAN);
    }
}
