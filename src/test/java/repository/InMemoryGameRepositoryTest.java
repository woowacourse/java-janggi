package repository;

import model.board.Country;
import model.pieces.PieceType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryGameRepositoryTest {

    @Test
    void 게임을_저장하고_다시_조회할_수_있다(){
        GameRepository repository = new InMemoryGameRepository();
        SavedGame savedGame = new SavedGame(null,
                Country.CHO,
                false,
                null,
                List.of(new SavedPiece(1,10,Country.CHO,PieceType.CHARIOT),
                        new SavedPiece(2,5,Country.HAN,PieceType.GENERAL))
        );

        repository.save(savedGame);
        Optional<SavedGame> findGame = repository.find();
        assertThat(findGame).contains(savedGame);
    }
}
