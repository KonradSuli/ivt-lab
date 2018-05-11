package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimaryTorpedoStore;
  private TorpedoStore mockSecondaryTorpedoStore;

  @Before
  public void init(){
	this.mockPrimaryTorpedoStore = mock(TorpedoStore.class);
	this.mockSecondaryTorpedoStore = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimaryTorpedoStore, mockSecondaryTorpedoStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
	when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
	when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
	when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
  }
  
  @Test
  public void fireTorpedo_Single_Failure() {
	//Arrange
	when(mockPrimaryTorpedoStore.fire(1)).thenReturn(false);
	when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
	
	//Act
	boolean result = ship.fireTorpedo(FiringMode.SINGLE);
	
	//Assert
	assertEquals(false, result);
	verify(mockPrimaryTorpedoStore, times(1)).fire(1);
	verify(mockSecondaryTorpedoStore, times(0)).fire(1);
  }
  
  @Test
  public void fireTorpedo_Single_Empty() {
	  
	//Arrange
	when(mockPrimaryTorpedoStore.fire(1)).thenReturn(false);
	when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
	
	when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
	when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
		
	//Act
	boolean result = ship.fireTorpedo(FiringMode.SINGLE);
		
	//Assert
	assertEquals(true, result);
	verify(mockPrimaryTorpedoStore, times(0)).fire(1);
	verify(mockSecondaryTorpedoStore, times(1)).fire(1);  
  }
  
  @Test
  public void fireTorpedo_Single_Alternating() {
	//Arrange
	when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
	when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
		
	when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
	when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
			
	//Act
	boolean resultFirst = ship.fireTorpedo(FiringMode.SINGLE);
	boolean resultSecond = ship.fireTorpedo(FiringMode.SINGLE);
	boolean resultThird = ship.fireTorpedo(FiringMode.SINGLE);
			
	//Assert
	assertEquals(true, resultFirst);
	assertEquals(true, resultSecond);
	assertEquals(true, resultThird);
	verify(mockPrimaryTorpedoStore, times(2)).fire(1);
	verify(mockSecondaryTorpedoStore, times(1)).fire(1);
	//TODO: verifying inner state
  }
  
  @Test
  public void fireTorpedo_All_OneFailure() {
	  
	//Arrange
		when(mockPrimaryTorpedoStore.fire(1)).thenReturn(false);
		when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
			
		when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
		when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
				
		//Act
		boolean result = ship.fireTorpedo(FiringMode.ALL);
				
		//Assert
		assertEquals(true, result);
		verify(mockPrimaryTorpedoStore, times(1)).fire(1);
		verify(mockSecondaryTorpedoStore, times(1)).fire(1);
  }
  
  @Test
  public void fireTorpedo_All_BothEmpty() {
	  
	//Arrange
		when(mockPrimaryTorpedoStore.fire(1)).thenReturn(false);
		when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
			
		when(mockSecondaryTorpedoStore.fire(1)).thenReturn(false);
		when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);
				
		//Act
		boolean result = ship.fireTorpedo(FiringMode.ALL);
				
		//Assert
		assertEquals(false, result);
		verify(mockPrimaryTorpedoStore, times(0)).fire(1);
		verify(mockSecondaryTorpedoStore, times(0)).fire(1);
  }
  
  @Test
  public void fireTorpedo_Single_SecondaryEmpty() {
	  
	  //Arrange
	  when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
	  when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
			
	  when(mockSecondaryTorpedoStore.fire(1)).thenReturn(false);
	  when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);
	  
	  //Act
	  ship.fireTorpedo(FiringMode.SINGLE);
	  boolean result = ship.fireTorpedo(FiringMode.SINGLE);
	  
	  assertEquals(true, result);
	  verify(mockPrimaryTorpedoStore, times(2)).fire(1);
	  verify(mockSecondaryTorpedoStore, times(0)).fire(1);
  }
  
  @Test
  public void fireTorpedo_Single_BothEmptyPrimaryLast() {
	  
	  //Arrange
	  when(mockPrimaryTorpedoStore.fire(1)).thenReturn(false);
	  when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
			
	  when(mockSecondaryTorpedoStore.fire(1)).thenReturn(false);
	  when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);
	  
	  //Act
	  boolean result = ship.fireTorpedo(FiringMode.SINGLE);
	  
	  assertEquals(false, result);
  }
  
  @Test
  public void fireTorpedo_Single_BothEmptySecondaryLast() {
	  
	  //Arrange
	  when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
	  when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
			
	  when(mockSecondaryTorpedoStore.fire(1)).thenReturn(false);
	  when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);
	  
	  ship.fireTorpedo(FiringMode.SINGLE);
	  
	  when(mockPrimaryTorpedoStore.fire(1)).thenReturn(false);
	  when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
	  //Act
	  boolean result = ship.fireTorpedo(FiringMode.SINGLE);
	  
	  assertEquals(false, result);
  }

}
