package academy.dto.inputs;

import static academy.utils.ConstantsConfig.HEIGHT;
import static academy.utils.ConstantsConfig.WIDTH;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SizeParams {
    @JsonProperty("width")
    private int width = WIDTH;

    @JsonProperty("height")
    private int height = HEIGHT;
}
