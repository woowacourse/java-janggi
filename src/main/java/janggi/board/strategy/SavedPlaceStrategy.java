package janggi.board.strategy;

import janggi.board.Position;
import janggi.piece.Piece;
import repository.dao.BoardDAO;

import java.util.Map;

public class SavedPlaceStrategy implements PlaceStrategy {

    private final BoardDAO boardDAO;

    public SavedPlaceStrategy(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    @Override
    public Map<Position, Piece> initialize() {
        return boardDAO.findAllPiecesOnBoard();
    }
}
