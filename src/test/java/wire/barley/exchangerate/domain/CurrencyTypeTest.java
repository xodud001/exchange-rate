package wire.barley.exchangerate.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTypeTest {

    @DisplayName("1. Message Formatting Test")
    @Test
    void test(){
        Assertions.assertThat(CurrencyType.KRW.getMessage()).isEqualTo("한국(KRW)") ;
    }
}