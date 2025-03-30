package view;

import domain.ScoreCalculator;
import domain.participants.Player;
import domain.piece.Piece;
import domain.position.Column;
import domain.position.Position;
import domain.position.Row;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OutputView {

    public void printBoard(Map<Position, Piece> alivePiecesInfo) {
        printColumnsInfo();
        printBoardInfo(alivePiecesInfo);
    }

    public void printScore(ScoreCalculator scoreCalculator) {
        System.out.printf("현재 초 점수 : %.1f\n", scoreCalculator.calculateChoScore());
        System.out.printf("현재 한 점수 : %.1f\n", scoreCalculator.calculateHanScore());
    }

    public void printWinner(Player player) {
        System.out.printf("%s가 승리했습니다!\n", player.getName());
    }

    private void printBoardInfo(Map<Position, Piece> alivePiecesInfo) {
        for (int row = Row.MAX_ROW; row >= Row.MIN_ROW; row--) {
            printRowInfo(row);
            for (int col = Column.MIN_COLUMN; col <= Column.MAX_COLUMN; col++) {
                Position position = Position.of(row, col);
                Optional<Piece> findPiece = getFindPiece(alivePiecesInfo, position);
                printPositionState(findPiece.orElse(null));
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printRowInfo(int row) {
        System.out.printf("%2d  ", row);
    }

    private Optional<Piece> getFindPiece(Map<Position, Piece> alivePiecesInfo, Position position) {
        return Optional.ofNullable(alivePiecesInfo.getOrDefault(position, null));
    }

    private void printPositionState(Piece piece) {
        if (piece == null) {
            System.out.print("- ");
            return;
        }
        System.out.print(PieceSymbol.findSymbol(piece) + " ");
    }

    private void printColumnsInfo() {
        String columnInfo = IntStream.range(Column.MIN_COLUMN, Column.MAX_COLUMN + 1)
                .mapToObj(value -> (char) ('a' + value))
                .map(Objects::toString)
                .collect(Collectors.joining(" "));

        System.out.println("    " + columnInfo);
    }
}
