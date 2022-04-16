package wire.barley.exchangerate.api;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CurrencyLayer {
    private boolean success;
    private int timestamp;
    private String source;
    private Quotes quotes;
}
