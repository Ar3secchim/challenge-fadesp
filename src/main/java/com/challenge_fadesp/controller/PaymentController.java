package com.challenge_fadesp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamentos")
public class PaymentController {

  @GetMapping
  public String getPayment() {
    return "Pagamento realizado com sucesso";
  }
}
