package displayInput;
import java.util.ArrayList;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.win32.StdCallLibrary;

public class HWNDTools {
	//ArrayList<HWND> hWndsFound;
	public interface User32 extends StdCallLibrary {
		User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

		boolean EnumWindows(WinUser.WNDENUMPROC lpEnumFunc, Pointer arg);

		int GetWindowTextA(HWND hWnd, byte[] lpString, int nMaxCount);
	}

	public static ArrayList<HWND> getOpenWindowsHandels() {
		return GethWndWindows();
	}

	public static void main(String[] args) {
		GethWndWindows();
	}

	public static ArrayList<HWND> GethWndWindows() {
		final ArrayList<HWND> ArrayListToReturn = new ArrayList<HWND>() ;
		final User32 user32 = User32.INSTANCE;
		user32.EnumWindows(new WNDENUMPROC() {
			int count = 0;

			@Override
			public boolean callback(HWND hWnd, Pointer arg1) {
				byte[] windowText = new byte[512];
				user32.GetWindowTextA(hWnd, windowText, 512);
				String wText = Native.toString(windowText);

				// get rid of this if block if you want all windows regardless
				// of whether
				// or not they have text
				if (wText.isEmpty()) {
					return true;
				}
				ArrayListToReturn.add(hWnd);

				System.out.println("Found window with text " + hWnd
						+ ", total " + ++count + " Text: " + wText);
				return true;
			}
		}, null);
		return ArrayListToReturn;
	}
	public static String getWindowTitle(HWND hWndIn){
		
		User32 user32 = User32.INSTANCE;
		byte[] windowText = new byte[512];
		user32.GetWindowTextA(hWndIn, windowText, 512);
		
		return Native.toString(windowText);
		
	}
}