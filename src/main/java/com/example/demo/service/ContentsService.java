package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ContentsMapper;

@Service
public class ContentsService {
	
	@Autowired
	ContentsMapper contentsMapper;
	
	public Map<String, Object> getContentsInfo() {
		
		Map<String, Object> result = new HashMap<>();
		
		result.put("numberOfModule", contentsMapper.getNumberOfModules());
		result.put("numberOfContents", contentsMapper.getNumberOfContents());
		
		return result;
	}

}
