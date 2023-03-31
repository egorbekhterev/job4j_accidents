package ru.job4j.accidents.controller;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.data.AccidentSpringDataService;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentSpringDataService service;

    @Test
    @WithMockUser
    public void shouldReturnCreateAccident() throws Exception {
        mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/createAccident"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("rules"));
    }

    @Test
    @WithMockUser
    public void shouldReturnAccidentById() throws Exception {
        Accident accident = new Accident();
        accident.setId(1);
        Mockito.when(service.findById(accident.getId())).thenReturn(Optional.of(accident));

        mockMvc.perform(get("/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/editAccident"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("rules"))
                .andExpect(model().attributeExists("accident"));
    }

    @Test
    @WithMockUser
    public void shouldReturnAccidentByIdNotFound() throws Exception {
        mockMvc.perform(get("/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    @WithMockUser
    public void saveAccidentTest() throws Exception {
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        Accident accident = new Accident();
        Mockito.when(service.save(argument.capture())).thenReturn(Optional.of(accident));

        mockMvc.perform(post("/saveAccident")
                        .param("id", "0")
                        .param("name", "Accident")
                        .param("text", "accidentDesc")
                        .param("address", "accidentAddress")
                        .param("carNumber", "oo000o999")
                        .param("type.id", "1")
                        .param("rIds", "1", "2"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        verify(service).save(argument.capture());

        assertThat(argument.getValue().getId()).isEqualTo(0);
        assertThat(argument.getValue().getName()).isEqualTo("Accident");
        assertThat(argument.getValue().getText()).isEqualTo("accidentDesc");
        assertThat(argument.getValue().getAddress()).isEqualTo("accidentAddress");
        assertThat(argument.getValue().getCarNumber()).isEqualTo("oo000o999");
        assertThat(argument.getValue().getType().getId()).isEqualTo(1);
        assertThat(argument.getValue().getRules().stream().map(Rule::getId).collect(Collectors.toSet()))
                .isEqualTo(Set.of(1, 2));
    }

    @Test
    @WithMockUser
    public void saveAccidentFailedTest() throws Exception {
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        Mockito.when(service.save(argument.capture())).thenReturn(Optional.empty());

        mockMvc.perform(post("/saveAccident")
                        .param("id", "0")
                        .param("name", "Accident")
                        .param("text", "accidentDesc")
                        .param("address", "accidentAddress")
                        .param("carNumber", "oo000o999")
                        .param("type.id", "1")
                        .param("rIds", "1", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"));
    }

   @Test
   @WithMockUser
   public void updateAccidentTest() throws Exception {
       ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
       Mockito.when(service.update(argument.capture())).thenReturn(true);

       mockMvc.perform(post("/updateAccident")
                       .param("id", "0")
                       .param("name", "Accident")
                       .param("text", "accidentDesc")
                       .param("address", "accidentAddress")
                       .param("carNumber", "oo000o999")
                       .param("type.id", "1")
                       .param("rIds", "1", "2"))
               .andDo(print())
               .andExpect(status().is3xxRedirection());

       verify(service).update(argument.capture());

       assertThat(argument.getValue().getId()).isEqualTo(0);
       assertThat(argument.getValue().getName()).isEqualTo("Accident");
       assertThat(argument.getValue().getText()).isEqualTo("accidentDesc");
       assertThat(argument.getValue().getAddress()).isEqualTo("accidentAddress");
       assertThat(argument.getValue().getCarNumber()).isEqualTo("oo000o999");
       assertThat(argument.getValue().getType().getId()).isEqualTo(1);
       assertThat(argument.getValue().getRules().stream().map(Rule::getId).collect(Collectors.toSet()))
               .isEqualTo(Set.of(1, 2));
   }

    @Test
    @WithMockUser
    public void updateAccidentFailedTest() throws Exception {
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        Mockito.when(service.update(argument.capture())).thenReturn(false);

        mockMvc.perform(post("/updateAccident")
                        .param("id", "0")
                        .param("name", "Accident")
                        .param("text", "accidentDesc")
                        .param("address", "accidentAddress")
                        .param("carNumber", "oo000o999")
                        .param("type.id", "1")
                        .param("rIds", "1", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"));
    }
}
