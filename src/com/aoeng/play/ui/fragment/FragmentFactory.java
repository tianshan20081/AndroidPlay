package com.aoeng.play.ui.fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author aoeng Aug 31, 2014 12:25:06 PM
 */
public class FragmentFactory {

	public static final int TAB_HOME = 0;
	public static final int TAB_APP = 1;
	public static final int TAB_GAME = 2;
	public static final int TAB_SUBJECT = 3;
	public static final int TAB_RECOMMEND = 4;
	public static final int TAB_CATEGORY = 5;
	public static final int TAB_TOP = 6;
	/** 记录所有的fragment，防止重复创建 */
	private static Map<Integer, BaseFragment> mFragmentMap = new HashMap<Integer, BaseFragment>();

	/** 采用工厂类进行创建Fragment，便于扩展，已经创建的Fragment不再创建 */
	public static BaseFragment createFragment(int index) {
		BaseFragment fragment = mFragmentMap.get(index);
		if (fragment == null) {
			switch (index) {
			case TAB_HOME:
				fragment = new HomeFragment();
				break;
			case TAB_APP:
				fragment = new AppFragment();
				break;
			case TAB_GAME:
				fragment = new GameFragment();
				break;
			case TAB_SUBJECT:
				fragment = new SubjectFragment();
				break;
			case TAB_RECOMMEND:
				fragment = new RecommendFragment();
				break;
			case TAB_CATEGORY:
				fragment = new CategoryFragment();
				break;
			case TAB_TOP:
				fragment = new HotFragment();
				break;
			}
			mFragmentMap.put(index, fragment);
		}
		return fragment;
	}

}
