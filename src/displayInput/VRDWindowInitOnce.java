package displayInput;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Patrick
 */

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;


import jna.extra.GDI32Extra;
import jna.extra.User32Extra;
import jna.extra.WinGDIExtra;

import com.sun.jna.Memory;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HBITMAP;
import com.sun.jna.platform.win32.WinDef.HDC;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.platform.win32.WinGDI.BITMAPINFO;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class VRDWindowInitOnce {
	private HWND hWnd;
	private Memory buffer;
	private HDC hdcWindow;
	private HBITMAP hBitmap;
	private int width;
	private int height;
	private BITMAPINFO bmi;
	private boolean needsInit;
	private HDC hdcMemDC;
	private HANDLE hOld;
	private RECT bounds;
	private BufferedImage image;
	private int oldWidth, oldHeight;
	public void bufferedInit(){

		hdcWindow = (User32Extra.INSTANCE).GetWindowDC(hWnd);
		hdcMemDC = GDI32.INSTANCE.CreateCompatibleDC(hdcWindow);

		bounds = new RECT();
		User32Extra.INSTANCE.GetWindowRect(hWnd, bounds);

		width = bounds.right - bounds.left;
		height = bounds.bottom - bounds.top;

		hBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(hdcWindow,
				width, height);

		hOld = GDI32.INSTANCE.SelectObject(hdcMemDC, hBitmap);
		GDI32Extra.INSTANCE.BitBlt(hdcMemDC, 0, 0, width, height, hdcWindow, 0,
				0, WinGDIExtra.SRCCOPY);

		GDI32.INSTANCE.SelectObject(hdcMemDC, hOld);
		GDI32.INSTANCE.DeleteDC(hdcMemDC);

	

		bmi = new BITMAPINFO();
		bmi.bmiHeader.biWidth = width;
		bmi.bmiHeader.biHeight = -height;
		bmi.bmiHeader.biPlanes = 1;
		bmi.bmiHeader.biBitCount = 32;
		bmi.bmiHeader.biCompression = WinGDI.BI_RGB;

		buffer = new Memory(width * height * 4);
		GDI32.INSTANCE.GetDIBits(hdcWindow, hBitmap, 0, height, buffer, bmi,
				WinGDI.DIB_RGB_COLORS);

		//BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//image.setRGB(0, 0, width, height, buffer.getIntArray(0, width * height), 0, width);
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		MemoryImageSource source = new MemoryImageSource(width, height,buffer.getIntArray(0, width * height), 0, width);
		image.getGraphics().drawImage(Toolkit.getDefaultToolkit().createImage(source), 0, 0, null);
		
		GDI32.INSTANCE.DeleteObject(hBitmap);
		User32.INSTANCE.ReleaseDC(hWnd, hdcWindow);

		
		needsInit = false;
		
	}
	public BufferedImage capture() {
		if (needsInit || (oldWidth != width || oldHeight != height)) {
			bufferedInit();
			return image;
		}
		hdcWindow = (User32Extra.INSTANCE).GetWindowDC(hWnd);
		hdcMemDC = GDI32.INSTANCE.CreateCompatibleDC(hdcWindow);		

		hBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(hdcWindow, width, height);

		hOld = GDI32.INSTANCE.SelectObject(hdcMemDC, hBitmap);
		GDI32Extra.INSTANCE.BitBlt(hdcMemDC, 0, 0, width, height, hdcWindow, 0, 0, WinGDIExtra.SRCCOPY);

		//GDI32.INSTANCE.SelectObject(hdcMemDC, hOld);
		//GDI32.INSTANCE.DeleteDC(hdcMemDC);


		GDI32.INSTANCE.GetDIBits(hdcWindow, hBitmap, 0, height, buffer, bmi,WinGDI.DIB_RGB_COLORS);
		
		MemoryImageSource source = new MemoryImageSource(width, height, buffer.getIntArray(0, width * height), 0, width);
		image.getGraphics().drawImage(Toolkit.getDefaultToolkit().createImage(source), 0, 0, null);
		
		GDI32.INSTANCE.DeleteObject(hBitmap);
		User32.INSTANCE.ReleaseDC(hWnd, hdcWindow);


		return image;

	}

	

	public VRDWindowInitOnce(String programTitleIn) {
		hWnd = User32.INSTANCE.FindWindow(null, programTitleIn);
		needsInit = true;
	}

	public BufferedImage getNewFrame() {
		return image = capture();
	}

}
