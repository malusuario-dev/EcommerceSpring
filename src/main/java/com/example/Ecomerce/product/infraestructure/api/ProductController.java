package com.example.Ecomerce.product.infraestructure.api;

import com.example.Ecomerce.common.application.mediator.Mediator;
import com.example.Ecomerce.product.application.command.create.CreateProductRequest;
import com.example.Ecomerce.product.application.command.create.CreateProductResponse;
import com.example.Ecomerce.product.infraestructure.api.dto.CreateProductRequestDto;
import com.example.Ecomerce.product.infraestructure.api.dto.CreateProductResponseDto;
import com.example.Ecomerce.product.infraestructure.api.mapper.MapperProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final Mediator mediator;
    private final MapperProduct mapperProduct;

    @PostMapping
    public ResponseEntity<CreateProductResponseDto> create(@RequestBody CreateProductRequestDto dto) {
        CreateProductRequest request = mapperProduct.mapToRequest(dto);
        CreateProductResponse response = mediator.dispatch(request);
        return ResponseEntity.ok(mapperProduct.mapToDto(response));
    }
}
