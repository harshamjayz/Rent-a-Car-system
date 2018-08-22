/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.clickme.rac.business.custom.impl;

import java.util.ArrayList;
import java.util.List;

import lk.clickme.rac.business.custom.CustomerBO;
import lk.clickme.rac.dao.CrudDAO;
import lk.clickme.rac.dao.DAOFactory;
import lk.clickme.rac.dao.custom.CustomerDAO;
import lk.clickme.rac.dao.custom.impl.CustomerDAOImpl;
import lk.clickme.rac.db.HibernateUtil;
import lk.clickme.rac.dto.CustomerDTO;
import lk.clickme.rac.entity.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Harsha madushan
 */
public class CustomerBOImpl implements CustomerBO{
    
    
    CustomerDAO customerDAO;
    SessionFactory sessionFactory;

    public CustomerBOImpl() {
        this.customerDAO = (CustomerDAO)DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Customer);
        sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    
    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws Exception {
        try(Session session = sessionFactory.openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();
            Customer customer = new Customer(customerDTO.getnIC(),customerDTO.getName(),customerDTO.getTelNO(),customerDTO.getAddress());
            customerDAO.save(customer);
            session.getTransaction().commit();
            return true;
        }catch (HibernateException ex){
            return false;
        }

    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws Exception {
        try(Session session = sessionFactory.openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();
            Customer customer = customerDAO.findByID(customerDTO.getcID());
            customer.setNIC(customerDTO.getnIC());
            customer.setName(customerDTO.getName());
            customer.setAddress(customerDTO.getAddress());
            customer.setTelNO(customerDTO.getTelNO());
            //Customer customer = new Customer(customerDTO.getnIC(),customerDTO.getName(),customerDTO.getTelNO(),customerDTO.getAddress());
            customerDAO.update(customer);
            session.getTransaction().commit();
            return true;
        }catch (HibernateException ex){
            return false;
        }
    }

    @Override
    public boolean deleteCustomer(int id) throws Exception {
        try(Session session = sessionFactory.openSession()){
            customerDAO.setSession(session);
            session.beginTransaction();
            customerDAO.delete(id);
            session.getTransaction().commit();
            return true;
        }catch (HibernateException exp){
            return false;
        }
    }

    @Override
    public CustomerDTO findByID(int id) throws Exception {
        try(Session session = sessionFactory.openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();
            Customer customer = customerDAO.findByID(id);
            session.getTransaction().commit();
            CustomerDTO customerDTO = new CustomerDTO(customer.getCID(), customer.getNIC(), customer.getName(),
                    customer.getAddress(), customer.getTelNO());
            return customerDTO;
        }catch (HibernateException exp){
            return  null;
        }
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws Exception {
        try(Session session = sessionFactory.openSession()) {
            customerDAO.setSession(session);
            session.beginTransaction();
            List<Customer> allCustomers = customerDAO.getAll();
            session.getTransaction().commit();
            ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
            for (Customer customer : allCustomers) {
                CustomerDTO customerDTO = new CustomerDTO(customer.getCID(), customer.getNIC(), customer.getName(),
                        customer.getAddress(), customer.getTelNO());
                customerDTOS.add(customerDTO);
            }
            return customerDTOS;
        }catch (HibernateException exp){
            return null;
        }
    }
    
}
