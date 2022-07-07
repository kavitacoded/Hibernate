package com.nt.test;

import javax.persistence.Cache;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.nt.entity.Product;

public class L2CacheTest{

    public static void main( String[] args )
    {
      //configuraion object
    	Configuration cfg=new Configuration();
    	cfg.configure("com/nt/cfgs/hibernate.cfg.xml");
 
    	//build session Factory obj
    	SessionFactory factory=cfg.buildSessionFactory();
    	
    	//build session
    	Session ses=factory.openSession();
    	
    	try(ses){
    		//get product class
    		Product prod=ses.get(Product.class,2); //gets from db and keeps in L1 & l2 cache
    		System.out.println(prod);
    		System.out.println("-----------------------");
    		Product prod1=ses.get(Product.class, 2); //gets from L1 cache
    		System.out.println(prod1);
    		System.out.println("-----------------------");
    		ses.evict(prod1);
    		Product prod2=ses.get(Product.class, 2); //gets from L2 cache and keeps in L1 cache
    		System.out.println(prod2);
    		System.out.println("------------------------");
    		ses.clear();
    		Cache cache=factory.getCache();
    		cache.evictAll(); //emptying L2 cache
    		Product prod3=ses.get(Product.class, 2);//gets from db & keeps in L1 and L2 cache
    		System.out.println(prod3);
    		
    	}//try
    	catch(HibernateException he) {
    		he.printStackTrace();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	
    }
}
