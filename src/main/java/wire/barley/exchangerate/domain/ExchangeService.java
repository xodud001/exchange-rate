package wire.barley.exchangerate.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wire.barley.exchangerate.api.ExchangeRateStore;
import wire.barley.exchangerate.api.TimePoint;
import wire.barley.exchangerate.web.dto.ExchangeRateDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeRateStore exchangeRateStore;

    public List<ExchangeRateDto> getExchangeRates(){
        List<ExchangeRate> exchangeRate = exchangeRateStore.getExchangeRate(TimePoint.LIVE);
        return exchangeRate.stream()
                .map(ExchangeRateDto::new)
                .collect(Collectors.toList());
    }

    public BigDecimal calAmount(List<ExchangeRateDto> exchangeRate, double amount, CurrencyType code) {
        for (ExchangeRateDto rate : exchangeRate) {
            if(rate.getToCode().equals(code)){
                BigDecimal currencyRate = BigDecimal.valueOf(rate.getRate());
                BigDecimal requestAmount = BigDecimal.valueOf(amount);
                return currencyRate.multiply(requestAmount);
            }
        }
        return BigDecimal.ZERO.subtract(BigDecimal.ONE);
    }
}
