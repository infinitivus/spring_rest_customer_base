package com.infinitivus.project.spring_rest_security_base.service.security_service;

import com.infinitivus.project.spring_rest_security_base.entity.security.UserData;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface IUserService {

//    void createRoles();

//    boolean verificationSchema();

//    void saveAdmin(UserData user);

//    UserData findByUsername(String username);

    @Secured({"ROLE_ADMIN"})
    List<UserData> listAllUser();

    @Secured({"ROLE_ADMIN"})
    UserData getUser(Integer id);

    @Secured({"ROLE_ADMIN"})
    void updateRoleToUser(Integer id, String userRole);

// Любой может войти
    Integer registrationUser(UserData user);

    void encoder(UserData user);

    @Secured({"ROLE_ADMIN"})
    void deleteUser(Integer id);
}

