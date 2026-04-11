package team.janggi.domain.board;

import java.util.List;
import team.janggi.domain.BoardSize;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.piece.Piece;
import team.janggi.domain.piece.PieceType;

public class NormalBoardPiecesInitializer implements BoardPiecesInitializer {
    private static final int CHO_BACK_RANK_Y = 9;    // 맨 아래
    private static final int HAN_BACK_RANK_Y = 0;    // 맨 위

    private static final int CHO_KING_RANK_Y = 8;    // 궁성 안
    private static final int CHO_CANNON_RANK_Y = 7;  // 포 라인
    private static final int CHO_SOLDIER_RANK_Y = 6; // 졸 라인
    private static final List<Integer> SOLDIER_X_POSITIONS = List.of(0, 2, 4, 6, 8);

    private final NormalSetup choSetup;
    private final NormalSetup hanSetup;

    public NormalBoardPiecesInitializer(NormalSetup choSetup, NormalSetup hanSetup) {
        this.choSetup = choSetup;
        this.hanSetup = hanSetup;
    }

    @Override
    public void initBoardStatus(BoardPieces status) {
        initMapByEmpty(status);

        initLayoutByTeam(status, Team.CHO, choSetup);
        initLayoutByTeam(status, Team.HAN, hanSetup);
    }

    private void initMapByEmpty( BoardPieces status) {
        for (int y = 0; y < BoardSize.Y; y++) {
            initMapRowByEmpty(status, y);
        }
    }

    private void initMapRowByEmpty(BoardPieces status, int y) {
        for (int x = 0; x < BoardSize.X; x++) {
            status.setPiece(new Position(x, y), Piece.EMPTY_PIECE);
        }
    }

    private void initLayoutByTeam(BoardPieces boardPieces, Team team, NormalSetup setup) {
        // 차
        setPieceYPositionReverseByTeam(boardPieces, Piece.of(PieceType.CHARIOT, team), team, 0, CHO_BACK_RANK_Y);
        setPieceYPositionReverseByTeam(boardPieces, Piece.of(PieceType.CHARIOT, team), team, 8, CHO_BACK_RANK_Y);

        // 포
        setPieceYPositionReverseByTeam(boardPieces, Piece.of(PieceType.CANNON, team), team, 1, CHO_CANNON_RANK_Y);
        setPieceYPositionReverseByTeam(boardPieces, Piece.of(PieceType.CANNON, team), team, 7, CHO_CANNON_RANK_Y);

        // 졸
        for (int x : SOLDIER_X_POSITIONS) {
            setPieceYPositionReverseByTeam(boardPieces, Piece.of(PieceType.SOLDIER, team), team, x, CHO_SOLDIER_RANK_Y);
        }

        // 왕
        setPieceYPositionReverseByTeam(boardPieces, Piece.of(PieceType.KING, team), team, 4, CHO_KING_RANK_Y);

        // 사
        setPieceYPositionReverseByTeam(boardPieces, Piece.of(PieceType.GUARD, team), team, 3, CHO_BACK_RANK_Y);
        setPieceYPositionReverseByTeam(boardPieces, Piece.of(PieceType.GUARD, team), team, 5, CHO_BACK_RANK_Y);

        // 상치림
        if (team == Team.CHO) {
            setup(boardPieces, setup, team, CHO_BACK_RANK_Y);
        }

        if (team == Team.HAN) {

            setup(boardPieces, setup, team, HAN_BACK_RANK_Y);
        }
    }

    private void setup(BoardPieces boardPieces, NormalSetup setup, Team team, int y) {
        if (setup == NormalSetup.왼상차림) {
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.ELEPHANT, team), team, 1, y);
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.HORSE, team), team, 2, y);
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.ELEPHANT, team), team, 6, y);
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.HORSE, team), team, 7, y);
            return;
        }

        if (setup == NormalSetup.오른상차림) {
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.HORSE, team), team, 1, y);
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.ELEPHANT, team), team, 2, y);
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.HORSE, team), team, 6, y);
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.ELEPHANT, team), team, 7, y);
            return;
        }

        if (setup == NormalSetup.안상차림) {
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.HORSE, team), team, 1, y);
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.ELEPHANT, team), team, 2, y);
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.ELEPHANT, team), team, 6, y);
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.HORSE, team), team, 7, y);
            return;
        }

        if (setup == NormalSetup.바깥상차림) {
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.ELEPHANT, team), team, 1, y);
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.HORSE, team), team, 2, y);
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.HORSE, team), team, 6, y);
            setPieceXPositionReverseByTeam(boardPieces, Piece.of(PieceType.ELEPHANT, team), team, 7, y);
        }
    }

    private void setPieceXPositionReverseByTeam(BoardPieces boardPieces, Piece piece, Team team, int x, int y) {
        boardPieces.setPiece(new Position(dimensionRevers(team, BoardSize.X - 1, x), y), piece);
    }

    private void setPieceYPositionReverseByTeam(BoardPieces boardPieces, Piece piece, Team team, int x, int y) {
        boardPieces.setPiece(new Position(x, dimensionRevers(team, BoardSize.Y - 1, y)), piece);
    }

    private int dimensionRevers(Team team, int offset, int dimension) {
        int actual = dimension;

        if (team == Team.HAN) {
            actual = offset - dimension;
        }

        return actual;
    }
}
