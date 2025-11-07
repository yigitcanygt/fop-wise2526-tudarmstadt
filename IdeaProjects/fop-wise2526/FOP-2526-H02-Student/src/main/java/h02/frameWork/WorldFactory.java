package h02.frameWork;

public class WorldFactory {

    /**
     * creates an instance of a world based on the given type and size. Either
     * - random world
     * - obstacle world
     *
     * @param worldType world type
     * @param worldSize world size
     * @return a world
     */
    public static AbstractWorld createWorld(WorldType worldType, int worldSize){
        return switch (worldType) {
            case WALL_WORLD -> new WallWorld(worldSize);
            case BLOCK_WORLD -> new BlockWorld(worldSize);
        };
    }
}

