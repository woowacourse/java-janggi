package janggi.domain.game;

import janggi.domain.DomainException;

public record RoomName(
        String name
) {

    private static final int ROOM_NAME_MIN_LENGTH = 1;
    private static final int ROOM_NAME_MAX_LENGTH = 10;
    public static final String ROOM_NAME_LENGTH_ERROR = "방 이름은 %d자 이상 %d 이하여야 합니다.";

    public RoomName {
        if(name.length() > 10 || name.isEmpty()) {
            throw new DomainException(
                    String.format(ROOM_NAME_LENGTH_ERROR, ROOM_NAME_MIN_LENGTH, ROOM_NAME_MAX_LENGTH));
        }
    }
}
