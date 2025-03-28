package janggi.service;

import janggi.dao.DBConnection;
import janggi.dao.PieceDao;
import janggi.dao.TurnDao;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.Position;
import janggi.domain.game.JanggiGame;
import janggi.domain.game.Score;
import janggi.domain.game.Turn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Side;
import java.sql.SQLException;
import java.util.Map;

public class JanggiService {

    private final PieceDao pieceDao;
    private final TurnDao turnDao;
    private final JanggiGame janggiGame;

    public JanggiService(PieceDao pieceDao, TurnDao turnDao) {
        this.pieceDao = pieceDao;
        this.turnDao = turnDao;
        this.janggiGame = init();
    }

    private JanggiGame init() {
        if (pieceDao.existsPieces()) {
            return loadJanggiGame();
        }
        return createJanggiGame();
    }

    private JanggiGame loadJanggiGame() {
        Map<Position, Piece> piecesByPosition = pieceDao.findAll();
        Turn turn = turnDao.find();
        return new JanggiGame(new Board(piecesByPosition), turn);
    }

    private JanggiGame createJanggiGame() {
        Board board = BoardFactory.initBoard();
        Turn turn = Turn.firstTurn();
        pieceDao.save(board);
        turnDao.save(turn);
        return new JanggiGame(
                board,
                turn
        );
    }

    public void movePiece(final Position start, final Position end) {
        janggiGame.movePiece(start, end);
        pieceDao.deleteByPosition(end);
        Piece piece = pieceDao.findByPosition(start);
        pieceDao.updateByPosition(piece, start, end);
        turnDao.update(janggiGame.getTurn());
        commit();
    }

    public boolean continueGame() {
        return janggiGame.continueGame();
    }

    public void clearGame() {
        pieceDao.clear();
        turnDao.clear();
        commit();
    }

    private void commit() {
        try {
            DBConnection.getInstance().commit();
        } catch (SQLException ignored) {
        }
    }

    public Map<Position, Piece> findPiecesByPosition() {
        return pieceDao.findAll();
    }

    public Side calculateWinner() {
        return janggiGame.calculateWinner();
    }

    public Score scoreBySide(final Side side) {
        return janggiGame.scoreBySide(side);
    }

    public Turn getTurn() {
        return janggiGame.getTurn();
    }
}
