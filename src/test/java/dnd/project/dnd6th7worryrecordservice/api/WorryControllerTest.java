package dnd.project.dnd6th7worryrecordservice.api;

import dnd.project.dnd6th7worryrecordservice.service.WorryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WorryController.class)
@DisplayName("MemberController 테스트")
public class WorryControllerTest {


    private MockMvc mvc;

    @MockBean
    private WorryService worryService;

    @BeforeEach
    public void setUp() {
        mvc =
                MockMvcBuilders.standaloneSetup(new WorryController(worryService))
                        .addFilters(new CharacterEncodingFilter("UTF-8", true)) // utf-8 필터 추가
                        .build();
    }

}

