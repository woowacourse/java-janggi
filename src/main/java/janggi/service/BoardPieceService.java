package janggi.service;

import janggi.dao.BoardPieceDao;
import janggi.dao.dto.BoardPieceFindDto;
import janggi.domain.Turn;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardPieceService {

    private final BoardPieceDao boardPieceDao;
    private final PieceService pieceService;

    public BoardPieceService() {
        this.boardPieceDao = new BoardPieceDao();
        this.pieceService = new PieceService();
    }

    public JanggiBoard initializeBoardPieces(int newGameId) {
        JanggiBoard board = JanggiBoard.initializeWithPieces();
        Map<Position, Piece> positionPieces = board.getBoard();
        for (Map.Entry<Position, Piece> positionPiece : positionPieces.entrySet()) {
            insertDBIfNotEmpty(newGameId, positionPiece.getValue(), positionPiece.getKey());
        }
        return new JanggiBoard(positionPieces);
    }

    public JanggiBoard loadBoardPieces() {
        Map<Position, Piece> positionPieces = findAllBoardPieces();
        return JanggiBoard.fillEmptyPiece(positionPieces);
    }

    public Map<Position, Piece> findAllBoardPieces() {
        List<BoardPieceFindDto> boardPieces = boardPieceDao.findAllPieces();

        Map<Position, Piece> positionPieces = new HashMap<>();
        for (BoardPieceFindDto boardPiece : boardPieces) {
            Position position = new Position(boardPiece.x(), boardPiece.y());
            String pieceType = boardPiece.pieceType();
            String side = boardPiece.side();
            Piece piece = PieceType.createPiece(pieceType, Turn.getStateByName(side));

            positionPieces.put(position, piece);
        }
        return positionPieces;
    }

    public void addBoardPiece(final int gameId, final Position position, final Piece piece) {
        int pieceId = pieceService.findPieceIdByPosition(piece);
        boardPieceDao.addPositionPiece(gameId, position.getX(), position.getY(), pieceId);
    }

    public void updatePiecePosition(final Position selectedPiecePosition, final Position destination) {
        boardPieceDao.deletePositionIfExists(destination);
        int boardId = boardPieceDao.findBoardPieceIdByPosition(selectedPiecePosition);
        boardPieceDao.updatePiecePosition(boardId, destination);
    }

    public Piece moveOrCatchPiece(final JanggiBoard board, final Position selectedPiecePosition, final Position destination) {
        Piece catchedPiece = board.moveOrCatchPiece(selectedPiecePosition, destination);
        updatePiecePosition(selectedPiecePosition, destination);

        return catchedPiece;
    }

    public List<Position> computeReachableDestination(final JanggiBoard board, final Turn turn, final Position selectedPiecePosition) {
        return board.computeReachableDestination(turn, selectedPiecePosition);
    }

    public Map<Turn, Integer> sumTotalPoints(JanggiBoard board) {
        Map<Turn, Integer> sideTotalScores = new HashMap<>();
        for (Turn side : Turn.getSides()) {
            sideTotalScores.put(side, board.sumSideTotalScore(side));
        }
        return sideTotalScores;
    }

    private void insertDBIfNotEmpty(final int newGameId, final Piece piece, final Position position) {
        if (piece.isOccupied()) {
            addBoardPiece(newGameId, position, piece);
        }
    }
}
