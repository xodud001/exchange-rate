package wire.barley.exchangerate.web.dto;

import lombok.Getter;
import wire.barley.exchangerate.domain.CurrencyType;
import wire.barley.exchangerate.domain.ExchangeRate;

@Getter
public class ExchangeRateDto {

    private String from;
    private CurrencyType fromCode;
    private String to;
    private CurrencyType toCode;
    private String rateMessage;
    private double rate;

    public ExchangeRateDto(ExchangeRate exchangeRate){
        this.from = exchangeRate.getFrom().getMessage();
        this.fromCode = exchangeRate.getFrom();
        this.to = exchangeRate.getTo().getMessage();
        this.toCode = exchangeRate.getTo();
        this.rate = exchangeRate.getRate();
        this.rateMessage = String.format("%,.2f ", exchangeRate.getRate())+toCode+"/"+fromCode;
    }
}
