package academy.dto.inputs;

import static academy.utils.ConstantsConfig.A;
import static academy.utils.ConstantsConfig.B;
import static academy.utils.ConstantsConfig.BLACK;
import static academy.utils.ConstantsConfig.C;
import static academy.utils.ConstantsConfig.D;
import static academy.utils.ConstantsConfig.E;
import static academy.utils.ConstantsConfig.F;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AffineParams {

    @JsonProperty("a")
    private double a = A;

    @JsonProperty("b")
    private double b = B;

    @JsonProperty("c")
    private double c = C;

    @JsonProperty("d")
    private double d = D;

    @JsonProperty("e")
    private double e = E;

    @JsonProperty("f")
    private double f = F;

    private long color = BLACK;
}
