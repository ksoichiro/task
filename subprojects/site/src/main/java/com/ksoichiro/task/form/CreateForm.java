package com.ksoichiro.task.form;

import com.ksoichiro.task.domain.Account;

public interface CreateForm<E, D> extends Form<E> {
    D toDTO(Account account);
}
