package com.DT.journal.repository;

import com.DT.journal.entity.JournalEntry;
import com.DT.journal.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
}
