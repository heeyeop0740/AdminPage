package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Doctor;

@Mapper
public interface DoctorMapper {
	
	public Doctor getDoctorById(Doctor doctor);
	
	public int registerDoctor(Doctor doctor);

}
