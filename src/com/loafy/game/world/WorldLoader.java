package com.loafy.game.world;

import com.google.gson.Gson;
import com.loafy.game.resources.Resources;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.data.ChunkData;
import com.loafy.game.world.data.PlayerData;
import com.loafy.game.world.data.WorldData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class WorldLoader {



    public static void save(Object data, String worldName, String fileName) {
        try {
            Gson gson = new Gson();
            FileWriter writer = new FileWriter(WorldLoader.getWorldPath(worldName) + "\\" + fileName);
            gson.toJson(data, writer);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object load(Class clazz, String worldName, String fileName) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(WorldLoader.getWorldPath(worldName) + "\\" + fileName);
            Object data = gson.fromJson(reader, clazz);
            reader.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*public static void saveInfo(World world) {
        try {
            Gson gson = new Gson();
            WorldData data = world.getData();
            FileWriter writer = new FileWriter(WorldLoader.getWorldPath(data.name) + "\\world.dat");
            gson.toJson(data, writer);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WorldData loadInfo(String name) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(WorldLoader.getWorldPath(name) + "\\world.dat\\");
            WorldData data = gson.fromJson(reader, WorldData.class);
            reader.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void savePlayerData(World world) {
        try {
            Gson gson = new Gson();
            WorldData data = world.getData();
            FileWriter writer = new FileWriter(WorldLoader.getWorldPath(data.name) + "\\world.dat");
            gson.toJson(data, writer);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PlayerData loadPlayerData(String name) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(WorldLoader.getWorldPath(name) + "\\player.dat\\");
            PlayerData data = gson.fromJson(reader, PlayerData.class);
            reader.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public static Chunk fetchChunk(String worldName, int chunkX, int chunkY) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(WorldLoader.getWorldPath(worldName) + "\\chunkData\\chunk" + chunkX + "," + chunkY);
            ChunkData data = gson.fromJson(reader, ChunkData.class);
            reader.close();

            Block[][] blocks = new Block[Chunk.SIZE][Chunk.SIZE];
            Block[][] walls = new Block[Chunk.SIZE][Chunk.SIZE];

            for (int x = 0; x < data.blocks.length; x++) {
                for (int y = 0; y < data.blocks[0].length; y++) {
                    blocks[x][y] = new Block(Material.fromID(data.blocks[x][y]), ((chunkX * Chunk.SIZE) + x) * Material.SIZE, ((chunkY * Chunk.SIZE) + y) * Material.SIZE);
                }
            }

            for (int x = 0; x < data.walls.length; x++) {
                for (int y = 0; y < data.walls[0].length; y++) {
                    walls[x][y] = new Block(Material.fromID(data.walls[x][y]), ((chunkX * Chunk.SIZE) + x) * Material.SIZE, ((chunkY * Chunk.SIZE) + y) * Material.SIZE);
                }
            }

            return new Chunk(blocks, walls, chunkX, chunkY);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveChunk(String worldName, Chunk chunk, Gson gson) {
        try {
            String path = WorldLoader.getWorldPath(worldName) + "\\chunkData\\chunk" + chunk.getChunkX() + "," + chunk.getChunkY();
            File file = new File(path);

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            ChunkData data = chunk.getData();
            FileWriter writer = new FileWriter(path);
            gson.toJson(data, writer);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected static String getWorldPath(String worldName) {
        return Resources.gameLocation + "\\saves\\" + worldName;
    }
}