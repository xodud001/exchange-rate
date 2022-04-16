package wire.barley.exchangerate.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CalExchangeDto {

    private String rateCode;
    private int amount;
}
