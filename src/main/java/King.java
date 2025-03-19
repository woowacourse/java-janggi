import java.util.List;
import java.util.Map;

public class King extends Piece {
    public static final String NAME = "장";

    public King(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Dot> getRoute(Dot origin, Dot destination) {
        int dx = origin.getDx(destination);
        int dy = origin.getDy(destination);

        if (Math.abs(dx) + Math.abs(dy) != 1) {
            throw new UnsupportedOperationException("[ERROR] 병이 이동할 수 있는 목적지가 아닙니다.");
        }

        return List.of(); //ToDO 빈값 확인해주기!!
    }

    @Override
    public boolean canMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece) {
        if (isSameDynasty(destinationPiece)) {
            throw new UnsupportedOperationException("[ERROR] 같은 나라의 말은 공격할 수 없습니다.");
        }

        return true;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
