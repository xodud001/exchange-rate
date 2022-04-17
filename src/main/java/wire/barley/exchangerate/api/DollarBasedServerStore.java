package wire.barley.exchangerate.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import wire.barley.exchangerate.api.info.ApiServerInfo;
import wire.barley.exchangerate.domain.CurrencyType;
import wire.barley.exchangerate.domain.ExchangeRate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DollarBasedServerStore implements ExchangeRateStore{

    private final WebClient client;
    private final ApiServerInfo info;
    private final ObjectMapper objectMapper;

    public DollarBasedServerStore(ApiServerInfo info, ObjectMapper objectMapper) {
        this.client = WebClient.builder()
                .baseUrl(info.baseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.info = info;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<ExchangeRate> getExchangeRate(TimePoint point) {
        MultiValueMap<String, String> params = info.accessKeyParam();
        params.addAll(info.currenciesParam());

        CurrencyLayer currencyLayer = callApiServer(point.getUri(), params);
        log.info("api call result={}", currencyLayer);
        Quotes quotes = currencyLayer.getQuotes();

        if(quotes == null){
            return new ArrayList<>();
        }

        List<ExchangeRate> rates = new ArrayList<>();
        rates.add(new ExchangeRate(CurrencyType.USD, CurrencyType.KRW, quotes.getUSDKRW()));
        rates.add(new ExchangeRate(CurrencyType.USD, CurrencyType.JPY, quotes.getUSDJPY()));
        rates.add(new ExchangeRate(CurrencyType.USD, CurrencyType.PHP, quotes.getUSDPHP()));

        return rates;
    }

    private CurrencyLayer callApiServer(String uri, MultiValueMap<String, String> params){
        Mono<String> mono = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(uri)
                        .queryParams(params)
                        .build()
                )
                .exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(String.class);
            } else {
                return response.createException().flatMap(Mono::error);
            }
        });
        String result = mono.onErrorReturn("").block();
        if (StringUtils.hasText(result)) {
            try {
                return objectMapper.readValue(result, CurrencyLayer.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return new CurrencyLayer();
    }
}
