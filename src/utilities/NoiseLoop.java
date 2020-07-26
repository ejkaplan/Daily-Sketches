package utilities;

public class NoiseLoop {

    private OpenSimplexNoise noise;
    private double radius, centerX, centerY;

    public NoiseLoop(double radius, double centerX, double centerY) {
        noise = new OpenSimplexNoise();
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public NoiseLoop(double radius) {
        this(radius, Math.random() * 99999, Math.random() * 99999);
    }

    public static double map(double val, double startOrig, double endOrig, double startTarget, double endTarget) {
        double p = (val - startOrig) / (endOrig - startOrig);
        return startTarget + p * (endTarget - startTarget);
    }

    /**
     * Generate a random value by tracing a circle in 2D noise space
     *
     * @param percent
     * @return
     */
    public double eval(double percent) {
        double angle = 2 * Math.PI * percent;
        double xOff = map(Math.cos(angle), -1, 1, centerX - radius, centerX + radius);
        double yOff = map(Math.sin(angle), -1, 1, centerY - radius, centerY + radius);
        return noise.eval(xOff, yOff);
    }

    /**
     * Generate a random value by tracing a circle in 4D noise space. SLOW - ONLY USE FOR PRE-RENDERED STUFF.
     *
     * @param x
     * @param y
     * @param percent
     * @return
     */
    public double eval(double x, double y, double percent) {
        double angle = 2 * Math.PI * percent;
        double uOff = map(Math.cos(angle), -1, 1, centerX - radius, centerX + radius);
        double vOff = map(Math.sin(angle), -1, 1, centerY - radius, centerY + radius);
        return noise.eval(x, y, uOff, vOff);
    }

}
