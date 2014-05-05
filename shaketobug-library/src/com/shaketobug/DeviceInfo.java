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

import android.os.Build;

public class DeviceInfo {

	private String bootloader;
	private String cpuabi;
	private String device;
	private String display;
	private String hardware;
	private String manufacturer;
	private String model;
	
	public DeviceInfo() {
	}
	
	/**
	 * Setup device current information form
	 * {@link Build} classs 
	 * 
	 * @return {@link String} of information about device
	 */
	public String setupDeviceInfo() {
		bootloader = Build.BOOTLOADER;
		cpuabi = Build.CPU_ABI;
		device = Build.DEVICE;
		display = Build.DISPLAY;
		hardware = Build.HARDWARE;
		manufacturer = Build.MANUFACTURER;
		model = Build.MODEL;
		
		return  "\n" + 
				"Bootloader: " + bootloader + "\n" + 
				"Cpu ABI: " + cpuabi + "\n" + 
				"Device: " + device + "\n" + 
				"Display: " + display + "\n" +
				"Hardware: " + hardware + "\n" + 
				"Manufacturer: " + manufacturer + "\n" + 
				"Model: " + model + "\n";
	}
}
