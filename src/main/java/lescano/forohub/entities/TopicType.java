package lescano.forohub.entities;

public enum TopicType {
    QUESTION("Duda", "Question"),
    SUGGESTION("Sugerencia", "Suggestion"),
    PROJECT("Proyecto", "Project"),
    BUG("Bug", "Error"),
    COMPLAINT("Queja", "Complaint");

    private final String spanish;
    private final String english;

    TopicType(String spanish, String english) {
        this.spanish = spanish;
        this.english = english;
    }

    public String getSpanish() {
        return spanish;
    }

    public String getEnglish() {
        return english;
    }

    public static TopicType fromString(String value) {
        for (TopicType type : TopicType.values()) {
            if (type.spanish.equalsIgnoreCase(value) || type.english.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid TopicType: " + value);
    }
}
