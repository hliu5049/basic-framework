package com.framework.system.repository;

import com.framework.system.model.UserRoleBind;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRoleBindRepository extends CrudRepository<UserRoleBind, Long> {

    List<UserRoleBind> findByUserId(Long userId);
}
