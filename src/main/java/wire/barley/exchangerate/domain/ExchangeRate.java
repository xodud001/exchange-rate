package wire.barley.exchangerate.domain;

import lombok.Getter;

@Getter
public class ExchangeRate {

    private CurrencyType from;
    private CurrencyType to;
    private double rate;

    public ExchangeRate(CurrencyType from, CurrencyType to, double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "to=" + to +
                ", from=" + from +
                ", rate=" + rate +
                '}';
    }
}
