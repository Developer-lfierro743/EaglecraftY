package com.xojangstudios.eaglecrafty.world;

import java.util.Random;


public class WorldGenerator {
    private static final int OCTAVES = 4;
    private static final double PERSISTENCE = 0.5;
    private static final double SCALE = 0.01;

    private final PerlinNoise perlinNoise;

    public WorldGenerator(long seed) {
        this.perlinNoise = new PerlinNoise(seed);
    }

    public double[][] generateWorld(int width, int height) {
        double[][] world = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                world[x][y] = generateHeight(x, y);
            }
        }

        return world;
    }

    private double generateHeight(int x, int y) {
        double total = 0;
        double frequency = SCALE;
        double amplitude = 1;

        for (int i = 0; i < OCTAVES; i++) {
            total += perlinNoise.noise(x * frequency, y * frequency) * amplitude;
            amplitude *= PERSISTENCE;
            frequency *= 2;
        }

        return total;
    }

    private static class PerlinNoise {
        private final int[] permutation;
        private final int[] p;

        public PerlinNoise(long seed) {
            permutation = new int[256];
            p = new int[512];
            Random random = new Random(seed);

            for (int i = 0; i < 256; i++) {
                permutation[i] = i;
            }

            for (int i = 0; i < 256; i++) {
                int j = random.nextInt(256 - i) + i;
                int tmp = permutation[i];
                permutation[i] = permutation[j];
                permutation[j] = tmp;
                p[i] = p[i + 256] = permutation[i];
            }
        }

        public double noise(double x, double y) {
            int X = (int) Math.floor(x) & 255;
            int Y = (int) Math.floor(y) & 255;

            x -= Math.floor(x);
            y -= Math.floor(y);

            double u = fade(x);
            double v = fade(y);

            int aa = p[p[X] + Y];
            int ab = p[p[X] + Y + 1];
            int ba = p[p[X + 1] + Y];
            int bb = p[p[X + 1] + Y + 1];

            return lerp(v, lerp(u, grad(p[aa], x, y), grad(p[ba], x - 1, y)),
                    lerp(u, grad(p[ab], x, y - 1), grad(p[bb], x - 1, y - 1)));
        }

        private double fade(double t) {
            return t * t * t * (t * (t * 6 - 15) + 10);
        }

        private double lerp(double t, double a, double b) {
            return a + t * (b - a);
        }

        private double grad(int hash, double x, double y) {
            int h = hash & 15;
            double u = h < 8 ? x : y;
            double v = h < 4 ? y : h == 12 || h == 14 ? x : 0;
            return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
        }
    }
}