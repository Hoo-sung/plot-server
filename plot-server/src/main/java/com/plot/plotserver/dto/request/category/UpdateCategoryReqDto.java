package com.plot.plotserver.dto.request.category;

import lombok.Getter;


@Getter
public class UpdateCategoryReqDto {

    private String categoryName;

    private String emoji;

    private String category_group;

    private boolean Star;

    private String tag; //나중에 tags로 바꿔야 한다.

}