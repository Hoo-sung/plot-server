package com.plot.plotserver.dto.request.email;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class EmailRequestDto {

    private String email;
}
