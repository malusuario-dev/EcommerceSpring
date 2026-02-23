package com.example.Ecomerce.product.application.command.create;

import com.example.Ecomerce.common.application.RequestHandler;
import com.example.Ecomerce.product.dominio.Product;
import com.example.Ecomerce.product.dominio.ProductImage;
import com.example.Ecomerce.product.dominio.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateProductHandler implements RequestHandler<CreateProductRequest, CreateProductResponse> {

    private final ProductRepository productRepository;

    @Override
    public CreateProductResponse handle(CreateProductRequest request) {
        List<ProductImage> images = request.getImageUrls() == null
                ? Collections.emptyList()
                : request.getImageUrls().stream()
                .filter(url -> url != null && !url.isBlank())
                .map(url -> ProductImage.builder().url(url.trim()).build())
                .toList();

        Product product = Product.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .stock(request.getStock())
                .price(request.getPrice())
                .active(true)
                .images(images)
                .build();

        Product saved = productRepository.upsert(product);
        return new CreateProductResponse(saved.getId());
    }

    @Override
    public Class<CreateProductRequest> getRequestType() {
        return CreateProductRequest.class;
    }
}
