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

package com.trashboxbobylev.summoningpixeldungeon.items.scrolls;

import com.trashboxbobylev.summoningpixeldungeon.Assets;
import com.trashboxbobylev.summoningpixeldungeon.Dungeon;
import com.trashboxbobylev.summoningpixeldungeon.actors.Actor;
import com.trashboxbobylev.summoningpixeldungeon.actors.Char;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Invisibility;
import com.trashboxbobylev.summoningpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.summoningpixeldungeon.effects.Speck;
import com.trashboxbobylev.summoningpixeldungeon.levels.RegularLevel;
import com.trashboxbobylev.summoningpixeldungeon.levels.Terrain;
import com.trashboxbobylev.summoningpixeldungeon.levels.rooms.Room;
import com.trashboxbobylev.summoningpixeldungeon.levels.rooms.secret.SecretRoom;
import com.trashboxbobylev.summoningpixeldungeon.levels.rooms.special.SpecialRoom;
import com.trashboxbobylev.summoningpixeldungeon.messages.Messages;
import com.trashboxbobylev.summoningpixeldungeon.scenes.CellSelector;
import com.trashboxbobylev.summoningpixeldungeon.scenes.GameScene;
import com.trashboxbobylev.summoningpixeldungeon.sprites.HeroSprite;
import com.trashboxbobylev.summoningpixeldungeon.utils.BArray;
import com.trashboxbobylev.summoningpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ScrollOfTeleportation extends Scroll {

	{
		initials = 8;
	}

	@Override
	public void doRead() {

		Sample.INSTANCE.play( Assets.SND_READ );
		Invisibility.dispel();
		
		teleportPreferringUnseen( curUser );
		setKnown();

		readAnimation();
	}
	
	@Override
	public void empoweredRead() {
		
		if (Dungeon.bossLevel()){
			GLog.warning( Messages.get(this, "no_tele") );
			return;
		}
		
		GameScene.selectCell(new CellSelector.Listener() {
			@Override
			public void onSelect(Integer target) {
				if (target != null) {
					//time isn't spent
					((HeroSprite)curUser.sprite).read();
					teleportToLocation(curUser, target);
					
				}
			}
			
			@Override
			public String prompt() {
				return Messages.get(ScrollOfTeleportation.class, "prompt");
			}
		});
	}
	
	public static void teleportToLocation(Hero hero, int pos){
		PathFinder.buildDistanceMap(pos, BArray.or(Dungeon.level.passable, Dungeon.level.avoid, null));
		if (PathFinder.distance[hero.pos] == Integer.MAX_VALUE
				|| (!Dungeon.level.passable[pos] && !Dungeon.level.avoid[pos])
				|| Actor.findChar(pos) != null){
			GLog.warning( Messages.get(ScrollOfTeleportation.class, "cant_reach") );
			return;
		}
		
		appear( hero, pos );
		if (!hero.flying) Dungeon.level.press( pos, hero );
		Dungeon.observe();
		GameScene.updateFog();
		
	}
	
	public static void teleportHero( Hero  hero ) {

		int count = 10;
		int pos;
		do {
			pos = Dungeon.level.randomRespawnCell();
			if (count-- <= 0) {
				break;
			}
		} while (pos == -1);
		
		if (pos == -1 || Dungeon.bossLevel()) {
			
			GLog.warning( Messages.get(ScrollOfTeleportation.class, "no_tele") );
			
		} else {
			
			GLog.i( Messages.get(ScrollOfTeleportation.class, "tele") );
			
			appear( hero, pos );
			if (!hero.flying) Dungeon.level.press( pos, hero );
			Dungeon.observe();
			GameScene.updateFog();
			
		}
	}
	
	public static void teleportPreferringUnseen( Hero hero ){
		
		if (Dungeon.bossLevel() || !(Dungeon.level instanceof RegularLevel)){
			teleportHero( hero );
			return;
		}
		
		RegularLevel level = (RegularLevel) Dungeon.level;
		ArrayList<Integer> candidates = new ArrayList<>();
		
		for (Room r : level.rooms()){
			if (r instanceof SpecialRoom){
				int terr;
				boolean locked = false;
				for (Point p : r.getPoints()){
					terr = level.map[level.pointToCell(p)];
					if (terr == Terrain.LOCKED_DOOR || terr == Terrain.BARRICADE){
						locked = true;
						break;
					}
				}
				if (locked){
					continue;
				}
			}
			
			int cell;
			for (Point p : r.charPlaceablePoints(level)){
				cell = level.pointToCell(p);
				if (level.passable[cell] && !level.visited[cell] && Actor.findChar(cell) == null){
					candidates.add(cell);
				}
			}
		}
		
		if (candidates.isEmpty()){
			teleportHero( hero );
		} else {
			int pos = Random.element(candidates);
			boolean secretDoor = false;
			int doorPos = -1;
			if (level.room(pos) instanceof SpecialRoom){
				SpecialRoom room = (SpecialRoom) level.room(pos);
				if (room.entrance() != null){
					doorPos = level.pointToCell(room.entrance());
					for (int i : PathFinder.NEIGHBOURS8){
						if (!room.inside(level.cellToPoint(doorPos + i))
								&& level.passable[doorPos + i]
								&& Actor.findChar(doorPos + i) == null){
							secretDoor = room instanceof SecretRoom;
							pos = doorPos + i;
							break;
						}
					}
				}
			}
			GLog.i( Messages.get(ScrollOfTeleportation.class, "tele") );
			appear( hero, pos );
			if (!hero.flying) Dungeon.level.press( pos, hero );
			if (secretDoor && level.map[doorPos] == Terrain.SECRET_DOOR){
				Sample.INSTANCE.play( Assets.SND_SECRET );
				int oldValue = Dungeon.level.map[doorPos];
				GameScene.discoverTile( doorPos, oldValue );
				Dungeon.level.discover( doorPos );
				ScrollOfMagicMapping.discover( doorPos );
			}
			Dungeon.observe();
			GameScene.updateFog();
		}
		
	}

	public static void appear( Char ch, int pos ) {

		ch.sprite.interruptMotion();

		ch.move( pos );
		if (ch.pos == pos) ch.sprite.place( pos );

		if (ch.invisible == 0) {
			ch.sprite.alpha( 0 );
			ch.sprite.parent.add( new AlphaTweener( ch.sprite, 1, 0.4f ) );
		}

		ch.sprite.emitter().start( Speck.factory(Speck.LIGHT), 0.2f, 3 );
		Sample.INSTANCE.play( Assets.SND_TELEPORT );
	}
	
	@Override
	public int price() {
		return isKnown() ? 40 * quantity : super.price();
	}
}
