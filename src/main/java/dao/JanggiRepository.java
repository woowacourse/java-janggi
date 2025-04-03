package dao;


import model.Point;
import model.janggiboard.Dot;
import model.piece.Piece;

import java.util.List;
import vo.PieceVo;

public interface JanggiRepository {
    int settingNewJanggiBoard(List<PieceVo> janggiBoard);

    List<PieceVo> settingBeforeJanggiBoard();

    void deletePiece(Point targetPoint);

    void changePieceLocation(Piece beforePiece, Point targetPoint);

    void updateTurn();

    int getGameTurn();
}

