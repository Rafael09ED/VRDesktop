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

public class VRDWindow {
	private HWND hWnd;

	public BufferedImage capture() {

		HDC hdcWindow = (User32Extra.INSTANCE).GetWindowDC(hWnd);
		HDC hdcMemDC = GDI32.INSTANCE.CreateCompatibleDC(hdcWindow);

		RECT bounds = new RECT();
		User32Extra.INSTANCE.GetWindowRect(hWnd, bounds);

		int width = bounds.right - bounds.left;
		int height = bounds.bottom - bounds.top;

		HBITMAP hBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(hdcWindow,
				width, height);

		HANDLE hOld = GDI32.INSTANCE.SelectObject(hdcMemDC, hBitmap);
		GDI32Extra.INSTANCE.BitBlt(hdcMemDC, 0, 0, width, height, hdcWindow, 0,
				0, WinGDIExtra.SRCCOPY);

		GDI32.INSTANCE.SelectObject(hdcMemDC, hOld);
		GDI32.INSTANCE.DeleteDC(hdcMemDC);

	

		BITMAPINFO bmi = new BITMAPINFO();
		bmi.bmiHeader.biWidth = width;
		bmi.bmiHeader.biHeight = -height;
		bmi.bmiHeader.biPlanes = 1;
		bmi.bmiHeader.biBitCount = 32;
		bmi.bmiHeader.biCompression = WinGDI.BI_RGB;

		
		// Same error as exhibited in VRDWindowInitOnce. This code should be unified, it's mostly the same...
		//System.out.println("bounds.right:" + bounds.right + ", bounds.left:" + bounds.left);
		//System.out.println("bounds.bottom:" + bounds.bottom + ", bounds.top:" + bounds.top);
		//System.out.println("width:" + width + ", height:" + height);
				
		Memory buffer = new Memory(width * height * 4);
		GDI32.INSTANCE.GetDIBits(hdcWindow, hBitmap, 0, height, buffer, bmi,
				WinGDI.DIB_RGB_COLORS);

		//BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//image.setRGB(0, 0, width, height, buffer.getIntArray(0, width * height), 0, width);
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		MemoryImageSource source = new MemoryImageSource(width, height,buffer.getIntArray(0, width * height), 0, width);
		image.getGraphics().drawImage(Toolkit.getDefaultToolkit().createImage(source), 0, 0, null);
		
		GDI32.INSTANCE.DeleteObject(hBitmap);
		User32.INSTANCE.ReleaseDC(hWnd, hdcWindow);

		return image;

	}

	BufferedImage image;

	public VRDWindow(String programTitleIn) {
		hWnd = User32.INSTANCE.FindWindow(null, programTitleIn);
	}

	public BufferedImage getNewFrame() {
		return image = capture();
	}

}
