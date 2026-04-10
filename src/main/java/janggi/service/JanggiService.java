package janggi.service;

import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.domain.dto.MoveCommand;
import janggi.domain.janggiGame.JanggiGame;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;
import janggi.domain.vo.position.Position;
import janggi.repositiory.game.GameData;
import janggi.repositiory.game.GameRepository;
import janggi.repositiory.piece.BoardSnapshot;
import janggi.repositiory.piece.PieceRepository;

import java.util.Map;
import java.util.Optional;

public class JanggiService {
    private final GameRepository gameRepository;
    private final PieceRepository pieceRepository;
    private final JanggiGame janggiGame;
    private BoardSnapshot boardSnapshot;
    private final boolean isResumed;

    private JanggiService(GameRepository gameRepository, PieceRepository pieceRepository, JanggiGame janggiGame, BoardSnapshot boardSnapshot, Boolean isResumed) {
        this.gameRepository = gameRepository;
        this.pieceRepository = pieceRepository;
        this.janggiGame = janggiGame;
        this.boardSnapshot = boardSnapshot;
        this.isResumed = isResumed;
    }

    public static JanggiService startGame(GameRepository gameRepository, PieceRepository pieceRepository) {
        Optional<GameData> gameData = gameRepository.findLatestGame();
        JanggiGame game;
        Long gameId;
        BoardSnapshot boardSnapshot;

        if (gameData.isEmpty() || gameData.get().isFinished()) {
            game = new JanggiGame(new Board(BoardInitializer.createBoard()));
            gameId = gameRepository.save(game.getFinishStatus(), game.getCurrentTeam());

            boardSnapshot = new BoardSnapshot(gameId, game.getBoard());

            pieceRepository.updateALL(boardSnapshot);

            return new JanggiService(gameRepository, pieceRepository, game, boardSnapshot,false);
        }

        gameId = gameData.get().gameId();
        game = new JanggiGame(new Board(pieceRepository.findAll(gameId)), gameData.get().currentTurn());

        boardSnapshot = new BoardSnapshot(gameId, game.getBoard());

        return new JanggiService(gameRepository, pieceRepository, game, boardSnapshot, true);
    }

    public void playTurn(MoveCommand moveCommand) {
        janggiGame.move(moveCommand.getFrom(), moveCommand.getTo());
        boardSnapshot = boardSnapshot.updatePieces(janggiGame.getBoard());

        pieceRepository.updateALL(boardSnapshot);
        gameRepository.updateStatus(boardSnapshot.gameId(), janggiGame);
    }

    public void skipTurn() {
        janggiGame.skipTurn();

        gameRepository.updateStatus(boardSnapshot.gameId(), janggiGame);
    }

    public void resign() {
        janggiGame.resign();

        gameRepository.updateStatus(boardSnapshot.gameId(), janggiGame);
    }

    public Map<Position, Piece> getBoard() {
        return janggiGame.getBoard();
    }

    public boolean isFinished() {
        return janggiGame.getFinishStatus().isFinished();
    }

    public Team getCurrentTeam() {
        return janggiGame.getCurrentTeam();
    }

    public Team decideWinner() {
        return janggiGame.decideWinner();
    }

    public boolean isResumed() {
        return isResumed;
    }
}
