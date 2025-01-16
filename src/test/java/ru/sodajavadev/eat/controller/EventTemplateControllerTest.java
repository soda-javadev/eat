package ru.sodajavadev.eat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.sodajavadev.eat.dto.EventTemplateDto;
import ru.sodajavadev.eat.entity.EventTemplateType;

import java.time.DayOfWeek;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sodajavadev.eat.controller.EventTemplateController.UI_V_1_EVENT_TEMPLATE;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "GM")
@Transactional
@Sql(scripts = "/sql/event-template.sql")
class EventTemplateControllerTest {

    private static final String URL_TEMPLATE_GET_ALL = UI_V_1_EVENT_TEMPLATE + "/all";

    @MockBean
    private JwtDecoder decoder;

    @MockBean
    private ClientRegistrationRepository registrationRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createEventTemplate() throws Exception {
        EventTemplateDto expected = createEventTemplateDtoTestForCreate();

        mockMvc.perform(post(UI_V_1_EVENT_TEMPLATE)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.templateName").value(expected.getTemplateName()))
                .andExpect(jsonPath("$.eventName").value(expected.getEventName()))
                .andExpect(jsonPath("$.type").value(expected.getType().toString()))
                .andExpect(jsonPath("$.minute").value(expected.getMinute()))
                .andExpect(jsonPath("$.hour").value(expected.getHour()))
                .andExpect(jsonPath("$.dayOfWeek").doesNotExist())
                .andExpect(jsonPath("$.dayOfMonth").doesNotExist())
                .andExpect(jsonPath("$.active").value(expected.getActive()));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get(UI_V_1_EVENT_TEMPLATE)
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.templateName").value("agl1 12:00"))
                .andExpect(jsonPath("$.eventName").value("agl1"))
                .andExpect(jsonPath("$.type").value((EventTemplateType.DAILY).toString()))
                .andExpect(jsonPath("$.minute").value(59))
                .andExpect(jsonPath("$.hour").value(23))
                .andExpect(jsonPath("$.dayOfWeek").doesNotExist())
                .andExpect(jsonPath("$.dayOfMonth").doesNotExist())
                .andExpect(jsonPath("$.active").value(false));
    }

    @Test
    void findAllOnlyActiveIsNull() throws Exception {
        mockMvc.perform(get(URL_TEMPLATE_GET_ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    void findAllOnlyActiveIsTrue() throws Exception {
        mockMvc.perform(get(URL_TEMPLATE_GET_ALL)
                        .param("onlyActive", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    void findAllOnlyActiveIsFalse() throws Exception {
        mockMvc.perform(get(URL_TEMPLATE_GET_ALL)
                        .param("onlyActive", "false"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(7));
    }

    @Test
    void updateEventTemplateDtoWithDailyType() throws Exception {
        var expected = createEventTemplateDtoTest();

        mockMvc.perform(put(UI_V_1_EVENT_TEMPLATE)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.templateName").value(expected.getTemplateName()))
                .andExpect(jsonPath("$.eventName").value(expected.getEventName()))
                .andExpect(jsonPath("$.type").value(expected.getType().toString()))
                .andExpect(jsonPath("$.minute").value(expected.getMinute()))
                .andExpect(jsonPath("$.hour").value(expected.getHour()))
                .andExpect(jsonPath("$.dayOfWeek").doesNotExist())
                .andExpect(jsonPath("$.dayOfMonth").doesNotExist())
                .andExpect(jsonPath("$.active").value(expected.getActive()));
    }

    @Test
    void updateEventTemplateDtoWithWeeklyType() throws Exception {
        var expected = createEventTemplateDtoTest();
        expected.setId(5L);
        expected.setType(EventTemplateType.WEEKLY);
        expected.setDayOfWeek(DayOfWeek.MONDAY);

        mockMvc.perform(put(UI_V_1_EVENT_TEMPLATE)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.templateName").value(expected.getTemplateName()))
                .andExpect(jsonPath("$.eventName").value(expected.getEventName()))
                .andExpect(jsonPath("$.type").value(expected.getType().toString()))
                .andExpect(jsonPath("$.minute").value(expected.getMinute()))
                .andExpect(jsonPath("$.hour").value(expected.getHour()))
                .andExpect(jsonPath("$.dayOfWeek").value(expected.getDayOfWeek().toString()))
                .andExpect(jsonPath("$.dayOfMonth").doesNotExist())
                .andExpect(jsonPath("$.active").value(expected.getActive()));
    }

    @Test
    void updateEventTemplateDtoWithMonthlyType() throws Exception {
        var expected = createEventTemplateDtoTest();
        expected.setId(6L);
        expected.setType(EventTemplateType.MONTHLY);
        expected.setDayOfMonth(25);

        mockMvc.perform(put(UI_V_1_EVENT_TEMPLATE)
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.templateName").value(expected.getTemplateName()))
                .andExpect(jsonPath("$.eventName").value(expected.getEventName()))
                .andExpect(jsonPath("$.type").value(expected.getType().toString()))
                .andExpect(jsonPath("$.minute").value(expected.getMinute()))
                .andExpect(jsonPath("$.hour").value(expected.getHour()))
                .andExpect(jsonPath("$.dayOfWeek").doesNotExist())
                .andExpect(jsonPath("$.dayOfMonth").value(expected.getDayOfMonth()))
                .andExpect(jsonPath("$.active").value(expected.getActive()));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(UI_V_1_EVENT_TEMPLATE)
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    private EventTemplateDto createEventTemplateDtoTest() {
        return EventTemplateDto.builder()
                .id(1L)
                .templateName("test")
                .eventName("test")
                .type(EventTemplateType.DAILY)
                .minute(1)
                .hour(1)
                .active(true)
                .build();
    }

    private EventTemplateDto createEventTemplateDtoTestForCreate() {
        return EventTemplateDto.builder()
                .templateName("test")
                .eventName("test")
                .type(EventTemplateType.DAILY)
                .minute(1)
                .hour(1)
                .active(true)
                .build();
    }
}