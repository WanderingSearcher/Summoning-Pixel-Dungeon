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

package com.trashboxbobylev.summoningpixeldungeon.items.weapon;

import com.trashboxbobylev.summoningpixeldungeon.Assets;
import com.trashboxbobylev.summoningpixeldungeon.Dungeon;
import com.trashboxbobylev.summoningpixeldungeon.actors.Actor;
import com.trashboxbobylev.summoningpixeldungeon.actors.Char;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Shrink;
import com.trashboxbobylev.summoningpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.summoningpixeldungeon.actors.mobs.Eye;
import com.trashboxbobylev.summoningpixeldungeon.effects.CellEmitter;
import com.trashboxbobylev.summoningpixeldungeon.effects.Splash;
import com.trashboxbobylev.summoningpixeldungeon.effects.particles.PurpleParticle;
import com.trashboxbobylev.summoningpixeldungeon.items.rings.RingOfFuror;
import com.trashboxbobylev.summoningpixeldungeon.items.rings.RingOfSharpshooting;
import com.trashboxbobylev.summoningpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.trashboxbobylev.summoningpixeldungeon.mechanics.Ballistica;
import com.trashboxbobylev.summoningpixeldungeon.messages.Messages;
import com.trashboxbobylev.summoningpixeldungeon.scenes.CellSelector;
import com.trashboxbobylev.summoningpixeldungeon.scenes.GameScene;
import com.trashboxbobylev.summoningpixeldungeon.sprites.CharSprite;
import com.trashboxbobylev.summoningpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.summoningpixeldungeon.sprites.MissileSprite;
import com.trashboxbobylev.summoningpixeldungeon.ui.QuickSlotButton;
import com.trashboxbobylev.summoningpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SpiritBow extends Weapon {
	
	public static final String AC_SHOOT		= "SHOOT";
	
	{
		image = ItemSpriteSheet.SPIRIT_BOW;
		
		defaultAction = AC_SHOOT;
		usesTargeting = true;
		
		unique = true;
		bones = false;
	}
	
	public boolean sniperSpecial = false;
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_EQUIP);
		actions.add(AC_SHOOT);
		return actions;
	}
	
	@Override
	public void execute(Hero hero, String action) {
		
		super.execute(hero, action);
		
		if (action.equals(AC_SHOOT)) {
			
			curUser = hero;
			curItem = this;
			GameScene.selectCell( shooter );
			
		}
	}
	
	@Override
	public String info() {
		String info = desc();
		
		info += "\n\n" + Messages.get( SpiritBow.class, "stats",
				Math.round(augment.damageFactor(min())),
				Math.round(augment.damageFactor(max())),
				STRReq());
		
		if (STRReq() > Dungeon.hero.STR()) {
			info += " " + Messages.get(Weapon.class, "too_heavy");
		} else if (Dungeon.hero.STR() > STRReq()){
			info += " " + Messages.get(Weapon.class, "excess_str", Dungeon.hero.STR() - STRReq());
		}
		
		switch (augment) {
			case SPEED:
				info += "\n\n" + Messages.get(Weapon.class, "faster");
				break;
			case DAMAGE:
				info += "\n\n" + Messages.get(Weapon.class, "stronger");
				break;
			case NONE:
		}
		
		if (enchantment != null && (cursedKnown || !enchantment.curse())){
			info += "\n\n" + Messages.get(Weapon.class, "enchanted", enchantment.name());
			info += " " + Messages.get(enchantment, "desc");
		}
		
		if (cursed && isEquipped( Dungeon.hero )) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed");
		} else if (!isIdentified() && cursedKnown){
			info += "\n\n" + Messages.get(Weapon.class, "not_cursed");
		}
		
		info += "\n\n" + Messages.get(MissileWeapon.class, "distance");
		
		return info;
	}
	
	@Override
	public int STRReq(int lvl) {
		lvl = Math.max(0, lvl);
		//strength req decreases at +1,+3,+6,+10,etc.
		return 10 - (int)(Math.sqrt(8 * lvl + 1) - 1)/2;
	}
	
	@Override
	public int min(int lvl) {
		return 1 + Dungeon.hero.lvl/5
				+ RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
				+ (curseInfusionBonus ? 1 : 0);
	}
	
	@Override
	public int max(int lvl) {
		return 6 + (int)(Dungeon.hero.lvl/2.5f)
				+ 2*RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
				+ (curseInfusionBonus ? 2 : 0);
	}
	
	private int targetPos;
	
	@Override
	public int damageRoll(Char owner) {
		int damage = augment.damageFactor(super.damageRoll(owner));
		
		if (owner instanceof Hero) {
			int exStr = ((Hero)owner).STR() - STRReq();
			if (exStr > 0) {
				damage += Random.IntRange( 0, exStr );
			}
		}
		
		if (sniperSpecial){
			switch (augment){
				case NONE:
					damage = Math.round(damage * 0.667f);
					break;
				case SPEED:
					damage = Math.round(damage * 0.5f);
					break;
				case DAMAGE:
					int distance = Dungeon.level.distance(owner.pos, targetPos) - 1;
					damage = Math.round(damage * (1f + 0.1f * distance));
					break;
			}
		}
		
		return damage;
	}
	
	@Override
	public float speedFactor(Char owner) {
		if (sniperSpecial){
			switch (augment){
				case NONE: default:
					return 0f;
				case SPEED:
					return 1f * RingOfFuror.attackDelayMultiplier(owner);
				case DAMAGE:
					return 2f * RingOfFuror.attackDelayMultiplier(owner);
			}
		} else {
			return super.speedFactor(owner);
		}
	}
	
	@Override
	public int level() {
		//need to check if hero is null for loading an upgraded bow from pre-0.7.0
		return (Dungeon.hero == null ? 0 : Dungeon.hero.lvl/5)
				+ (curseInfusionBonus ? 1 : 0);
	}
	
	//for fetching upgrades from a boomerang from pre-0.7.1
	public int spentUpgrades() {
		return super.level() - (curseInfusionBonus ? 1 : 0);
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	public SpiritArrow knockArrow(){
		return new SpiritArrow();
	}
	
	public class SpiritArrow extends MissileWeapon {
		
		{
			image = ItemSpriteSheet.SPIRIT_ARROW;
		}

        @Override
        public int image() {
            switch (SpiritBow.this.augment){
                case DAMAGE:
                    return ItemSpriteSheet.SPIRIT_BLAST;
                default:
                    return ItemSpriteSheet.SPIRIT_ARROW;
            }
        }

        @Override
		public int damageRoll(Char owner) {
			return SpiritBow.this.damageRoll(owner);
		}
		
		@Override
		public boolean hasEnchant(Class<? extends Enchantment> type, Char owner) {
			return SpiritBow.this.hasEnchant(type, owner);
		}
		
		@Override
		public int proc(Char attacker, Char defender, int damage) {
			return SpiritBow.this.proc(attacker, defender, damage);
		}
		
		@Override
		public float speedFactor(Char user) {
			return SpiritBow.this.speedFactor(user);
		}
		
		@Override
		public float accuracyFactor(Char owner) {
			if (sniperSpecial && SpiritBow.this.augment == Augment.DAMAGE){
				return Float.POSITIVE_INFINITY;
			} else {
				return super.accuracyFactor(owner);
			}
		}
		
		@Override
		public int STRReq(int lvl) {
			return SpiritBow.this.STRReq(lvl);
		}
		
		@Override
        public void onThrow(int cell) {
			Char enemy = Actor.findChar( cell );
			if (enemy == null || enemy == curUser) {
				parent = null;
				Splash.at( cell, 0xCC99FFFF, 1 );
			} else {
				if (!curUser.shoot( enemy, this )) {
					Splash.at(cell, 0xCC99FFFF, 1);
				}
				if (sniperSpecial && SpiritBow.this.augment != Augment.SPEED) sniperSpecial = false;
			}
		}
		
		int flurryCount = -1;
		
		@Override
		public void cast(final Hero user, final int dst) {
            final Ballistica ballistica = new Ballistica( user.pos, dst, Ballistica.STOP_TERRAIN );
			final int cell = SpiritBow.this.augment == Augment.DAMAGE ?  ballistica.collisionPos : throwPos( user, dst );
            SpiritBow.this.targetPos = cell;
            if (sniperSpecial) {
                if (SpiritBow.this.augment == Augment.DAMAGE) {
                    user.busy();

                    Sample.INSTANCE.play( Assets.SND_MISS, 0.6f, 0.6f, 1.5f );
                    user.sprite.zap(cell);

                    ((MissileSprite) user.sprite.parent.recycle(MissileSprite.class)).
                            reset(user.sprite,
                                    cell,
                                    this,
                                    new Callback() {
                                        @Override
                                        public void call() {
                                            for (int pos : ballistica.subPath(1, ballistica.dist)) {


                                                Char ch = Actor.findChar( pos );
                                                if (ch == null) {
                                                    continue;
                                                }

                                                if (Char.hit( user, ch, false )) {
                                                    Sample.INSTANCE.play( Assets.SND_HIT, 1, 1, Random.Float( 0.8f, 1.25f ) );
                                                    int damage = (int) (damageRoll(user)*0.9f);
                                                    ch.sprite.bloodBurstA( user.sprite.center(), damage );
                                                    ch.sprite.flash();

                                                    ch.damage(damage , new SpiritBow.SpiritArrow() );
                                                    SpiritBow.this.proc(user, ch, damage);
                                                } else {
                                                    ch.sprite.showStatus( CharSprite.NEUTRAL,  ch.defenseVerb() );
                                                }
                                            }

                                            user.spendAndNext(castDelay(user, dst));
                                            sniperSpecial = false;
                                        }
                                    });

                } else if ( SpiritBow.this.augment == Augment.SPEED){
                    if (flurryCount == -1) flurryCount = 3;

                    final Char enemy = Actor.findChar( cell );

                    if (enemy == null){
                        user.spendAndNext(castDelay(user, dst));
                        sniperSpecial = false;
                        flurryCount = -1;
                        return;
                    }
                    QuickSlotButton.target(enemy);

                    final boolean last = flurryCount == 1;

                    Sample.INSTANCE.play( Assets.SND_MISS, 0.6f, 0.6f, 1.5f );

                    ((MissileSprite) user.sprite.parent.recycle(MissileSprite.class)).
                            reset(user.sprite,
                                    cell,
                                    this,
                                    new Callback() {
                                        @Override
                                        public void call() {
                                            if (enemy.isAlive()) {
                                                curUser = user;
                                                onThrow(cell);
                                            }

                                            if (last) {
                                                user.spendAndNext(castDelay(user, dst));
                                                sniperSpecial = false;
                                                flurryCount = -1;
                                            }
                                        }
                                    });

                    user.sprite.zap(cell, new Callback() {
                        @Override
                        public void call() {
                            flurryCount--;
                            if (flurryCount > 0){
                                cast(user, dst);
                            }
                        }
                    });

                } else {
                    super.cast(user, dst);
                    return;
                }
            } else {
				super.cast(user, dst);
				return;
			}
		}
	}
	
	private CellSelector.Listener shooter = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer target ) {
			if (target != null) {
				knockArrow().cast(curUser, target);
			}
		}
		@Override
		public String prompt() {
			return Messages.get(SpiritBow.class, "prompt");
		}
	};
}
