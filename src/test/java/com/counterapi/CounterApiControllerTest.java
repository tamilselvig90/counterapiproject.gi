package com.counterapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.counterapi.model.Word;
import com.counterapi.controller.CounterApiController;
import com.counterapi.service.CounterApiService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CounterApiController.class)
public class CounterApiControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    private CounterApiController counterApiController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CounterApiService counterApiService;

    @Before
    public void setUp() throws Exception {
        counterApiController = new CounterApiController();
        mockMvc = MockMvcBuilders.standaloneSetup( counterApiController ).build();

    }

    @Test
    public void getWordCount() throws Exception {
        Mockito.when( this.counterApiService.getCount( new ArrayList() ) ).thenReturn( new ArrayList() );
        counterApiController.setCounterApiService( counterApiService );

        mockMvc.perform( post( "/counter-api/search" )
                .contentType( MediaType.APPLICATION_JSON ).content(
                objectMapper.writeValueAsBytes( new Word() ) ).header( "Authorization", "Basic b3B0dXM6Y2FuZGlkYXRlcw==" ) )
                .andDo( print() )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8 ) );
    }

    @Test
    public void getTopCount() throws Exception {
        Mockito.when( this.counterApiService.getTopCount( 5 ) ).thenReturn( new String( "eget|17\nvel|17\nsed|16\nin|15\net|14" ) );
        counterApiController.setCounterApiService( counterApiService );

        mockMvc.perform( get( "/counter-api/top/5" ).contentType( MediaType.APPLICATION_JSON ).header("Authorization", "Basic b3B0dXM6Y2FuZGlkYXRlcw==") )
                .andDo( print() )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( "text/csv" ) );
    }


}
