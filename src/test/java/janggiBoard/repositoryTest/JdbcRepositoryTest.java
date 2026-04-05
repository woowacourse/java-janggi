package janggiBoard.repositoryTest;

import db.JdbcBoardRepository;
import domain.Position;
import domain.Team;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.strategy.PalaceStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class JdbcRepositoryTest {

    private final JdbcBoardRepository jdbcBoardRepository = new JdbcBoardRepository();

    @Test
    void 보드를_저장하고_다시_불러왔을때_정보가_일치해야한다() {
        HashMap<Position, Piece> board = new HashMap<>();
        Position position = new Position(0, 0);
        board.put(position, new King(Team.CHO, new PalaceStrategy()));

        jdbcBoardRepository.save(board);
        Map<Position, Piece> result = jdbcBoardRepository.findAll();

        Assertions.assertThat(result.get(position).getPieceType()).isEqualTo(PieceType.KING);
    }
}
