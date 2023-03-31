package ru.job4j.accidents.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class RegistryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser
    public void shouldReturnRegPage() throws Exception {
        mockMvc.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users/reg"));
    }

    @Test
    @WithMockUser
    public void shouldReturnErrorRegPage() throws Exception {
        mockMvc.perform(get("/reg?error=true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users/reg"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", is("This username already exists.")));
    }

    @Test
    @WithMockUser
    public void regUserTestIsOk() throws Exception {
        mockMvc.perform(post("/reg")
                .param("id", "0")
                .param("password", "test")
                .param("username", "test")
                .param("authority.id", "1")
                .param("enabled", "true"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(repository).save(argument.capture());

        assertThat(argument.getValue().getId()).isEqualTo(0);
        assertThat(argument.getValue().getPassword()).isEqualTo(passwordEncoder.encode("test"));
        assertThat(argument.getValue().getUsername()).isEqualTo("test");
        assertThat(argument.getValue().getAuthority().getId()).isEqualTo(1);
        assertThat(argument.getValue().isEnabled()).isEqualTo(true);
    }

    @Test
    @WithMockUser
    public void regUserTestHasError() throws Exception {
        Mockito.doThrow(new RuntimeException()).when(repository).save(any());

        mockMvc.perform(post("/reg")
                        .param("id", "0")
                        .param("password", "test")
                        .param("username", "test")
                        .param("authority.id", "1")
                        .param("enabled", "true"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/reg?error=true"));
    }
}
