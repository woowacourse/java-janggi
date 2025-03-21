package janggi.view;

import janggi.controller.KnightElephantSettingCommand;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

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

    public Entry<Integer, Integer> inputHanMoveSource() {
        System.out.println("한나라의 움직일 말의 위치를 입력하세요. (예: 1,2) : ");
        return inputPosition();
    }

    public Entry<Integer, Integer> inputHanMoveDestination() {
        System.out.println("한나라의 말을 이동할 위치를 입력하세요. (예: 1,2) : ");
        return inputPosition();
    }

    public Entry<Integer, Integer> inputChoMoveSource() {
        System.out.println("초나라의 움직일 말의 위치를 입력하세요. (예: 1,2) : ");
        return inputPosition();
    }

    public Entry<Integer, Integer> inputChoMoveDestination() {
        System.out.println("초나라의 말을 이동할 위치를 입력하세요. (예: 1,2) : ");
        return inputPosition();
    }

    private Entry<Integer, Integer> inputPosition() {
        String[] position = SCANNER.nextLine().split(",");
        return Map.entry(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
    }
}
