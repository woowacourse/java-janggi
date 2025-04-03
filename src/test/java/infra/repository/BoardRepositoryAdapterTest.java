package infra.repository;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.BoardPosition;
import domain.piece.Chariot;
import domain.piece.Team;
import infra.dao.PieceDao;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardRepositoryAdapterTest {

    private final PieceDao pieceDao = new PieceDao();

    @BeforeEach
    void setUp() {
        pieceDao.deleteAll();
    }

    @AfterEach
    void tearDown() {
        pieceDao.deleteAll();
    }
    
    @Nested
    class ValidCases {

        @Test
        @DisplayName("보드를 저장할 수 있다.")
        void save() {
            // given
            BoardRepositoryAdapter repository = new BoardRepositoryAdapter(pieceDao);
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new Chariot(Team.RED)
            ));
            repository.save(board);

            // when
            Board loadedBoard = repository.load().get();

            // then
            assertThat(loadedBoard.getPieces()).isEqualTo(board.getPieces());
        }

        @Test
        @DisplayName("기물이 존재하지 않음을 판별한다.")
        void hasAnyPiece() {
            // given
            BoardRepositoryAdapter repository = new BoardRepositoryAdapter(pieceDao);

            // when & then
            assertThat(repository.hasAnyPiece()).isFalse();
        }

        @Test
        @DisplayName("저장된 기물을 가져온다.")
        void load_savedBoard() {
            // given
            BoardRepositoryAdapter repository = new BoardRepositoryAdapter(pieceDao);
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new Chariot(Team.RED)
            ));
            repository.save(board);

            // when
            Board loadedBoard = repository.load().get();

            // then
            assertThat(loadedBoard.getPieces()).isEqualTo(board.getPieces());
        }

        @Test
        @DisplayName("저장된 기물이 없다면 가져오지 못한다.")
        void load_noSavedBoard() {
            // given
            BoardRepositoryAdapter repository = new BoardRepositoryAdapter(pieceDao);

            // when
            Optional<Board> loadedBoard = repository.load();

            // then
            assertThat(loadedBoard).isEmpty();
        }

        @Test
        @DisplayName("보드를 삭제하면 데이터가 사라진다.")
        void deleteAll() {
            // given
            BoardRepositoryAdapter repository = new BoardRepositoryAdapter(pieceDao);
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new Chariot(Team.RED)
            ));
            repository.save(board);

            // when
            repository.deleteAll();

            // then
            assertThat(repository.load()).isEmpty();
        }
    }
}
