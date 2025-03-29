package janggi.service;

import janggi.dao.BoardDao;
import janggi.dao.dto.BoardPieceFindResponse;
import janggi.dao.dto.PieceFindResponse;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Side;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardService {

    private final BoardDao boardDao;

    public BoardService() {
        this.boardDao = new BoardDao();
    }

    public Map<Position, Piece> findAllBoardPieces() {
        List<BoardPieceFindResponse> boardPieces = boardDao.findAllPieces();

        Map<Position, Piece> positionPieces = new HashMap<>();
        for (BoardPieceFindResponse boardPiece : boardPieces) {
            Position position = new Position(boardPiece.x(), boardPiece.y());
            String pieceType = boardPiece.pieceType();
            String side = boardPiece.side();
            Piece piece = PieceType.createPiece(pieceType, Side.findSideByName(side));

            positionPieces.put(position, piece);
        }
        return positionPieces;
    }

    public Piece findBoardPieceByPosition(final Position position) {
        PieceFindResponse pieceByPosition = boardDao.findPieceByPosition(position.getX(), position.getY());

        String pieceType = pieceByPosition.pieceType();
        String side = pieceByPosition.side();

        return PieceType.createPiece(pieceType, Side.findSideByName(side)); //FIXME: PieceType에서 아예 만들기
    }

    public void addBoardPiece(final int gameId, final Position position, final Piece piece) {
        Side side = Side.NONE;
        if(piece.isCho()) {
            side = Side.CHO;
        }
        if(piece.isHan()) {
            side = Side.HAN;
        }
        boardDao.addPositionPiece(gameId, position.getX(), position.getY(), piece, side);
    }

    public void updatePiecePosition(final Position selectedPiecePosition, final Position destination) {
        int i = boardDao.deletePositionIfExists(destination);
        int boardId = boardDao.findBoardIdByPosition(selectedPiecePosition);
        boardDao.updatePiecePosition(boardId, destination);
    }
}
