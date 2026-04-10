package data;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardRepositoryTest extends DatabaseTestHelper {
    private DataSource dataSource;
    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        dataSource = createDataSource();
        boardRepository = new BoardRepository();
    }

    @Test
    void 장기판을_저장하고_다시_조회한다() throws Exception {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(4, 0), Piece.of(Camp.CHO, PieceType.GENERAL));
        pieces.put(new Position(0, 0), Piece.of(Camp.CHO, PieceType.CHARIOT));
        pieces.put(new Position(4, 9), Piece.of(Camp.HAN, PieceType.GENERAL));
        Board board = Board.from(pieces);

        try (Connection connection = getConnection(dataSource)) {
            boardRepository.save(connection, board);
            Board loadedBoard = boardRepository.findById(connection, board.id());

            assertThat(loadedBoard.turn()).isEqualTo(Camp.CHO);
            assertThat(loadedBoard.isGameInProgress()).isTrue();
            assertThat(loadedBoard.pieces()).isEqualTo(board.pieces());
        }
    }

    @Test
    void 저장된_장기판을_수정한_뒤_다시_저장한다() throws Exception {
        Map<Position, Piece> pieces = new HashMap<>();
        Position choGeneral = new Position(4, 0);
        Position from = new Position(4, 8);
        Position to = new Position(4, 9);
        pieces.put(choGeneral, Piece.of(Camp.CHO, PieceType.GENERAL));
        pieces.put(from, Piece.of(Camp.CHO, PieceType.CHARIOT));
        pieces.put(to, Piece.of(Camp.HAN, PieceType.GENERAL));
        Board board = Board.from(pieces);

        try (Connection connection = getConnection(dataSource)) {
            boardRepository.save(connection, board);
            board.move(from, to);
            boardRepository.save(connection, board);

            Board loadedBoard = boardRepository.findById(connection, board.id());

            assertThat(loadedBoard.isGameInProgress()).isFalse();
            assertThat(loadedBoard.winner()).isEqualTo(Camp.CHO);
            assertThat(loadedBoard.pieces()).isEqualTo(board.pieces());
        }
    }

    @Test
    void 저장된_장기판을_삭제한다() throws Exception {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(4, 0), Piece.of(Camp.CHO, PieceType.GENERAL));
        pieces.put(new Position(4, 9), Piece.of(Camp.HAN, PieceType.GENERAL));
        Board board = Board.from(pieces);

        try (Connection connection = getConnection(dataSource)) {
            boardRepository.save(connection, board);

            boardRepository.delete(connection, board);

            assertThatThrownBy(() -> boardRepository.findById(connection, board.id()))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
