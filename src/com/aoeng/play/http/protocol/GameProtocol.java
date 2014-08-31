package com.aoeng.play.http.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.aoeng.play.domain.AppInfo;
import com.aoeng.play.utils.LogUtils;

public class GameProtocol extends BaseProtocol<List<AppInfo>> {

	@Override
	protected String getKey() {
		// TODO Auto-generated method stub
		return "game";
	}

	@Override
	protected List<AppInfo> parseFromJson(String json) {
		// TODO Auto-generated method stub
		try {
			List<AppInfo> infos = new ArrayList<AppInfo>();
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				AppInfo info = new AppInfo();
				info.setId(obj.getLong("id"));
				info.setName(obj.getString("name"));
				info.setPackageName(obj.getString("packageName"));
				info.setIconUrl(obj.getString("iconUrl"));
				info.setStars(Float.valueOf(obj.getString("stars")));
				info.setSize(obj.getLong("size"));
				info.setDownloadUrl(obj.getString("downloadUrl"));
				info.setDes(obj.getString("des"));
				infos.add(info);
			}
			return infos;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e(e);
			return null;
		}

	}

}
