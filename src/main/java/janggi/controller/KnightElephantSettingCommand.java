package janggi.controller;

import janggi.domain.piece.gererator.KnightElephantSetting;
import java.util.Arrays;

public enum KnightElephantSettingCommand {

    KNIGHT_ELEPHANT_KNIGHT_ELEPHANT("1", "마상마상", KnightElephantSetting.KNIGHT_ELEPHANT_KNIGHT_ELEPHANT),
    KNIGHT_ELEPHANT_ELEPHANT_KNIGHT("2", "마상상마", KnightElephantSetting.KNIGHT_ELEPHANT_ELEPHANT_KNIGHT),
    ELEPHANT_KNIGHT_KNIGHT_ELEPHANT("3", "상마마상", KnightElephantSetting.ELEPHANT_KNIGHT_KNIGHT_ELEPHANT),
    ELEPHANT_KNIGHT_ELEPHANT_KNIGHT("4", "상마상마", KnightElephantSetting.ELEPHANT_KNIGHT_ELEPHANT_KNIGHT);

    private final String command;
    private final String description;
    private final KnightElephantSetting knightElephantSetting;

    KnightElephantSettingCommand(String command, String description, KnightElephantSetting knightElephantSetting) {
        this.command = command;
        this.description = description;
        this.knightElephantSetting = knightElephantSetting;
    }

    public static KnightElephantSettingCommand fromCommand(String command) {
        return Arrays.stream(values())
                .filter(knightElephantSettingCommand -> knightElephantSettingCommand.command.equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 세팅입니다."));
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public KnightElephantSetting getKnightElephantSetting() {
        return knightElephantSetting;
    }
}
