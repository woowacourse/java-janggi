package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import model.Team;
import model.janggiboard.Dot;
import model.janggiboard.JanggiBoard;

public class OutputVIew {

    private static final String RESET = "\u001B[0m";
    private static final String FONT_BLACK = "\u001B[30m";
    private static final String FONT_RED = "\u001B[31m";
    private static final String FONT_YELLOW = "\u001B[33m";
    private static final String BACKGROUND_RED = "\u001B[41m";
    private static final String BACKGROUND_YELLOW = "\u001B[43m";
    private static final String BACKGROUND_BLUE = "\u001B[44m";

    public static void displayJanggiBoard(JanggiBoard janggiBoard) {

        ArrayList<ArrayList<Dot>> janggiBoardList = janggiBoard.getJanggiBoard();
        List<String> chineseNumberCharacterToVerticalIndex = Arrays.asList("一", "二", "三", "四", "五", "六", "七",
                "八", "九", "十");
        List<String> chineseNumberCharacterToHorizontalIndex = Arrays.asList("一", "二", "三", "四", "五", "六", "七",
                "八", "九");

        for (int i = janggiBoardList.size() - 1; i >= 0; i--) {
            ArrayList<Dot> horizontalDotList = janggiBoardList.get(i);
            List<String> horizontalNameLine = new ArrayList<>();
            System.out.print(BACKGROUND_YELLOW + FONT_BLACK + chineseNumberCharacterToVerticalIndex.get(i) + RESET
                    + BACKGROUND_YELLOW
                    + FONT_YELLOW + "ㅡ" + RESET);
            for (Dot dot : horizontalDotList) {
                if (dot.isPlaced()) {
                    String teamFormat = BACKGROUND_BLUE + FONT_BLACK;
                    if (dot.getPiece().getTeam() == Team.RED) {
                        teamFormat = BACKGROUND_RED + FONT_BLACK;
                    }
                    horizontalNameLine.add(teamFormat + dot.getPiece().getPieceName().getName() + RESET);
                    continue;
                }
                horizontalNameLine.add(BACKGROUND_YELLOW + FONT_BLACK + "ㅇ" + RESET);
            }
            System.out.println(
                    java.lang.String.join(BACKGROUND_YELLOW + FONT_BLACK + "ㅡㅡㅡ" + RESET, horizontalNameLine));
            if (i != 0) {
                String verticalLineFormat = BACKGROUND_YELLOW + FONT_BLACK + "ㅣ" + RESET;
                List<String> verticalLines = Collections.nCopies(9, verticalLineFormat);
                System.out.println(
                        BACKGROUND_YELLOW + FONT_YELLOW + "ㅡㅡ" + RESET + java.lang.String.join(
                                BACKGROUND_YELLOW + FONT_YELLOW + "ㅡㅡㅡ" + RESET, verticalLines));
            }

        }
        System.out.println(
                BACKGROUND_YELLOW + FONT_YELLOW + "ㅡㅡ" + RESET + java.lang.String.join(
                        BACKGROUND_YELLOW + FONT_YELLOW + "ㅡㅡㅡ" + RESET,
                        changeBackGroundYellowFontBlack(chineseNumberCharacterToHorizontalIndex)));
    }

    private static List<String> changeBackGroundYellowFontBlack(List<String> strings) {
        List<String> newStrings = new ArrayList<>();
        for (String string : strings) {
            newStrings.add(BACKGROUND_YELLOW + FONT_BLACK + string + RESET);
        }
        return newStrings;
    }

    public static void displayErrorMessage(String e) {
        System.out.println(FONT_RED + e + RESET);
    }
}
