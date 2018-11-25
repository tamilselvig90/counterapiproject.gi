package com.counterapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.counterapi.service.CounterApiService;

@RunWith(SpringJUnit4ClassRunner.class)
public class CounterApiServiceTest {
	protected Logger logger = LoggerFactory.getLogger( CounterApiServiceTest.class);
	private CounterApiService counterApiService = new CounterApiService();
	@Test
	public void getWordCount(){
		List<String> inputList = new ArrayList();
		inputList.add("Duis");
		inputList.add("Sed");
		inputList.add("Donec");
		inputList.add("Augue");
		inputList.add("Pellentesque");
		inputList.add("123");

		assertNotNull( counterApiService.getCount(inputList));
		assertTrue( counterApiService.getCount(inputList).toString().equals("[{Duis=11}, {Sed=16}, {Donec=8}, {Augue=7}, {Pellentesque=6}, {123=0}]"));
	}
	
	@Test
	public void getTopCount(){
		assertNotNull( counterApiService.getTopCount(5));
	}
}
