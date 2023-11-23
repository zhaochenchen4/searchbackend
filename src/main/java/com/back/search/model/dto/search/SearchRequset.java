package com.back.search.model.dto.search;

import com.back.search.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequset extends PageRequest implements Serializable {
    private String searchText;
    private String type;
    private static final long serialVersionUID = 1L;
}
