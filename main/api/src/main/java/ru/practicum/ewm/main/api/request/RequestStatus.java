package ru.practicum.ewm.main.api.request;

public enum RequestStatus {
    PENDING,
    CONFIRMED,
    REJECTED,
    CANCELED;

    public static RequestStatus from(String text) {
        for (RequestStatus status : RequestStatus.values()) {
            if (status.name().equalsIgnoreCase(text)) {
                return status;
            }
        }

        return null;
    }
}
