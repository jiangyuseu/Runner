package com.auto.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.core.UiWatcher;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.auto.input.Utf7ImeHelper;

public class Runner extends UiAutomatorTestCase {

	public void testDemo() throws UiObjectNotFoundException, IOException,
			InterruptedException {

		// findAndRunApp();

		login();

		uploadVideo();
		
		logout();

	}

	//退出登录
	private void logout() throws UiObjectNotFoundException {
		UiObject settings = new UiObject(new UiSelector()
						.resourceId("com.youku.phone:id/usercenter_btn_settings"));
		settings.clickAndWaitForNewWindow();
		
		UiScrollable content = new UiScrollable(
				new UiSelector().scrollable(true));
		content.setAsVerticalList();
		content.scrollIntoView(new UiSelector().resourceId("com.youku.phone:id/exit"));
		
		UiObject exit = new UiObject(new UiSelector().resourceId("com.youku.phone:id/exit"));
		exit.clickAndWaitForNewWindow();
		
		UiObject positive = new UiObject(new UiSelector().resourceId("com.youku.phone:id/negtive_btn_layout"));
		positive.clickAndWaitForNewWindow();
		
		pressBack(1);
	}

	// 视频上传
	private void uploadVideo() throws UiObjectNotFoundException {
		// 选择默认视频夹中的视频
		UiObject videoFile = new UiObject(
				new UiSelector()
						.resourceId("com.youku.phone:id/message_center_nav_item2"));
		videoFile.click();

		UiScrollable videos = new UiScrollable(
				new UiSelector().scrollable(true));
		videos.setAsVerticalList();

		UiObject item = videos.getChildByText(
				new UiSelector().className("android.widget.TextView"), "默认视频夹");
		item.clickAndWaitForNewWindow();
		// 添加本地视频到默认视频夹
		UiObject uploadVideo = new UiObject(
				new UiSelector().resourceId("com.youku.phone:id/upload_video"));
		if (uploadVideo.exists()) {
			uploadVideo.clickAndWaitForNewWindow();
		} else {
			// 已上传过视频，通过其他入口进入视频列表
			UiObject uploadVideo2 = new UiObject(new UiSelector().text("添加视频"));
			uploadVideo2.clickAndWaitForNewWindow();
		}

		// 选择一个视频
		UiObject video = new UiObject(
				new UiSelector()
						.resourceId("com.youku.phone:id/media_picker_item_image_thumbnail"));
		video.clickAndWaitForNewWindow();
		// 下一步
		UiObject next = new UiObject(
				new UiSelector()
						.resourceId("com.youku.phone:id/video_cut_next"));
		next.clickAndWaitForNewWindow();
		// 视频上传
		UiObject uploadToYouku = new UiObject(
				new UiSelector().resourceId("com.youku.phone:id/video_upload"));
		uploadToYouku.clickAndWaitForNewWindow();
		
		sleepForTime(30000);
		
		pressBack(2);
		
	}

	// youku账号登录
	private void login() throws UiObjectNotFoundException {
		// 点击我的 按钮
		UiObject myBtn = new UiObject(new UiSelector().text("我的").className(
				"android.widget.TextView"));
		myBtn.click();

		sleepForTime(3000);

		// 点击我的视频
		UiObject loginBtn = new UiObject(new UiSelector().text("我的视频")
				.className("android.widget.TextView"));
		loginBtn.clickAndWaitForNewWindow(3000);

		// 输入用户名和密码
		UiObject inputPhone = new UiObject(
				new UiSelector().resourceId("com.youku.phone:id/login_name"));
		inputPhone.setText(Utf7ImeHelper.e("15151860255"));

		sleepForTime(1000);

		UiObject passEdit = new UiObject(
				new UiSelector().resourceId("com.youku.phone:id/login_pwd"));
		passEdit.setText(Utf7ImeHelper.e("jiangyu123"));

		sleepForTime(1000);

		// 点击登录
		UiObject lBtn = new UiObject(new UiSelector().text("登录").className(
				"android.widget.Button"));
		lBtn.clickAndWaitForNewWindow(5000);
	}
	
	private void pressBack(int times){
		UiDevice device = getUiDevice();
		for(int i=0;i<times;i++){
			device.pressBack();
		}
	}
	
	private void sleepForTime(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	// 寻找和打开APP
	private void findAndRunApp() throws UiObjectNotFoundException, IOException,
			InterruptedException {

		String line;
		String cmd = "adb shell am start -n com.youku.phone";
		String cmdreturn = "";
		Runtime run = Runtime.getRuntime();
		Process pr = run.exec(cmd);
		pr.waitFor();
		BufferedReader buf = new BufferedReader(new InputStreamReader(
				pr.getInputStream()));
		while ((line = buf.readLine()) != null) {
			System.out.println(cmdreturn);
		}

		// UiDevice device = getUiDevice();
		// device.pressHome();
		//
		// UiScrollable appview = new UiScrollable (new
		// UiSelector().scrollable(true));
		// appview.setAsHorizontalList();
		//
		// UiObject openapp = appview.getChildByText(new
		// UiSelector().text("QQ"),"QQ");
		// openapp.clickAndWaitForNewWindow();
		// sleep(5000);//睡眠5s
		// System.out.println("sleep success");
	}

}
