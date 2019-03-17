package com.message.demo.service.message;

import com.message.demo.utils.Properties;
import com.message.demo.model.Message;
import com.message.demo.service.details.MessageDetailsService;
import org.hamcrest.core.IsAnything;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class MessageServiceTest {

    private final static Long MESSAGE_ID = 1L;
    private final static String MESSAGE = "test message";
    private MessageRepository messageRepository;
    private MessageDetailsService messageDetailsService;
    private MessageServiceImpl sut;

    @Before
    public void before() {
        messageRepository = Mockito.mock(MessageRepository.class);
        messageDetailsService = Mockito.mock(MessageDetailsService.class);
        sut = new MessageServiceImpl(messageRepository, messageDetailsService);
    }

    /**
     * test creat message service method
     */
    @Test
    public void create() {
        when(messageDetailsService.isPalindrome(isA(String.class))).thenReturn(false);
        when(messageRepository.save(isA(Message.class))).thenReturn(createUpdateMockMessage());

        Message givenMessage = givenMessage();
        Message resultMessage = sut.create(givenMessage);

        assertThat(resultMessage.getId()).isNotNull();
        assertThat(resultMessage.getId()).isEqualTo(MESSAGE_ID);
        assertThat(resultMessage.getId()).isEqualTo(MESSAGE_ID);
        assertThat(resultMessage.getCreatedDate()).isNotNull();
        assertThat(resultMessage.getModifiedDate()).isNotNull();
    }

    /**
     * test successful path for get service method
     */
    @Test
    public void getSuccess() {
        when(messageRepository.findById(MESSAGE_ID)).thenReturn(getMockMessage());
        Message resultMessage = sut.get(MESSAGE_ID);

        assertThat(resultMessage.getId()).isNotNull();
        assertThat(resultMessage.getId()).isEqualTo(MESSAGE_ID);
        assertThat(resultMessage.getMessage()).isEqualTo(MESSAGE);
        assertThat(resultMessage.getCreatedDate()).isNotNull();
        assertThat(resultMessage.getModifiedDate()).isNotNull();
    }

    /**
     * test fail path for get service method
     */
    @Test(expected = MessageNotFoundException.class)
    public void getFail() {
        when(messageRepository.findById(MESSAGE_ID)).thenReturn(Optional.empty());
        sut.get(MESSAGE_ID);
    }

    /**
     * test delete service method
     */
    @Test
    public void delete() {
        doNothing().when(messageRepository).deleteById(isA(Long.class));
        sut.delete(MESSAGE_ID);
    }

    /**
     * test fail path for update service method
     */
    @Test(expected = MessageNotFoundException.class)
    public void updateFail() {
        doThrow(MessageNotFoundException.class).when(messageRepository).findById(isA(Long.class));
        Message givenMessage = givenMessageWithId();
        sut.update(givenMessage);
    }

    /**
     * test success path for update service method
     */
    @Test
    public void updateSuccess() {
        when(messageDetailsService.isPalindrome(isA(String.class))).thenReturn(false);
        when(messageRepository.findById(MESSAGE_ID)).thenReturn(getMockMessage());
        when(messageRepository.save(isA(Message.class))).thenReturn(createUpdateMockMessage());

        Message givenMessage = givenMessageWithId();
        Message resultMessage = sut.update(givenMessage);

        assertThat(resultMessage.getId()).isNotNull();
        assertThat(resultMessage.getId()).isEqualTo(MESSAGE_ID);
        assertThat(resultMessage.getMessage()).isEqualTo(MESSAGE);
        assertThat(resultMessage.getCreatedDate()).isNotNull();
        assertThat(resultMessage.getModifiedDate()).isNotNull();
    }

    /**
     * test sucess path of findPaginated service method
     */
    @Test
    public void findPaginatedSuccess(){
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.ASC, "message");

        Message message  = givenMessage();
        List<Message> messages = new ArrayList<>();
        messages.add(message);

        Page<Message> page = new PageImpl<Message>(messages,pageable, messages.size());
        when(messageRepository.findAll(isA(Pageable.class))).thenReturn(page);

        Page<Message> resultPage = sut.findPaginated(0, 20, "asc", "message");
        assertThat(resultPage.getTotalPages()).isEqualTo(1);
        assertThat(resultPage.getTotalElements()).isEqualTo(1);
    }

    /**
     * test fail path of findPaginated service method when direction is wrong
     */
    @Test(expected = PaginationSortingException.class)
    public void findPaginatedFailWrongDirection(){
        sut.findPaginated(0, 20, "text", "message");
    }

    /**
     * test fail path of findPaginated service method when orderBy contains an invalid property
     */
    @Test(expected = PaginationSortingException.class)
    public void findPaginatedFailWrongOrderBy(){
        sut.findPaginated(0, 20, "asc", "text");
    }

    private Message givenMessageWithId() {
        Message message = givenMessage();
        message.setId(MESSAGE_ID);
        return message;
    }

    private Message givenMessage() {
        Message message = new Message();
        message.setMessage(MESSAGE);
        return message;
    }

    private Optional<Message> getMockMessage() {
        Message message = createUpdateMockMessage();
        return Optional.ofNullable(message);
    }

    private Message createUpdateMockMessage() {
        Date now = new Date();
        Message message = new Message();
        message.setId(MESSAGE_ID);
        message.setMessage(MESSAGE);
        message.setCreatedDate(now);
        message.setModifiedDate(now);
        message.getProperties().put(Properties.IS_PALENDROME.toString(), "false");
        return message;
    }

}
