package team.janggi.repository;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.piece.King;
import team.janggi.domain.piece.Piece;
import team.janggi.domain.piece.PieceType;
import team.janggi.infra.ConnectionManager;
import team.janggi.infra.TestH2Connection;

public class BoardPieceRepositoryTest {
    private ConnectionManager connectionManager;

    @BeforeEach
    public void setup() {
        connectionManager = new TestH2Connection();
    }

    @Test
    public void 특정_게임_ID에_대한_기물_정보를_저장한다() {
        // given
        Connection connection = connectionManager.getConnection();
        BoardPieceRepository boardPieceRepository = new BoardPieceRepository();

        // when
        Map<Position, Piece> testMap = new HashMap<>();
        testMap.put(new Position(4, 1), new King(Team.HAN));

        // then
        Assertions.assertDoesNotThrow(() -> boardPieceRepository.saveBoardPiece(1, testMap, connection));
    }

    @Test
    public void 특정_게임_ID에_대한_기물_정보를_가져온다() {
        // given
        Connection connection = connectionManager.getConnection();
        BoardPieceRepository boardPieceRepository = new BoardPieceRepository();

        // when
        Map<Position, Piece> loadBoard = boardPieceRepository.loadBoardPiece(1, connection);
        Piece testPiece = loadBoard.get(Position.of(4, 8));

        // then
        Assertions.assertEquals(PieceType.KING, testPiece.getPieceType());
    }
}
