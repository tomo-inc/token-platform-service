package com.tomo.model.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketTokenCategoryReq {
    @NotEmpty
    private String category;
    private List<String> tags;
    private Long chainIndex;
    private String address;
    private Integer pageNum;
    private Integer pageSize;

}
