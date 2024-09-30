/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitymanagerdemo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Address;
import model.Customer;

/**
 *
 * @author 64050030 Kitipum Nornua
 */
public class EntityManagerDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //createData();
        //printAllCustomer();
        printCustomerByCity("Pathumthani");
    }

    public void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public static void createData(){
        Customer customer1 = new Customer(1L, "John", "Henry", "jh@mail.com"); 
        Address address1 = new Address(1L, "123/4 Viphavadee Rd.", "Bangkok", "10900", "TH"); 
        address1.setCustomerFk(customer1);
        customer1.setAddressId(address1);
        
        Customer customer2 = new Customer(2L, "Marry", "Jane", "mj@mail.com"); 
        Address address2 = new Address(2L, "123/5 Viphavadee Rd.", "Bangkok", "10900", "TH"); 
        address2.setCustomerFk(customer2);
        customer2.setAddressId(address2);
        
        Customer customer3 = new Customer(3L, "Peter", "Parker", "pp@mail.com"); 
        Address address3 = new Address(3L, "543/21 Fake Rd.", "Nonthaburi", "20900", "TH"); 
        address3.setCustomerFk(customer3);
        customer3.setAddressId(address3);
        
        Customer customer4 = new Customer(4L, "Bruce", "Wayn", "bw@mail.com"); 
        Address address4 = new Address(4L, "678/90 Unreal Rd.", "Pathumthani", "30500", "TH"); 
        address4.setCustomerFk(customer4);
        customer4.setAddressId(address4);
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(address1);
            em.persist(customer1);
            em.flush();
            
            em.persist(address2);
            em.persist(customer2);
            em.flush();
            
            em.persist(address3);
            em.persist(customer3);
            em.flush();
            
            em.persist(address4);
            em.persist(customer4);
            em.flush();
            
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public static void printAllCustomer(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Customer.findAll");
        List<Customer> customerList = (List<Customer>)query.getResultList();
        
        for(Customer customer: customerList){
            Address address = customer.getAddressId();
            System.out.println("-----------------------");
            System.out.println("First Name: " + customer.getFirstname());
            System.out.println("Last Name: " + customer.getLastname());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Street: " + address.getStreet());
            System.out.println("City: " + address.getCity());
            System.out.println("Country: " + address.getCountry());
            System.out.println("Zip Code: " + address.getZipcode());
            System.out.println("-----------------------");
        }
    }
    public static void printCustomerByCity(String city){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();        
        List<Customer> customerList = em.createQuery(
            "SELECT c FROM Customer c WHERE c.addressId.city = :city",Customer.class)
            .setParameter("city",city)
            .getResultList();
            
        for(Customer customer: customerList){
            Address address = customer.getAddressId();
            System.out.println("-----------------------");
            System.out.println("First Name: " + customer.getFirstname());
            System.out.println("Last Name: " + customer.getLastname());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Street: " + address.getStreet());
            System.out.println("City: " + address.getCity());
            System.out.println("Country: " + address.getCountry());
            System.out.println("Zip Code: " + address.getZipcode());
            System.out.println("-----------------------");
        }                           
    }
}
