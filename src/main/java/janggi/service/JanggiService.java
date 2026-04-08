package janggi.service;

import janggi.domain.JanggiGameManager;
import janggi.domain.turn.ChoTurn;
import janggi.domain.turn.GameState;
import janggi.domain.turn.HanTurn;
import janggi.domain.board.Board;
import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.space.piece.Piece;
import janggi.domain.space.piece.Team;
import janggi.domain.strategy.LoadStrategy;
import janggi.infrastructure.BoardRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JanggiService {
    private JanggiGameManager gameManager;
    private final BoardRepository boardRepository;

    public JanggiService(JanggiGameManager janggiGameManager, BoardRepository boardRepository) {
        this.gameManager = janggiGameManager;
        this.boardRepository = boardRepository;
    }

    public void startNewGame(Board initBoard) {
        gameManager = new JanggiGameManager(new ChoTurn(initBoard));
    }

    public void startLoadGame(long gameId) {
        String currentTurn = boardRepository.findTurnById(gameId);
        Map<Position, Piece> pieceInfo = boardRepository.findPiecesById(gameId);

        Board loadedBoard = new Board(new LoadStrategy(pieceInfo));

        GameState currentState = new HanTurn(loadedBoard);
        if ("CHO".equals(currentTurn)) {
            currentState = new ChoTurn(loadedBoard);
        }

        this.gameManager = new JanggiGameManager(currentState);
    }


    public boolean isFinished() {
        return gameManager.isFinished();
    }

    public void movePiece(Position from, Position to) {
        gameManager.move(from, to);
    }

    public Map<Position, Space> getBoardDto() {
        return gameManager.captureBoard();
    }

    public void saveGame(long gameId) {
        Map<Position, Piece> arrivePieces = getAlivePieces();
        String currentTurn = getCurrentTurn();
        boardRepository.saveGame(gameId, currentTurn, arrivePieces);
    }

    public List<Long> getSavedGameIds() {
        return boardRepository.findAllGameIds();
    }

    public boolean isDuplicateId(long gameId) {
        return boardRepository.existsById(gameId);
    }

    public String getCurrentTurn() {
        return gameManager.getCurrentTeam()
                .map(Team::toString)
                .orElse("Finished");
    }

    public double getCurrentTurnScore() {
        return gameManager.getPieceScore();
    }

    private Map<Position, Piece> getAlivePieces() {
        Map<Position, Space> board = gameManager.captureBoard();

        return board.entrySet().stream()
                .filter(entry -> !entry.getValue().isBlank())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (Piece) entry.getValue()
                ));
    }
}
