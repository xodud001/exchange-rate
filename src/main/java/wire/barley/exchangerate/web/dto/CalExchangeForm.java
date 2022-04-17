package wire.barley.exchangerate.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import wire.barley.exchangerate.domain.CurrencyType;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CalExchangeForm {

    @NotNull
    private CurrencyType rateCode;

    @NotNull(message = "")
    @Range(min = 0, max = 10_000)
    private Integer amount;
}
