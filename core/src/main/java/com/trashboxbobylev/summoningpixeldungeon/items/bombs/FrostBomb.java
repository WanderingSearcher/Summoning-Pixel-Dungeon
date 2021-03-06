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

package com.trashboxbobylev.summoningpixeldungeon.items.bombs;

import com.trashboxbobylev.summoningpixeldungeon.Dungeon;
import com.trashboxbobylev.summoningpixeldungeon.actors.Actor;
import com.trashboxbobylev.summoningpixeldungeon.actors.Char;
import com.trashboxbobylev.summoningpixeldungeon.actors.blobs.Blob;
import com.trashboxbobylev.summoningpixeldungeon.actors.blobs.Freezing;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Chill;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Frost;
import com.trashboxbobylev.summoningpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.summoningpixeldungeon.effects.CellEmitter;
import com.trashboxbobylev.summoningpixeldungeon.effects.particles.BlastParticle;
import com.trashboxbobylev.summoningpixeldungeon.levels.RegularLevel;
import com.trashboxbobylev.summoningpixeldungeon.levels.rooms.Room;
import com.trashboxbobylev.summoningpixeldungeon.mechanics.ShadowCaster;
import com.trashboxbobylev.summoningpixeldungeon.scenes.GameScene;
import com.trashboxbobylev.summoningpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.summoningpixeldungeon.utils.BArray;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class FrostBomb extends Bomb {
	
	{
		image = ItemSpriteSheet.FROST_BOMB;
		harmless = true;
		fuseDelay = 1;
	}
	
	@Override
	public void explode(int cell) {
		super.explode(cell);
		GameScene.flash(0xFFA5F1FF);
		if (Dungeon.level instanceof RegularLevel) {
            Room room = ((RegularLevel) Dungeon.level).room(cell);
            if (room != null) {
                for (Point point : room.getPoints()) {
                    int tile = Dungeon.level.pointToCell(point);
                    Char ch = Actor.findChar(tile);
                    if (ch != null && ch != Dungeon.hero) {
                        Buff.affect(ch, Frost.class, 15f);
                    } else if (ch instanceof Hero) {
                        Buff.affect(ch, Chill.class, 9f);
                    }
                }
            }
        } else {
//		PathFinder.buildDistanceMap( cell, BArray.not( Dungeon.level.solid, null ), 2 );
//		for (int i = 0; i < PathFinder.distance.length; i++) {
//			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
//				GameScene.add(Blob.seed(i, 10, Freezing.class));
//				Char ch = Actor.findChar(i);
//				if (ch != null){
//					Buff.affect(ch, Frost.class, 15f);
//				}
//			}
//		}
            boolean[] FOV = new boolean[Dungeon.level.length()];
            Point c = Dungeon.level.cellToPoint(cell);
            ShadowCaster.castShadow(c.x, c.y, FOV, Dungeon.level.losBlocking, Dungeon.level.viewDistance);

            ArrayList<Char> affected = new ArrayList<>();

            for (int i = 0; i < FOV.length; i++) {
                if (FOV[i]) {
                    Char ch = Actor.findChar(i);
                    if (ch != null){
                        affected.add(ch);
                    }
                }
            }

            for (Char ch : affected){
                if (ch != null && ch != Dungeon.hero) {
                    Buff.affect(ch, Frost.class, 15f);
                } else if (ch instanceof Hero) {
                    Buff.affect(ch, Chill.class, 9f);
                }
            }
        }
	}
	
	@Override
	public int price() {
		//prices of ingredients
		return quantity * (35 + 40);
	}
}
