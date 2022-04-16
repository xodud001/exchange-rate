package wire.barley.exchangerate.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import wire.barley.exchangerate.api.info.ApiServerInfo;
import wire.barley.exchangerate.api.info.CurrencyLayerServerInfo;
import wire.barley.exchangerate.config.BasicConfig;
import wire.barley.exchangerate.domain.ExchangeRate;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class DollarBasedStoreTest {

    BasicConfig basicConfig = new BasicConfig();
    String json = "{\"success\":true,\"terms\":\"https:\\/\\/currencylayer.com\\/terms\",\"privacy\":\"https:\\/\\/currencylayer.com\\/privacy\",\"timestamp\":1649751124,\"source\":\"USD\",\"quotes\":{\"USDJPY\":125.596499,\"USDKRW\":1230.059957,\"USDPHP\":52.12597}}";

    @DisplayName("1. 환률 API 요청 테스트 - 성공")
    @Test
    void test1(){
        ExchangeRateStore exchangeRateStore = new DollarBasedServerStore(new CurrencyLayerServerInfo(), basicConfig.objectMapper());
        List<ExchangeRate> exchangeRate = exchangeRateStore.getExchangeRate(TimePoint.LIVE);

        exchangeRate.forEach(System.out::println);
        assertThat(exchangeRate.size()).isGreaterThan(0);
    }

    @DisplayName("2. 환률 API 요청 테스트 - 실패")
    @Test
    void test2(){
        ExchangeRateStore exchangeRateStore = new DollarBasedServerStore(new BadServerInfo(), basicConfig.objectMapper());
        List<ExchangeRate> exchangeRate = exchangeRateStore.getExchangeRate(TimePoint.NONE);

        assertThat(exchangeRate.size()).isEqualTo(0);
    }

    @Test
    void test3() throws JsonProcessingException {
        ObjectMapper objectMapper = basicConfig.objectMapper();
        CurrencyLayer currencyLayer = objectMapper.readValue(json, CurrencyLayer.class);

        assertThat(currencyLayer.isSuccess()).isTrue();
    }

    static class BadServerInfo implements ApiServerInfo {

        @Override
        public String baseUrl() {
            return "https://bad.url.com";
        }

        @Override
        public MultiValueMap<String, String> accessKeyParam() {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("access_key", "non_access_key");
            return map;
        }

        @Override
        public MultiValueMap<String, String> currenciesParam() {
            return new LinkedMultiValueMap<>();
        }
    }

    @DisplayName("4. String Format")
    @Test
    void test4(){
        double rate = 1226.425322;
        String result = String.format("%,.2f", rate);
        Assertions.assertThat(result).isEqualTo("1,226.42");
    }
}