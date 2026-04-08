package janggi;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.PieceSetup;
import janggi.domain.piece.Piece;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.domain.score.Score;
import janggi.domain.team.Team;
import janggi.repository.GameInfo;
import janggi.repository.GameRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JanggiGame {
    private static final int FROM_INDEX = 0;
    private static final int TO_INDEX = 1;

    private final GameRepository repository;
    private Board board;
    private int gameId;

    public JanggiGame(GameRepository repository) {
        this.repository = repository;
    }

    public Optional<Team> checkPreviousGame() {
        Optional<GameInfo> gameInfo = repository.findGame();
        if (gameInfo.isEmpty()) {
            return Optional.empty();
        }
        if (gameInfo.get().getWinner() != null) {
            repository.deleteGame(gameInfo.get().getGameId());
            return Optional.empty();
        }
        gameId = gameInfo.get().getGameId();
        board = BoardFactory.restore(repository.findPieces(gameId));
        return Optional.of(Team.valueOf(gameInfo.get().getCurrentTeam()));
    }

    public void initialize(String hanSetup, String choSetup) {
        board = BoardFactory.create(PieceSetup.from(hanSetup), PieceSetup.from(choSetup));
        gameId = repository.createGame(board.showBoard());
    }

    public Team playTurn(List<String> positions, Team currentTeam) {
        Movement movement = createMovement(positions);
        board.move(movement, currentTeam);
        Team nextTurn = currentTeam.convert();
        repository.movePiece(gameId, movement.getFrom(), movement.getTo());
        repository.updateTurn(gameId, nextTurn);
        return nextTurn;
    }

    public boolean isFinished(Team currentTeam) {
        return board.isGeneralCaptured(currentTeam);
    }

    public void saveWinner(Team winner) {
        repository.updateWinner(gameId, winner);
    }

    public Map<Position, Piece> getBoard() {
        return board.showBoard();
    }

    public Score getScore() {
        return Score.from(board.showBoard());
    }

    private Movement createMovement(List<String> positions) {
        Position from = Position.from(positions.get(FROM_INDEX));
        Position to = Position.from(positions.get(TO_INDEX));
        return new Movement(from, to);
    }
}
