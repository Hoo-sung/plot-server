package com.plot.plotserver.service;


import com.plot.plotserver.domain.CategoryGroup;
import com.plot.plotserver.domain.User;
import com.plot.plotserver.dto.request.categorygroup.NewCategoryGroupReqDto;
import com.plot.plotserver.dto.request.categorygroup.UpdateCategoryGroupReqDto;
import com.plot.plotserver.dto.response.category_group.CategoryGroupResponseDto;
import com.plot.plotserver.exception.categorygroup.CategoryGroupAlreadyExistException;
import com.plot.plotserver.exception.categorygroup.CategoryGroupDeleteFailException;
import com.plot.plotserver.exception.categorygroup.CategoryGroupSavedFailException;
import com.plot.plotserver.exception.categorygroup.CategoryGroupUpdateFailException;
import com.plot.plotserver.repository.CategoryGroupRepository;
import com.plot.plotserver.repository.UserRepository;
import com.plot.plotserver.util.SecurityContextHolderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryGroupService {

    private final CategoryGroupRepository categoryGroupRepository;

    private final UserRepository userRepository;

    @Transactional
    public void saveCategoryGroup(NewCategoryGroupReqDto newCategoryGroupReqDto) {

        try {
            Long userId = SecurityContextHolderUtil.getUserId();
            Optional<User> user = userRepository.findById(userId);

            //해당이름의 CategoryGroup이 존재하면, 에러
            Optional<CategoryGroup> optionalCategoryGroup = categoryGroupRepository.findByUserIdAndName(userId, newCategoryGroupReqDto.getGroupName());
            if (optionalCategoryGroup.isPresent()) {//이미 존재하는 카테고리 그룹이다.
                throw new CategoryGroupAlreadyExistException("이미 존재하는 CategoryGroup 입니다.");
            }
            CategoryGroup categoryGroup = CategoryGroup.builder()
                    .name(newCategoryGroupReqDto.getGroupName())
                    .color(newCategoryGroupReqDto.getColor())
                    .user(user.get())
                    .build();

            categoryGroupRepository.save(categoryGroup);

        }catch(CategoryGroupAlreadyExistException e){
            throw e;
        }catch(Exception e){
            throw new CategoryGroupSavedFailException("CategoryGroup 생성에 실패하였습니다.");
        }
    }

    @Transactional
    public void update(Long categoryGroupId, UpdateCategoryGroupReqDto updateCategoryGroupReqDto) {

        try {

            Long userId = SecurityContextHolderUtil.getUserId();
            Optional<User> user = userRepository.findById(userId);

            //해당이름의 CategoryGroup이 존재하면, 에러
            Optional<CategoryGroup> optionalCategoryGroup = categoryGroupRepository.findByUserIdAndName(userId, updateCategoryGroupReqDto.getGroupName());
            if (optionalCategoryGroup.isPresent()) {//이미 존재하는 카테고리 그룹이다.
                throw new CategoryGroupAlreadyExistException("이미 존재하는 CategoryGroup 입니다.");
            }


            CategoryGroup categoryGroup = categoryGroupRepository.findById(categoryGroupId).get();
            categoryGroup.updateCategoryGroup(updateCategoryGroupReqDto);


        }catch(CategoryGroupAlreadyExistException e){
            throw e;
        }catch (Exception e){
            throw new CategoryGroupUpdateFailException("Category Group 수정에 실패했습니다.");
        }
    }


    @Transactional
    public void delete(Long categoryGroupId) {

        try {
            Optional<CategoryGroup> categoryGroup = categoryGroupRepository.findById(categoryGroupId);
            categoryGroupRepository.delete(categoryGroup.get());
        }catch (Exception e){
            throw new CategoryGroupDeleteFailException("Category Group 삭제에 실패했습니다.");
        }
    }


    public List<CategoryGroupResponseDto> getAll() {

        List<CategoryGroup> categoryGroupList = categoryGroupRepository.findByUserId(SecurityContextHolderUtil.getUserId());
        List<CategoryGroupResponseDto> result = new ArrayList<>();

        categoryGroupList.forEach(categoryGroup -> {
            result.add(CategoryGroupResponseDto.of(categoryGroup));
        });
        return result;
    }
}
