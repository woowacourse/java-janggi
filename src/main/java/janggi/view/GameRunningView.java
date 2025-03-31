package janggi.view;

import janggi.domain.board.Column;
import janggi.domain.board.PlayingBoard;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import janggi.dto.GameResultDto;
import java.util.Scanner;

public class GameRunningView {
    private static final Scanner scanner = new Scanner(System.in);

    public String readCommand() {
        System.out.println("시작위치, 움직일기물, 목적위치를 입력해주세요(예시: 12 마 33)");
        System.out.println("(종료는 q)");

        return scanner.nextLine();
    }

    public void printBoard(PlayingBoard playingBoard) {
        StringBuilder sb = new StringBuilder();

        sb.append(getBoardHeader());
        for (Row row : Row.values()) {
            sb.append(String.format(setDefaultColor() + "%d | ", row.intValue() % 10));
            for (Column column : Column.values()) {
                Position position = new Position(row, column);
                Piece piece = playingBoard.getPieceBy(position);
                String color = setColorBy(piece.getColor());
                String pieceName = PieceTypeName.getNameFrom(piece);

                if(piece.isPieceType(PieceType.NONE) && position.inPalace()) {
                    color = TextColor.YELLOW.getColor();
                }
                sb.append(color + pieceName + " ");
            }
            sb.append(System.lineSeparator());
            sb.append(setDefaultColor());
        }
        System.out.println(sb);
    }

    private String getBoardHeader() {
        return "  | 1  2 3  4 5  6 7  8 9\n"
                + "--|----------------------\n";
    }

    public void printTurnNotice(TeamColor turnColor) {
        StringBuilder sb = new StringBuilder();

        String teamName = TeamColorName.getNameFrom(turnColor);
        sb.append(setColorBy(turnColor) + teamName);
        sb.append(setDefaultColor() + " 차례입니다");

        System.out.println(sb);
    }

    public void printGameResult(GameResultDto gameResultDto) {
        printWinner(gameResultDto.winColor());
        printScoreReport(gameResultDto.blueScore(), gameResultDto.redScore());
    }

    private void printWinner(String winColor) {
        StringBuilder sb = new StringBuilder();

        sb.append(setColorBy(TeamColor.valueOf(winColor)) + winColor);
        sb.append(setDefaultColor() + " 승리!\n");

        System.out.println(sb);
    }

    private void printScoreReport(int blueScore, int redScore) {
        System.out.println("-------게임 결과-------");
        System.out.printf(setDefaultColor() + "초나라 점수: %d\n", blueScore);
        System.out.printf(setDefaultColor() + "한나라 점수: %d\n", redScore);
    }

    private String setDefaultColor() {
        return TextColor.DEFAULT.getColor();
    }

    private String setColorBy(TeamColor color) {
        if (color == TeamColor.RED) {
            return TextColor.RED.getColor();
        }
        if (color == TeamColor.BLUE) {
            return TextColor.BLUE.getColor();
        }
        return TextColor.DEFAULT.getColor();
    }
}
