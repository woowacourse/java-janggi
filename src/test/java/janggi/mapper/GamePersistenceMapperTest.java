package janggi.mapper;

import janggi.domain.Game;
import janggi.domain.PalaceTopology;
import janggi.domain.PieceInfo;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.factory.PieceFactory;
import janggi.domain.piece.PieceType;
import janggi.domain.turn.ChoTurn;
import janggi.domain.turn.Finish;
import janggi.domain.turn.HanTurn;
import janggi.domain.turn.PlayerTurn;
import janggi.dto.BoardPiece;
import janggi.dto.GameRoom;
import janggi.factory.BoardFactory;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GamePersistenceMapperTest {
    private final PieceFactory pieceFactory = new PieceFactory(PalaceTopology.from());
    private final GamePersistenceMapper mapper = new GamePersistenceMapper(pieceFactory);

    @Test
    void 게임을_gameRoom과_boardPiece로_변환한다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), pieceFactory.create(PieceType.CHA, Side.HAN));
        pieces.put(new Position(2, 5), pieceFactory.create(PieceType.GUNG, Side.HAN));
        pieces.put(new Position(9, 5), pieceFactory.create(PieceType.GUNG, Side.CHO));
        pieces.put(new Position(10, 1), pieceFactory.create(PieceType.CHA, Side.CHO));

        Game game = createGame(pieces, 13.0, 14.5, Side.HAN, false);

        GameRoom gameRoom = mapper.toGameRoom(1, game);
        List<BoardPiece> boardPieces = mapper.toBoardPieces(1, game);

        assertThat(gameRoom).isEqualTo(new GameRoom(1, "HAN", false, 13.0, 14.5));
        assertThat(boardPieces).containsExactlyInAnyOrder(
                new BoardPiece(1, 1, 1, "CHA", "HAN"),
                new BoardPiece(1, 2, 5, "GUNG", "HAN"),
                new BoardPiece(1, 9, 5, "GUNG", "CHO"),
                new BoardPiece(1, 10, 1, "CHA", "CHO")
        );
    }

    @Test
    void gameRoom과_boardPiece로_진행중인_게임을_복원한다() {
        GameRoom gameRoom = new GameRoom(1, "CHO", false, 13.0, 14.5);
        List<BoardPiece> boardPieces = List.of(
                new BoardPiece(1, 1, 1, "CHA", "HAN"),
                new BoardPiece(1, 2, 5, "GUNG", "HAN"),
                new BoardPiece(1, 9, 5, "GUNG", "CHO"),
                new BoardPiece(1, 10, 1, "CHA", "CHO")
        );

        Game restored = mapper.restore(gameRoom, boardPieces);

        assertThat(restored.isFinished()).isFalse();
        assertThat(restored.getCurrentSide()).isEqualTo(Side.CHO);
        assertThat(restored.getCurrentScoreStatus().choScore()).isEqualTo(13.0);
        assertThat(restored.getCurrentScoreStatus().hanScore()).isEqualTo(14.5);
        assertThat(restored.getCurrentBoard()[0][0]).isEqualTo(new PieceInfo(Side.HAN, PieceType.CHA));
        assertThat(restored.getCurrentBoard()[1][4]).isEqualTo(new PieceInfo(Side.HAN, PieceType.GUNG));
        assertThat(restored.getCurrentBoard()[8][4]).isEqualTo(new PieceInfo(Side.CHO, PieceType.GUNG));
        assertThat(restored.getCurrentBoard()[9][0]).isEqualTo(new PieceInfo(Side.CHO, PieceType.CHA));
    }

    @Test
    void gameRoom과_boardPiece로_종료된_게임을_복원한다() {
        GameRoom gameRoom = new GameRoom(1, "CHO", true, 13.0, 1.5);
        List<BoardPiece> boardPieces = List.of(
                new BoardPiece(1, 9, 5, "GUNG", "CHO"),
                new BoardPiece(1, 10, 1, "CHA", "CHO")
        );

        Game restored = mapper.restore(gameRoom, boardPieces);

        assertThat(restored.isFinished()).isTrue();
        assertThat(restored.getCurrentSide()).isEqualTo(Side.CHO);
        assertThat(restored.getCurrentScoreStatus().choScore()).isEqualTo(13.0);
        assertThat(restored.getCurrentScoreStatus().hanScore()).isEqualTo(1.5);
        assertThat(restored.getCurrentBoard()[8][4]).isEqualTo(new PieceInfo(Side.CHO, PieceType.GUNG));
        assertThat(restored.getCurrentBoard()[9][0]).isEqualTo(new PieceInfo(Side.CHO, PieceType.CHA));
    }

    private Game createGame(Map<Position, Piece> pieces, double choScore, double hanScore, Side currentSide, boolean finished) {
        Map<Position, Piece> board = BoardFactory.createEmptyBoard(pieceFactory);
        board.putAll(pieces);

        Map<Side, Double> scoresBySide = new HashMap<>();
        scoresBySide.put(Side.CHO, choScore);
        scoresBySide.put(Side.HAN, hanScore);

        Board janggiBoard = new Board(board, scoresBySide);
        PlayerTurn playerTurn = createPlayerTurn(janggiBoard, currentSide, finished);

        return new Game(playerTurn);
    }

    private PlayerTurn createPlayerTurn(Board board, Side currentSide, boolean finished) {
        if (finished) {
            return new Finish(board, currentSide);
        }
        if (currentSide == Side.CHO) {
            return new ChoTurn(board);
        }
        return new HanTurn(board);
    }
}
