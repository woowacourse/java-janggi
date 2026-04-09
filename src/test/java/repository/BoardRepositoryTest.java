package repository;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import domain.state.GameInitializer;
import domain.state.JanggiGame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardRepositoryTest {
    private final BoardRepository boardRepository = new BoardRepository();
    private final GameRepository gameRepository = new GameRepository();

    private JanggiGame game;
    private long gameId;

    @BeforeEach
    void init() {
        String sql1 = "DELETE FROM BOARD";
        String sql2 = "DELETE FROM GAME_ROOM";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement psmt1 = conn.prepareStatement(sql1);
             PreparedStatement psmt2 = conn.prepareStatement(sql2)) {
            psmt1.executeUpdate();
            psmt2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        gameId = gameRepository.save(Team.CHO, "테스트용 장기방");
        game = GameInitializer.init(SettingType.LEFT, SettingType.LEFT);
        boardRepository.saveAll(game, gameId);
    }

    @Test
    void 기물의_좌표가_변경되어야_한다() {
        Position from = Position.of(1, 1);
        Position to = Position.of(3, 1);

        // when
        boardRepository.updatePosition(gameId, from, to);

        PieceType pieceType = getPieceTypeFromDatabase(to);

        // then
        Assertions.assertThat(pieceType).isEqualTo(PieceType.CHA);
    }

    private PieceType getPieceTypeFromDatabase(Position to) {
        String sql = "SELECT PIECE_TYPE FROM BOARD WHERE GAME_ROOM_ID = ? AND POSITION_ROW = ? AND POSITION_COLUMN = ?";

        PieceType pieceType = null;
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setLong(1, gameId);
            psmt.setInt(2, to.getRow().getValue());
            psmt.setInt(3, to.getColumn().getValue());

            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                pieceType = PieceType.valueOf(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pieceType;
    }

    @Test
    void 선택한_좌표의_기물이_삭제되어야_한다() {
        Position to = Position.of(3, 1);

        // when
        boardRepository.delete(gameId, to);

        PieceType pieceType = getPieceTypeFromDatabase(to);

        // then
        Assertions.assertThat(pieceType).isNull();
    }

    @Test
    void 저장된_데이터_그대로_로딩되어야_한다() {
        Map<Position, Piece> load = boardRepository.load(gameId);

        Assertions.assertThat(load).containsAllEntriesOf(game.getBoard());
    }
}