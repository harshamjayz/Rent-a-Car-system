package lk.clickme.rac.dao;

import org.hibernate.Session;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class CrudDAOImpl<T,Id> implements CrudDAO<T,Id>{

    protected Session session;
    private Class<T> entity;

    public CrudDAOImpl(){
        entity = (Class<T>)(((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public void setSession(Session session){
        this.session = session;
    }

    @Override
    public void save(T entity) throws Exception {
        session.persist(entity);
    }

    @Override
    public void delete(Id id) throws Exception {
        T t = session.find(entity,id);
        session.remove(t);

    }

    @Override
    public void update(T entity) throws Exception {
        session.persist(entity);
    }

    @Override
    public T findByID(Id id) throws Exception {
        return session.find(entity,id);
    }

    @Override
    public List<T> getAll() throws Exception {
        return session.createQuery("FROM "+ entity.getName()).list();
    }
}
