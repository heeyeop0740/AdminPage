package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContentsMapper {
	
	public int getNumberOfModules();
	
	public int getNumberOfContents();

}
