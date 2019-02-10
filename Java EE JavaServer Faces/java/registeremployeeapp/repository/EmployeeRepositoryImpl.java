package registeremployeeapp.repository;

import registeremployeeapp.domain.entities.Employee;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final EntityManager entityManager;

    @Inject
    public EmployeeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Employee save(Employee entity) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(entity);
        this.entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public List<Employee> findAll() {
        this.entityManager.getTransaction().begin();
        List<Employee> employees = this.entityManager
                .createQuery("SELECT e FROM employees e", Employee.class)
                .getResultList();
        this.entityManager.getTransaction().commit();
        return employees;
    }

    @Override
    public Employee findById(String id) {
        this.entityManager.getTransaction().begin();
        Employee employee = this.entityManager
                .createQuery("SELECT e FROM employees e WHERE e.id = :id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();
        this.entityManager.getTransaction().commit();
        return employee;
    }

    @Override
    public void removeById(String id) {
        this.entityManager.getTransaction().begin();
        Employee employee = this.entityManager.createQuery("SELECT e FROM employees e WHERE e.id = :id", Employee.class)
                .setParameter("id", id).getSingleResult();
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.remove(employee);
        this.entityManager.getTransaction().commit();
    }
}
