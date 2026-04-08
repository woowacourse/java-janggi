package janggi.service;

import janggi.domain.janggiGame.JanggiGame;
import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.domain.dto.MoveCommand;
import janggi.domain.janggiGame.StartGameResponse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.vo.position.Position;
import janggi.repositiory.game.GameData;
import janggi.repositiory.game.GameRepository;
import janggi.repositiory.piece.PieceData;
import janggi.repositiory.piece.PieceRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JanggiService {
    private final GameRepository gameRepository;
    private final PieceRepository pieceRepository;
    private Long gameId;

    public JanggiService(GameRepository gameRepository, PieceRepository pieceRepository) {
        this.gameRepository = gameRepository;
        this.pieceRepository = pieceRepository;
    }

    public StartGameResponse startGame() {
        Optional<GameData> gameData = gameRepository.findLatestOngoingGame();

        if (gameData.isEmpty()) {
            JanggiGame game = new JanggiGame(new Board(BoardInitializer.createBoard()));
            gameId = gameRepository.save(game.isFinished(), game.getCurrentTeam());
            return new StartGameResponse(game, false);
        }

        gameId = gameData.get().gameId();
        JanggiGame resumedGame = new JanggiGame(new Board(loadPieces()), gameData.get().currentTurn());
        return new StartGameResponse(resumedGame, true);
    }

    public void playTurn(MoveCommand moveCommand, JanggiGame janggiGame) {
        janggiGame.playTurn();
        janggiGame.move(moveCommand.getFrom(), moveCommand.getTo());
        janggiGame.changeTurn();

        pieceRepository.updateALL(gameId, janggiGame.getBoard());
        gameRepository.update(gameId, janggiGame.isFinished(), janggiGame.getCurrentTeam());
    }

    private Map<Position, Piece> loadPieces() {
        Map<Position, Piece> pieces = new HashMap<>();
        List<PieceData> pieceData = pieceRepository.findAll(gameId);

        for (PieceData data : pieceData) {
            Position position = new Position(data.row(), data.col());
            Piece piece = PieceFactory.create(data.type(), data.team());

            pieces.put(position, piece);
        }

        return pieces;
    }
}
