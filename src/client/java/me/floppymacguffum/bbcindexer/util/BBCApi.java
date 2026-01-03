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

import java.net.URI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import me.floppymacguffum.bbcindexer.ui.BBCIndexerServerBrowser;

public class BBCApi {
	public static void getBBCServers(BBCIndexerServerBrowser callbackScreen, String motd, String version, String region, boolean cracked, int page) {
		String encodedMotd = MiscUtils.encodeHTTPString(motd);
		String encodedVersion = MiscUtils.encodeHTTPString(version);
		String encodedRegion = MiscUtils.encodeHTTPString(region);
		String pageParam = "" + page;
		String crackedString = cracked ? "on" : "off";
		String queryStr = "?motd=" + encodedMotd + "&version=" + encodedVersion + "&region=" + encodedRegion + "&page=" + pageParam + "&offlineOnly=" + crackedString + "&limit=20";
		String url = "https://api.breakblocks.com/api/v0.1/servers/find" + queryStr;
		final StupidBBCServersHack bbcServers = new StupidBBCServersHack(null);
		callbackScreen.setBBCServers(bbcServers.servers);
		new Thread(() -> {
			try {
				HttpURLConnection request = (HttpURLConnection) new URI(url).toURL().openConnection();
				request.setRequestMethod("GET");
				request.setDoInput(true);
				BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
				String ln, txt = "";
				while((ln = br.readLine()) != null) {
					txt += ln;
				}
				br.close();
				JSONObject jso = null;
				try {
					jso = new JSONObject(txt);
				} catch(Exception e) {
					e.printStackTrace();
					callbackScreen.setErroredOut(true);
					return;
				}
				JSONArray ja = null;
				try {
					ja = jso.getJSONArray("results");
				} catch(Exception e) {
					e.printStackTrace();
					callbackScreen.setErroredOut(true);
					return;
				}
				int elements = ja.length();
				bbcServers.servers = new BBCServer[elements];
				for(int i = 0; i < elements; i++)
				{
					JSONObject jobj = null;
					try {
						jobj = ja.getJSONObject(i);
						bbcServers.servers[i] = new BBCServer(jobj);
					} catch(Exception e) {
						e.printStackTrace();
						callbackScreen.setErroredOut(true);
						return;
					}
				}
				callbackScreen.setBBCServers(bbcServers.servers);
			} catch(Exception e) {
				e.printStackTrace();
				callbackScreen.setErroredOut(true);
				return;
			}
		}).start();
	}
}
