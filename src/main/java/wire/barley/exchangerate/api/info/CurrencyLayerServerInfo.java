package wire.barley.exchangerate.api.info;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import wire.barley.exchangerate.domain.CurrencyType;

import javax.annotation.PostConstruct;

@Component
public class CurrencyLayerServerInfo implements ApiServerInfo{

    private static final String BASE_URL = "http://api.currencylayer.com";
    private static final String ACCESS_KEY = "53d4d24c5acbcee565bfbe820fbeb4c9";

    private static final MultiValueMap<String, String> accessKeyParam = new LinkedMultiValueMap<>();
    private static final MultiValueMap<String, String> currenciesParam = new LinkedMultiValueMap<>();

    public String baseUrl(){
        return BASE_URL;
    }

    @PostConstruct
    private void init(){
        currenciesParam.add("currency", CurrencyType.KRW.toString());
        currenciesParam.add("currency", CurrencyType.JPY.toString());
        currenciesParam.add("currency", CurrencyType.PHP.toString());

        accessKeyParam.add("access_key", ACCESS_KEY);
    }

    public MultiValueMap<String, String> accessKeyParam(){
        return accessKeyParam;
    }

    public MultiValueMap<String, String> currenciesParam(){
        return currenciesParam;
    }
}
