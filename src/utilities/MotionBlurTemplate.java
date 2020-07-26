package utilities; /**
 * Template by beesandbombs, from tutorial by necessarydisorder
 * https://necessarydisorder.wordpress.com/2018/07/02/getting-started-with-making-processing-gifs-and-using-the-beesandbombs-template/
 */

import processing.core.PApplet;

public class MotionBlurTemplate extends PApplet {

    public static void main(String[] args) {
        PApplet.main("utilities.MotionBlurTemplate");
    }

    private int[][] result;
    protected float t, c;

    public static float ease(float p) {
        return 3 * p * p - 2 * p * p * p;
    }

    public static float ease(float p, float g) {
        if (p < 0.5)
            return 0.5f * pow(2 * p, g);
        else
            return 1 - 0.5f * pow(2 * (1 - p), g);
    }

    public void draw() {
        if (!recording) {
            t = mouseX * 1.0f / width;
            c = mouseY * 1.0f / height;
            if (mousePressed) {
                println(c);
            }
            draw_();
        } else {
            for (int i = 0; i < width * height; i++) {
                for (int a = 0; a < 3; a++) {
                    result[i][a] = 0;
                }
            }

            c = 0;
            for (int sa = 0; sa < samplesPerFrame; sa++) {
                t = map(frameCount - 1 + sa * shutterAngle / samplesPerFrame, 0, numFrames, 0, 1);
                draw_();
                loadPixels();
                for (int i = 0; i < pixels.length; i++) {
                    result[i][0] += pixels[i] >> 16 & 0xff;
                    result[i][1] += pixels[i] >> 8 & 0xff;
                    result[i][2] += pixels[i] & 0xff;
                }
            }

            loadPixels();
            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = 0xff << 24 |
                        (int) (result[i][0] * 1.0 / samplesPerFrame) << 16 |
                        (int) (result[i][1] * 1.0 / samplesPerFrame) << 8 |
                        (int) (result[i][2] * 1.0 / samplesPerFrame);
            }
            updatePixels();

            saveFrame("output/fr####.png");
            System.out.println(frameCount + "/" + numFrames);
            if (frameCount == numFrames) exit();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    protected int samplesPerFrame = 5;        // How many samples are averaged into one frame
    protected int numFrames = 50;             // How long is the final animation going to be
    protected float shutterAngle = 0.9f;      // How much does a given frame affect the next one?
    protected boolean recording = true;      // Are you recording or testing?

    public void settings() {
        size(500, 500);
    }

    public void setup() {
        result = new int[width * height][3];
    }

    void draw_() {
        // Use this as draw. The actual draw function is just dealing with motion blur.
    }

}