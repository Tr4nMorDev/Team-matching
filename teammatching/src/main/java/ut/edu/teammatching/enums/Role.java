package ut.edu.teammatching.enums;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    STUDENT, LECTURER;

    @JsonCreator
    public static Role fromString(String value) {
        return Role.valueOf(value.toUpperCase()); // 🔥 Convert "student" thành "STUDENT"
    }
}
