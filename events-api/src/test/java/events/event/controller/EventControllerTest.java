package events.event.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import events.account.domain.Account;
import events.config.BasicAuthInterceptor;
import events.config.EventsBootTestConfiguration;
import events.config.MockEvnetsEntityHelper;
import events.event.domain.Event;
import events.event.dto.EventRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Base64;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventsBootTestConfiguration.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@DisplayName("Event Api 테스트")
class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockEvnetsEntityHelper mockEvnetsEntityHelper;

    private Account account;
    private Event savedEvent;
    private final String EVENT_RESOURCE = "/api/v1/events";

    @BeforeEach
    void setUp() {
        savedEvent = mockEvnetsEntityHelper.mockEvent();
        account = savedEvent.getRegister();
    }

    @Test
    @DisplayName("Event 생성 테스트")
    void createEvent() throws Exception {
        // given
        EventRequest request = new EventRequest();
        request.setName("SpringBoot 스터디");
        request.setContents("스프링 부트와 JPA 대한 학습");
        request.setPrice(10000);
        request.setLocation("장은빌딩 18층 카페");
        request.setAvailAbleParticipant(20);
        LocalDateTime beginEnrollmentDateTime = LocalDateTime.now().plusMinutes(1);
        LocalDateTime beginEventDateTime = beginEnrollmentDateTime.plusMonths(1);
        request.setBeginEnrollmentDateTime(beginEnrollmentDateTime);
        request.setEndEnrollmentDateTime(beginEnrollmentDateTime.plusDays(1));
        request.setBeginEventDateTime(beginEventDateTime);
        request.setEndEventDateTime(beginEventDateTime.plusHours(8));


        // when
        this.mockMvc.perform(post(EVENT_RESOURCE)
                    .header(HttpHeaders.AUTHORIZATION, getBasicAuthHeader())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("contents").exists())
                .andExpect(jsonPath("location").exists())
                .andExpect(jsonPath("price").exists())
                .andExpect(jsonPath("beginEnrollmentDateTime").exists())
                .andExpect(jsonPath("endEnrollmentDateTime").exists())
                .andExpect(jsonPath("beginEventDateTime").exists())
                .andExpect(jsonPath("endEventDateTime").exists())
                .andExpect(jsonPath("availAbleParticipant").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("create-event",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaTypes.HAL_JSON_UTF8_VALUE)
                        ),
                        requestFields(
                                fieldWithPath("name").description("이벤트 명"),
                                fieldWithPath("contents").description("이벤트 내용"),
                                fieldWithPath("location").description("이벤트 장소"),
                                fieldWithPath("price").description("이벤트 가격"),
                                fieldWithPath("availAbleParticipant").description("참석 가능 인원"),
                                fieldWithPath("beginEnrollmentDateTime").description("이벤트 등록 시작시간"),
                                fieldWithPath("endEnrollmentDateTime").description("이벤트 등록 종료시간"),
                                fieldWithPath("beginEventDateTime").description("이벤트 시작시간"),
                                fieldWithPath("endEventDateTime").description("이벤트 종료시간")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description(HttpHeaders.LOCATION),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaTypes.HAL_JSON_UTF8_VALUE)
                        ),
                        responseFields(
                                fieldWithPath("name").description("이벤트 명"),
                                fieldWithPath("contents").description("이벤트 내용"),
                                fieldWithPath("location").description("이벤트 장소"),
                                fieldWithPath("price").description("이벤트 가격"),
                                fieldWithPath("availAbleParticipant").description("참석 가능 인원"),
                                fieldWithPath("beginEnrollmentDateTime").description("이벤트 등록 시작시간"),
                                fieldWithPath("endEnrollmentDateTime").description("이벤트 등록 종료시간"),
                                fieldWithPath("beginEventDateTime").description("이벤트 시작시간"),
                                fieldWithPath("endEventDateTime").description("이벤트 종료시간"),
                                fieldWithPath("register.email").description("이벤트 등록자 email"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.events.href").description("link to list"),
                                fieldWithPath("_links.update.href").description("link to update"),
                                fieldWithPath("_links.delete.href").description("link to delete"),
                                fieldWithPath("_links.profile.href").description("link to profile")
                        )
                ))
        ;
    }

    private String getBasicAuthHeader() {
        String credential = account.getEmail() + BasicAuthInterceptor.BASIC_AUTH_SPLITTER + MockEvnetsEntityHelper.DEFAULT_PASSWORD;
        String encodedCredential = Base64.getEncoder().encodeToString(credential.getBytes());
        return BasicAuthInterceptor.BASIC_AUTH_HEADER + encodedCredential;
    }

    @Test
    @DisplayName("Event 단건 조회 테스트")
    void readEvent() throws Exception {
        // when & then
        this.mockMvc.perform(get(EVENT_RESOURCE + "/{id}", savedEvent.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(savedEvent.getName()))
                .andExpect(jsonPath("contents").value(savedEvent.getContents()))
                .andExpect(jsonPath("location").value(savedEvent.getLocation()))
                .andExpect(jsonPath("price").value(savedEvent.getPrice()))
                .andExpect(jsonPath("availAbleParticipant").value(savedEvent.getAvailAbleParticipant()))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("read-event",
                        pathParameters(
                            parameterWithName("id").description("이벤트 Id")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaTypes.HAL_JSON_UTF8_VALUE)
                        ),
                        responseFields(
                                fieldWithPath("name").description("이벤트 명"),
                                fieldWithPath("contents").description("이벤트 내용"),
                                fieldWithPath("location").description("이벤트 장소"),
                                fieldWithPath("price").description("이벤트 가격"),
                                fieldWithPath("availAbleParticipant").description("참석 가능 인원"),
                                fieldWithPath("beginEnrollmentDateTime").description("이벤트 등록 시작시간"),
                                fieldWithPath("endEnrollmentDateTime").description("이벤트 등록 종료시간"),
                                fieldWithPath("beginEventDateTime").description("이벤트 시작시간"),
                                fieldWithPath("endEventDateTime").description("이벤트 종료시간"),
                                fieldWithPath("register.email").description("이벤트 등록자 email"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.events.href").description("link to list"),
                                fieldWithPath("_links.profile.href").description("link to profile")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("존재하지 않는 Event 조회시 404에러 처리")
    void readEventNotFound() throws Exception {
        //when & then
        this.mockMvc.perform(get(EVENT_RESOURCE + "/10000"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errorMessage").exists());
    }

    @Test
    @DisplayName("이벤트 목록 조회")
    void readEvents() throws Exception {
        this.mockMvc.perform(get(EVENT_RESOURCE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.briefEventResponseList[0].name").exists())
                .andExpect(jsonPath("_embedded.briefEventResponseList[0].price").exists())
                .andExpect(jsonPath("_embedded.briefEventResponseList[0].location").exists())
                .andExpect(jsonPath("_embedded.briefEventResponseList[0].attendancesCount").exists())
                .andExpect(jsonPath("_embedded.briefEventResponseList[0].availAbleParticipant").exists())
                .andExpect(jsonPath("_embedded.briefEventResponseList[0].beginEnrollmentDateTime").exists())
                .andExpect(jsonPath("_embedded.briefEventResponseList[0].endEnrollmentDateTime").exists())
                .andDo(document("read-events",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaTypes.HAL_JSON_UTF8_VALUE)
                        ),
                        relaxedResponseFields(
                                fieldWithPath("_embedded.briefEventResponseList[]").description("Event 목록 리스트"),
                                fieldWithPath("_embedded.briefEventResponseList[].price").description("이벤트 명"),
                                fieldWithPath("_embedded.briefEventResponseList[].price").description("이벤트 가격"),
                                fieldWithPath("_embedded.briefEventResponseList[].location").description("이벤트 장소"),
                                fieldWithPath("_embedded.briefEventResponseList[].attendancesCount").description("참석 인원"),
                                fieldWithPath("_embedded.briefEventResponseList[].availAbleParticipant").description("참석 가능 인원"),
                                fieldWithPath("_embedded.briefEventResponseList[].beginEnrollmentDateTime").description("이벤트 등록 시작시간"),
                                fieldWithPath("_embedded.briefEventResponseList[].endEnrollmentDateTime").description("이벤트 등록 종료시간")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("이벤트 수정 테스트")
    void updateEvent() throws Exception {
        EventRequest request = new EventRequest();
        request.setName("SpringBoot 스터디 -> REST API 테스트");
        request.setContents("스프링 부트와 JPA 대한 학습");
        request.setPrice(20000);
        request.setLocation("삼성빌등 7층 카페");
        request.setAvailAbleParticipant(20);
        LocalDateTime beginEnrollmentDateTime = LocalDateTime.now().plusMinutes(1);
        LocalDateTime beginEventDateTime = beginEnrollmentDateTime.plusMonths(1);
        request.setBeginEnrollmentDateTime(beginEnrollmentDateTime);
        request.setEndEnrollmentDateTime(beginEnrollmentDateTime.plusDays(1));
        request.setBeginEventDateTime(beginEventDateTime);
        request.setEndEventDateTime(beginEventDateTime.plusHours(8));

        // when & then
        this.mockMvc.perform(put(EVENT_RESOURCE + "/{id}", savedEvent.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(jsonPath("name").value(request.getName()))
                .andExpect(jsonPath("location").value(request.getLocation()))
                .andExpect(jsonPath("price").value(request.getPrice()))
                .andDo(document("update-event",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaTypes.HAL_JSON_UTF8_VALUE)
                        ),
                        pathParameters(
                                parameterWithName("id").description("이벤트 Id")
                        ),
                        requestFields(
                                fieldWithPath("name").description("이벤트 명"),
                                fieldWithPath("contents").description("이벤트 내용"),
                                fieldWithPath("location").description("이벤트 장소"),
                                fieldWithPath("price").description("이벤트 가격"),
                                fieldWithPath("availAbleParticipant").description("참석 가능 인원"),
                                fieldWithPath("beginEnrollmentDateTime").description("이벤트 등록 시작시간"),
                                fieldWithPath("endEnrollmentDateTime").description("이벤트 등록 종료시간"),
                                fieldWithPath("beginEventDateTime").description("이벤트 시작시간"),
                                fieldWithPath("endEventDateTime").description("이벤트 종료시간")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaTypes.HAL_JSON_UTF8_VALUE)
                        ),
                        responseFields(
                                fieldWithPath("name").description("이벤트 명"),
                                fieldWithPath("contents").description("이벤트 내용"),
                                fieldWithPath("location").description("이벤트 장소"),
                                fieldWithPath("price").description("이벤트 가격"),
                                fieldWithPath("availAbleParticipant").description("참석 가능 인원"),
                                fieldWithPath("beginEnrollmentDateTime").description("이벤트 등록 시작시간"),
                                fieldWithPath("endEnrollmentDateTime").description("이벤트 등록 종료시간"),
                                fieldWithPath("beginEventDateTime").description("이벤트 시작시간"),
                                fieldWithPath("endEventDateTime").description("이벤트 종료시간"),
                                fieldWithPath("register.email").description("이벤트 등록자 email"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.events.href").description("link to list"),
                                fieldWithPath("_links.profile.href").description("link to profile")
                        )
                ))
        ;

    }

    @Test
    @DisplayName("Event 삭제 테스트")
    void deleteEvent() throws Exception {
        // when & then
        this.mockMvc.perform(delete(EVENT_RESOURCE + "/{id}", savedEvent.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").exists())
                .andDo(document("delete-event",
                        pathParameters(
                                parameterWithName("id").description("이벤트 Id")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaTypes.HAL_JSON_UTF8_VALUE)
                        ),
                        responseFields(
                                fieldWithPath("content").description("삭제된 이벤트 id"),
                                fieldWithPath("_links.events.href").description("link to list"),
                                fieldWithPath("_links.profile.href").description("link to profile")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("이벤트 생성/구정 오류시 Bad Request 및 메시지가 잘 내려오는지 확인 테스트.")
    void createEventExceptionTest() throws Exception {
        // given
        EventRequest eventRequest = new EventRequest();
        eventRequest.setName("SpringBoot 스터디");
        eventRequest.setContents("스프링 부트와 JPA 대한 학습");
        eventRequest.setPrice(Event.MAX_PRICE + 1);

        // when & then
        this.mockMvc.perform(post(EVENT_RESOURCE)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(eventRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorMessage").exists())
        ;
    }
}