package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.PsctCode;
import com.example.demo.model.User;
import com.example.demo.model.UserInfo;
import com.example.demo.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@Tag(name="사용자", description = "사용자 관리 API")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@ExceptionHandler(RuntimeException.class)
	public Map<String, Object> loginCatcher(Exception ex, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		result.put("message", ex.getMessage());
		result.put("status", "failed");
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return result;
	}
	
	@GetMapping("/userinfo")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "담당 환자 분류", description = "분류별 환자의 수를 반환합니다.")
	public Map<String, Object> getUserInfo(HttpServletRequest request) {
		System.out.println("/userInfo");
		System.out.println(request.getHeader("origin"));
		
		Map<String, Object> result = new HashMap<>();
		UserInfo userInfo = userService.getUserInfo(request);
        
		result.put("userInfo", userInfo);
		result.put("status", "success");
		
		System.out.println(result);

		return result;
	}
	
	@PostMapping("/user/register")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "psctCode등록", description = "psctCode를 등록합니다.")
	public Map<String, Object> registerPsctCode(@RequestBody PsctCode psctCode, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		
		// instId, id - 토큰에서 가져오는 정보 User 모델객체로 설정한다. {psct_code, user name, start_date, end_date} - 리퀘스트 바디에서 가져오는 정보
		psctCode.setStffId((int)request.getAttribute("id"));
		psctCode.setInstId((int)request.getAttribute("instId"));
		// tb_user에 해당하는 환자가 존재하는지 확인할 것 psct_code는 새로 생성한 것이라서 찾을 수 없고, name은 중복이 가능해서 특정 엔티티를 결정할 수 없음
//		
//		User user = userService.getUserByCode(psctCode.getPsctCode());
//		if(user != null) {
//			psctCode.setuId(user.getUid());
//			userService.updateUser(psctCode);
//		}
		// userService에서 제공하는 DB의 TB_PSCT_CODE 테이블에 해당 정보를 넣는다.
		if(userService.registerUser(psctCode) == 0) {
			result.put("status", "failed");
			return result;
		}
		result.put("status", "success");
		return result;
	}
	
	/**
	 * 해당 메서드에서는 name, startDate, endDate만 수정할 수 있도록 설정하였습니다.
	 * @param id
	 * @param newPsctCode
	 * @param request
	 * @return
	 */
	@PostMapping("/user/{id}/update")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "psctCode 업데이트", description = "수정하려는 psctCode에 변경 내용 반영.")
	public Map<String, Object> updatePsctCode(@PathVariable int id, @RequestBody PsctCode newPsctCode, HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<>();
		
		PsctCode psctCode = userService.getPsctCodeById(id, (int)request.getAttribute("instId"), (int)request.getAttribute("id"));
		if(psctCode == null) 
			throw new RuntimeException("해당 id의 psctCode를 찾을 수 없습니다.");
		System.out.println("psctcode : " + psctCode);
		System.out.println("new psctcode : " + newPsctCode);
		psctCode.updatePsctCode(newPsctCode);
		System.out.println(psctCode);
		
		if(userService.updateUser(psctCode) == 0) {
			result.put("status", "failed");
			return result;
		}
		
		result.put("status", "success");
		return result;
	}
		
	@GetMapping("/user/code")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "처방코드 발급", description = "중복되지 않는 처방코드를 발급합니다.")
	public Map<String, Object> getCode(HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<>();
		// 서비스단에서 문자열 생성
		String psctCode = userService.getCode();
		// 서비스단에서 TB_PSCT_CODE 중복여부 확인
		while(userService.isSame(psctCode)) {
			psctCode = userService.getCode();
		}
		// 중복되지 않는다면 해당 코드와 상태 리턴
		result.put("psctCode", psctCode);
		
		return result;
	}
	
	@GetMapping("/user/page/{pageNum}")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "특정 페이지의 환자 정보", description = "특정 수의 환자 정보를 반환합니다.")
	public Map<String, Object> getUsers(@PathVariable int pageNum, HttpServletRequest request) {
		// TODO getUsers의 결과의 length == 0일때 fail을 알려줘야한다. 페이지의 수를 어떻게 할지 생각할 필요 있음.
		Map<String, Object> result = new HashMap<>();
		List<User> users = userService.getUsers((int)request.getAttribute("id"),(int)request.getAttribute("instId"), Integer.valueOf(pageNum));
		
		if(users.size() == 0) {
			result.put("status", "failed");
			return result;
		}
		
		result.put("users",  users);
		result.put("status", "success");
		
		return result;
	}
	
	@GetMapping("/user/delete")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "psctCode 삭제", description = "특정 psctCode엔티티를 삭제합니다.")
	public Map<String, Object> deletePsctCode(int[] id) {
		// TODO 특정 id의 psctCode 엔티티의 삭제가 실패했을 떄 rollback 고려해야함.	
		// TODO 존재하지 않는 id의 엔티티를 삭제하려고 할 때 따로 표기할 것인가?
		// TODO NullPointerException 처리를 어떻게 할 것인가?
		Map<String, Object> result = new HashMap<>();
		
		if(userService.deletePsctCodeById(id) == 0) {
			result.put("status", "failed");
			return result;
		}
		result.put("status", "success");
		return result;
	}
}