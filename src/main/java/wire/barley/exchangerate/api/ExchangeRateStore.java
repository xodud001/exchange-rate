package wire.barley.exchangerate.api;

import wire.barley.exchangerate.domain.ExchangeRate;

import java.util.List;

public interface ExchangeRateStore {

    /**
     *
     * @return 환율에 대한 정보가 담긴 리스트가 반환된다.
     *          API 서버 호출에 실패할 경우 빈 리스트가 반환된다
     */
    List<ExchangeRate> getExchangeRate(TimePoint point);
}
