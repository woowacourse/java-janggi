package view;

import static domain.piece.coordiante.Coordinate.MAX_COL;
import static domain.piece.coordiante.Coordinate.MAX_ROW;

import domain.board.Board;
import domain.game.Game;
import domain.piece.Country;
import domain.piece.coordiante.Coordinate;
import java.util.List;

public class OutputView {

    public void printJanggiBoard(Board board) {
        StringBuilder builder = new StringBuilder();

        for (int row = 1; row <= MAX_ROW; row++) {
            if (row == 1) {
                for (int col = 1; col <= MAX_COL; col++) {
                    builder.append(" ").append(col);
                }
                builder.append('\n');
            }
            for (int col = 1; col <= MAX_COL; col++) {
                if (col == 1) {
                    convertRowEdgeValue(row, builder);
                }
                Coordinate coordinate = new Coordinate(row, col);
                if (board.isBlankCoordinate(coordinate)) {
                    builder.append("＿");
                    continue;
                }
                Country country = board.findCountryByCoordinate(coordinate);
                if (country == Country.CHO) {
                    builder.append("\u001B[32m").append(board.findPieceTypeByCoordinate(coordinate).getPieceName())
                            .append("\u001B[0m");
                }
                if (country == Country.HAN) {
                    builder.append("\u001B[31m").append(board.findPieceTypeByCoordinate(coordinate).getPieceName())
                            .append("\u001B[0m");
                }
            }
            builder.append('\n');
        }
        System.out.println(builder);
    }

    private void convertRowEdgeValue(int row, StringBuilder builder) {
        if (row == 10) {
            builder.append(0);
        } else {
            builder.append(row);
        }
    }

    public void printEndGame(boolean isChoGungDead, boolean isHanGungDead) {
        if (isChoGungDead) {
            System.out.println("한나라가 승리하였습니다.");
        }
        if (isHanGungDead) {
            System.out.println("조나라가 승리하였습니다.");
        }
    }

    public void printScore(int hanScore, int choScore) {
        System.out.println("한나라 점수: " + hanScore);
        System.out.println("조나라 점수: " + choScore);
    }

    public void printPreviousGameMessage() {
        System.out.println("이전 게임을 불러왔습니다.");
    }

    public void printNewGameMessage() {
        System.out.println("새로운 게임을 시작합니다.");
    }

    public void printAllGames(List<Game> janggiGames) {
        System.out.println("저장된 게임 목록");
        for (Game game : janggiGames) {
            System.out.println("게임방 id: " + game.getId() +
                    ", 게임방 이름: " + game.getName() +
                    ", 차례: " + game.getCurrentName());
        }
    }
}
