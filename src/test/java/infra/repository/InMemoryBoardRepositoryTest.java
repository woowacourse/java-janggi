package infra.repository;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.BoardPosition;
import domain.piece.Chariot;
import domain.piece.Team;
import fake.InMemoryBoardRepository;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class InMemoryBoardRepositoryTest {

    @Nested
    class ValidCases {

        @Test
        @DisplayName("보드를 저장하면 조회할 수 있다.")
        void save() {
            // given
            InMemoryBoardRepository repository = new InMemoryBoardRepository();
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
            InMemoryBoardRepository repository = new InMemoryBoardRepository();

            // when & then
            assertThat(repository.hasAnyPiece()).isFalse();
        }

        @Test
        @DisplayName("저장된 기물을 가져온다.")
        void load() {
            // given
            InMemoryBoardRepository repository = new InMemoryBoardRepository();
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new Chariot(Team.RED)
            ));
            repository.save(board);

            // when
            Board loadedBoard = repository.load().get();
            ;

            // then
            assertThat(loadedBoard.getPieces()).isEqualTo(board.getPieces());
        }

        @Test
        @DisplayName("보드를 삭제하면 데이터가 사라진다.")
        void deleteAll() {
            // given
            InMemoryBoardRepository repository = new InMemoryBoardRepository();
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
