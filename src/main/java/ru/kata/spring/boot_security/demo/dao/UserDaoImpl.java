package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
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
    public User getUserById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        return em.createQuery(jpql, User.class).setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public void editUser(User user) {
        User userToBeEdit = em.find(User.class, user.getId());
        userToBeEdit.setName(user.getName());
        userToBeEdit.setLastName(user.getLastName());
        userToBeEdit.setAge(user.getAge());
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