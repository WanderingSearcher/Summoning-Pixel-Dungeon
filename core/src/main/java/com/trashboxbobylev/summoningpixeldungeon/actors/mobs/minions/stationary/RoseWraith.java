/*
 *
 *  * Pixel Dungeon
 *  * Copyright (C) 2012-2015 Oleg Dolya
 *  *
 *  * Shattered Pixel Dungeon
 *  * Copyright (C) 2014-2019 Evan Debenham
 *  *
 *  * Summoning Pixel Dungeon
 *  * Copyright (C) 2019-2020 TrashboxBobylev
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */

package com.trashboxbobylev.summoningpixeldungeon.actors.mobs.minions.stationary;

import com.trashboxbobylev.summoningpixeldungeon.Dungeon;
import com.trashboxbobylev.summoningpixeldungeon.actors.Char;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.FlavourBuff;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.powers.MagicPower;
import com.trashboxbobylev.summoningpixeldungeon.actors.mobs.Wraith;
import com.trashboxbobylev.summoningpixeldungeon.mechanics.Ballistica;
import com.trashboxbobylev.summoningpixeldungeon.sprites.CharSprite;
import com.trashboxbobylev.summoningpixeldungeon.sprites.RoseWraithSprite;
import com.watabou.utils.Random;

public class RoseWraith extends StationaryMinion {
    {
        spriteClass = RoseWraithSprite.class;
    }

    @Override
    public int defenseSkill(Char enemy) {
        int round = Math.round(super.attackSkill(enemy) * 1.8f);
        if (buff(MagicPower.class) != null) round = Math.round(super.attackSkill(enemy) * 1f);
        return round;
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
    }

    @Override
    protected boolean doAttack(Char enemy) {
        boolean visible = Dungeon.level.heroFOV[pos];

        Timer timer = buff(Timer.class);
        if (timer == null){
            int timing = Random.IntRange(4, 8);
            if (buff(MagicPower.class) != null) timing /= 3;
            Buff.affect(this, Timer.class, timing*attackDelay());
            Wraith.summonAt(this);
            this.damage(1, this);
        }

        spend(attackDelay());
        next();

        return !visible;
    }

    @Override
    protected boolean act() {
        if (buff(Timer.class) != null) sprite.showStatus(CharSprite.DEFAULT, String.valueOf(buff(Timer.class).cooldown()+1));
        return super.act();
    }

    public static class Timer extends FlavourBuff {

    }


}
