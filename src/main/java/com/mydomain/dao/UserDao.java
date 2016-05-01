package com.mydomain.dao;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mydomain.infra.ServicesFactory;
import com.mydomain.model.User;
import com.mydomain.model.dto.UserDTO;

public class UserDao {
	
	public UserDTO getUser(String id) {
		UserDTO userDto = new UserDTO();
		User user = userDto.toModel(id);
		Datastore dataStore = ServicesFactory.getMongoDB();
		Query<User> getQuery = dataStore.createQuery(User.class).field("_id").equal(user.getId());
		User userRequested = (User) getQuery.get();
		UserDTO userDtoResult = new UserDTO();
		userDtoResult = userDtoResult.fillFromModel(userRequested);
		return userDtoResult;
	}
	
	
	public List<UserDTO> getUsers() {
		Datastore dataStore = ServicesFactory.getMongoDB();
		List<User> users = dataStore.createQuery(User.class).asList();
		List<UserDTO> usersDtoList = new ArrayList();
		
		for(int index = 0; index < users.size(); index++){
			UserDTO usersDto = new UserDTO();
			usersDto.fillFromModel(users.get(index));
			usersDtoList.add(usersDto);
		}
		return usersDtoList;
	}

	public void createUser(UserDTO userDto){
		User user = userDto.toModel();
		Datastore dataStore = ServicesFactory.getMongoDB();
		dataStore.save(user);
	}

	public void updateUser(UserDTO userDto){
		User user = userDto.toModel();
		Datastore dataStore = ServicesFactory.getMongoDB();
		UpdateOperations<User> ops;
		ops = dataStore.createUpdateOperations(User.class);
		ops.set("name", 	user.getName());
		ops.set("email", 	user.getEmail());
		ops.set("password", user.getPassword());
		ops.set("age", 		user.getAge());
//		user.createUpdateOperations(ops);
		Query<User> updateQuery = dataStore.createQuery(User.class).field("_id").equal(user.getId());
		dataStore.update(updateQuery, ops);
	}
	
	public void deleteUser(String id) {
		UserDTO userDto = new UserDTO();
		User user = userDto.toModel(id);
		Datastore dataStore = ServicesFactory.getMongoDB();
		Query<User> deleteQuery = dataStore.createQuery(User.class).field("_id").equal(user.getId());
		dataStore.delete(deleteQuery);
	}
}
