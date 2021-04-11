package com.user_api.main.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.user_api.main.entities.User;

@Repository
@Transactional
public class UserRepository {

	@PersistenceContext
	private EntityManager userEntityManager;

	private static final int BATCH_SIZE= 5;

	public boolean addUserList(List<User> userList) {
		try {

			for(int i= 0; i< userList.size(); i++) {
				userEntityManager.persist(userList.get(i));

				if(i>0 && (i+ 1)% BATCH_SIZE== 0) {
					userEntityManager.flush();
					userEntityManager.clear();
				}
			}

			return true;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}

	public User updateUser(User user) {
		try {
			return userEntityManager.merge(user);
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}

	public boolean deleteUser(String userId) {
		try {
			User user= userEntityManager.find(User.class, userId);

			if(user!= null) {
				userEntityManager.remove(user);
				return true;
			}

			return false;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}

	public List<User> getUserByFilter(String field, String filter) {
		try {
			String selectQuery= "SELECT u FROM User u ";
			Query query= null;

			if(field== null || filter== null)
				query= userEntityManager.createQuery(selectQuery, User.class);
			else if(field!= null && filter!= null) {
				query= userEntityManager.createQuery(selectQuery+ "WHERE u."+ field+ "= ?1", User.class);
				query.setParameter(1, filter);
			}

			return query.getResultList();
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}

}