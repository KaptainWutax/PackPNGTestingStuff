package packpng;

import kaptainwutax.seedutils.lcg.rand.JRand;

import java.util.Arrays;

public class NoiseGeneratorOctaves {
	private final NoiseGeneratorPerlin[] noiseArray;
	private final int octaves;

	public NoiseGeneratorOctaves(JRand rand, int nbOctaves) {
		octaves = nbOctaves;
		noiseArray = new NoiseGeneratorPerlin[nbOctaves];

		for(int octave = 0; octave < nbOctaves; octave++) {
			noiseArray[octave] = new NoiseGeneratorPerlin(rand);
		}
	}

	public double func_647_a(double x, double z) {
		double d2 = 0.0D;
		double d3 = 1.0D;

		for(NoiseGeneratorPerlin perlin: this.noiseArray) {
			d2 += perlin.func_642_a(x * d3, z * d3) / d3;
			d3 /= 2D;
		}

		return d2;
	}

	public double[] generateNoise(double[] buffer, double x, double y, double z, int sizeX, int sizeY, int sizeZ, double offsetX, double offsetY, double offsetZ) {
		if(buffer == null) {
			buffer = new double[sizeX * sizeY * sizeZ];
		} else {
			Arrays.fill(buffer, 0.0D);
		}

		double octavesFactor = 1.0D;
		// we care only about 315 332 400 417 316 333 401 and 418
		for(int octave = 0; octave < octaves; octave++) {
			noiseArray[octave].generatePermutations(buffer, x, y, z, sizeX, sizeY, sizeZ, offsetX * octavesFactor, offsetY * octavesFactor, offsetZ * octavesFactor, octavesFactor);
			octavesFactor /= 2D;
		}

		return buffer;
	}

	public double[] generateFixedNoise(double[] buffer, int x, int z, int sizeX, int sizeZ, double offsetX, double offsetZ) {
		return generateNoise(buffer, x, 10D, z, sizeX, 1, sizeZ, offsetX, 1.0D, offsetZ);
	}

}
