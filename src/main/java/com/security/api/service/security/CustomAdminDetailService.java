//package com.security.api.service.security;
//
//import com.security.api.advice.exception.CAdminNotFoundException;
//import com.security.api.repo.AdminJpaRepo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
//@Service
//public class CustomAdminDetailService implements UserDetailsService {
//
//    private final AdminJpaRepo adminJpaRepo;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return adminJpaRepo.findById(Long.valueOf(username)).orElseThrow(CAdminNotFoundException::new);
//    }
//}
