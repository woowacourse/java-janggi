package domain.piece;

public final class 포 extends JanggiPiece {

    public 포(final JanggiSide side) {
        super(side, JanggiPieceType.포);
    }

    @Override
    public boolean is포() {
        return true;
    }

    @Override
    public void checkPieceCanMove(JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece) {
        if (hurdleCount != 1) {
            throw new IllegalStateException("포는 장애물 1개를 뛰어넘어야 합니다.");
        }
        if (hurdlePiece.is포()) {
            throw new IllegalStateException("포는 포를 넘을 수 없습니다.");
        }
        if (isMyTeam(targetPiece)) {
            throw new IllegalStateException("같은 팀의 기물은 잡을 수 없습니다.");
        }
        if (targetPiece.is포()) {
            throw new IllegalStateException("포는 포를 잡을 수 없습니다.");
        }
    }
}
