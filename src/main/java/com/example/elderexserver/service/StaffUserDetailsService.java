package com.example.elderexserver.service;

import com.example.elderexserver.data.staff.DTO.StaffAuth;
import com.example.elderexserver.data.staff.Staff;
import com.example.elderexserver.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StaffUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(StaffUserDetailsService.class);
    private final StaffRepository staffRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        logger.info("Loading staff by identifier: {}", identifier);

        Staff staff = staffRepository.findByEmail(identifier);

        logger.debug("Staff found: {} (ID: {}, Name: {} {})",
                staff.getEmail(), staff.getId(), staff.getFirst_Name(), staff.getLast_Name());

        return new StaffAuth(staff);
    }

    public UserDetails loadStaffById(Integer staffId) throws UsernameNotFoundException {
        logger.info("Loading staff by id: {}", staffId);

        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> {
                    logger.warn("Staff not found: {} (ID: {})", staffId, staffId);
                    return new UsernameNotFoundException("No staff found with id: " + staffId);
                });

        return new StaffAuth(staff);
    }
}
