package com.example.nutri_well.controller;

import com.example.nutri_well.dto.CategoryResponseDTO;
import com.example.nutri_well.dto.FoodResponseDTO;
import com.example.nutri_well.repository.FoodRepository;
import com.example.nutri_well.service.CategoryService;
import com.example.nutri_well.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/food")
public class FoodController {
    private final FoodService foodService;
    private final CategoryService categoryService;

    @GetMapping("/detail")
    public ModelAndView showPage(@RequestParam("foodId") Long foodId){
        ModelAndView mav = new ModelAndView("/food/food_detail");
        FoodResponseDTO dto = foodService.findById(foodId);
        mav.addObject("categories",categoryService.findByParentCategoryIsNull());
        mav.addObject("food",dto);
        mav.addObject("foodId", dto.getId());
        return mav;
    }
}
