package com.rideshare.fare.service;

import com.rideshare.fare.model.RideRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class FareCalculationServiceTest {

    @Mock 
    private DiscountService discountService;
    private FareCalculationService fareService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fareService = new FareCalculationService(discountService);
    }

    // -------------------------------
    // BOUNDARY VALUE ANALYSIS
    // -------------------------------

    @Test
    void testCalculateFare_BVA_MinDistance() {
      
        RideRequest request = new RideRequest(1, false, null);
        double fare = fareService.calculateFare(request);
        assertEquals(50 + (1 * 10), fare); 
    }

    @Test
    void testCalculateFare_BVA_MaxDistance() {
      
        RideRequest request = new RideRequest(100, false, null);
        double fare = fareService.calculateFare(request);
        assertEquals(50 + (100 * 10), fare); // Base fare + distance fare
    }

    // -------------------------------
    // EQUIVALENCE PARTITIONING
    // -------------------------------

    @Test
    void testCalculateFare_EP_ValidPromoCode() {
       
        RideRequest request = new RideRequest(10, false, "SAVE10");
        when(discountService.getDiscountPercentage("SAVE10")).thenReturn(10.0);
        double fare = fareService.calculateFare(request);
        double expectedFare = 50 + (10 * 10) - ((50 + (10 * 10)) * 0.10); 
        assertEquals(expectedFare, fare);
    }

    @Test
    void testCalculateFare_EP_InvalidPromoCode() {
      
        RideRequest request = new RideRequest(10, false, "INVALID");
        when(discountService.getDiscountPercentage("INVALID")).thenReturn(0.0);
        double fare = fareService.calculateFare(request);
        double expectedFare = 50 + (10 * 10); 
        assertEquals(expectedFare, fare);
    }

    // -------------------------------
    // DECISION TABLE TEST
    // -------------------------------

    @Test
    void testCalculateFare_DT_PeakWithPromoAndCap() {
 
        RideRequest request = new RideRequest(10, true, "SAVE80");
        when(discountService.getDiscountPercentage("SAVE80")).thenReturn(80.0);
        
        double fare = fareService.calculateFare(request);
        
        double subtotal = 50 + (10 * 10); 
        double peakFare = subtotal * 1.5; 
        double discount = peakFare * 0.80; 
        double cappedDiscount = Math.min(discount, peakFare * 0.50); 
        double expectedFare = peakFare - cappedDiscount;
        assertEquals(expectedFare, fare);
    }

    // -------------------------------
    // ADDITIONAL CASES
    // -------------------------------

    @Test
    void testCalculateFare_OffPeak_NoPromo() {
     
        RideRequest request = new RideRequest(10, false, null);
    
        double fare = fareService.calculateFare(request);
        double expectedFare = 50 + (10 * 10); 
        assertEquals(expectedFare, fare);
    }

    @Test
    void testCalculateFare_NullRequest_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> fareService.calculateFare(null));
    }

    @Test
    void testCalculateFare_ZeroDistance_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> fareService.calculateFare(new RideRequest(0, false, null)));
    }
}
