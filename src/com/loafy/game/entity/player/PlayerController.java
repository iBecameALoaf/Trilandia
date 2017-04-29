package com.loafy.game.entity.player;

import com.loafy.game.input.Controls;
import com.loafy.game.input.InputManager;
import com.loafy.game.item.ItemStack;
import com.loafy.game.item.container.Inventory;
import com.loafy.game.world.Chunk;
import com.loafy.game.world.World;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.blocks.BlockChest;
import org.lwjgl.input.Keyboard;

public class PlayerController {

    private EntityPlayer player;
    private World world;

    /**
     * The current block that the player is destroying.
     */
    private Block dugBlock;

    public PlayerController(EntityPlayer player) {
        this.player = player;
        this.world = player.getWorld();
    }

    public void update(float delta) {
        int leftk = Controls.getControls().get("left");
        int rightk = Controls.getControls().get("right");
        player.left = Keyboard.isKeyDown(leftk);
        player.right = Keyboard.isKeyDown(rightk);

        if (Keyboard.isKeyDown(Controls.getControls().get("jump")))
            player.jump();

        if (InputManager.keyPressed(Controls.getControls().get("inventory"))) {
            player.toggleInventory();
        }

        handleItemActions(delta);
    }

    /**
     * Handle mouse actions on the selected item.
     */
    public void handleItemActions(float delta) {
        PlayerInventory inventory = player.getInventory();
        World world = player.getWorld();
        ItemStack itemstack = inventory.getSlots()[inventory.getHotbarSlot()].getItemStack();
        if (InputManager.mouse1) {
            itemstack.getItem().useLeft(player, delta);
        } else if (InputManager.mouse2p) {
            itemstack.getItem().useRight(player, delta);


            /*
            int mx = (int) (InputManager.mouseX + world.xOffset);
            int my = (int) (InputManager.mouseY + world.yOffset);

            Block block = world.getBlock(mx, my);

            if (block instanceof BlockChest) { //TODO literally just make a method inside the blocks when clicked. much easier.
                BlockChest chest = (BlockChest) block;

                player.openInventory();
                player.setActiveContainer(chest.getContainer()); // good
            } else {
                itemstack.getItem().useRight(player);
            }


            */
        }

        clearBlockDamage();
    }

    /**
     * Iterate through visible chunks and remove all block damage player inflicted.
     */
    public void clearBlockDamage() {  //TODO this may be inneficient because its updating like everything lol, also add to entityplayer class lol
        for (Chunk chunk : world.getActiveChunks()) {
            for (int x = 0; x < chunk.getBlocks().length; x++) {
                for (int y = 0; y < chunk.getBlocks()[x].length; y++) {
                    Block blocks = chunk.getBlocks()[x][y];
                    Block walls = chunk.getWalls()[x][y];
                    if (blocks != dugBlock) {
                        blocks.setHardness(blocks.getMaxHardness());

                    }

                    if (walls != dugBlock) {
                        walls.setHardness(walls.getMaxHardness());
                    }
                }
            }
        }

        dugBlock = null;
    }

    public Block getDugBlock() {
        return dugBlock;
    }

    public void setDugBlock(Block block) {
        this.dugBlock = block;
    }
}
