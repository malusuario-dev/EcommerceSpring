package com.example.Ecomerce.product.infraestructure.api.mapper;

import com.example.Ecomerce.product.application.command.create.CreateProductRequest;
import com.example.Ecomerce.product.application.command.create.CreateProductResponse;
import com.example.Ecomerce.product.infraestructure.api.dto.CreateProductRequestDto;
import com.example.Ecomerce.product.infraestructure.api.dto.CreateProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class MapperProduct {

    public CreateProductRequest mapToRequest(CreateProductRequestDto dto) {
        CreateProductRequest request = new CreateProductRequest();
        request.setNombre(dto.getNombre());
        request.setDescripcion(dto.getDescripcion());
        request.setStock(dto.getStock());
        request.setPrice(dto.getPrice());
        request.setImageUrls(dto.getImageUrls());
        return request;
    }

    public CreateProductResponseDto mapToDto(CreateProductResponse response) {
        return new CreateProductResponseDto(response.getId());
    }
}
