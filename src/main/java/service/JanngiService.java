package service;

import dao.JanggiDao;
import dao.PieceDao;
import domain.Board;
import domain.chesspiece.ChessPiece;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.position.InitialChessPiecePositionsGenerator;
import domain.score.Score;
import domain.type.ChessTeam;
import game.Janggi;
import game.Turn;
import java.sql.SQLException;
import java.util.List;

public class JanngiService {

    private final PieceDao pieceDao;
    private final JanggiDao janggiDao;

    public JanngiService(final PieceDao pieceDao, final JanggiDao janggiDao) {
        this.pieceDao = pieceDao;
        this.janggiDao = janggiDao;
    }

    public Janggi getJanggi() {
        return new Janggi(createBoard(), createTurn());
    }

    public void processTurn(final Janggi janggi, final ChessPosition fromPosition, final ChessPosition toPosition) {
        try {
            clear();
            janggi.processTurn(fromPosition, toPosition);
            gameSave(janggi);
        } catch (final RuntimeException e) {
            throw new IllegalArgumentException("턴 진행을 실패하였습니다.");
        }

    }

    public void gameSave(final Janggi janggi) {
        final List<ChessPiece> chessPieces = janggi.getChessPiecesMapView().values()
                .stream()
                .toList();
        pieceDao.saveAll(chessPieces);
        janggiDao.save(janggi);
    }

    public void clear() {
        pieceDao.clear();
        janggiDao.clear();
    }

    public ChessTeam getWinner(final Janggi janggi) {
        final Score blueTeamScore = janggi.calculateScoreByTeam(ChessTeam.BLUE);
        final Score redTeamScore = janggi.calculateScoreByTeam(ChessTeam.RED);
        if (blueTeamScore.compareTo(redTeamScore) > 0) {
            return ChessTeam.BLUE;
        }
        return ChessTeam.RED;
    }

    public Score getScoreByTeam(final Janggi janggi, final ChessTeam team) {
        final Score score = janggi.calculateScoreByTeam(team);
        return score.subtract(Score.hundred());
    }

    public boolean canContinueGame(final Janggi janggi) {
        final Score hundred = Score.hundred();
        final Score blueScore = janggi.calculateScoreByTeam(ChessTeam.BLUE).subtract(hundred);
        final Score redScore = janggi.calculateScoreByTeam(ChessTeam.RED).subtract(hundred);
        return blueScore.isPositiveScoreValue() && redScore.isPositiveScoreValue();
    }

    private Board createBoard() {
        return new Board(createChessPiecePositions());
    }

    private ChessPiecePositions createChessPiecePositions() {
        List<ChessPiece> chessPieces = pieceDao.findAll();
        if (chessPieces.isEmpty()) {
            chessPieces = new InitialChessPiecePositionsGenerator().generate();
        }
        return new ChessPiecePositions(chessPieces);
    }

    private Turn createTurn() {
        return janggiDao.findTurn().orElse(Turn.create());
    }
}
