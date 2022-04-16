package wire.barley.exchangerate.api.info;

import org.springframework.util.MultiValueMap;

import java.util.Map;

public interface ApiServerInfo {

    String baseUrl();
    MultiValueMap<String, String> accessKeyParam();
    MultiValueMap<String, String> currenciesParam();
}
