package janggi.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Coordinate;
import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Team;
import janggi.service.PlayingTurn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RepositoryTest {

    private Repository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryGameRepository();
    }

    @Test
    @DisplayName("피스를 저장할 수 있다.")
    void save() {
        final var piece = new Piece(Team.CHO, new Coordinate(1, 1), PieceType.CHA);

        repository.save(piece);

        final var pieces = repository.allPieces();
        assertThat(pieces).contains(piece);
    }

    @Test
    @DisplayName("저장된 피스를 좌표를 이용해 찾아서 수정한다.")
    void update() {
        final var piece = new Piece(Team.CHO, new Coordinate(1, 1), PieceType.CHA);
        repository.save(piece);

        repository.update(new Coordinate(1, 1), new Coordinate(2, 2));

        final var pieces = repository.allPieces();
        assertAll(
            () -> assertThat(pieces).noneMatch(savedPiece -> savedPiece.isAt(new Coordinate(1, 1))),
            () -> assertThat(pieces).contains(new Piece(Team.CHO, new Coordinate(2, 2), PieceType.CHA))
        );
    }

    @Test
    @DisplayName("저장된 모든 피스를 반환한다.")
    void findAll() {
        final var piece1 = new Piece(Team.CHO, new Coordinate(1, 1), PieceType.CHA);
        final var piece2 = new Piece(Team.HAN, new Coordinate(2, 2), PieceType.MA);
        repository.save(piece1);
        repository.save(piece2);

        final var pieces = repository.allPieces();
        assertThat(pieces).contains(piece1, piece2);
    }

    @Test
    @DisplayName("좌표를 이용해 해당 좌표로 저장된 피스를 삭제한다.")
    void deleteByCoordinate() {
        final var piece = new Piece(Team.CHO, new Coordinate(1, 1), PieceType.CHA);
        repository.save(piece);

        repository.deleteByCoordinate(new Coordinate(1, 1));

        final var pieces = repository.allPieces();
        assertThat(pieces).doesNotContain(piece);
    }

    @Test
    @DisplayName("턴 정보를 수정한다.")
    void updateTurn() {
        final var playingTurn = new PlayingTurn(Team.HAN, 2);

        repository.updateTurn(playingTurn);

        final var turn = repository.getTurn();
        assertAll(
            () -> assertThat(turn.currentTeam()).isEqualTo(Team.HAN),
            () -> assertThat(turn.currentRound()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("저장된 모든 피스와 턴 정보를 삭제한다.")
    void clear() {
        //given
        final var piece = new Piece(Team.CHO, new Coordinate(1, 1), PieceType.CHA);
        repository.save(piece);

        repository.updateTurn(new PlayingTurn(Team.HAN, 2));

        //when
        repository.clear();

        //then
        final var pieces = repository.allPieces();
        final var playingTurn = repository.getTurn();
        assertAll(
            () -> assertThat(pieces).isEmpty(),
            () -> assertThat(playingTurn.currentRound()).isEqualTo(1)
        );
    }
}
