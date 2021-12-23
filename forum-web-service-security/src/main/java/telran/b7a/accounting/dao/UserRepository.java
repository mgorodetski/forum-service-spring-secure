package telran.b7a.accounting.dao;


import org.springframework.data.mongodb.repository.MongoRepository;

import telran.b7a.accounting.model.UserAccount;

public interface UserRepository extends MongoRepository<UserAccount, String> {

}
