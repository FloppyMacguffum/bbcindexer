/*
MIT License

Copyright (c) 2026 FloppyMacguffum

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package me.floppymacguffum.bbcindexer.ui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class BBCIndexerMainMenu extends Screen {
	private Screen parentScreen;

	public BBCIndexerMainMenu(Screen parentScreen) {
		super(Component.literal("Break Blocks Club Server Indexer Main Menu"));
		this.parentScreen = parentScreen;
	}

	protected void init() {
		super.init();
		addRenderableWidget(new BBCIndexerButton((width / 2) - 75, height / 2 - 12, Component.literal("Search Server List"), button -> minecraft.setScreen(new BBCIndexerSearchServers(this))));
		addRenderableWidget(new BBCIndexerButton((width / 2) - 75, height / 2 + 12, Component.literal("Browse Servers"), button -> minecraft.setScreen(new BBCIndexerServerBrowser(this, "", "", "", false, 1))));
		addRenderableWidget(new BBCIndexerButton(0, 0, 20, 20, Component.literal("<-"), button -> minecraft.setScreen(parentScreen)));
	}

	public void render(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
		super.render(context, mouseX, mouseY, deltaTicks);
		context.drawCenteredString(font, title, width / 2, 15, -1);
	}

	public void onClose() {
		minecraft.setScreen(parentScreen);
	}
}
