package com.message.demo.rest.message;

import com.message.demo.model.Message;
import com.message.demo.service.message.MessageServiceImpl;
import com.message.demo.utils.Properties;
import net.minidev.json.JSONObject;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration()
@WebAppConfiguration
public class MessageResourceTest {

    private final static Long MESSAGE_ID = 1L;
    private final static String MESSAGE = "test message";

    private MockMvc mockMvc;

    @MockBean
    private MessageServiceImpl messageService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MessagesResource(messageService)).build();
    }

    @Test
    public void createMessage() throws Exception {
        when(messageService.create(isA(Message.class))).thenReturn(mockMessage());
        mockMvc.perform(post("/v1/messages")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getMockMessageRequest().toJSONString()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("$.id", Is.is(1)));
    }

    @Test
    public void getMessage() throws Exception {
        when(messageService.get(isA(Long.class))).thenReturn(mockMessage());
        mockMvc.perform(get("/v1/messages/" + 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("createdDate").exists())
                .andExpect(jsonPath("modifiedDate").exists())
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.message", Is.is(MESSAGE)));
    }

    @Test
    public void getMessages() throws Exception {
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.ASC, "message");

        List<Message> messages = new ArrayList<>();
        messages.add(mockMessage());

        Page<Message> page = new PageImpl<Message>(messages, pageable, messages.size());
        when(messageService.findPaginated(0, 20, "asc", "message"))
                .thenReturn(page);

        mockMvc.perform(get("/v1/messages"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void updateMessage() throws Exception {
        when(messageService.update(isA(Message.class))).thenReturn(mockMessage());
        mockMvc.perform(put("/v1/messages/" + 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getMockMessageRequest().toJSONString()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void deleteMessage() throws Exception {
        doNothing().when(messageService).delete(isA(Long.class));
        mockMvc.perform(delete("/v1/messages/" + 1))
                .andExpect(status().is2xxSuccessful());
    }

    private Message mockMessage() {
        Date now = new Date();
        Message message = new Message();
        message.setId(MESSAGE_ID);
        message.setMessage(MESSAGE);
        message.setCreatedDate(now);
        message.setModifiedDate(now);
        message.getProperties().put(Properties.IS_PALENDROME.toString(), "false");
        return message;

    }

    private JSONObject getMockMessageRequest() {
        JSONObject messageBody = new JSONObject();
        messageBody.put("message", MESSAGE);
        return messageBody;
    }

}
