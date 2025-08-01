package project.products.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import project.products.product.service.ProductService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductViewController {

    private final ProductService productService;

    @GetMapping
    public String productList(Model model) {
        model.addAttribute("products", productService.getAll(PageRequest.of(0, 10)));
        return "products";
    }

    @GetMapping("/new")
    public String newProductForm() {
        return "product_form";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getOne(id));
        return "product_edit_form";
    }
}
