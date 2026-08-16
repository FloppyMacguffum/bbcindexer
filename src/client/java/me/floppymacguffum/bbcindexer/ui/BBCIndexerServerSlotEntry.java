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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;

import org.joml.Matrix3x2fStack;

import me.floppymacguffum.bbcindexer.util.BBCServer;

public class BBCIndexerServerSlotEntry extends ObjectSelectionList.Entry<BBCIndexerServerSlotEntry> {
	private Minecraft mc;
	private BBCServer server;
	private BBCIndexerServerSlotList bbcIndexerServerSlotList;

	public BBCIndexerServerSlotEntry(BBCServer server, BBCIndexerServerSlotList bbcIndexerServerSlotList) {
		this.mc = Minecraft.getInstance();
		this.server = server;
		this.bbcIndexerServerSlotList = bbcIndexerServerSlotList;
	}

	public Component getNarration() {
		return Component.nullToEmpty(server.getAddress().replaceAll("\\.", " dot ") + " port " + server.getPort());
	}

	@Override
	public void extractContent(GuiGraphicsExtractor context, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		final Matrix3x2fStack mat = context.pose();
		mat.pushMatrix();
		mat.translate(getContentX(), getContentY());
		String displayStr = server.getAddress() + ":" + server.getPort() + "\247r - " + server.getVersion() + "\247r - cracked: " + (server.getOfflineMode() ? "yes" : "no");
		context.centeredText(mc.font, displayStr, getWidth() / 2, getContentHeight() / 2 - mc.font.lineHeight / 2, -1);
		mat.popMatrix();
	}

	@Override
	public boolean isMouseOver(final double mx, final double my) {
		int startX = 0;
		int endX = mc.gui.screen.width + (bbcIndexerServerSlotList.maxScrollAmount() > 0 ? -6 : -1);
		int startY = getContentY();
		int endY = getContentY() + getContentHeight();
		return mx >= startX && mx < endX && my >= startY && my < endY;
	}

	public BBCServer getBBCServer() {
		return server;
	}
}
