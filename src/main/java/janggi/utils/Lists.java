package janggi.utils;

import java.util.List;

public final class Lists {

    private Lists() {

    }

    public static <T> List<T> exceptLast(final List<T> list) {
        if (list == null || list.isEmpty()) {
            return List.of();
        }
        return list.subList(0, Math.max(0, list.size() - 1));
    }
}
