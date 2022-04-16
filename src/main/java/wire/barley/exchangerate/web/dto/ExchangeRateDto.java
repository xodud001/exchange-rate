package wire.barley.exchangerate.web.dto;

import lombok.Getter;
import wire.barley.exchangerate.domain.ExchangeRate;

@Getter
public class ExchangeRateDto {

    private String from;
    private String fromCode;
    private String to;
    private String toCode;
    private String rateMessage;
    private double rate;

    public ExchangeRateDto(ExchangeRate exchangeRate){
        this.from = exchangeRate.getFrom().getMessage();
        this.fromCode = exchangeRate.getFrom().toString();
        this.to = exchangeRate.getTo().getMessage();
        this.toCode = exchangeRate.getTo().toString();
        this.rate = exchangeRate.getRate();
        this.rateMessage = String.format("%,.2f ", exchangeRate.getRate())+toCode+"/"+fromCode;
    }
}
