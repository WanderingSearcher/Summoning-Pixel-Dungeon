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

package com.trashboxbobylev.summoningpixeldungeon.sprites;

import com.trashboxbobylev.summoningpixeldungeon.Assets;
import com.trashboxbobylev.summoningpixeldungeon.actors.Char;
import com.watabou.noosa.TextureFilm;

public class HellBatSprite extends MobSprite {

	public HellBatSprite() {
		super();
		
		texture( Assets.HELL_BAT );
		
		TextureFilm frames = new TextureFilm( texture, 15, 15 );
		
		idle = new Animation( 8, true );
		idle.frames( frames, 0, 1 );
		
		run = new Animation( 12, true );
		run.frames( frames, 0, 1 );
		
		attack = new Animation( 12, false );
		attack.frames( frames, 2, 3, 0, 1 );
		
		die = new Animation( 12, false );
		die.frames( frames, 4, 5, 6 );
		
		play( idle );
	}

    @Override
    public void link( Char ch ) {
        super.link( ch );
        add( State.BURNING );
    }

    @Override
    public void die() {
        super.die();
        remove( State.BURNING );
    }
}