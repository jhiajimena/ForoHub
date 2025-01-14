package lescano.forohub.entities;

public enum TopicStatus {
    UNANSWERED("No respondido", "Unanswered"),
    ANSWERED("Respondido", "Answered"),
    RESOLVED("Resuelto", "Resolve");

    private final String spanish;
    private final String english;

    TopicStatus(String spanish, String english) {
        this.spanish = spanish;
        this.english = english;
    }

    public String getSpanish() {
        return spanish;
    }

    public String getEnglish() {
        return english;
    }

    public static TopicStatus fromString(String value) {
        for (TopicStatus status : TopicStatus.values()) {
            if (status.spanish.equalsIgnoreCase(value) || status.english.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid TopicType: " + value);
    }
}
