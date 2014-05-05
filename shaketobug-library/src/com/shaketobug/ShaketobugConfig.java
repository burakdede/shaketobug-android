/*
 * Copyright (C) Burak Dede.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shaketobug;

import android.graphics.Color;

public class ShaketobugConfig {
	
	/** Actionbar background color */
	private int actionbarColor = Color.BLACK;
	/** Actionbar title for the Feedback activity */
	private String actionbarTitle = "Shaketobug";
	/** Email "to" field shown in the email sending app */
	private String emailToField = "burakdede87@gmail.com";
	/** Email "subject" field in the email sendin app */
	private String emailSubjectField = "Bug Report";
	/** Whether to use dark or light icon for "send" */
	private boolean useDarkIcons = false;
	/** What color will be used on drawing canvas */
	private int pencilColor = Color.GREEN;
	/** Custom drawable - will override actionbarColor attribute */
	private int actionbarBackgroundDrawable = Integer.MIN_VALUE;
	
	
	public ShaketobugConfig() {
	}
	
	public ShaketobugConfig(int actionbarColor, String actionbarTitle, 
			String emailToField, String emailSubjectField, boolean useDarkIcons, 
			int pencilColor, int actionbarBackgroundDrawable) {
		this.actionbarColor = actionbarColor;
		this.actionbarTitle = actionbarTitle;
		this.emailToField = emailToField;
		this.emailSubjectField = emailSubjectField;
		this.useDarkIcons = useDarkIcons;
		this.pencilColor = pencilColor;
		this.actionbarBackgroundDrawable = actionbarBackgroundDrawable;
	}

	public int getActionbarBackgrounDrawable() {
		return actionbarBackgroundDrawable;
	}

	public void setActionbarBackgrounDrawable(int actionbarBackgrounDrawable) {
		this.actionbarBackgroundDrawable = actionbarBackgrounDrawable;
	}

	public int getPencilColor() {
		return pencilColor;
	}

	public void setPencilColor(int pencilColor) {
		this.pencilColor = pencilColor;
	}

	public boolean isUseDarkIcons() {
		return useDarkIcons;
	}

	public void setUseDarkIcons(boolean useDarkIcons) {
		this.useDarkIcons = useDarkIcons;
	}

	public int getActionbarColor() {
		return actionbarColor;
	}

	public void setActionbarColor(int actionbarColor) {
		this.actionbarColor = actionbarColor;
	}

	public String getActionbarTitle() {
		return actionbarTitle;
	}

	public void setActionbarTitle(String actionbarTitle) {
		this.actionbarTitle = actionbarTitle;
	}

	public String getEmailToField() {
		return emailToField;
	}

	public void setEmailToField(String emailToField) {
		this.emailToField = emailToField;
	}

	public String getEmailSubjectField() {
		return emailSubjectField;
	}

	public void setEmailSubjectField(String emailSubjectField) {
		this.emailSubjectField = emailSubjectField;
	}
	
}
