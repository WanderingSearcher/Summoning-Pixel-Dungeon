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

package com.trashboxbobylev.summoningpixeldungeon.actors.buffs;

import com.trashboxbobylev.summoningpixeldungeon.Badges;
import com.trashboxbobylev.summoningpixeldungeon.Dungeon;
import com.trashboxbobylev.summoningpixeldungeon.actors.Char;
import com.trashboxbobylev.summoningpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.summoningpixeldungeon.items.artifacts.Artifact;
import com.trashboxbobylev.summoningpixeldungeon.items.artifacts.HornOfPlenty;
import com.trashboxbobylev.summoningpixeldungeon.messages.Messages;
import com.trashboxbobylev.summoningpixeldungeon.ui.BuffIndicator;
import com.trashboxbobylev.summoningpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

public class Hunger extends Buff implements Hero.Doom {

	private static final float STEP	= 10f;

	public static final float HUNGRY	= 2000f;
	public static final float STARVING	= 3000f;

	private float level;
	private float partialDamage;

	private static final String LEVEL			= "level";
	private static final String PARTIALDAMAGE 	= "partialDamage";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put( LEVEL, level );
		bundle.put( PARTIALDAMAGE, partialDamage );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		level = bundle.getFloat( LEVEL );
		partialDamage = bundle.getFloat(PARTIALDAMAGE);
	}

	@Override
	public boolean act() {

        if (!target.isAlive() || !(target instanceof Hero)) {

            diactivate();

        }

        return true;
	}

	public void satisfy( float energy ) {

		Artifact.ArtifactBuff buff = target.buff( HornOfPlenty.hornRecharge.class );
		if (buff != null && buff.isCursed()){
			energy *= 0.67f;
			GLog.negative( Messages.get(this, "cursedhorn") );
		}

		adjustHunger( energy );
	}

	//directly interacts with hunger, no checks.
	public static void adjustHunger(float energy ) {
//		if (level < 0) {
//			level = 0;
//		} else if (level > STARVING) {
//			float excess = level - STARVING;
//			level = STARVING;
//			partialDamage += excess * (target.HT/1000f);
//		}
//
//		BuffIndicator.refreshHero();
        Hunger hunger = Buff.affect(Dungeon.hero, Hunger.class);
        Char target = hunger.target;
        if (Dungeon.level.locked || target.buff(WellFed.class) != null || Dungeon.depth == 21){
            return;
        }
        if (hunger.isStarving()){
            hunger.partialDamage += energy * target.HT/1000f;

            if (hunger.partialDamage > 1){
                target.damage( (int)hunger.partialDamage, target);
                hunger.partialDamage -= (int)hunger.partialDamage;
            }
        } else {
            float newLevel = hunger.level - energy;
            boolean statusUpdated = false;
            if (newLevel >= STARVING) {

                GLog.negative(Messages.get(hunger, "onstarving"));
                Dungeon.hero.resting = false;
                Dungeon.hero.damage(1, hunger);
                statusUpdated = true;

                Dungeon.hero.interrupt();

            } else if (newLevel >= HUNGRY && hunger.level < HUNGRY) {

                GLog.warning(Messages.get(hunger, "onhungry"));
                statusUpdated = true;

            }
            hunger.level = newLevel;

            if (statusUpdated) {
                BuffIndicator.refreshHero();
            }
        }

	}

	public boolean isStarving() {
		return level >= STARVING;
	}

	public int hunger() {
		return (int)Math.ceil(level);
	}

	@Override
	public int icon() {
		if (level < HUNGRY) {
			return BuffIndicator.NONE;
		} else if (level < STARVING) {
			return BuffIndicator.HUNGER;
		} else {
			return BuffIndicator.STARVATION;
		}
	}

	@Override
	public String toString() {
		if (level < STARVING) {
			return Messages.get(this, "hungry");
		} else {
			return Messages.get(this, "starving");
		}
	}

	@Override
	public String desc() {
		String result;
		if (level < STARVING) {
			result = Messages.get(this, "desc_intro_hungry");
		} else {
			result = Messages.get(this, "desc_intro_starving");
		}

		result += Messages.get(this, "desc");

		return result;
	}

	@Override
	public void onDeath() {

		Badges.validateDeathFromHunger();

		Dungeon.fail( getClass() );
		GLog.negative( Messages.get(this, "ondeath") );
	}
}
