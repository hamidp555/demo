package com.message.demo.rest.message;

import com.message.demo.model.Message;
import com.message.demo.service.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessagesResource {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final MessageService messageService;

    MessagesResource(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Handler to create a message
     *
     * @param request The object containg the message fields to be used to create a message
     * @return
     */
    @PostMapping(value = "/v1/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> create(@RequestBody MessageRequest request) {
        LOGGER.info("Processing request to create message.");
        Message newMessage = messageService.create(toMessage(request));
        LOGGER.info("Successfully created message");
        return new ResponseEntity<MessageResponse>(fromMessage(newMessage), HttpStatus.CREATED);
    }

    /**
     * Handler to get all messages paginated
     *
     * @param page
     * @param size
     * @param direction
     * @param orderBy
     * @return
     */
    @GetMapping(value = "/v1/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Message> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,
                                @RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy) {
        LOGGER.info("Processing get all message paginated");
        Page<Message> messages = messageService.findPaginated(page, size, direction, orderBy);
        LOGGER.info("Successfully processed get all message paginated");
        return messages;
    }

    /**
     * Handler to get a message by id
     *
     * @param id of the message to retrieve
     * @return
     */
    @GetMapping(value = "/v1/messages/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageModel get(@PathVariable("id") Long id) {
        LOGGER.info("Processing get message: {} request", id);
        Message message = messageService.get(id);
        LOGGER.info("Successfully processed get message: {}", id);
        return toGetMessageResponse(message);
    }

    /**
     * Handler to delete a message
     *
     * @param id of the message to delete
     * @return
     */
    @DeleteMapping(value = "/v1/messages/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        LOGGER.info("Processing delete message: {} request", id);
        messageService.delete(id);
        LOGGER.info("Successfully processed delete message: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Handler to update a message
     *
     * @param request the object containong the message fileds to update
     * @param id      of the message to update
     * @return
     */
    @PutMapping(value = "/v1/messages/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody MessageRequest request, @PathVariable("id") Long id) {
        LOGGER.info("Processing update message: {} request", id);
        Message message = new Message();
        message.setMessage(request.getMessage());
        message.setId(id);
        messageService.update(message);
        LOGGER.info("Successfully processed update message: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Heplper method that created the object to be mashalled to json and returned as the response body
     *
     * @param message
     * @return
     */
    private MessageModel toGetMessageResponse(Message message) {
        MessageModel messageModel = new MessageModel();
        messageModel.setId(message.getId());
        messageModel.setMessage(message.getMessage());
        messageModel.setCreatedDate(message.getCreatedDate());
        messageModel.setModifiedDate(message.getModifiedDate());
        messageModel.setProperties(message.getProperties());
        return messageModel;
    }

    /**
     * Helper method to convert a request object to Message domain model
     *
     * @param request
     * @return
     */
    private Message toMessage(MessageRequest request) {
        Message message = new Message();
        message.setMessage(request.getMessage());
        return message;
    }

    /**
     * helper method to convert a message domain model to the object to be marshalled and returned as the response body
     *
     * @param message
     * @return
     */
    private MessageResponse fromMessage(Message message) {
        MessageResponse response = new MessageResponse();
        response.setId(message.getId());
        return response;
    }


}
