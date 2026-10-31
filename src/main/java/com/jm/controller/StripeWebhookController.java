package com.jm.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jm.service.PedidoService;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/webhook")
public class StripeWebhookController {

    private final PedidoService pedidoService;
    private final ObjectMapper objectMapper;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    public StripeWebhookController(PedidoService pedidoService, ObjectMapper objectMapper) {
        this.pedidoService = pedidoService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/stripe")
    public String handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) {

        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (Exception e) {
            System.out.println("❌ Invalid signature");
            return "Invalid signature";
        }

        System.out.println("📩 EVENTO: " + event.getType());

        switch (event.getType()) {

            case "checkout.session.completed" -> handlePago(event);

            case "checkout.session.expired",
                 "payment_intent.payment_failed" -> handleCancel(event);
        }

        return "OK";
    }


    private void handlePago(Event event) {

        try {
            JsonNode node = objectMapper.readTree(event.getData().getObject().toJson());

            String pedidoIdStr = node.get("client_reference_id").asText();

            Integer pedidoId = Integer.valueOf(pedidoIdStr);

            pedidoService.actualizarEstado(pedidoId, PedidoService.PAGADO);

            System.out.println("💰 PAGADO: " + pedidoId);

        } catch (Exception e) {
            System.out.println("❌ Error handlePago: " + e.getMessage());
        }
    }

    private void handleCancel(Event event) {

        try {
            Session session = objectMapper.readValue(
                    event.getData().toJson(),
                    Session.class
            );

            if (session.getClientReferenceId() == null) return;

            Integer pedidoId = Integer.valueOf(session.getClientReferenceId());

            pedidoService.actualizarEstado(pedidoId, PedidoService.CANCELADO);

            System.out.println("❌ CANCELADO: " + pedidoId);

        } catch (Exception e) {
            System.out.println("❌ Error en handleCancel: " + e.getMessage());
        }
    }
}