package openGL;

import java.awt.image.BufferedImage;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

import tools.FPSCounter;
import displayInput.VRDWindow;
import static org.lwjgl.opengl.GL11.*;

public class OpenGLEngine {

	static VRDWindow windowCatcher;
	static FPSCounter fpsTracker;

	public static void main(String[] args) throws Exception {
		Display.setDisplayMode(new DisplayMode(1080, 720));
		Display.create();
		fpsTracker = new FPSCounter();
		fpsTracker.getFPS();
		while (!Display.isCloseRequested()) {
			setDisplayWindow();
			drawBackground();
			drawWindowIn();
			Display.update();
			fpsTracker.fpsTracker();
			System.out.println(fpsTracker.getAdvFPS());
			// Display.sync(60); // caps FPS

		}

		Display.destroy();

	}

	public static void setDisplayWindow() {

		// Clear Screen
		glClear(GL_COLOR_BUFFER_BIT);
		// Modify Projection
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 0, 480, -1, 1);

		// Modify View

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

	}

	public static void drawWindowIn() throws Exception {
		windowCatcher = new VRDWindow("Steam");
		
		// InternalTextureLoader.createTextureID();
		glEnable(GL_TEXTURE_2D);
		Texture liveTexture = BufferedImageUtil.getTexture("liveWindowTexture",
				windowCatcher.getNewFrame());
		// System.out.println(liveTexture.getImageHeight());
		glBindTexture(GL_TEXTURE_2D, liveTexture.getTextureID());

		glBegin(GL_QUADS); // liveTexture.getImageHeight()
							// liveTexture.getImageWidth()
		glTexCoord2d(0.0, 0.0); // Reed says that this is really slow and bad, and that we should use buffer image (idk what that is)
		glVertex2i(0, liveTexture.getImageHeight());
		glTexCoord2d(1.0, 0.0);
		glVertex2i(liveTexture.getImageWidth(), liveTexture.getImageHeight());
		glTexCoord2d(1.0, 1.0);
		glVertex2i(liveTexture.getImageWidth(), 0);
		glTexCoord2d(0.0, 1.0);
		glVertex2i(0, 0);
		glEnd();

		glDisable(GL_TEXTURE_2D);
	}

	public static void drawBackground() {
		glBegin(GL_QUADS);

		glColor3d(.4, .4, .4);
		glVertex2d(0, 0);
		glVertex2d(Display.getWidth(), 0);
		glVertex2d(Display.getWidth(), Display.getHeight());
		glVertex2d(0, Display.getHeight());

		glEnd();

	}
}
