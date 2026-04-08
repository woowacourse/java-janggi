package service;

import model.board.Board;
import model.board.BoardFactory;
import model.board.ScoreResult;
import model.coordinate.Position;
import model.formation.FormationFactory;
import model.formation.JanggiFormation;
import model.game.GameStatus;
import model.game.Janggi;
import model.game.MoveResult;
import model.game.Team;
import model.game.dto.GameDto;
import model.piece.Piece;
import repository.JanggiRepository;
import repository.command.MoveCommand;

import java.util.Map;
import java.util.Optional;

public class JanggiService {

    private final JanggiRepository janggiRepository;
    private Long gameId;
    private Janggi janggi;

    public JanggiService(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;
    }

    public boolean tryResumeGame() {
        Optional<GameDto> gameDtoOptional = janggiRepository.findRecentGame();
        if (gameDtoOptional.isEmpty()) {
            return false;
        }
        GameDto gameDto = gameDtoOptional.get();
        this.gameId = gameDto.gameId();
        startPlayingGame(gameDto);
        return true;
    }

    private void startPlayingGame(GameDto gameDto) {
        Map<Position, Piece> boardMap = janggiRepository.findPiecesByGameId(gameDto.gameId());
        Board board = new Board(boardMap);
        Team currentTurn = Team.fromName(gameDto.turn());
        this.janggi = new Janggi(board, currentTurn);
    }

    public void startNewGame(JanggiFormation hanFormation, JanggiFormation choFormation) {
        Map<Position, Piece> pieceByFormation = FormationFactory.generateFormation(hanFormation, choFormation);
        Board board = BoardFactory.generatePieces(pieceByFormation);
        this.janggi = new Janggi(board);
        this.gameId = janggiRepository.saveGame(janggi.getTurn(), janggi.board());
    }

    public MoveResult move(Position current, Position next) {
        GameStatus status = janggi.move(current, next);
        MoveCommand moveCommand = new MoveCommand(current, next, janggi.getTurn());
        janggiRepository.updateGame(gameId, moveCommand, status);

        Optional<Team> winnerOptional = Optional.empty();
        if (status == GameStatus.WIN_BY_CAPTURE) {
            winnerOptional = Optional.of(janggi.getWinnerByCapture());
        }

        return new MoveResult(janggi.board(), winnerOptional);
    }

    public Piece findPieceAt(Position position, Team turn) {
        return janggi.findPieceAt(position, turn);
    }

    public ScoreResult calculateScoreResult() {
        ScoreResult result = janggi.calculateScoreResultOfTeams();
        janggiRepository.updateCurrentGameStatus(gameId, GameStatus.WIN_BY_SCORE);
        return result;
    }

    public void quit() {
        janggi.quit();
    }

    public boolean isPlaying() {
        return janggi.isPlaying();
    }

    public Team getTurn() {
        return janggi.getTurn();
    }

    public Map<Position, Piece> getBoard() {
        return janggi.board();
    }
}
