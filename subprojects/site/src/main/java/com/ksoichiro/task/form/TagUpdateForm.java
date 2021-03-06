package com.ksoichiro.task.form;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Tag;
import com.ksoichiro.task.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagUpdateForm implements UpdateForm<Tag, TagDTO> {
    @NotNull
    @Getter(onMethod = @__({@Override}))
    private Integer id;
    @NotEmpty
    private String name;

    @Override
    public TagDTO toDTO(Account account) {
        final TagDTO tagDTO = new TagDTO();
        tagDTO.setAccount(account);
        BeanUtils.copyProperties(this, tagDTO);
        return tagDTO;
    }
}
