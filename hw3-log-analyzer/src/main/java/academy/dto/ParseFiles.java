package academy.dto;

import academy.dto.parse.NgixTypes;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ParseFiles {
    String file;
    List<NgixTypes> ngixTypesList;
}
