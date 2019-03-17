package com.message.demo.service.message;

import com.message.demo.model.Message;
import com.message.demo.service.details.MessageDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final MessageRepository messageRepository;
    private final MessageDetailsService messageDetailsService;

    MessageServiceImpl(MessageRepository messageRepository,
                       MessageDetailsService messageDetailsService) {
        this.messageRepository = messageRepository;
        this.messageDetailsService = messageDetailsService;
    }

    /**
     * @param message a placeholder that contains the properties of the message to be created
     * @return
     */
    @Override
    public Message create(Message message) {
        LOGGER.debug("Processing create policy request.");

        String messageStr = message.getMessage();
        Message copyMessage = new Message();
        copyMessage.setMessage(messageStr);
        copyMessage.getProperties().put("isPalindrome",
                Boolean.toString(messageDetailsService.isPalindrome(messageStr)));

        Message newMessage = messageRepository.save(copyMessage);
        LOGGER.debug("Message: created with id: {}", newMessage.getId());
        return newMessage;

    }

    /**
     * @param id of the message to be retrieved
     * @return
     */
    @Override
    public Message get(long id) {
        Optional<Message> msgOptional = messageRepository.findById(id);
        if (!msgOptional.isPresent()) {
            throw new MessageNotFoundException(id);
        }
        LOGGER.debug("Message: retrieved with id: {}", id);
        return msgOptional.get();
    }

    /**
     * @param message a placeholder that contains the properties of the message to be retrieved and updated
     * @return the updated message
     */
    @Override
    public Message update(Message message) {
        LOGGER.debug("Updating message {} in DB", message.getId());
        Message oldMessage = get(message.getId());
        String messageStr = message.getMessage();
        oldMessage.setMessage(messageStr);
        oldMessage.getProperties().put("isPalindrome",
                Boolean.toString(messageDetailsService.isPalindrome(messageStr)));
        LOGGER.debug("Message: updated with id: {}", message.getId());
        return messageRepository.save(oldMessage);
    }

    /**
     * @param id of the message to be retrieved
     */
    @Override
    public void delete(Long id) {
        messageRepository.deleteById(id);
        LOGGER.debug("Message: deleted with id: {}", id);
    }

    /**
     * @param page      zero-based page index.
     * @param size      the size of the page to be returned.
     * @param direction must not be {@literal null}.
     * @param sortBy    property to be used for sorting
     * @return a new Page with the above properties
     */
    @Override
    public Page<Message> findPaginated(int page, int size, String direction, String sortBy) {
        Pageable pageable = null;
        try {
            if (!Message.validFields.contains(sortBy)) {
                throw new PaginationSortingException("Not a valid message property");
            }
            if(direction.equalsIgnoreCase("ASC")){
                pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
            }else if(direction.equalsIgnoreCase("DESC")){
                pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
            }else{
                throw new PaginationSortingException("Not a valid direction");
            }
        } catch (IllegalArgumentException ex) {
            throw new PaginationSortingException(ex.getMessage());
        }
        return messageRepository.findAll(pageable);
    }


}
