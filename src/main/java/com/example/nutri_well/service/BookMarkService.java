package com.example.nutri_well.service;

import com.example.nutri_well.dto.BookMarkRequestDTO;
import com.example.nutri_well.dto.BookMarkResponseDTO;
import com.example.nutri_well.dto.FoodResponseDTO;
import com.example.nutri_well.entity.BookMark;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookMarkService {
    BookMarkResponseDTO updateStates(BookMarkRequestDTO bookMark, boolean isPreferred);
    BookMarkResponseDTO findByFoodIdAndUserId(Long foodId, Long userId);
    List<FoodResponseDTO> findTop5Foods();
    List<BookMarkResponseDTO> findByUserId(Long userId);
}
