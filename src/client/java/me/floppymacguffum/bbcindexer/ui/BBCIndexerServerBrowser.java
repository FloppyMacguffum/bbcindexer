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

import net.minecraft.text.Text;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.AddServerScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;

import me.floppymacguffum.bbcindexer.util.BBCServer;
import me.floppymacguffum.bbcindexer.util.BBCApi;

public class BBCIndexerServerBrowser extends Screen {
	private Screen parentScreen;
	private BBCIndexerButton bbcBackButton, bbcNextButton, bbcAddServerButton, bbcJoinServerButton;
	private BBCServer[] bbcServers;
	private BBCIndexerServerSlotList bbcServerListSlots;
	private ServerInfo globServerInfo;
	private String motd, version, region;
	private int page;
	private boolean needsUpdating, erroredOut, cracked;

	public BBCIndexerServerBrowser(Screen parentScreen, String motd, String version, String region, boolean cracked, int page) {
		super(Text.literal("Break Blocks Club Server Browser"));
		this.parentScreen = parentScreen;
		this.bbcServers = bbcServers;
		this.motd = motd;
		this.version = version;
		this.region = region;
		this.page = page;
		this.bbcServers = null;
		BBCApi.getBBCServers(this, motd, version, region, cracked, page);
		this.needsUpdating = false;
		this.erroredOut = false;
	}

	protected void init() {
		super.init();
		addDrawableChild(new BBCIndexerButton(-69, 0, 0, 20, 20, Text.literal("<-"), button -> client.setScreen(parentScreen)));
		bbcBackButton = new BBCIndexerButton(-420, 0, height - 20, 60, 20, Text.literal("Back"), button -> previousPage());
		bbcBackButton.active = (bbcServers != null && page >= 2);
		addDrawableChild(bbcBackButton);
		bbcNextButton = new BBCIndexerButton(-69420, width - 60, height - 20, 60, 20, Text.literal("Next"), button -> nextPage());
		bbcNextButton.active = (bbcServers != null && bbcServers.length > 19);
		addDrawableChild(bbcNextButton);
		bbcAddServerButton = new BBCIndexerButton(-28980, width - 166, height - 20, 100, 20, Text.literal("Add to my servers"), button -> addToServerList());
		bbcJoinServerButton = new BBCIndexerButton(-4761, 66, height - 20, 100, 20, Text.literal("Join server"), button -> joinServer());
		bbcServerListSlots = new BBCIndexerServerSlotList(client, width, height, 6 + (textRenderer.fontHeight + 2) * 3, 30, textRenderer.fontHeight + 4);
		addDrawableChild(bbcServerListSlots);
		if(bbcServers != null) {
			for(BBCServer srv : bbcServers) {
				bbcServerListSlots.addEntryToList(new BBCIndexerServerSlotEntry(srv));
			}
		}
		bbcAddServerButton.active = bbcServerListSlots.getSelectedOrNull() != null;
		bbcJoinServerButton.active = bbcServerListSlots.getSelectedOrNull() != null;
		addDrawableChild(bbcAddServerButton);
		addDrawableChild(bbcJoinServerButton);
	}

	public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
		super.render(context, mouseX, mouseY, deltaTicks);
		bbcBackButton.active = (bbcServers != null && page >= 2);
		bbcNextButton.active = (bbcServers != null && bbcServers.length > 19);
		bbcAddServerButton.active = bbcServerListSlots.getSelectedOrNull() != null;
		bbcJoinServerButton.active = bbcServerListSlots.getSelectedOrNull() != null;
		context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 7, -1);
		context.drawCenteredTextWithShadow(textRenderer, Text.literal("Page: " + (erroredOut ? "\247cError" : (bbcServers == null ? "Loading..." : page))), width / 2, 20, -1);
		if(needsUpdating) {
			bbcServerListSlots.clearAllEntries();
			for(BBCServer srv : bbcServers) {
				bbcServerListSlots.addEntryToList(new BBCIndexerServerSlotEntry(srv));
			}
			needsUpdating = false;
		}
	}

	public void close() {
		client.setScreen(parentScreen);
	}

	private void nextPage() {
		page++;
		bbcServerListSlots.clearAllEntries();
		BBCApi.getBBCServers(this, motd, version, region, cracked, page);
	}

	private void previousPage() {
		page--;
		bbcServerListSlots.clearAllEntries();
		BBCApi.getBBCServers(this, motd, version, region, cracked, page);
	}

	private void addToServerList() {
		String base = bbcServerListSlots.getSelectedOrNull().getBBCServer().getAddress();
		int port = bbcServerListSlots.getSelectedOrNull().getBBCServer().getPort();
		globServerInfo = new ServerInfo("BBC Server", base + ":" + port, ServerInfo.ServerType.OTHER);
		client.setScreen(new AddServerScreen(this, Text.literal("Add BBC Server"), this::actuallyAddServer, globServerInfo));
	}

	private void actuallyAddServer(boolean confirmation) {
		if(confirmation) {
			ServerInfo si = new ServerInfo("", "", ServerInfo.ServerType.OTHER);
			si.copyFrom(globServerInfo);
			ServerList sl = new ServerList(client);
			sl.loadFile();
			sl.add(si, false);
			sl.saveFile();
		}
		client.setScreen(this);
	}

	private void joinServer() {
		String base = bbcServerListSlots.getSelectedOrNull().getBBCServer().getAddress();
		int port = bbcServerListSlots.getSelectedOrNull().getBBCServer().getPort();
		ServerAddress sa = new ServerAddress(base, port);
		ServerInfo si = new ServerInfo("BBC Server", base + ":" + port, ServerInfo.ServerType.OTHER);
		ConnectScreen.connect(this, client, sa, si, false, null);
	}

	public void setBBCServers(BBCServer[] bbcServers) {
		this.bbcServers = bbcServers;
		if(bbcServers != null) {
			if(bbcServerListSlots == null) {
				needsUpdating = true;
				return;
			}
			bbcServerListSlots.clearAllEntries();
			for(BBCServer srv : bbcServers) {
				bbcServerListSlots.addEntryToList(new BBCIndexerServerSlotEntry(srv));
			}
		}
	}

	public void setErroredOut(boolean erroredOut) {
		this.erroredOut = erroredOut;
	}
}
