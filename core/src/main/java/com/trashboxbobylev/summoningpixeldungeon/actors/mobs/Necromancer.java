/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Adrenaline;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Corruption;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Empowered;
import com.trashboxbobylev.summoningpixeldungeon.effects.Beam;
import com.trashboxbobylev.summoningpixeldungeon.effects.CellEmitter;
import com.trashboxbobylev.summoningpixeldungeon.effects.Pushing;
import com.trashboxbobylev.summoningpixeldungeon.effects.Speck;
import com.trashboxbobylev.summoningpixeldungeon.items.Generator;
import com.trashboxbobylev.summoningpixeldungeon.items.Item;
import com.trashboxbobylev.summoningpixeldungeon.items.potions.PotionOfHealing;
import com.trashboxbobylev.summoningpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.trashboxbobylev.summoningpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.trashboxbobylev.summoningpixeldungeon.items.weapon.melee.staffs.Staff;
import com.trashboxbobylev.summoningpixeldungeon.scenes.GameScene;
import com.trashboxbobylev.summoningpixeldungeon.sprites.NecromancerSprite;
import com.trashboxbobylev.summoningpixeldungeon.sprites.SkeletonSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Necromancer extends Mob {
	
	{
		spriteClass = NecromancerSprite.class;
		
		HP = HT = 40;
		defenseSkill = 11;
		
		EXP = 7;
		maxLvl = 14;

        loot = Generator.Category.STAFFS;
        lootChance = 0.125f;
		
		properties.add(Property.UNDEAD);
		
		HUNTING = new Hunting();
	}
	
	public boolean summoning = false;
	private Emitter summoningEmitter = null;
	private int summoningPos = -1;
	
	private boolean firstSummon = true;
	
	private NecroSkeleton mySkeleton;
	private int storedSkeletonID = -1;
	
	@Override
	public void updateSpriteState() {
		super.updateSpriteState();
		
		if (summoning && summoningEmitter == null){
			summoningEmitter = CellEmitter.get( summoningPos );
			summoningEmitter.pour(Speck.factory(Speck.RATTLE), 0.2f);
			sprite.zap( summoningPos );
		}
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 5);
	}

    @Override
    protected Item createLoot() {
        Staff loot;
        do {
            loot = Generator.randomStaff();
            //50% chance of re-rolling tier 4 or 5 melee weapons
        } while (loot.tier >= 4 && Random.Int(2) == 0);
        loot.level(0);
        return loot;
    }
	
	@Override
	public void die(Object cause) {
		if (storedSkeletonID != -1){
			Actor ch = Actor.findById(storedSkeletonID);
			storedSkeletonID = -1;
			if (ch instanceof NecroSkeleton){
				mySkeleton = (NecroSkeleton) ch;
			}
		}
		
		if (mySkeleton != null && mySkeleton.isAlive()){
			mySkeleton.die(null);
		}
		
		if (summoningEmitter != null){
			summoningEmitter.killAndErase();
			summoningEmitter = null;
		}
		
		super.die(cause);
	}
	
	private static final String SUMMONING = "summoning";
	private static final String FIRST_SUMMON = "first_summon";
	private static final String SUMMONING_POS = "summoning_pos";
	private static final String MY_SKELETON = "my_skeleton";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put( SUMMONING, summoning );
		bundle.put( FIRST_SUMMON, firstSummon );
		if (summoning){
			bundle.put( SUMMONING_POS, summoningPos);
		}
		if (mySkeleton != null){
			bundle.put( MY_SKELETON, mySkeleton.id() );
		}
	}
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		summoning = bundle.getBoolean( SUMMONING );
		if (bundle.contains(FIRST_SUMMON)) firstSummon = bundle.getBoolean(FIRST_SUMMON);
		if (summoning){
			summoningPos = bundle.getInt( SUMMONING_POS );
		}
		if (bundle.contains( MY_SKELETON )){
			storedSkeletonID = bundle.getInt( MY_SKELETON );
		}
	}
	
	public void onZapComplete(){
		if (mySkeleton == null){
			return;
		}
		
		//heal skeleton first
		if (mySkeleton.HP < mySkeleton.HT){
			
			sprite.parent.add(new Beam.HealthRay(sprite.center(), mySkeleton.sprite.center()));
            Sample.INSTANCE.play( Assets.SND_RAY );
			
			mySkeleton.HP = Math.min(mySkeleton.HP + 8, mySkeleton.HT);
			mySkeleton.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			
			//otherwise give it adrenaline
		} else if (mySkeleton.buff(Adrenaline.class) == null) {
			
			sprite.parent.add(new Beam.HealthRay(sprite.center(), mySkeleton.sprite.center()));
            Sample.INSTANCE.play( Assets.SND_RAY );
			
			Buff.affect(mySkeleton, Adrenaline.class, 4f);
            Buff.affect(mySkeleton, Empowered.class, 4f);
		}
	}
	
	private class Hunting extends Mob.Hunting{
		
		@Override
		public boolean act(boolean enemyInFOV, boolean justAlerted) {
			enemySeen = enemyInFOV;
			
			if (storedSkeletonID != -1){
				Actor ch = Actor.findById(storedSkeletonID);
				storedSkeletonID = -1;
				if (ch instanceof NecroSkeleton){
					mySkeleton = (NecroSkeleton) ch;
				}
			}
			
			if (summoning){
				
				//push anything on summoning spot away, to the furthest valid cell
				if (Actor.findChar(summoningPos) != null) {
					int pushPos = pos;
					for (int c : PathFinder.NEIGHBOURS8) {
						if (Actor.findChar(summoningPos + c) == null
								&& Dungeon.level.passable[summoningPos + c]
								&& Dungeon.level.trueDistance(pos, summoningPos + c) > Dungeon.level.trueDistance(pos, pushPos)) {
							pushPos = summoningPos + c;
						}
					}
					
					//push enemy, or wait a turn if there is no valid pushing position
					if (pushPos != pos) {
						Char ch = Actor.findChar(summoningPos);
						Actor.addDelayed( new Pushing( ch, ch.pos, pushPos ), -1 );
						
						ch.pos = pushPos;
						Dungeon.level.occupyCell(ch );
						
					} else {
						spend(TICK);
						return true;
					}
				}
				
				summoning = firstSummon = false;
				
				mySkeleton = new NecroSkeleton();
				mySkeleton.pos = summoningPos;
				GameScene.add( mySkeleton );
				Dungeon.level.occupyCell( mySkeleton );
				Sample.INSTANCE.play(Assets.SND_BONES);
				summoningEmitter.burst( Speck.factory( Speck.RATTLE ), 5 );
				sprite.idle();
				
				if (buff(Corruption.class) != null){
					Buff.affect(mySkeleton, Corruption.class);
				}
				
				spend(TICK);
				return true;
			}
			
			if (mySkeleton != null &&
					(!mySkeleton.isAlive()
					|| !Dungeon.level.mobs.contains(mySkeleton)
					|| mySkeleton.alignment != alignment)){
				mySkeleton = null;
			}
			
			//if enemy is seen, and enemy is within range, and we haven no skeleton, summon a skeleton!
			if (enemySeen && Dungeon.level.distance(pos, enemy.pos) <= 4 && mySkeleton == null){
				
				summoningPos = -1;
				for (int c : PathFinder.NEIGHBOURS8){
					if (Actor.findChar(enemy.pos+c) == null
							&& Dungeon.level.passable[enemy.pos+c]
							&& fieldOfView[enemy.pos+c]
							&& Dungeon.level.trueDistance(pos, enemy.pos+c) < Dungeon.level.trueDistance(pos, summoningPos)){
						summoningPos = enemy.pos+c;
					}
				}
				
				if (summoningPos != -1){
					
					summoning = true;
					summoningEmitter = CellEmitter.get(summoningPos);
					summoningEmitter.pour(Speck.factory(Speck.RATTLE), 0.2f);
					
					sprite.zap( summoningPos );
					
					spend( firstSummon ? TICK : 2*TICK );
				} else {
					//wait for a turn
					spend(TICK);
				}
				
				return true;
			//otherwise, if enemy is seen, and we have a skeleton...
			} else if (enemySeen && mySkeleton != null){
				
				target = enemy.pos;
				
				if (!fieldOfView[mySkeleton.pos]){
					
					//if the skeleton is not next to the enemy
					//teleport them to the closest spot next to the enemy that can be seen
					if (!Dungeon.level.adjacent(mySkeleton.pos, enemy.pos)){
						int telePos = -1;
						for (int c : PathFinder.NEIGHBOURS8){
							if (Actor.findChar(enemy.pos+c) == null
									&& Dungeon.level.passable[enemy.pos+c]
									&& fieldOfView[enemy.pos+c]
									&& Dungeon.level.trueDistance(pos, enemy.pos+c) < Dungeon.level.trueDistance(pos, telePos)){
								telePos = enemy.pos+c;
							}
						}
						
						if (telePos != -1){
							sprite.zap(telePos);
							ScrollOfTeleportation.appear(mySkeleton, telePos);
							mySkeleton.teleportSpend();
						}
					}
					
				} else {
					
					//zap skeleton
					if (mySkeleton.HP < mySkeleton.HT || mySkeleton.buff(Adrenaline.class) == null) {
						sprite.zap(mySkeleton.pos);
					}
					
				}
				
				spend(TICK);
				return true;
				
				
			//otherwise, default to regular hunting behaviour
			} else {
				return super.act(enemyInFOV, justAlerted);
			}
		}
	}
	
	public static class NecroSkeleton extends Skeleton {
		
		{
			state = WANDERING;
			
			spriteClass = NecroSkeletonSprite.class;
			
			//no loot or exp
			maxLvl = -5;
			
			//15/25 health to start
			HP = 15;
		}
		
		private void teleportSpend(){
			spend(TICK);
		}
		
		public static class NecroSkeletonSprite extends SkeletonSprite{
			
			public NecroSkeletonSprite(){
				super();
				brightness(0.75f);
			}
			
			@Override
			public void resetColor() {
				super.resetColor();
				brightness(0.75f);
			}
		}
		
	}
}
