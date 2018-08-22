/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.clickme.rac.business.custom.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import lk.clickme.rac.business.custom.PaymentBO;
import lk.clickme.rac.dao.DAOFactory;
import lk.clickme.rac.dao.custom.PaymentDAO;
import lk.clickme.rac.dao.custom.RentaldetailDAO;
import lk.clickme.rac.db.HibernateUtil;
import lk.clickme.rac.dto.PaymentDTO;
import lk.clickme.rac.dto.RentaldetailDTO;
import lk.clickme.rac.entity.Payment;
import lk.clickme.rac.entity.RentalDetail;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Harsha madushan
 */
public class PaymentBOImpl implements PaymentBO{

    PaymentDAO paymentDAO;
    RentaldetailDAO rentaldetailDAO;
    SessionFactory sessionFactory;

    public PaymentBOImpl() {
        this.rentaldetailDAO = (RentaldetailDAO)DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Rentaldetail);
        this.paymentDAO = (PaymentDAO)DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Payment);
        sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    @Override
    public boolean savePayment(PaymentDTO paymentDTO) throws Exception {
        try (Session session = sessionFactory.openSession()){
             session.beginTransaction();
             rentaldetailDAO.setSession(session);
            RentalDetail rentalDetail = rentaldetailDAO.findByID(paymentDTO.getRentalID());
            Payment payment = new Payment(paymentDTO.getpMethod(),paymentDTO.getPaneltyFee(),paymentDTO.getAmount(),rentalDetail);
             paymentDAO.setSession(session);
             paymentDAO.save(payment);
             session.getTransaction().commit();
             return  true;
        }catch (HibernateException exp){
            return false;
        }
//        Payment Epayment = new Payment(payment.getpID(), payment.getcID(),payment.getRentID(), payment.getpMethod(), payment.getPaneltyFee(), payment.getAmount());
//        return paymentDAO.save(Epayment);
    }

    @Override
    public boolean updatePayment(PaymentDTO paymentDTO) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            RentalDetail rentalDetail = rentaldetailDAO.findByID(paymentDTO.getRentalID());
            Payment payment = new Payment(paymentDTO.getpMethod(), paymentDTO.getPaneltyFee(), paymentDTO.getAmount(),rentalDetail);
            paymentDAO.setSession(session);
            paymentDAO.update(payment);
            session.getTransaction().commit();
            return true;
        }catch (HibernateException exp){
            return  false;
        }
//        Payment Epayment = new Payment(payment.getpID(), payment.getcID(),payment.getRentID(), payment.getpMethod(), payment.getPaneltyFee(), payment.getAmount());
//        return paymentDAO.update(Epayment);
    }

    @Override
    public boolean deletePayment(int id) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            paymentDAO.setSession(session);
            paymentDAO.delete(id);
            session.getTransaction().commit();
            return true;
        }catch (HibernateException exp){
            return false;
        }

//        return paymentDAO.delete(id);
    }

    @Override
    public PaymentDTO findByID(int id) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            paymentDAO.setSession(session);
            Payment payment = paymentDAO.findByID(id);
            session.getTransaction().commit();
            PaymentDTO paymentDTO = new PaymentDTO(payment.getpID(), payment.getpMethod(), payment.getPaneltyFee(),
                    payment.getAmount(), payment.getRentalDetail().getRentalID());
            return paymentDTO;
        }catch (HibernateException exp){
            return null;
        }

//        Payment payment = paymentDAO.findByID(id);
//        PaymentDTO paymentDTO = new PaymentDTO(payment.getpID(), payment.getcID(),payment.getRentID(), payment.getpMethod(), payment.getPaneltyFee(), payment.getAmount());
//        return paymentDTO;
    }

    @Override
    public ArrayList<PaymentDTO> getAllPayment() throws Exception {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            paymentDAO.setSession(session);
            List<Payment> payments =  paymentDAO.getAll();
            ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();

            for (Payment payment:payments ) {
                PaymentDTO paymentDTO = new PaymentDTO(payment.getpID(),payment.getpMethod(),payment.getPaneltyFee(),
                                                        payment.getAmount(),payment.getRentalDetail().getRentalID());
                paymentDTOS.add(paymentDTO);
            }
            return paymentDTOS;
        }catch (HibernateException exp){
            return null;
        }

//        ArrayList<Payment> payments = paymentDAO.getAll();
//        ArrayList<PaymentDTO> paymentDTOs = new ArrayList<>();
//        for (Payment payment : payments) {
//            PaymentDTO paymentDTO = new PaymentDTO(payment.getpID(), payment.getcID(),payment.getRentID(), payment.getpMethod(), payment.getPaneltyFee(), payment.getAmount());
//            paymentDTOs.add(paymentDTO);
//        }
//        return paymentDTOs;
    }

    @Override
    public boolean FinishPayment(PaymentDTO paymentDTO, RentaldetailDTO rentaldetailDTO)throws Exception{
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            paymentDAO.setSession(session);
            rentaldetailDAO.setSession(session);
//            RentalDetail rentalObject = rentaldetailDAO.findByID(paymentDTO.getRentalID());
//            Payment payment = new Payment(paymentDTO.getpMethod(), paymentDTO.getPaneltyFee(), paymentDTO.getAmount(), rentalObject);
//            Payment paymnt = paymentDAO.findByID(paymentDTO.getpID());
//            paymnt.setpMethod(paymentDTO.getpMethod());
//            paymnt.setPaneltyFee(paymentDTO.getPaneltyFee());
//            paymnt.setAmount(paymentDTO.getAmount());

            RentalDetail reobject2 = rentaldetailDAO.findByID(rentaldetailDTO.getRentalID());
            reobject2.setRentalState(0);
            rentaldetailDAO.update(reobject2);
            RentalDetail reobject3 = rentaldetailDAO.findByID(rentaldetailDTO.getRentalID());
            Payment payment = new Payment(paymentDTO.getpMethod(), paymentDTO.getPaneltyFee(), paymentDTO.getAmount(), reobject3);
            //paymnt.setRentalDetail(reobject2);

            paymentDAO.save(payment);
            session.getTransaction().commit();

            return true;
        }
//        }catch (HibernateException exp) {
//            return false;
//        }



//            connection = DBConnection.getInstance().getConnection();
//            connection.setAutoCommit(false);
//            Payment Epayment = new Payment(0,payment.getcID(), payment.getRentID(), payment.getpMethod(), payment.getPaneltyFee(), payment.getAmount());
//            Boolean result1 = paymentDAO.save(Epayment);
//            if(result1 == true){
//                Rentaldetail Erentaldetail = new Rentaldetail(rentaldetail.getRentalID(), rentaldetail.getCustomerID(), rentaldetail.getVehicleID(), rentaldetail.getRentalState(), rentaldetail.getRentFrom(), rentaldetail.getRentTo());
//                Boolean result2 = rentaldetailDAO.update(Erentaldetail);
//                if (!result2) {
//                    connection.rollback();
//                    return false;
//                }
//            }else{
//                connection.rollback();
//                return false;
//            }
//        } catch (Exception ex) {
//            connection.rollback();
//            throw ex;
//        }finally{
//            connection.setAutoCommit(true);
//        }
//        return true;
        
    }
    
    
    
}
