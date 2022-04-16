package wire.barley.exchangerate.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wire.barley.exchangerate.api.ExchangeRateStore;
import wire.barley.exchangerate.api.TimePoint;
import wire.barley.exchangerate.web.dto.ExchangeRateDto;

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

}
