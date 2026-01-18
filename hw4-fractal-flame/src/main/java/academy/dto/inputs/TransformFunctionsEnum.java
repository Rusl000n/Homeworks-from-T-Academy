package academy.dto.inputs;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransformFunctionsEnum {
    LINEAR("linear"),
    SINUSOIDAL("sinusoidal"),
    SPHERICAL("spherical"),
    SWIRL("swirl"),
    HORSESHOE("horseshoe"),
    POPCORN("popcorn");

    private final String jsonName;

    TransformFunctionsEnum(String jsonName) {
        this.jsonName = jsonName;
    }

    @JsonValue
    public String getJsonName() {
        return jsonName;
    }
}
