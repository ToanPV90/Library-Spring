package com.wkrzywiec.spring.library.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wkrzywiec.spring.library.service.LibraryUserDetailService;

@Controller
public class LibraryController {
	
	public static final int USERS_PER_PAGE = 20;
	
	@Autowired
	LibraryUserDetailService userService;

	@GetMapping("/")
	public String showHomePage(){
		
		return "home";
	}

	
	@GetMapping("/admin-panel")
	public String showAdminPanel(	@RequestParam(value="search", required=false) String searchText,
									@RequestParam(value="pageNo", required=false) Integer pageNo,
									ModelMap model){
		
			if (searchText == null && pageNo == null) {
				return "admin-panel";
			}
			
			if (searchText != null && pageNo == null){
				pageNo = 1;
				model.put("pageNo", 1);
			}
			
			model.addAttribute("resultsCount", userService.searchUsersResultsCount(searchText));
			
			model.addAttribute("pageCount", userService.searchUserPagesCount(searchText, USERS_PER_PAGE));
			
			model.addAttribute("userList", userService.searchUsers(searchText, pageNo, USERS_PER_PAGE));
			return "admin-panel";
	}


}
