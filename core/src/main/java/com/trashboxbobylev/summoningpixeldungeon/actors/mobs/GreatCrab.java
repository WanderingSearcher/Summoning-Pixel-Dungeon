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

import com.trashboxbobylev.summoningpixeldungeon.Dungeon;
import com.trashboxbobylev.summoningpixeldungeon.actors.Char;
import com.trashboxbobylev.summoningpixeldungeon.actors.mobs.npcs.Ghost;
import com.trashboxbobylev.summoningpixeldungeon.items.food.MysteryMeat;
import com.trashboxbobylev.summoningpixeldungeon.items.wands.Wand;
import com.trashboxbobylev.summoningpixeldungeon.messages.Messages;
import com.trashboxbobylev.summoningpixeldungeon.sprites.CharSprite;
import com.trashboxbobylev.summoningpixeldungeon.sprites.GreatCrabSprite;
import com.trashboxbobylev.summoningpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class GreatCrab extends Crab {

	{
		spriteClass = GreatCrabSprite.class;

		HP = HT = 25;
		defenseSkill = 0; //see damage()
		baseSpeed = 0.25f;

		EXP = 32;

		state = WANDERING;

		properties.add(Property.MINIBOSS);
	}

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 8, 12 );
    }

    private int moving = 0;

	@Override
	protected boolean getCloser( int target ) {
		//this is used so that the crab remains slower, but still detects the player at the expected rate.
		moving++;
		if (moving < 4) {
			return super.getCloser( target );
		} else {
			moving = 0;
			return true;
		}

	}

    @Override
    public int attackSkill( Char target ) {
        return 15;
    }

	@Override
	public void damage( int dmg, Object src ){
		//crab blocks all attacks originating from its current enemy if it sees them.
		//All direct damage is negated, no exceptions. environmental effects go through as normal.
		if ((enemySeen && state != SLEEPING && paralysed == 0)
				&& ((src instanceof Wand && enemy == Dungeon.hero)
				|| (src instanceof Char && enemy == src))){
			GLog.negative( Messages.get(this, "noticed") );
			sprite.showStatus( CharSprite.NEUTRAL, Messages.get(this, "blocked") );
		} else {
			super.damage( dmg, src );
		}
	}

	@Override
	public void die( Object cause ) {
		super.die( cause );

		Ghost.Quest.process();

		Dungeon.level.drop( new MysteryMeat(), pos );
		Dungeon.level.drop( new MysteryMeat(), pos ).sprite.drop();
	}
}
