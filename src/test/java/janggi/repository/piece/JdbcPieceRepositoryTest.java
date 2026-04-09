package janggi.repository.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.config.DatabaseManager;
import janggi.config.DdlAuto;
import janggi.config.TestConfig;
import janggi.domain.position.Position;
import janggi.entity.PieceEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JdbcPieceRepositoryTest {

    private final PieceRepository pieceRepository = new JdbcPieceRepository();

    @BeforeAll
    static void setUp() {
        TestConfig.setUp();
    }

    @BeforeEach
    void setUpDatabase() {
        DatabaseManager.initTable(DdlAuto.CREATE_DROP);
    }

    @Test
    void 기물들을_저장할_수_있다() {
        // given
        Long gameId = insertGame("CHO", "PLAYING");
        List<PieceEntity> pieces = List.of(
                PieceEntity.toEntity(1, 1, "CHO", "CHARIOT"),
                PieceEntity.toEntity(1, 2, "CHO", "HORSE"),
                PieceEntity.toEntity(10, 1, "HAN", "CHARIOT")
        );

        // when
        DatabaseManager.withTransaction(connection -> {
            pieceRepository.saveAll(connection, gameId, pieces);
            return null;
        });

        // then
        List<PieceEntity> savedPieces = pieceRepository.findAllByGameId(gameId);
        assertThat(savedPieces).hasSize(3);
        assertThat(savedPieces).containsExactlyInAnyOrderElementsOf(pieces);
    }

    @Test
    void 기물을_이동할_수_있다() {
        // given
        Long gameId = insertGame("CHO", "PLAYING");
        List<PieceEntity> pieces = List.of(
                PieceEntity.toEntity(1, 1, "CHO", "CHARIOT")
        );

        DatabaseManager.withTransaction(connection -> {
            pieceRepository.saveAll(connection, gameId, pieces);
            return null;
        });

        Position from = Position.from(1, 1);
        Position to = Position.from(2, 1);

        // when
        DatabaseManager.withTransaction(connection -> {
            pieceRepository.updatePiece(connection, gameId, from, to);
            return null;
        });

        // then
        List<PieceEntity> updatedPieces = pieceRepository.findAllByGameId(gameId);
        assertThat(updatedPieces).hasSize(1);
        assertThat(updatedPieces).containsExactly(
                PieceEntity.toEntity(2, 1, "CHO", "CHARIOT")
        );
    }

    @Test
    void 도착지의_기물을_잡으면서_이동할_수_있다() {
        // given
        Long gameId = insertGame("CHO", "PLAYING");
        List<PieceEntity> pieces = List.of(
                PieceEntity.toEntity(1, 1, "CHO", "CHARIOT"),
                PieceEntity.toEntity(2, 1, "HAN", "SOLDIER")
        );

        DatabaseManager.withTransaction(connection -> {
            pieceRepository.saveAll(connection, gameId, pieces);
            return null;
        });

        Position from = Position.from(1, 1);
        Position to = Position.from(2, 1);

        // when
        DatabaseManager.withTransaction(connection -> {
            pieceRepository.updatePiece(connection, gameId, from, to);
            return null;
        });

        // then
        List<PieceEntity> updatedPieces = pieceRepository.findAllByGameId(gameId);
        assertThat(updatedPieces).hasSize(1);
        assertThat(updatedPieces).containsExactly(
                PieceEntity.toEntity(2, 1, "CHO", "CHARIOT")
        );
    }

    @Test
    void 이동할_기물이_없으면_예외가_발생한다() {
        // given
        Long gameId = insertGame("CHO", "PLAYING");

        Position from = Position.from(1, 1);
        Position to = Position.from(2, 1);

        // when & then
        assertThatThrownBy(() -> DatabaseManager.withTransaction(connection -> {
            pieceRepository.updatePiece(connection, gameId, from, to);
            return null;
        })).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 기물이 존재하지 않습니다.");
    }

    @Test
    void 특정_게임의_기물들을_조회할_수_있다() {
        // given
        Long firstGameId = insertGame("CHO", "PLAYING");
        Long secondGameId = insertGame("HAN", "PLAYING");

        DatabaseManager.withTransaction(connection -> {
            pieceRepository.saveAll(connection, firstGameId, List.of(
                    PieceEntity.toEntity(1, 1, "CHO", "CHARIOT"),
                    PieceEntity.toEntity(1, 2, "CHO", "HORSE")
            ));
            pieceRepository.saveAll(connection, secondGameId, List.of(
                    PieceEntity.toEntity(10, 1, "HAN", "CHARIOT")
            ));
            return null;
        });

        // when
        List<PieceEntity> pieces = pieceRepository.findAllByGameId(firstGameId);

        // then
        assertThat(pieces).hasSize(2);
        assertThat(pieces).containsExactlyInAnyOrder(
                PieceEntity.toEntity(1, 1, "CHO", "CHARIOT"),
                PieceEntity.toEntity(1, 2, "CHO", "HORSE")
        );
    }

    private Long insertGame(String turn, String state) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO janggi_game (turn, state) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            statement.setString(1, turn);
            statement.setString(2, state);
            statement.executeUpdate();

            try (var keys = statement.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        } catch (Exception e) {
            throw new RuntimeException("테스트용 게임 저장 실패", e);
        }
    }

}
