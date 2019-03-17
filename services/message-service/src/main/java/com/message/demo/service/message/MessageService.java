package com.message.demo.service.message;

import com.message.demo.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {

    Message create(Message message);

    Message get(long id);

    Message update(Message message);

    void delete(Long id);

    Page<Message> findPaginated(int page, int size, String sort, String orderBy);

}
