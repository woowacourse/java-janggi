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
import model.piece.Piece;
import repository.JanggiRepository;

import java.util.Map;

public class JanggiService {

    private final JanggiRepository janggiRepository;
    private Long gameId;
    private Janggi janggi;

    public JanggiService(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;
    }

    public void startNewGame(JanggiFormation hanFormation, JanggiFormation choFormation) {
        Map<Position, Piece> pieceByFormation = FormationFactory.generateFormation(hanFormation, choFormation);
        Board board = BoardFactory.generatePieces(pieceByFormation);
        this.janggi = new Janggi(board);
        this.gameId = janggiRepository.saveGame(janggi);
    }

    public GameStatus move(Position current, Position next) {
        GameStatus status = janggi.move(current, next);
        janggiRepository.updateGame(gameId, janggi);
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

    public Map<Position, Piece> board() {
        return janggi.board();
    }
}
