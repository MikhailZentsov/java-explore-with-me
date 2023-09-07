package ru.practicum.ewm.main.api.event;

public enum StateAction {
    PUBLISH_EVENT,
    REJECT_EVENT,
    SEND_TO_REVIEW,
    CANCEL_REVIEW;

    public static StateAction from(String text) {
        for (StateAction state : StateAction.values()) {
            if (state.name().equalsIgnoreCase(text)) {
                return state;
            }
        }

        return null;
    }
}
