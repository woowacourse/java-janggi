package domain.board;

import static domain.piece.PieceType.MA;
import static domain.piece.PieceType.SANG;

import common.exception.JanggiException;
import domain.piece.BasicPiece;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.player.Team;
import domain.position.Position;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum Formation {
    SANG_MA_SANG_MA(List.of(SANG, MA, SANG, MA), 1),
    MA_SANG_MA_SANG(List.of(MA, SANG, MA, SANG), 2),
    MA_SANG_SANG_MA(List.of(MA, SANG, SANG, MA), 3),
    SANG_MA_MA_SANG(List.of(SANG, MA, MA, SANG), 4);

    private final List<PieceType> formation;
    private final int inputNumber;

    Formation(List<PieceType> formation, int inputNumber) {
        this.formation = formation;
        this.inputNumber = inputNumber;
    }

    public static Formation from(int inputNumber) {
        return Arrays.stream(values())
                .filter(formation -> formation.inputNumber == inputNumber)
                .findFirst()
                .orElseThrow(() -> new JanggiException("1에서 4까지 숫자만 입력해주세요. 입력값: %s".formatted(inputNumber)));
    }

    public void putFormation(Map<Position, BasicPiece> board, Team team) {
        List<Integer> columnPositions = List.of(1, 2, 6, 7);

        for (int i = 0; i < formation.size(); i++) {
            PieceType pieceType = formation.get(i);
            int column = columnPositions.get(i);
            Piece piece = pieceType.createPiece(team);
            Position position = new Position(team.getColumn(), column);
            board.put(position, piece);
        }
    }
}
