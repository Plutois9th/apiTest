package api.Data;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data


public class ColorData {
    private Integer id;
    private String name;
    private Integer year;
    private String color;
    private String pantone_value;

}
