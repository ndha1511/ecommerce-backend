package com.code.salesappbackend.controllers;

import com.code.salesappbackend.models.Category;
import com.code.salesappbackend.services.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getCategory() {
        return categoryService.findAll();
    }

    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        category.setId(id);
        return categoryService.save(category);
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "Deleted Address";
    }

    @PatchMapping("/{id}")
    public Category patchCategory(@PathVariable Long id, @RequestBody Map<String, ?> data) {
        return categoryService.updatePatch(id, data);
    }
}

