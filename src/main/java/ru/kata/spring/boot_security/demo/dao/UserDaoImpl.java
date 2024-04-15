package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Component
public class UserDaoImpl implements UserDao {
    @PersistenceContext()
    private EntityManager em;

    @Override
    public List<User> getAllUsers() {
        String jpql = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles";
        return em.createQuery(jpql, User.class).getResultList();
    }

    @Override
    public void createUser(User user) {
        em.persist(user);
    }


    @Override
    public User getUserById(Long userId) {
        String jpql = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :userId";
        return em.createQuery(jpql, User.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public User getUserByUsername(String username) {
        String jpql = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username";
        return em.createQuery(jpql, User.class).setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public void editUser(Long id, User user) {
        User userToBeEdit = em.find(User.class, id);
        userToBeEdit.setName(user.getName());
        userToBeEdit.setUsername(user.getUsername());
        userToBeEdit.setPassword(user.getPassword());
        userToBeEdit.setRoles(user.getRoles());
        em.merge(userToBeEdit);
    }

    @Override
    public void deleteUser(long id) {
        em.remove(em.find(User.class, id));
    }

}