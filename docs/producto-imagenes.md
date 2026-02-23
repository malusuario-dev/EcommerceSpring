# Flujo mínimo para conectar `Producto` + `ProductImage`

Este flujo está pensado para tu estado actual: `ProductoEntity` y `ProductImage` ya mapeadas con relación `@OneToMany` / `@ManyToOne`.

## 1) Qué agregar para que funcione bien “por ahora”

1. **Métodos helper en la entidad producto** para mantener sincronizada la relación bidireccional.
2. **Repositorio JPA** para `ProductoEntity`.
3. **DTO de entrada** para crear producto con lista de URLs de imágenes.
4. **Servicio transaccional** que construya el producto y agregue imágenes.
5. **Controller** `POST /api/v1/products` que llame al servicio.

---

## 2) Entidad `ProductoEntity`: helpers recomendados

> Mantén `cascade = CascadeType.ALL` y `orphanRemoval = true` como ya lo tienes.

```java
@Entity
@Table(name = "productos")
@Getter
@Setter
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Integer stock;
    private boolean active;
    private BigDecimal price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    public void addImage(ProductImage image) {
        images.add(image);
        image.setProduct(this);
    }

    public void removeImage(ProductImage image) {
        images.remove(image);
        image.setProduct(null);
    }
}
```

## 3) Repositorio

```java
public interface ProductoJpaRepository extends JpaRepository<ProductoEntity, Long> {
}
```

## 4) DTO de creación

```java
@Data
public class CreateProductRequestDto {
    private String nombre;
    private String descripcion;
    private Integer stock;
    private BigDecimal price;
    private List<String> imageUrls;
}
```

## 5) Servicio transaccional

```java
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductoJpaRepository productoJpaRepository;

    @Transactional
    public ProductoEntity create(CreateProductRequestDto dto) {
        ProductoEntity product = new ProductoEntity();
        product.setNombre(dto.getNombre());
        product.setDescripcion(dto.getDescripcion());
        product.setStock(dto.getStock());
        product.setPrice(dto.getPrice());
        product.setActive(true);

        if (dto.getImageUrls() != null) {
            for (String url : dto.getImageUrls()) {
                if (url == null || url.isBlank()) continue;
                ProductImage image = new ProductImage();
                image.setUrl(url.trim());
                product.addImage(image);
            }
        }

        return productoJpaRepository.save(product);
    }
}
```

## 6) Controller

```java
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CreateProductRequestDto dto) {
        ProductoEntity created = productService.create(dto);
        return ResponseEntity.ok(created.getId());
    }
}
```

---

## 7) JSON de prueba

```json
{
  "nombre": "Mouse Gamer",
  "descripcion": "Mouse RGB",
  "stock": 10,
  "price": 59.90,
  "imageUrls": [
    "http://localhost:8080/static/mouse-1.jpg",
    "http://localhost:8080/static/mouse-2.jpg"
  ]
}
```

## 8) Qué dejar para la siguiente iteración

- Validaciones de tamaño/formato si pasas a subida real de archivos (`MultipartFile`).
- Endpoint para agregar/quitar imágenes de un producto existente.
- Mover a flujo hexagonal completo: `controller -> mediator -> handler -> puertos` (como tu módulo de usuarios).
