package com.jm.controller;

import com.jm.model.Pedido;
import com.jm.repository.PedidoRepository;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PedidoRepository pedidoRepository;
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;
    @Value("${app.frontend.success-url}")
    private String successUrl;

    @Value("${app.frontend.cancel-url}")
    private String cancelUrl;

    public PaymentController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @PostMapping("/create-session")
    public String createSession(@RequestParam Integer pedidoId) throws Exception {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)

                // 🌐 frontend
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)

                // 🔥 CLAVE PARA WEBHOOK
                .setClientReferenceId(pedidoId.toString())

                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("eur")
                                                .setUnitAmount(
                                                        pedido.getTotal()
                                                                .multiply(java.math.BigDecimal.valueOf(100))
                                                                .longValue()
                                                )
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Pedido #" + pedidoId)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        Session session = Session.create(params);

        return session.getUrl();
    }
}