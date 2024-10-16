package itmo_diploma.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import itmo_diploma.requests.PaymentRequest;
import itmo_diploma.services.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Платежи")
@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController extends Controller {

    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Object> pay(@RequestBody @Valid PaymentRequest paymentRequest) {
        paymentService.pay(paymentRequest);

        return response();
    }
}
