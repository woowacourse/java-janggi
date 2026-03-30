package movepolicy.rule;

public class BasicMoveRule implements MoveRule {

    @Override
    public void validatePathPieces(MovePath movePath) {
        validateInterveningPiecesEmpty(movePath);
        validateDestinationPiece(movePath);
    }

    private static void validateInterveningPiecesEmpty(MovePath movePath) {
        if (movePath.hasInterveningFullPiece()) {
            throw new IllegalArgumentException("이동 경로엔 기물이 있을 수 없습니다.");
        }
    }

    private void validateDestinationPiece(MovePath movePath) {
        if (movePath.isTargetEmpty()) {
            return;
        }
        if (movePath.isTargetSameSide()) {
            throw new IllegalArgumentException("같은 진영의 말은 공격할 수 없습니다.");
        }
    }
}
