package repository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import domain.Board;
import domain.Game;
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

public class PieceRepositoryTest {
    private PieceRepository pieceRepository;
    private Connection connection;
    private final GameRepository gameRepository = new GameRepository();

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

            this.connection = connection;
        } catch (Exception e) {
            System.out.println("테이블을 생성하지 못했습니다" + e.getMessage());
            throw new RuntimeException(e);
        }
        pieceRepository = new PieceRepository();
    }

    @Test
    void 기물이_저장되고_조회된다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.from(1, 4), new Pawn(Team.CHO));

        Board board = new Board(pieces);
        Game game = new Game(board);
        game.assignId(1L);

        // when
        gameRepository.save(game, connection);
        pieceRepository.save(game, connection);
        Map<Position, Piece> found = pieceRepository.findByGameId(1L, connection);

        // then
        assertThat(found).hasSize(1);
    }
}
