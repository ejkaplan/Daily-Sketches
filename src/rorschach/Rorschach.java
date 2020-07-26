package rorschach;

import processing.core.PApplet;
import utilities.*;

public class Rorschach extends PApplet {

    private NoiseLoop noise;
    private float increment = 0.02f;
    private double gifFrames = 10 * 24;

    public static void main(String[] args) {
        PApplet.main("rorschach.Rorschach");
    }

    public void settings() {
        size(600, 600);
    }

    public void setup() {
        noise = new NoiseLoop(1);
    }

    public void draw() {
        if (frameCount > gifFrames) {
            exit();
            return;
        }
        double percent = frameCount / gifFrames;
        render(percent);
        saveFrame("src/rorschach/output/gif-" + nf(frameCount, 4) + ".png");
    }

    public void render(double percent) {
        background(0);
        loadPixels();
        for (int x = 0; x < width; x++) {
            double noiseX = x * increment;
            for (int y = 0; y < height; y++) {
                double noiseY = y * increment;
                int i = y * width + x;
                if (x <= 0.5 * width) {
                    double n = noise.eval(noiseX, noiseY, percent);
                    float b = n > 0 ? 255 : 0;
                    pixels[i] = color(b, b, b);
                } else {
                    pixels[i] = pixels[y * width + (width - x)];
                }
            }
        }
        updatePixels();
    }

}
