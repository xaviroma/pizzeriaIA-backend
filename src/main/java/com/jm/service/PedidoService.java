package com.jm.service;

import com.jm.model.*;
import com.jm.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final IngredienteRepository ingredienteRepository;
    private final ProductoIngredienteRepository productoIngredienteRepository;

    public static final String PENDIENTE = "PENDIENTE";
    public static final String PAGADO = "PAGADO";
    public static final String CANCELADO = "CANCELADO";

    public PedidoService(
            PedidoRepository pedidoRepository,
            ProductoRepository productoRepository,
            ClienteRepository clienteRepository,
            IngredienteRepository ingredienteRepository,
            ProductoIngredienteRepository productoIngredienteRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
        this.ingredienteRepository = ingredienteRepository;
        this.productoIngredienteRepository = productoIngredienteRepository;
    }

    public Pedido crearPedido(PedidoRequest request) {

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEstado(PENDIENTE);
        pedido.setFecha(new Timestamp(System.currentTimeMillis()));

        List<PedidoProducto> productos = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (PedidoRequest.Item item : request.getCart()) {

            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (!producto.isDisponible()) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "El producto no está disponible: " + producto.getNombre()
                );
            }

            BigDecimal precio;
            String tamanoGuardar;

            if (producto.getCategoria().getId() == 1) {

                tamanoGuardar = item.getTamanoSeleccionado();

                precio = switch (item.getTamanoSeleccionado()) {
                    case "Pequena" -> producto.getPrecioPequena();
                    case "Mediana" -> producto.getPrecioMediana();
                    case "Familiar" -> producto.getPrecioFamiliar();
                    default -> throw new RuntimeException("Tamaño no válido: " + item.getTamanoSeleccionado());
                };

            } else {
                tamanoGuardar = "Normal";
                precio = producto.getPrecioPequena();
            }

            PedidoProducto pp = new PedidoProducto();
            pp.setPedido(pedido);
            pp.setProducto(producto);
            pp.setCantidad(item.getQuantity());
            pp.setTamano(tamanoGuardar);

            List<PedidoProductoIngrediente> ingredientes = new ArrayList<>();

            if (item.getIngredientesIds() != null) {
                for (Integer idIng : item.getIngredientesIds()) {

                    Ingrediente ing = ingredienteRepository.findById(idIng)
                            .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Ingrediente no encontrado: " + idIng
                            ));

                    if (!productoIngredienteRepository.existsByProductoIdAndIngredienteId(
                            producto.getId(), idIng)) {
                        throw new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "El ingrediente " + ing.getNombre()
                                        + " no pertenece al producto " + producto.getNombre()
                        );
                    }

                    PedidoProductoIngrediente ppi = new PedidoProductoIngrediente();
                    ppi.setPedidoProducto(pp);
                    ppi.setIngrediente(ing);

                    ingredientes.add(ppi);
                }
            }

            pp.setIngredientes(ingredientes);

            productos.add(pp);

            total = total.add(precio.multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        pedido.setProductos(productos);
        pedido.setTotal(total);

        Pedido saved = pedidoRepository.save(pedido);

        System.out.println("Pedido guardado ID = " + saved.getId());

        return saved;
    }

    // 🔥 USADO POR STRIPE WEBHOOK
    @Transactional
    public void actualizarEstado(Integer pedidoId, String estado) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // 🔒 evita sobrescribir si ya está en ese estado
        if (pedido.getEstado() != null && pedido.getEstado().equals(estado)) {
            return;
        }

        pedido.setEstado(estado);

        pedidoRepository.save(pedido);
    }
}