package com.example.nutri_well.service;

import com.example.nutri_well.dto.CategoryResponseDTO;
import com.example.nutri_well.dto.FoodResponseDTO;
import com.example.nutri_well.dto.FoodSuggestResponseDTO;
import com.example.nutri_well.entity.Category;
import com.example.nutri_well.entity.Food;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodService {
    List<FoodResponseDTO> searchByFoodName(String name, Pageable pageable);

    List<FoodResponseDTO> searchByCategoryId(CategoryResponseDTO category, Pageable pageable);

    List<FoodResponseDTO> findByparentCategoryFood(Category category, Pageable pageable);

    int getTotalPages();

    List<FoodSuggestResponseDTO> findByNameStartingWith(String prefix, Pageable pageable);

    FoodResponseDTO findById(Long foodId);

    List<FoodResponseDTO> findAllByNutrientsInRange(String foodname, List<String> names, Integer min, Integer max, Pageable pageable);

    List<FoodResponseDTO> findAllByNutrientsInRange(Long category, List<String> names, Integer min, Integer max, Pageable pageable);

    List<FoodResponseDTO> findAllByNutrientsParentCategoryInRange(Long category, List<String> names, Integer min, Integer max, Pageable pageable);

    Food findEntityById(Long foodId);
}