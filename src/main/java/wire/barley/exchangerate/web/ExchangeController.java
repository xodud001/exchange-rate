package wire.barley.exchangerate.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import wire.barley.exchangerate.domain.CurrencyType;
import wire.barley.exchangerate.domain.ExchangeService;
import wire.barley.exchangerate.web.dto.CalExchangeForm;
import wire.barley.exchangerate.web.dto.ExchangeRateDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping("/exchange")
    public String exchangeForm(Model model){
        CalExchangeForm form = new CalExchangeForm();
        form.setRateCode(CurrencyType.KRW);
        model.addAttribute("form", form);
        model.addAttribute("rates", exchangeService.getExchangeRates());
        return "exchangeForm";
    }

    @PostMapping("/exchange")
    public String calExchange(@Validated @ModelAttribute("form") CalExchangeForm request, BindingResult bindingResult, Model model){
        log.info("request={}", request);

        List<ExchangeRateDto> rates = exchangeService.getExchangeRates();
        model.addAttribute("rates", rates);

        log.info("errors={}", bindingResult);
        FieldError rateCode = bindingResult.getFieldError("rateCode");
        if(rateCode != null && rateCode.isBindingFailure()){
            return "exchangeForm";
        }
        if(bindingResult.hasErrors()){
            return "exchangeForm";
        }

        BigDecimal amount = exchangeService.calAmount(rates, request.getAmount(), request.getRateCode());

        if(amount.compareTo(BigDecimal.ZERO) < 0){
            log.info("계산 실패");
            bindingResult.reject("calError");
            return "exchangeForm";
        }

        model.addAttribute("resultMessage", String.format(" %,.2f %s", amount.doubleValue(),request.getRateCode()));
        return "exchangeForm";
    }
}
