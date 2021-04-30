package com.tcs.lbw;

import java.awt.AWTException;
import java.awt.Button;
import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.TextField;
import java.awt.event.InputEvent;

public class Auto {
	static PointerInfo pi;
	static Robot rb;
	static Frame f;	
	static TextField tf;
	static Button bt; 

	static long val;
	static int val2;
	
	
	public Auto() throws AWTException{
		pi = MouseInfo.getPointerInfo();		
		rb = new Robot();
	}
	
	public static void pointLocation(){
		pi = MouseInfo.getPointerInfo();
		System.out.println("x ==== " + pi.getLocation().x);
		System.out.println("y ====" + pi.getLocation().y);
		
	}
	
	public static void mouseMove() throws AWTException{
		rb = new Robot();
		
		try {
			Thread.sleep(80000);
			rb.mouseMove(1147, 459); 
			rb.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			rb.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			// 근태관리 버튼 위치(3204,177)
			rb.mouseMove(1247, 459); 
			rb.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			rb.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			Thread.sleep(20000);
			rb.mouseMove(1347, 459); 
			rb.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			rb.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			Thread.sleep(500000);
			rb.mouseMove(1377, 459); 
			rb.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			rb.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			Thread.sleep(80000);
			rb.mouseMove(1399, 459); 
			rb.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			rb.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			Thread.sleep(1147, 459);
			rb.mouseWheel(50);
			rb.mousePress(InputEvent.SHIFT_DOWN_MASK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws AWTException {
		//pointLocation();
		mouseMove();			
	}
}
