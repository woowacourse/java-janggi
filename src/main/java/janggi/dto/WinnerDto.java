package janggi.dto;

import janggi.domain.player.Name;

public record WinnerDto(String name) {

    public static WinnerDto from(Name name) {
        return new WinnerDto(name.name());
    }
}
