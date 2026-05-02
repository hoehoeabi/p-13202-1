package com.back.p13202.domain.article;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleForm {

    @NotEmpty(message = "제목은 필수입니다이")
    @Size(max = 200)
    private String title;

    @NotEmpty(message = "내용은 써야지")
    private String content;
}
