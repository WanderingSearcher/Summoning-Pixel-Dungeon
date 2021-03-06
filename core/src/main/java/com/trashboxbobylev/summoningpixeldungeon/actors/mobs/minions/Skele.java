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

package com.trashboxbobylev.summoningpixeldungeon.actors.mobs.minions;

import com.trashboxbobylev.summoningpixeldungeon.Assets;
import com.trashboxbobylev.summoningpixeldungeon.Dungeon;
import com.trashboxbobylev.summoningpixeldungeon.actors.Char;
import com.trashboxbobylev.summoningpixeldungeon.items.Generator;
import com.trashboxbobylev.summoningpixeldungeon.levels.features.Chasm;
import com.trashboxbobylev.summoningpixeldungeon.messages.Messages;
import com.trashboxbobylev.summoningpixeldungeon.sprites.SkeletonSprite;
import com.trashboxbobylev.summoningpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Skele extends Minion {
    {
        spriteClass = SkeletonSprite.class;

        baseMaxDR = 4;

        properties.add(Property.UNDEAD);
        properties.add(Property.INORGANIC);
    }

    @Override
    public void die( Object cause ) {

        super.die( cause );

        if (cause == Chasm.class) return;

        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
            Char ch = findChar( pos + PathFinder.NEIGHBOURS8[i] );
            //do not hurt allies or hero
            if (ch != null && ch.isAlive() && ch.alignment == Alignment.ENEMY && ch != Dungeon.hero) {
                int minDamage, maxDamage;
                switch (lvl){
                    case 0: default:
                        minDamage = 3;
                        maxDamage = 20;
                        break;
                    case 1:
                        minDamage = 6;
                        maxDamage = 35;
                        break;
                    case 2:
                        minDamage = 9;
                        maxDamage = 50;
                        break;
                    case 3:
                        minDamage = 13;
                        maxDamage = 75;
                        break;
                }
                int damage = Random.NormalIntRange(minDamage, maxDamage);
                damage = Math.max( 0,  damage - (ch.drRoll() + ch.drRoll()) );
                ch.damage( damage, this );
            }
        }

        if (Dungeon.level.heroFOV[pos]) {
            Sample.INSTANCE.play( Assets.SND_BONES );
        }
    }
}
