package dao;

import java.util.ArrayList;
import java.util.List;
import model.Point;
import model.janggiboard.Dot;
import model.piece.Piece;
import vo.PieceVo;

public class FakeJanggiDao implements JanggiRepository {


    @Override
    public int settingNewJanggiBoard(List<PieceVo> pieceVos) {
        return 0;
    }

    @Override
    public List<PieceVo> settingBeforeJanggiBoard() {
        return List.of();
    }

    @Override
    public void deletePiece(Point targetPoint) {

    }

    @Override
    public void changePieceLocation(Piece beforePiece, Point targetPoint) {

    }

    @Override
    public void updateTurn() {

    }

    @Override
    public int getGameTurn() {
        return 0;
    }
}
