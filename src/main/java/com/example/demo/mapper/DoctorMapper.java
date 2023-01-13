package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Doctor;

@Mapper
public interface DoctorMapper {
	
	public Doctor getDoctorByUserId(Doctor doctor);
	
	public Doctor getDoctorById(int id);
	
	public int registerDoctor(Doctor doctor);

}
