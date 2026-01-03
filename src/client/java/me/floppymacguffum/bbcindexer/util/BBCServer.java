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
package me.floppymacguffum.bbcindexer.util;

import org.json.JSONObject;
import org.json.JSONException;

public class BBCServer {
	private String address;
	private int port;
	private boolean offlineMode;
	private String version;

	public BBCServer(JSONObject jo) throws JSONException {
		this.address = jo.getString("address");
		this.port = jo.getInt("port");
		this.offlineMode = jo.getInt("offline_mode") == 0 ? false : true;
		this.version = jo.getString("version");
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public boolean getOfflineMode() {
		return offlineMode;
	}

	public String getVersion() {
		return version;
	}
}
