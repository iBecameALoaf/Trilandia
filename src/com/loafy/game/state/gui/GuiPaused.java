package com.loafy.game.state.gui;

import com.loafy.game.Main;
import com.loafy.game.state.GameState;
import com.loafy.game.state.IngameState;
import com.loafy.game.world.World;
import com.loafy.game.world.WorldLoader;

public class GuiPaused extends Gui {

    public GuiPaused (final IngameState state) {
        super(state, "Paused");

        float xOffset = 350;
        float space = 56;

        GuiButton resume = new GuiButton("Resume", xOffset + 0 * space) {

            public void action() {
                state.setCurrentGui(null);
            }
        };

        GuiButton audioSettings = new GuiButton("Audio Settings", xOffset + 1 * space) {

            public void action() {
                state.setCurrentGui(state.guiAudioSettings);
            }
        };

        GuiButton videoSettings = new GuiButton("Video Settings", xOffset + 2 * space) {

            public void action() {
                state.setCurrentGui(state.guiVideoSettings);
            }
        };

        GuiButton controls = new GuiButton("Controls", xOffset + 3 * space) {

            public void action() {
                state.setCurrentGui(state.guiControls);
            }
        };

        GuiButton exit = new GuiButton("Save and exit", xOffset + 4 * space) {

            public void action() {
                Main.setState(GameState.MENU);
                Main.menuState.setCurrentGui(Main.menuState.guiMainMenu);
                state.setCurrentGui(null);

                World world = state.getWorld();
                WorldLoader.save(world.getData(), world.getName(), "world.dat");
                state.getWorld().unload();

                state.generated = false;
                state.loaded = false;
            }
        };

        addButton(resume);
        addButton(audioSettings);
        addButton(videoSettings);
        addButton(controls);
        addButton(exit);
    }
}