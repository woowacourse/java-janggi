package janggi.board;

import janggi.piece.Piece;
import janggi.setting.AssignType;
import janggi.setting.CampType;
import janggi.value.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JanggiBoard {

    private static final int X_MAX = 8;
    private static final int X_MIN = 0;
    private static final int Y_MAX = 9;
    private static final int Y_MIN = 0;

    private final List<Piece> choPieces = new ArrayList<>();
    private final List<Piece> hanPieces = new ArrayList<>();

    public JanggiBoard(AssignType choAssignType, AssignType hanAssignType) {
        choPieces.addAll(choAssignType.makeAssign(CampType.CHO));
        hanPieces.addAll(hanAssignType.makeAssign(CampType.HAN));
    }

    public void movePiece(CampType campType, Position targetPiecePosition, Position destination) {
        validatePositionInRange(targetPiecePosition);
        validatePositionInRange(destination);

        if (campType == CampType.CHO) {
            Piece target = choPieces.stream().filter(piece -> piece.getPosition().equals(targetPiecePosition))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 이동할 말이 존재하지 않습니다."));
            choPieces.remove(target);
            Piece movedTarget = target.move(destination, hanPieces, choPieces);
            choPieces.add(movedTarget);
            return;
        }

        Piece target = hanPieces.stream().filter(piece -> piece.getPosition().equals(targetPiecePosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 이동할 말이 존재하지 않습니다."));
        hanPieces.remove(target);
        Piece movedTarget = target.move(destination, choPieces, hanPieces);
        hanPieces.add(movedTarget);
    }

    private void validatePositionInRange(Position position) {
        if (position.getX() < X_MIN || position.getX() > X_MAX || position.getY() < Y_MIN || position.getY() > Y_MAX) {
            throw new IllegalArgumentException("[ERROR] x좌표는 0~8, y좌표는 0~9 사이로 입력해주세요.");
        }
    }

    public List<Piece> getChoPieces() {
        return Collections.unmodifiableList(choPieces);
    }

    public List<Piece> getHanPieces() {
        return Collections.unmodifiableList(hanPieces);
    }
}
