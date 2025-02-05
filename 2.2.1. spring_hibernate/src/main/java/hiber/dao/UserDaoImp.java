package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    @Transactional
    public void addCar(Car car) {
        sessionFactory.getCurrentSession().save(car);
        car.getUser().setCar(car);
    }

    @Override
    @Transactional
    public User getUserByCarModel(String carModel) {
        Query<User> query = sessionFactory.getCurrentSession().
                createQuery("from User where car.model = :carModel", User.class).
                setParameter("carModel", carModel);
        return query.uniqueResult();
    }

    @Override
    @Transactional
    public User getUserByCarSeries(int carSeries) {
        Query<User> query = sessionFactory.getCurrentSession().
                createQuery("from User where car.series = :carSeries", User.class).
                setParameter("carSeries", carSeries);
        return query.uniqueResult();
    }
}
