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

package com.trashboxbobylev.summoningpixeldungeon.actors.mobs;

import com.trashboxbobylev.summoningpixeldungeon.Assets;
import com.trashboxbobylev.summoningpixeldungeon.Dungeon;
import com.trashboxbobylev.summoningpixeldungeon.actors.Actor;
import com.trashboxbobylev.summoningpixeldungeon.actors.Char;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Barrier;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Charm;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Light;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Sleep;
import com.trashboxbobylev.summoningpixeldungeon.effects.Speck;
import com.trashboxbobylev.summoningpixeldungeon.items.Generator;
import com.trashboxbobylev.summoningpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.trashboxbobylev.summoningpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.trashboxbobylev.summoningpixeldungeon.mechanics.Ballistica;
import com.trashboxbobylev.summoningpixeldungeon.sprites.SuccubusSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Succubus extends Mob {
	
	private static final int BLINK_DELAY	= 5;
	
	private int delay = 0;
	
	{
		spriteClass = SuccubusSprite.class;
		
		HP = HT = 95;
		defenseSkill = 25;
		viewDistance = Light.DISTANCE;
		
		EXP = 13;
		maxLvl = 26;
		
		loot = Generator.random(Generator.Category.SCROLL);
		lootChance = 0.25f;

		properties.add(Property.DEMONIC);
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 24, 34 );
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		
		if (enemy.buff(Charm.class) != null ){
			int shield = (HP - HT) + (8 + damage);
			if (shield > 0){
				HP = HT;
				Buff.affect(this, Barrier.class).setShield(shield);
			} else {
				HP += 8 + damage;
			}
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 2 );
			Sample.INSTANCE.play( Assets.SND_CHARMS );
		} else if (Random.Int( 3 ) == 0) {
			//attack will reduce by 5 turns, so effectively 3-4 turns
			Buff.affect( enemy, Charm.class, Random.IntRange( 3, 4 ) + 5 ).object = id();
			enemy.sprite.centerEmitter().start( Speck.factory( Speck.HEART ), 0.2f, 5 );
			Sample.INSTANCE.play( Assets.SND_CHARMS );
		}
		
		return damage;
	}
	
	@Override
	protected boolean getCloser( int target ) {
		if (fieldOfView[target] && Dungeon.level.distance( pos, target ) > 2 && delay <= 0) {
			
			blink( target );
			spend( -1 / speed() );
			return true;
			
		} else {
			
			delay--;
			return super.getCloser( target );
			
		}
	}
	
	private void blink( int target ) {
		
		Ballistica route = new Ballistica( pos, target, Ballistica.PROJECTILE);
		int cell = route.collisionPos;

		//can't occupy the same cell as another char, so move back one.
		if (Actor.findChar( cell ) != null && cell != this.pos)
			cell = route.path.get(route.dist-1);

		if (Dungeon.level.avoid[ cell ]){
			ArrayList<Integer> candidates = new ArrayList<>();
			for (int n : PathFinder.NEIGHBOURS8) {
				cell = route.collisionPos + n;
				if (Dungeon.level.passable[cell] && Actor.findChar( cell ) == null) {
					candidates.add( cell );
				}
			}
			if (candidates.size() > 0)
				cell = Random.element(candidates);
			else {
				delay = BLINK_DELAY;
				return;
			}
		}
		
		ScrollOfTeleportation.appear( this, cell );
		
		delay = BLINK_DELAY;
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 40;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 13);
	}
	
	{
		immunities.add( Sleep.class );
		immunities.add( Charm.class );
	}
}
