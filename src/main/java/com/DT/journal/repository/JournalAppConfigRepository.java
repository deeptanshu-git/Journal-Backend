package com.DT.journal.repository;

import com.DT.journal.entity.JournalAppConfig;
import com.DT.journal.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalAppConfigRepository extends MongoRepository<JournalAppConfig, ObjectId> {

}
