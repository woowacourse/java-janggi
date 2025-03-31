package janggi.service;

import janggi.dao.BoardPieceDao;
import janggi.dao.dto.BoardPieceFindDto;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Side;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardPieceService {

    private final BoardPieceDao boardPieceDao;

    public BoardPieceService() {
        this.boardPieceDao = new BoardPieceDao();
    }

    public Map<Position, Piece> findAllBoardPieces() {
        List<BoardPieceFindDto> boardPieces = boardPieceDao.findAllPieces();

        Map<Position, Piece> positionPieces = new HashMap<>();
        for (BoardPieceFindDto boardPiece : boardPieces) {
            Position position = new Position(boardPiece.x(), boardPiece.y());
            String pieceType = boardPiece.pieceType();
            String side = boardPiece.side();
            Piece piece = PieceType.createPiece(pieceType, Side.getSideByName(side));

            positionPieces.put(position, piece);
        }
        return positionPieces;
    }

    public void initializeBoardPieces(Map<Position, Piece> positionPieces, int newGameId) {
        for (Map.Entry<Position, Piece> positionPieceEntry : positionPieces.entrySet()) {
            addBoardPiece(newGameId, positionPieceEntry.getKey(), positionPieceEntry.getValue());
        }
    }

    public void addBoardPiece(final int gameId, final Position position, final Piece piece) {
        Side side = Side.NONE;
        if(piece.isCho()) {
            side = Side.CHO;
        }
        if(piece.isHan()) {
            side = Side.HAN;
        }
        boardPieceDao.addPositionPiece(gameId, position.getX(), position.getY(), piece, side);
    }

    public void updatePiecePosition(final Position selectedPiecePosition, final Position destination) {
        boardPieceDao.deletePositionIfExists(destination);
        int boardId = boardPieceDao.findBoardPieceIdByPosition(selectedPiecePosition);
        boardPieceDao.updatePiecePosition(boardId, destination);
    }
}
