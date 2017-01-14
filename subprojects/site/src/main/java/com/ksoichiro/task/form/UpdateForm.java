package com.ksoichiro.task.form;

import com.ksoichiro.task.domain.Account;
import org.springframework.beans.BeanUtils;

public interface UpdateForm<E, D> extends Form<E> {
    D toDTO(Account account);

    default void copyFrom(E entity) {
        BeanUtils.copyProperties(entity, this);
    }

    Integer getId();

    default boolean cannotDecideWhatToUpdate() {
        return getId() == null;
    }
}
