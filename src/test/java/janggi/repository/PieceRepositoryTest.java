package janggi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.JanggiGame;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Tank;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import janggi.infrastructure.DBInitializer;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PieceRepositoryTest {

    private final GameRepository gameRepository = new GameRepository();
    private final PieceRepository pieceRepository = new PieceRepository();

    @BeforeEach
    void setUp() {
        DBInitializer.initialize();
    }

    @Test
    void 보드기물들을_저장하고_복원한다() {
        Board board = new Board();
        JanggiGame game = gameRepository.save(new JanggiGame());

        pieceRepository.saveAll(game.findGameId(), board);

        Board restored = pieceRepository.findByGameId(game.findGameId());

        Map<Position, Piece> pieces = restored.findAllPieces();
        assertThat(pieces).hasSize(32);
    }

    @Test
    void 기물이동을_DB에_반영한다() {
        Board board = Board.of(Map.of(
                new Position(0, 0), new Tank(Team.HAN)
        ));
        JanggiGame game = gameRepository.save(new JanggiGame());
        pieceRepository.saveAll(game.findGameId(), board);

        pieceRepository.movePiece(game.findGameId(), new Position(0, 0), new Position(0, 3));

        Board restored = pieceRepository.findByGameId(game.findGameId());
        Map<Position, Piece> pieces = restored.findAllPieces();
        assertThat(pieces).hasSize(1);
        assertThat(pieces.containsKey(new Position(0, 3))).isTrue();
        assertThat(pieces.containsKey(new Position(0, 0))).isFalse();
    }

    @Test
    void 상대_기물을_잡으며_이동하면_잡힌_기물이_삭제된다() {
        Board board = Board.of(Map.of(
                new Position(0, 0), new Tank(Team.HAN),
                new Position(0, 3), new Soldier(Team.CHO)
        ));
        JanggiGame game = gameRepository.save(new JanggiGame());
        pieceRepository.saveAll(game.findGameId(), board);

        pieceRepository.movePiece(game.findGameId(), new Position(0, 0), new Position(0, 3));

        Board restored = pieceRepository.findByGameId(game.findGameId());
        Map<Position, Piece> pieces = restored.findAllPieces();
        assertThat(pieces).hasSize(1);
        assertThat(pieces.get(new Position(0, 3))).isInstanceOf(Tank.class);
    }

}
