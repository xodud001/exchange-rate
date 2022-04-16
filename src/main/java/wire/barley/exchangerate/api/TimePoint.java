package wire.barley.exchangerate.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimePoint {
    NONE("/"),
    LIVE("/live");

    private String uri;
}
