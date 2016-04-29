package com.mydomain.dao;

import java.util.List;

import javax.ws.rs.PathParam;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.mydomain.model.User;
import com.mydomain.service.HibernateUtil;

public class UserDao {


	public User getUser(Integer id) {
		Session ses = HibernateUtil.currentSession();
		try {
			Criteria crit =  ses.createCriteria(User.class);
			crit.add(Restrictions.idEq(id));
			User u = (User)crit.uniqueResult();
			return u;
		} finally {
			HibernateUtil.closeSession();
		}
	}
	
	public List<User> getUsers() {
		Session ses = HibernateUtil.currentSession();
		try {
			List<User> list = ses.createCriteria(User.class).list();
			return list;
		} finally {
			HibernateUtil.closeSession();
		}
	}
	
	public void createUser(User u){
		Session ses = HibernateUtil.currentSession();
		try {
			Transaction tx = ses.beginTransaction();
			ses.save(u);
			tx.commit();
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	public void updateUser(User u){
		
		Session ses = HibernateUtil.currentSession();
		try {
			Transaction tx = ses.beginTransaction();
			ses.update(u);
			tx.commit();
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	public boolean deleteUser(Integer id) {
		Session ses = HibernateUtil.currentSession();
		try {
			Transaction tx = ses.beginTransaction();
			User u = (User) ses.load(User.class, id);
			ses.delete(u);
			tx.commit();
			return true;
		} finally {
			HibernateUtil.closeSession();
		}
	}
}