package janggi.dao;

import janggi.board.Board;
import janggi.board.Pieces;
import janggi.dao.connection.MysqlConnection;
import janggi.piece.Team;
import janggi.piece.pieces.Piece;
import janggi.position.Position;
import java.util.Map;
import java.util.Map.Entry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {
    private BoardDao boardDao;
    private PieceDao pieceDao;

    @BeforeEach
    void setUp() {
        MysqlConnection dbConnection = new MysqlConnection();
        boardDao = new BoardDao(dbConnection);
        pieceDao = new PieceDao(dbConnection);
        boardDao.deleteAllBoards();
        pieceDao.deleteAllPieces();
    }

    @Test
    @DisplayName("초기화된 모든 기물을 저장한다.")
    void test() {
        // given
        Pieces pieces = new Pieces();
        Board board = new Board(pieces, Team.CHO);
        Map<Position, Piece> piecesAndPosition = pieces.getPieces();
        String boardId = boardDao.addBoard(board);

        // when & then
        for (Entry<Position, Piece> entry : piecesAndPosition.entrySet()) {
            Assertions.assertThatCode(
                            () -> pieceDao.addPiece(
                                    entry.getValue(),
                                    entry.getKey().getColumn(),
                                    entry.getKey().getRow(),
                                    boardId))
                    .doesNotThrowAnyException();
        }
    }
}
