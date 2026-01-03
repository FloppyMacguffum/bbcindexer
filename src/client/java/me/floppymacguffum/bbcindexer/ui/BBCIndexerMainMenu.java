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

import net.minecraft.text.Text;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;

public class BBCIndexerMainMenu extends Screen {
	private Screen parentScreen;

	public BBCIndexerMainMenu(Screen parentScreen) {
		super(Text.literal("Break Blocks Club Server Indexer Main Menu"));
		this.parentScreen = parentScreen;
	}

	protected void init() {
		super.init();
		addDrawableChild(new BBCIndexerButton((width / 2) - 75, height / 2 - 12, Text.literal("Search Server List"), button -> client.setScreen(new BBCIndexerSearchServers(this))));
		addDrawableChild(new BBCIndexerButton((width / 2) - 75, height / 2 + 12, Text.literal("Browse Servers"), button -> client.setScreen(new BBCIndexerServerBrowser(this, "", "", "", false, 1))));
		addDrawableChild(new BBCIndexerButton(0, 0, 20, 20, Text.literal("<-"), button -> client.setScreen(parentScreen)));
	}

	public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
		super.render(context, mouseX, mouseY, deltaTicks);
		context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 15, -1);
	}

	public void close() {
		client.setScreen(parentScreen);
	}
}
