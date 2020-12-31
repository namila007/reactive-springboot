package me.namila.rx.reactivespringboot.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.namila.rx.reactivespringboot.core.configuration.JsonSortDeserialization;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageableResponse<T> extends PageImpl<T> {

    public PageableResponse(@JsonProperty("content") List<T> content,
                            @JsonProperty("number") int page,
                            @JsonProperty("size") int size,
                            @JsonProperty("sort") @JsonDeserialize(using = JsonSortDeserialization.class) Sort sort,
                            @JsonProperty("totalElements") long totalElements) {
        super(content, PageRequest.of(page, size, sort), totalElements);
    }

}
