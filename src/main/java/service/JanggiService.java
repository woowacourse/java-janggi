package service;

import model.board.Board;
import model.board.BoardFactory;
import model.board.ScoreResult;
import model.coordinate.Position;
import model.formation.FormationFactory;
import model.formation.JanggiFormation;
import model.game.GameStatus;
import model.game.Janggi;
import model.game.Team;
import model.game.dao.GameDao;
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
        Optional<GameDao> gameDaoOptional = janggiRepository.findRecentGame();
        if (gameDaoOptional.isEmpty()) {
            return false;
        }
        GameDao gameDao = gameDaoOptional.get();
        this.gameId = gameDao.gameId();
        startPlayingGame(gameDao);
        return true;
    }

    private void startPlayingGame(GameDao gameDao) {
        Map<Position, Piece> boardMap = janggiRepository.findPiecesByGameId(gameDao.gameId());
        Board board = new Board(boardMap);
        Team currentTurn = Team.fromName(gameDao.turn());
        this.janggi = new Janggi(board, currentTurn);
    }

    public void startNewGame(JanggiFormation hanFormation, JanggiFormation choFormation) {
        Map<Position, Piece> pieceByFormation = FormationFactory.generateFormation(hanFormation, choFormation);
        Board board = BoardFactory.generatePieces(pieceByFormation);
        this.janggi = new Janggi(board);
        this.gameId = janggiRepository.saveGame(janggi.getTurn(), janggi.board());
    }

    public GameStatus move(Position current, Position next) {
        GameStatus status = janggi.move(current, next);
        janggiRepository.updateGame(gameId, new MoveCommand(current, next, janggi.getTurn()));
        return status;
    }

    public Piece findPieceAt(Position position, Team turn) {
        return janggi.findPieceAt(position, turn);
    }

    public ScoreResult calculateScoreResult() {
        return janggi.calculateScoreResultOfTeams();
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

    public Team getWinnerByCapture() {
        return janggi.getWinnerByCapture();
    }

    public Map<Position, Piece> getBoard() {
        return janggi.board();
    }
}
