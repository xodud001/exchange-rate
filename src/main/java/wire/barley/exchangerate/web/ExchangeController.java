package wire.barley.exchangerate.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import wire.barley.exchangerate.domain.ExchangeService;
import wire.barley.exchangerate.web.dto.CalExchangeDto;
import wire.barley.exchangerate.web.dto.ExchangeRateDto;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @ModelAttribute("rates")
    public List<ExchangeRateDto> rates(){
        return exchangeService.getExchangeRates();
    }

    @GetMapping
    public String welcome(Model model){
        return "redirect:/exchange";
    }

    @GetMapping("/exchange")
    public String exchangeForm(Model model){
        return "exchangeForm";
    }

    @PostMapping("/exchange")
    public String calExchange(@ModelAttribute CalExchangeDto request, Model model){
        log.info("request={}", request);
        return "exchangeForm";
    }
}
