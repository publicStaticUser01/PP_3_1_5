package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext()
    private EntityManager em;

    @Override
    public Role getRoleByName(String roleName) {
        String jpql = "SELECT r FROM Role r WHERE r.name = :roleName";
        return em.createQuery(jpql, Role.class).setParameter("roleName", roleName)
                .getSingleResult();
    }
}
