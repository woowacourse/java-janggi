package janggi.view;

import janggi.controller.KnightElephantSettingCommand;
import janggi.domain.piece.Position;
import java.util.Arrays;
import java.util.Scanner;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    public boolean inputGameContinue() {
        System.out.println("이전 게임을 이어서 하시겠습니까? (Y/N) : ");
        String input = SCANNER.nextLine();
        return input.equalsIgnoreCase("Y");
    }

    public boolean inputGameExit() {
        System.out.println("게임을 종료하시겠습니까? (Y/N) : ");
        String input = SCANNER.nextLine();
        return input.equalsIgnoreCase("Y");
    }

    public KnightElephantSettingCommand inputHanKnightElephantSetting() {
        System.out.println("한나라의 기본 세팅을 선택하세요.");
        return inputKnightElephantSetting();
    }

    public KnightElephantSettingCommand inputChoKnightElephantSetting() {
        System.out.println("초나라의 기본 세팅을 선택하세요.");
        return inputKnightElephantSetting();
    }

    private KnightElephantSettingCommand inputKnightElephantSetting() {
        Arrays.stream(KnightElephantSettingCommand.values())
            .forEach(knightElephantSettingCommand -> System.out.println(
                knightElephantSettingCommand.getCommand() + ". "
                    + knightElephantSettingCommand.getDescription()));
        return KnightElephantSettingCommand.fromCommand(SCANNER.nextLine());
    }

    public Position inputHanMoveSource() {
        System.out.println("한나라의 움직일 말의 위치를 입력하세요. (예: 1,2) : ");
        return inputPosition();
    }

    public Position inputHanMoveDestination() {
        System.out.println("한나라의 말을 이동할 위치를 입력하세요. (예: 1,2) : ");
        return inputPosition();
    }

    public Position inputChoMoveSource() {
        System.out.println("초나라의 움직일 말의 위치를 입력하세요. (예: 1,2) : ");
        return inputPosition();
    }

    public Position inputChoMoveDestination() {
        System.out.println("초나라의 말을 이동할 위치를 입력하세요. (예: 1,2) : ");
        return inputPosition();
    }

    private Position inputPosition() {
        String[] position = SCANNER.nextLine().split(",");
        return new Position(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
    }
}
