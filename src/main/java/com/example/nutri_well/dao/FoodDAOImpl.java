package com.example.nutri_well.dao;

import com.example.nutri_well.entity.Food;
import com.example.nutri_well.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FoodDAOImpl implements FoodDAO{
    private final FoodRepository foodRepository;

    @Override
    public Page<Food> searchByFoodName(String name , Pageable pageable) {
        return foodRepository.findByNameContaining(name, pageable);
    }

    @Override
    public Page<Food> searchByCategoryId(Long categoryid, Pageable pageable) {
        return  foodRepository.findByCategoryId(categoryid,pageable);
    }

    @Override
    public int getTotalPages(Page<Food> page) {
        return page.getTotalPages();
    }

    @Override
    public Food findByName(String name) {
        return foodRepository.findByName(name);
    }

    @Override
    public Page<Food> findAllByNutrientsNotIn(String foodname, List<String> names, Pageable pageable) {
        return foodRepository.findAllByNutrientsNotIn(foodname,names, pageable);
    }

    @Override
    public Page<Food> findAllByNutrientsNotIn(Long categoryid, List<String> names, Pageable pageable) {
        return foodRepository.findAllByNutrientsNotIn(categoryid,names,pageable);
    }

    @Override
    public Food findById(Long foodId) {
        return foodRepository.findById(foodId).get();
    }

    @Override
    public Food findByFoodCode(String foodcode) {
        return foodRepository.findByFoodCode(foodcode);
    }
}
