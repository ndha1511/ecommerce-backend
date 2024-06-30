package com.code.salesappbackend.controllers;

import com.code.salesappbackend.dtos.requests.CategoryDto;
import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.mappers.CategoryMapper;
import com.code.salesappbackend.models.Category;
import com.code.salesappbackend.services.interfaces.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public Response getCategory() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get categories successfully",
                categoryService.findAll()
        );
    }

    @PostMapping
    public Response addCategory(@RequestBody @Valid CategoryDto categoryDto)
        throws Exception {
        Category category = categoryMapper.categoryDto2Category(categoryDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "create category successfully",
                categoryService.save(category)
        );
    }

    @PutMapping("/{id}")
    public Response updateCategory(@PathVariable Long id, @RequestBody  @Valid Category category)
        throws Exception {
        category.setId(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "updated category",
                categoryService.update(id, category)
        );
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "Deleted Address";
    }

    @PatchMapping("/{id}")
    public Response patchCategory(@PathVariable Long id, @RequestBody Map<String, ?> data)
            throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "updated category",
                categoryService.updatePatch(id, data)
        );
    }
}

