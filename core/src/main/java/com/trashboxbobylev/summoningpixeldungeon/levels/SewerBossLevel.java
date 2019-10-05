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

package com.trashboxbobylev.summoningpixeldungeon.levels;

import com.trashboxbobylev.summoningpixeldungeon.Bones;
import com.trashboxbobylev.summoningpixeldungeon.actors.Actor;
import com.trashboxbobylev.summoningpixeldungeon.actors.mobs.Goo;
import com.trashboxbobylev.summoningpixeldungeon.items.Heap;
import com.trashboxbobylev.summoningpixeldungeon.items.Item;
import com.trashboxbobylev.summoningpixeldungeon.levels.builders.Builder;
import com.trashboxbobylev.summoningpixeldungeon.levels.builders.LoopBuilder;
import com.trashboxbobylev.summoningpixeldungeon.levels.rooms.Room;
import com.trashboxbobylev.summoningpixeldungeon.levels.rooms.secret.RatKingRoom;
import com.trashboxbobylev.summoningpixeldungeon.levels.rooms.standard.EmptyRoom;
import com.trashboxbobylev.summoningpixeldungeon.levels.rooms.standard.SewerBossEntranceRoom;
import com.trashboxbobylev.summoningpixeldungeon.levels.rooms.standard.StandardRoom;
import com.trashboxbobylev.summoningpixeldungeon.scenes.GameScene;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SewerBossLevel extends SewerLevel {

	{
		color1 = 0x48763c;
		color2 = 0x59994a;
	}
	
	private int stairs = 0;
	
	@Override
	protected ArrayList<Room> initRooms() {
		ArrayList<Room> initRooms = new ArrayList<>();
		initRooms.add ( roomEntrance = roomExit = new SewerBossEntranceRoom());
		
		int standards = standardRooms();
		for (int i = 0; i < standards; i++) {
			initRooms.add(new EmptyRoom());
		}
		
		initRooms.add(new RatKingRoom());
		return initRooms;
	}
	
	@Override
	protected int standardRooms() {
		//2 to 4, average 3
		return 2+Random.chances(new float[]{1, 1, 1});
	}
	
	protected Builder builder(){
		return new LoopBuilder()
				.setPathLength(1f, new float[]{1})
				.setTunnelLength(new float[]{0, 3, 1}, new float[]{1});
	}
	
	@Override
	protected float waterFill(){
		return 0.50f;
	}
	
	@Override
	protected int waterSmoothing(){
		return 5;
	}
	
	@Override
	protected float grassFill() {
		return 0.20f;
	}
	
	@Override
	protected int grassSmoothing() {
		return 4;
	}
	
	protected int nTraps() {
		return 0;
	}

	@Override
	protected void createMobs() {
		Goo boss = new Goo();
		Room room;
		do {
			room = randomRoom(StandardRoom.class);
		} while (room == roomEntrance);
		boss.pos = pointToCell(room.random());
		mobs.add( boss );
	}
	
	public Actor respawner() {
		return null;
	}
	
	@Override
	protected void createItems() {
		Item item = Bones.get();
		if (item != null) {
			int pos;
			do {
				pos = pointToCell(roomEntrance.random());
			} while (pos == entrance || solid[pos]);
			drop( item, pos ).setHauntedIfCursed(1f).type = Heap.Type.REMAINS;
		}
	}

	@Override
	public int randomRespawnCell() {
		int pos;
		do {
			pos = pointToCell(roomEntrance.random());
		} while (pos == entrance || !passable[pos] || Actor.findChar(pos) != null);
		return pos;
	}

	
	public void seal() {
		if (entrance != 0) {

			super.seal();
			
			set( entrance, Terrain.WATER );
			GameScene.updateMap( entrance );
			GameScene.ripple( entrance );
			
			stairs = entrance;
			entrance = 0;
		}
	}
	
	public void unseal() {
		if (stairs != 0) {

			super.unseal();
			
			entrance = stairs;
			stairs = 0;
			
			set( entrance, Terrain.ENTRANCE );
			GameScene.updateMap( entrance );

		}
	}
	
	private static final String STAIRS	= "stairs";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( STAIRS, stairs );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		stairs = bundle.getInt( STAIRS );
		roomExit = roomEntrance;
	}
}