package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ContentsService;

@RestController
public class ContentController {
	
	@Autowired
	ContentsService contentsService;
	
	@GetMapping("contents/count")
	public Map<String, Object> getContentsNum() {
		
		Map<String, Object> result = contentsService.getContentsInfo();
		result.put("status", "success");
		
		return result;
	}
}