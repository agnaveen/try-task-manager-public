//package com.techriseyou.taskmanager.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import com.techriseyou.taskmanager.entity.User;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class DonationEventServiceTest {
//
//    @InjectMocks
//    private DonationEventService donationEventService;
//
//    @Mock
//    private DonationEventRepository repository;
//
//    @Mock
//    private UserInfoService userInfoService;
//
//    @Mock
//    private DonationEventMapper donationEventMapper;
//
//    private User user;
//    private DonationEventDTO donationEventDTO;
//    private DonationEvent donationEvent;
//
//    @BeforeEach
//    void setUp() {
//        user = new User();
//        donationEventDTO = new DonationEventDTO();
//        donationEvent = new DonationEvent();
//    }
//
//    @Test
//    void testCreateDonationEventWithValidUser() {
//        when(userInfoService.getCurrentUserDetails()).thenReturn(Optional.of(user));
//        when(donationEventMapper.toEntityWithUser(donationEventDTO, user)).thenReturn(donationEvent);
//        when(repository.save(donationEvent)).thenReturn(donationEvent);
//        when(donationEventMapper.toDTO(donationEvent)).thenReturn(donationEventDTO);
//
//        DonationEventDTO result = donationEventService.createDonationEvent(donationEventDTO);
//
//        assertEquals(donationEventDTO, result);
//    }
//
//    @Test
//    void testCreateDonationEventWithNoCurrentUser() {
//        when(userInfoService.getCurrentUserDetails()).thenReturn(Optional.empty());
//        when(donationEventMapper.toEntityWithUser(donationEventDTO, null)).thenReturn(donationEvent);
//        when(repository.save(donationEvent)).thenReturn(donationEvent);
//        when(donationEventMapper.toDTO(donationEvent)).thenReturn(donationEventDTO);
//
//        DonationEventDTO result = donationEventService.createDonationEvent(donationEventDTO);
//
//        assertEquals(donationEventDTO, result);
//    }
//
//    @Test
//    void testGetDonationEventWithExistingId() {
//        when(repository.findById(1L)).thenReturn(Optional.of(donationEvent));
//        when(donationEventMapper.toDTO(donationEvent)).thenReturn(donationEventDTO);
//
//        DonationEventDTO result = donationEventService.getDonationEvent(1L);
//
//        assertEquals(donationEventDTO, result);
//    }
//
//    @Test
//    void testGetDonationEventWithInvalidId() {
//        when(repository.findById(1L)).thenReturn(Optional.empty());
//
//        DonationEventDTO result = donationEventService.getDonationEvent(1L);
//
//        assertNull(result);
//    }
//}
