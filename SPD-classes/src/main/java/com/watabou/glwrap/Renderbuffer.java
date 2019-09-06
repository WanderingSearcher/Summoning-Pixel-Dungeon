/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * Summoning Pixel Dungeon
 * Copyright (C) 2019-2020 TrashboxBobylev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.watabou.glwrap;

import com.badlogic.gdx.Gdx;

public class Renderbuffer {

	public static final int RGBA8		= Gdx.gl.GL_RGBA;	// ?
	public static final int DEPTH16		= Gdx.gl.GL_DEPTH_COMPONENT16;
	public static final int STENCIL8	= Gdx.gl.GL_STENCIL_INDEX8;
	
	private int id;
	
	public Renderbuffer() {
		id = Gdx.gl.glGenRenderbuffer();
	}
	
	public int id() {
		return id;
	}
	
	public void bind() {
		Gdx.gl.glBindRenderbuffer( Gdx.gl.GL_RENDERBUFFER, id );
	}
	
	public void delete() {
		Gdx.gl.glDeleteRenderbuffer( id );
	}
	
	public void storage( int format, int width, int height ) {
		Gdx.gl.glRenderbufferStorage( Gdx.gl.GL_RENDERBUFFER, format , width, height );
	}
}
