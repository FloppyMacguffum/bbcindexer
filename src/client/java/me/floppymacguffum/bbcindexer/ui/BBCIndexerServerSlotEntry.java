/*
MIT License

Copyright (c) 2025 FloppyMacguffum

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

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;

import org.joml.Matrix3x2fStack;

import me.floppymacguffum.bbcindexer.util.BBCServer;

public class BBCIndexerServerSlotEntry extends AlwaysSelectedEntryListWidget.Entry<BBCIndexerServerSlotEntry> {
	private MinecraftClient mc;
	private BBCServer server;

	public BBCIndexerServerSlotEntry(BBCServer server) {
		this.mc = MinecraftClient.getInstance();
		this.server = server;
	}

	public Text getNarration() {
		return Text.of(server.getAddress().replaceAll("\\.", " dot ") + " port " + server.getPort());
	}

	@Override
	public void render(DrawContext context, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		final Matrix3x2fStack mat = context.getMatrices();
		mat.pushMatrix();
		mat.translate(x, y);
		String displayStr = server.getAddress() + ":" + server.getPort() + "\247r - " + server.getVersion() + "\247r - cracked: " + (server.getOfflineMode() ? "yes" : "no");
		context.drawCenteredTextWithShadow(mc.textRenderer, displayStr, width / 2, height / 2 - mc.textRenderer.fontHeight / 2, -1);
		mat.popMatrix();
	}

	public BBCServer getBBCServer() {
		return server;
	}
}
