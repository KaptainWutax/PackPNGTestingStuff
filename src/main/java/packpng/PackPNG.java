package packpng;

import kaptainwutax.seedutils.lcg.LCG;
import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.util.math.Mth;

public class PackPNG {

	public static final LCG DUNGEON_SKIP = LCG.JAVA.combine(40); //Dungeons: 40 calls (if all attempts fail)
	public static final LCG DIRT_SKIP = LCG.JAVA.combine(1400); //Dirt: 1400 calls (some nextInt(3)s might double skip)
	public static final LCG GRAVEL_SKIP = LCG.JAVA.combine(700); //Gravel: 700 calls (some nextInt(3)s might double skip)
	public static final LCG COAL_SKIP = LCG.JAVA.combine(760); //Coal Ore: 760 calls (some nextInt(3)s might double skip)
	public static final LCG IRON_SKIP = LCG.JAVA.combine(440); //Iron Ore(?): 440 calls (some nextInt(3)s might double skip)
	public static final LCG GOLD_SKIP = LCG.JAVA.combine(44); //Gold Ore(?): 44 calls (some nextInt(3)s might double skip)
	public static final LCG REDSTONE_SKIP = LCG.JAVA.combine(160); //Redstone Ore: 160 calls (some nextInt(3)s might double skip)
	public static final LCG DIAMOND_SKIP = LCG.JAVA.combine(20); //Diamond Ore(?): 20 calls (some nextInt(3)s might double skip)

	public static final LCG PRE_TREE_SKIP = LCG.combine(DUNGEON_SKIP, DIRT_SKIP, GRAVEL_SKIP, COAL_SKIP, IRON_SKIP, GOLD_SKIP, REDSTONE_SKIP, DIAMOND_SKIP);

	public static final LCG OCTAVES_SKIP = LCG.JAVA.combine(16 * 262 + 16 * 262 + 8 * 262 + 4 * 262 + 4 * 262 + 10 * 262 + 16 * 262);
	public static final LCG SKIP_387 = LCG.JAVA.combine(387);
	public static final LCG SKIP_830 = LCG.JAVA.combine(830);

	public static void main(String[] args) {
		ChunkRand rand = new ChunkRand();
		long lastTime = System.nanoTime();

		for(long structureSeed = 0; structureSeed < 1L << 48; structureSeed++) {
			testDecorators(structureSeed, 6, 9, 6, 42, 9, rand);

			if((structureSeed & Mth.mask(20)) == 0) {
				long time = System.nanoTime();
				System.out.println((double)(time - lastTime) / 1_000_000_000);
				lastTime = time;
			}
		}
	}

	public static boolean testDecorators(long worldSeed, int chunkX, int chunkZ, int waterfallX, int waterfallY, int waterfallZ, ChunkRand rand) {
		waterfallX &= 15; waterfallZ &= 15;
		rand.setPopulationSeed(worldSeed, chunkX, chunkZ, MCVersion.v1_7);

		rand.advance(PRE_TREE_SKIP);

		NoiseGeneratorOctaves octaves = new NoiseGeneratorOctaves(new JRand(OCTAVES_SKIP.nextSeed(worldSeed ^ LCG.JAVA.multiplier)), 8);

		//+5 for forest, +2 for seasonal forest
		int attempts = 2;
		attempts += (int)((octaves.func_647_a((double)(chunkX << 4) * 0.5D, (double)(chunkX << 4) * 0.5D) / 8D + rand.nextDouble() * 4D + 4D) / 3D);
		if(rand.nextInt(10) == 0)attempts++;

		if(!TREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE(attempts))return false;

		postTreeSkip(rand);

		for(int i = 0; i < 50; i++) {
			int x = rand.next(4);
			int y = rand.nextInt(120) + 8;
			int z = rand.next(4);

			if(waterfallY == y && waterfallX == x && waterfallZ == z) {
				return true;
			}
		}

		return false;
	}

	private static boolean TREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE(int attempts) {
		return true;
	}

	private static void postTreeSkip(ChunkRand rand) {
		rand.advance(SKIP_387);
		if(rand.next(1) == 0)rand.advance(SKIP_387);
		if(rand.next(2) == 0)rand.advance(SKIP_387);
		if(rand.next(3) == 0)rand.advance(SKIP_387);
		rand.advance(SKIP_830);
		if(rand.next(5) == 0)rand.advance(SKIP_387);
	}

}
