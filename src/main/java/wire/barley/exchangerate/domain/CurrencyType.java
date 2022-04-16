package wire.barley.exchangerate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

/**
 * ISO 4217 기준으로 제정된 통화 코드 3자리
 */
@AllArgsConstructor
public enum CurrencyType {

    USD("미국"),
    KRW("한국"),
    JPY("일본"),
    PHP("필리핀");

    private String ko;

    public String getMessage(){
        return String.format("%s(%s)", ko, this);
    }

}
