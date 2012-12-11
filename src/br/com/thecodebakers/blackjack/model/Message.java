/*
 * Copyright (C) 2011 The Code Bakers
 * Authors: Cleuton Sampaio e Francisco Rodrigues
 * e-mail: thecodebakers@gmail.com
 * Project: https://code.google.com/p/open-source-android-blackjack/
 * Site: http://thecodebakers.blogspot.com
 *
 * Licensed under the GNU GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://gplv3.fsf.org/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author Cleuton Sampaio e Francisco Rogrigues - thecodebakers@gmail.com
 */

package br.com.thecodebakers.blackjack.model;



/**
 * Esta classe representa uma mensagem gerada pelo objeto de negócio.<br/> 
 * 
 * This class represents a message generated by the business object.
 * 
 * 
 * @author Cleuton Sampaio e Francisco Rogrigues - thecodebakers@gmail.com
 *
 */
public class Message {
	
	private String text;
	
	
	public String getText() {
		return text;
	}

	public Message(String text) {
		super();
		this.text = text;
	}
}
