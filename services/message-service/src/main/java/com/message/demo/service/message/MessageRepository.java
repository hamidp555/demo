package com.message.demo.service.message;

import com.message.demo.model.Message;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
}
