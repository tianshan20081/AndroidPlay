package com.aoeng.play.http.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.aoeng.play.domain.UserInfo;
import com.aoeng.play.utils.LogUtils;

/**
 * 
 * @author aoeng Aug 31, 2014 12:16:02 PM
 */
public class UserProtocol extends BaseProtocol<List<UserInfo>> {

	@Override
	protected String getKey() {
		// TODO Auto-generated method stub
		return "user";
	}

	@Override
	protected List<UserInfo> parseFromJson(String json) {
		// TODO Auto-generated method stub
		try {
			List<UserInfo> infos = new ArrayList<UserInfo>();
			JSONObject obj = new JSONObject(json);
			UserInfo info = new UserInfo();
			info.setName(obj.getString("name"));
			info.setEmail(obj.getString("email"));
			info.setUrl(obj.getString("url"));
			infos.add(info);
			return infos;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e(e);
			return null;
		}
	}

}
