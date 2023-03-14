package parallelScripts;

import org.testng.annotations.Test;

public class SampleTwoTest {
  @Test(groups="featureOne")
	  public void testOne() {
		  System.out.println("TestOne from SampleTwo");
	  }
	  @Test
	  public void testTwo() {
		  System.out.println("TestTwo from SampleTwo");
	  }
	  @Test
	  public void testThree() {
		  System.out.println("TestThree from SampleTwo");
	  }
	  @Test
	  public void testFour() {
		  System.out.println("TestFour from SampleTwo");
	  }
  }
