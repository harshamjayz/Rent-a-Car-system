/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.clickme.rac.business.custom.impl;

import java.util.ArrayList;
import java.util.List;

import lk.clickme.rac.business.BOFactory;
import lk.clickme.rac.business.custom.CustomerBO;
import lk.clickme.rac.business.custom.RentalDetailBO;
import lk.clickme.rac.dao.DAOFactory;
import lk.clickme.rac.dao.custom.CustomerDAO;
import lk.clickme.rac.dao.custom.PaymentDAO;
import lk.clickme.rac.dao.custom.RentaldetailDAO;
import lk.clickme.rac.dao.custom.VehicleDAO;
import lk.clickme.rac.db.HibernateUtil;
import lk.clickme.rac.dto.RentaldetailDTO;
import lk.clickme.rac.entity.Customer;
import lk.clickme.rac.entity.Payment;
import lk.clickme.rac.entity.RentalDetail;
import lk.clickme.rac.entity.Vehicle;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static lk.clickme.rac.dao.DAOFactory.DAOType.Rentaldetail;


/**
 *
 * @author Harsha madushan
 */
public class RentaldetailBOImpl implements RentalDetailBO{

    private RentaldetailDAO rentaldetailDAO;
    private CustomerDAO customerDAO;
    private VehicleDAO vehicleDAO;
    private PaymentDAO paymentDAO;
    private CustomerBO customerBO;
    SessionFactory sessionFactory;
    public RentaldetailBOImpl() {
        this.customerBO = (CustomerBO)BOFactory.getInstance().getBO(BOFactory.BOType.CustomerBO);
        this.rentaldetailDAO = (RentaldetailDAO)DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Rentaldetail);
        this.customerDAO = (CustomerDAO)DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Customer);
        this.vehicleDAO = (VehicleDAO)DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Vehicle);
        this.paymentDAO = (PaymentDAO)DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Payment);
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean saveRentaldetail(RentaldetailDTO rentaldetailDTO) throws Exception {
        try (Session session = sessionFactory.openSession() ){
            session.beginTransaction();
            rentaldetailDAO.setSession(session);
            customerDAO.setSession(session);
            vehicleDAO.setSession(session);
            paymentDAO.setSession(session);
            Customer customer1 = customerDAO.findByID(rentaldetailDTO.getCustomerID());
            Vehicle vehicle1 = vehicleDAO.findByID(rentaldetailDTO.getVehicleID());
            System.out.println(vehicle1);
            RentalDetail rentalDetail = new RentalDetail(rentaldetailDTO.getRentalState(),rentaldetailDTO.getRentFrom(),rentaldetailDTO.getRentTo(),customer1,vehicle1);
            rentaldetailDAO.save(rentalDetail);
            session.getTransaction().commit();
            return true;
        }catch (HibernateException exp){
            return false;
        }

//        RentalDetail rentaldetail = new RentalDetail(rentaldetailDTO.getRentalID(),rentaldetailDTO.getCustomerID(),rentaldetailDTO.getVehicleID(),rentaldetailDTO.getRentalState(),rentaldetailDTO.getRentFrom(),rentaldetailDTO.getRentTo());
//        return rentaldetailDAO.save(rentaldetail);
    }

    @Override
    public boolean updateRentaldetail(RentaldetailDTO rentaldetailDTO) throws Exception {
        try (Session session = sessionFactory.openSession() ) {
            session.beginTransaction();
            rentaldetailDAO.setSession(session);
            customerDAO.setSession(session);
            vehicleDAO.setSession(session);
            RentalDetail rentaldetail = rentaldetailDAO.findByID(rentaldetailDTO.getRentalID());
            rentaldetail.setRentalID(rentaldetailDTO.getRentalID());
            rentaldetail.setRentalState(rentaldetailDTO.getRentalState());
            rentaldetail.setRentFrom(rentaldetailDTO.getRentFrom());
            rentaldetail.setRentTo(rentaldetailDTO.getRentTo());
            Customer customer1 = customerDAO.findByID(rentaldetailDTO.getCustomerID());
            rentaldetail.setCustomer(customer1);
            Vehicle vehicle1 = vehicleDAO.findByID(rentaldetailDTO.getVehicleID());
            rentaldetail.setVehicle(vehicle1);

            rentaldetailDAO.update(rentaldetail);
            return true;
        }catch (HibernateException exp){
            return false;
        }

//        Rentaldetail rentaldetail = new Rentaldetail(rentaldetailDTO.getRentalID(),rentaldetailDTO.getCustomerID(), rentaldetailDTO.getVehicleID(), rentaldetailDTO.getRentalState(), rentaldetailDTO.getRentFrom(), rentaldetailDTO.getRentTo());
//        return rentaldetailDAO.update(rentaldetail);
    }

    @Override
    public boolean deleteRentaldetail(int id) throws Exception {
        try (Session session = sessionFactory.openSession() ) {
            session.beginTransaction();
            rentaldetailDAO.setSession(session);
            rentaldetailDAO.delete(id);
            session.getTransaction().commit();
            return true;
        }catch (HibernateException exp){
            return false;
        }

//        return rentaldetailDAO.delete(id);

    }

    @Override
    public RentaldetailDTO findByID(int id) throws Exception {

        try (Session session = sessionFactory.openSession() ) {
            session.beginTransaction();
            rentaldetailDAO.setSession(session);
            RentalDetail rentalDetail = rentaldetailDAO.findByID(id);
            RentaldetailDTO rentaldetailDTO = new RentaldetailDTO(rentalDetail.getRentalID(),rentalDetail.getCustomer().getCID(),rentalDetail.getVehicle().getvID(),rentalDetail.getRentalState(),rentalDetail.getRentFrom(),rentalDetail.getRentTo());
            return rentaldetailDTO;
        }catch (HibernateException exp){
            return null;
        }
//        Rentaldetail rentaldetail = rentaldetailDAO.findByID(id);
//        RentaldetailDTO rentaldetailDTO = new RentaldetailDTO(rentaldetail.getRentalID(),rentaldetail.getCustomerID(),rentaldetail.getVehicleID(), rentaldetail.getRentalState(), rentaldetail.getRentFrom(), rentaldetail.getRentTo());
//        return rentaldetailDTO;
    }

    @Override
    public ArrayList<RentaldetailDTO> getAllRentaldetail() throws Exception {
        ArrayList<RentaldetailDTO> rentaldetailDTOS = new ArrayList<>();
        List<RentalDetail> rentalDetails = null;
        try (Session session = sessionFactory.openSession() ) {
            session.beginTransaction();
            rentaldetailDAO.setSession(session);

            rentalDetails = rentaldetailDAO.getAll();
            session.getTransaction().commit();
            if(rentalDetails == null){
                return null;
            }
            for (RentalDetail rentalDetail : rentalDetails) {
                RentaldetailDTO rentaldetailDTO = new RentaldetailDTO(rentalDetail.getRentalID(), rentalDetail.getCustomer().getCID(),rentalDetail.getVehicle().getvID(),
                        rentalDetail.getRentalState(), rentalDetail.getRentFrom(), rentalDetail.getRentTo());
                rentaldetailDTOS.add(rentaldetailDTO);
            }
            return rentaldetailDTOS;
        }catch (HibernateException exp){
            throw exp;
        }
//        return rentaldetailDTOS;



//       ArrayList<Rentaldetail> rentaldetails = rentaldetailDAO.getAll();
//       ArrayList<RentaldetailDTO> rentadetailDTOs = new ArrayList<RentaldetailDTO>();
//        for (Rentaldetail rentaldetail : rentaldetails) {
//            RentaldetailDTO rentaldetailDTO = new RentaldetailDTO(rentaldetail.getRentalID(),rentaldetail.getCustomerID(),rentaldetail.getVehicleID(), rentaldetail.getRentalState(), rentaldetail.getRentFrom(), rentaldetail.getRentTo());
//            rentadetailDTOs.add(rentaldetailDTO);
//        }
//        return rentadetailDTOs;
    }

   /*@Override
    public ArrayList<CustomerDTO> getAllFinalRentalDetails() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String newDate = dateFormat.format(date);
        
        
        ArrayList<CustomerDTO> Allrentaldetails = new ArrayList<>();
        ArrayList<RentaldetailDTO> rentaldetails = getAllRentaldetail();
        for (RentaldetailDTO rentaldetail : rentaldetails) {
            
                    
        }
        ArrayList<CustomerDTO> customerdetails = customerBO.getAllCustomer();
        
        
    }*/

}
