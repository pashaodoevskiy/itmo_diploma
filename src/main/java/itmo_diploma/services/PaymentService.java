package itmo_diploma.services;

import itmo_diploma.requests.PaymentRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {

    private UserCourseService userCourseService;
    private AuthService authService;

    public void pay(PaymentRequest paymentRequest) {
        Long courseId = paymentRequest.getCourseId();
        Long userId = authService.getCurrentUser().getId();

        userCourseService.pay(userId, courseId);
    }
}
