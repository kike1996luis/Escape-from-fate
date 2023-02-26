package com.me.EFF;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ContactDetection implements ContactListener {

	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		if (fa == null || fb == null) {
			return;
		}
		
		if (collisionPlayerWeapon(fa, fb)) {
			if (fa.getUserData() instanceof Weapon) {
				player pla = (player) fb.getUserData();
				if (!pla.isRekilling()) {
					Weapon tba = (Weapon) fa.getUserData();
					tba.collisionWithPlayer();
				}
			} else if (fb.getUserData() instanceof Weapon) {
				player pla = (player) fa.getUserData();
				if (!pla.isRekilling()) {
					Weapon tba = (Weapon) fb.getUserData();
					tba.collisionWithPlayer();
				}
			}
		}

		if (collisionEnemyWeapon(fa, fb)) {
			if (fa.getUserData() instanceof Weapon) {
				Enemy enemy = (Enemy) fb.getUserData();
				if (!enemy.isDeath() && !enemy.isKnocked() && enemy.persiguiendo) {
					Weapon tba = (Weapon) fa.getUserData();
					tba.collisionWithEnemy(enemy);
				}
			} else if (fb.getUserData() instanceof Weapon) {
				Enemy enemy = (Enemy) fb.getUserData();
				if (!enemy.isDeath() && !enemy.isKnocked() && enemy.persiguiendo) {
					Weapon tba = (Weapon) fb.getUserData();
					tba.collisionWithEnemy(enemy);
				}
			}
		}
		
		if (collisionWeaponEnemy(fa, fb)) {

			if (fa.getUserData() instanceof Enemy) {
				Enemy ba = (Enemy) fa.getUserData();
				Weapon asd = (Weapon) fb.getUserData();
				if (asd.getTimeDropped() < 30 && !ba.isKnocked()) {
					ba.knockOut(asd.getWeapon(), asd.getImpulse());
				} else {
					asd.collisionWithEnemy(ba.getBody());	//Se mueve a la direccion opuesta
				}

			} else if (fb.getUserData() instanceof Enemy) {
				Enemy bb = (Enemy) fb.getUserData();
				Weapon asd = (Weapon) fa.getUserData();
				if (asd.getTimeDropped() < 30 && !bb.isKnocked()) {
					bb.knockOut(asd.getWeapon(), asd.getImpulse());
				} else {
					asd.collisionWithEnemy(bb.getBody());
				}
			}
		}

		if (collisionPlayerItem(fa, fb)) {
			if (fa.getUserData() instanceof Item) {
				player pla = (player) fb.getUserData();
				if (!pla.isRekilling()) {
					Item tba = (Item) fa.getUserData();
					tba.setReceived();
				}
			} else if (fb.getUserData() instanceof Item) {
				player pla = (player) fa.getUserData();
				if (!pla.isRekilling()) {
					Item tba = (Item) fb.getUserData();
					tba.setReceived();
				}
			}
		}

		if (itemDetector(fa, fb)) {

			if (fa.getUserData() instanceof Weapon) {
				UserDataWrapper aux = (UserDataWrapper) fb.getUserData();
				if (aux.getObject() instanceof player) {
					Weapon tba = (Weapon) fa.getUserData();
					tba.collisionWithPlayer();
				}
			} else if (fb.getUserData() instanceof Weapon) {
				UserDataWrapper aux = (UserDataWrapper) fa.getUserData();
				if (aux.getObject() instanceof player) {
					Weapon tba = (Weapon) fb.getUserData();
					tba.collisionWithPlayer();
				}
			} else if (fb.getUserData() instanceof Item) {
				UserDataWrapper aux = (UserDataWrapper) fa.getUserData();
				if (aux.getObject() instanceof player) {
					player pla = (player) aux.getObject();
					if (!pla.isRekilling()) {
						Item tba = (Item) fb.getUserData();
						tba.setReceived();
					}
				}
			} else if (fa.getUserData() instanceof Item) {
				UserDataWrapper aux = (UserDataWrapper) fb.getUserData();
				if (aux.getObject() instanceof player) {
					player pla = (player) aux.getObject();
					if (!pla.isRekilling()) {
						Item tba = (Item) fa.getUserData();
						tba.setReceived();
					}
				}
			}
		}

		if (isBulletContact(fa, fb)) {

			// Condiciones para que la bala se destruya, si la bala viene del jugador y
			// colisiona con el jugador, no se destruye. Si la bala viene del enemigo y
			// colisiona con el enemigo no se destruye. Tampoco se destruye si colisiona con
			// un arma ni con una partícula. Al igual que ciertos materiales la bala debe
			// pasarles de largo en vez de destruirse y por eso se excluyen.

			boolean pass = true;
			if (fa.getUserData() instanceof Bullet && !(fb.getUserData() instanceof Bullet)) {
				Bullet ba = (Bullet) fa.getUserData();
				if (fb.getUserData() instanceof UserDataWrapper) {
					UserDataWrapper tmp = (UserDataWrapper) fb.getUserData();
					if (tmp.getID().equals("itemdetector") || tmp.getID().equals("melee")) {
						pass = false;
					}
					if (tmp.getID().equals("limit")) {
						Limit lim = (Limit) tmp.getObject();
						if (lim.isTraspassable()) {
							pass = false;
						}
					}
				}
				if (fb.getUserData() instanceof Enemy) {
					Enemy aux = (Enemy) fb.getUserData();
					if (aux.isKnocked()) {
						pass = false;
					}
				}
				if (!(ba.bulletFrom().equals("player") && fb.getUserData() instanceof player)
						&& !(ba.bulletFrom().equals("enemy") && fb.getUserData() instanceof Enemy)
						&& (!(fb.getUserData() instanceof Weapon)) && (!(fb.getUserData() instanceof Particle))
						&& pass) {
					if (fb.getUserData() instanceof Materials) {
						Materials bb = (Materials) fb.getUserData();
						if (!(bb.getName().equals("littleFlowerVase") || bb.getName().equals("glassTable")
								|| bb.getName().equals("bigDesk") || bb.getName().equals("mediumDesk")
								|| bb.getName().equals("desk") || bb.getName().equals("bed")
								|| bb.getName().equals("circularTable") || bb.getName().equals("wallGlass"))) {
							if (bb.getName().equals("ironWall") || bb.getName().equals("ironWall1")) {
								if(bb.typeAngle().equals("vertical")) {
									ba.impacto(fb, false, true, "vertical");
								}else {
									ba.impacto(fb, false, true, "horizontal");
								}
								ba.impacto(fb, false, true, "null");
							} else {
								ba.impacto(fb, false, false, "null");
							}
						}
					} else {
						ba.impacto(fb, true, false, "null");
					}
				}
			}
			if (fb.getUserData() instanceof Bullet && !(fa.getUserData() instanceof Bullet)) {
				Bullet ba = (Bullet) fb.getUserData();
				if (fa.getUserData() instanceof UserDataWrapper) {
					UserDataWrapper tmp = (UserDataWrapper) fa.getUserData();
					if (tmp.getID().equals("itemdetector") || tmp.getID().equals("melee")) {
						pass = false;
					}
					if (tmp.getID().equals("limit")) {
						Limit lim = (Limit) tmp.getObject();
						if (lim.isTraspassable()) {
							pass = false;
						}
					}
				}
				if (fa.getUserData() instanceof Enemy) {
					Enemy aux = (Enemy) fa.getUserData();
					if (aux.isKnocked()) {
						pass = false;
					}
				}

				if (!(ba.bulletFrom().equals("player") && fa.getUserData() instanceof player)
						&& !(ba.bulletFrom().equals("enemy") && fa.getUserData() instanceof Enemy)
						&& (!(fa.getUserData() instanceof Weapon)) && (!(fa.getUserData() instanceof Particle))
						&& pass) {
					if (fa.getUserData() instanceof Materials) {
						Materials bb = (Materials) fa.getUserData();
						if (!(bb.getName().equals("littleFlowerVase") || bb.getName().equals("glassTable")
								|| bb.getName().equals("bigDesk") || bb.getName().equals("mediumDesk")
								|| bb.getName().equals("desk") || bb.getName().equals("bed")
								|| bb.getName().equals("circularTable") || bb.getName().equals("wallGlass"))) {
							if (bb.getName().equals("ironWall") || bb.getName().equals("ironWall1")) {
								if(bb.typeAngle().equals("vertical")) {
									ba.impacto(fb, false, true, "vertical");
								}else {
									ba.impacto(fb, false, true, "horizontal");
								}
							} else {
								ba.impacto(fb, false, false, "null");
							}
						}
					} else {
						ba.impacto(fa, true, false, "null");
					}
				}
			}
		}
		if (collisionBulletMaterial(fa, fb)) {

			if (fa.getUserData() instanceof Materials) {
				Materials ba = (Materials) fa.getUserData();
				Bullet bb = (Bullet) fb.getUserData();
				ba.collisionWithBullet(bb);
			} else if (fb.getUserData() instanceof Materials) {
				Materials bb = (Materials) fb.getUserData();
				Bullet ba = (Bullet) fa.getUserData();
				bb.collisionWithBullet(ba);
			}
		}
		
		if(collisionForcedWeapon(fa, fb)) {
			
			if (fa.getUserData() instanceof Materials) {
				Materials ba = (Materials) fa.getUserData();
				if(!ba.isBreakable() && !ba.isTraspassable()) {
					Weapon bb = (Weapon) fb.getUserData();
					bb.forcedCollision();
				}
			} else if (fb.getUserData() instanceof Materials) {
				Materials bb = (Materials) fb.getUserData();
				if(!bb.isBreakable() && !bb.isTraspassable()) {
					Weapon ba = (Weapon) fa.getUserData();
					ba.forcedCollision();
				}
			} else {
				
				if(fa.getUserData() instanceof UserDataWrapper) {
					
					UserDataWrapper aux1 = (UserDataWrapper) fa.getUserData();
					if (aux1.getObject() instanceof Limit) {
						Weapon bb = (Weapon) fb.getUserData();
						bb.forcedCollision();
					}
				}if(fb.getUserData() instanceof UserDataWrapper) {
					
					UserDataWrapper aux1 = (UserDataWrapper) fb.getUserData();
					if (aux1.getObject() instanceof Limit) {
						Weapon ba = (Weapon) fa.getUserData();
						ba.forcedCollision();
					}
				}
			}
		}
		if (collisionWeaponMaterial(fa, fb)) {

			if (fa.getUserData() instanceof Materials) {
				Materials ba = (Materials) fa.getUserData();
				Weapon bb = (Weapon) fb.getUserData();
				ba.collisionWithWeapon(bb);
			} else if (fb.getUserData() instanceof Materials) {
				Materials bb = (Materials) fb.getUserData();
				Weapon ba = (Weapon) fa.getUserData();
				bb.collisionWithWeapon(ba);
			}
		}
		if (collisionExplosionPlayer(fa, fb)) {

			if (fa.getUserData() instanceof player) {
				player ba = (player) fa.getUserData();
				ba.getExplosion();
			} else if (fb.getUserData() instanceof player) {
				player bb = (player) fb.getUserData();
				bb.getExplosion();
			}
		}
		if (collisionExplosionEnemy(fa, fb)) {

			if (fa.getUserData() instanceof Enemy) {
				Enemy ba = (Enemy) fa.getUserData();
				ba.getExplosion();
			} else if (fb.getUserData() instanceof Enemy) {
				Enemy bb = (Enemy) fb.getUserData();
				bb.getExplosion();
			}
		}
		if (collisionExplosionWeapon(fa, fb)) {

			if (fa.getUserData() instanceof Weapon) {
				Weapon ba = (Weapon) fa.getUserData();
				ba.getExplosion();
			} else if (fb.getUserData() instanceof Weapon) {
				Weapon bb = (Weapon) fb.getUserData();
				bb.getExplosion();
			}
		}
		if (collisionExplosionMaterial(fa, fb)) {

			if (fa.getUserData() instanceof Materials) {
				Materials ba = (Materials) fa.getUserData();
				ba.getExplosion();
			} else if (fb.getUserData() instanceof Materials) {
				Materials bb = (Materials) fb.getUserData();
				bb.getExplosion();
			}
		}

		if (collisionExplosionParticle(fa, fb)) {

			if (fa.getUserData() instanceof Particle) {
				Particle ba = (Particle) fa.getUserData();
				ba.getExplosion();
			} else if (fb.getUserData() instanceof Particle) {
				Particle bb = (Particle) fb.getUserData();
				bb.getExplosion();
			}
		}

		if (collisionBulletPlayer(fa, fb)) {

			if (fa.getUserData() instanceof Bullet) {
				Bullet ba = (Bullet) fa.getUserData();
				player bb = (player) fb.getUserData();
				if(!bb.isKnocked()) {
					if (bb.getHurt(ba) && !bb.isDeath()) {
						ba.getHurt();
					} 
				}
			} else if (fb.getUserData() instanceof Bullet) {
				Bullet ba = (Bullet) fb.getUserData();
				player bb = (player) fa.getUserData();
				if(!bb.isKnocked()) {
					if (bb.getHurt(ba) && !bb.isDeath()) {
						ba.getHurt();
					} 
				}
			}
		}

		if (collisionBulletEnemy(fa, fb)) {

			if (fa.getUserData() instanceof Bullet) {
				Bullet ba = (Bullet) fa.getUserData();
				Enemy bb = (Enemy) fb.getUserData();
				if(!bb.isKnocked()) {
					if (bb.getHurt(ba) && !bb.isDeath()) {
						ba.getHurt();
					} 
				}
			} else if (fb.getUserData() instanceof Bullet) {
				Bullet ba = (Bullet) fb.getUserData();
				Enemy bb = (Enemy) fa.getUserData();
				if(!bb.isKnocked()) {
					if (bb.getHurt(ba) && !bb.isDeath()) {
						ba.getHurt();
					} 
				}
			}
		}

		if (isMeleeContact(fa, fb)) {

			if (fa.getUserData() instanceof Materials) {
				Materials ba = (Materials) fa.getUserData();
				UserDataWrapper pa = (UserDataWrapper) fb.getUserData();
				if (pa.getObject() instanceof Enemy) {
					Enemy aux = (Enemy) pa.getObject();
					if (aux.isMeleeActivated()) {
						ba.collisionWithMelee();
					}
				} else {
					ba.collisionWithMelee();
				}
			} else if (fb.getUserData() instanceof Materials) {
				Materials bb = (Materials) fb.getUserData();
				UserDataWrapper pa = (UserDataWrapper) fa.getUserData();
				if (pa.getObject() instanceof Enemy) {
					Enemy aux = (Enemy) pa.getObject();
					if (aux.isMeleeActivated()) {
						bb.collisionWithMelee();
					}
				} else {
					bb.collisionWithMelee();
				}
			} else if (fa.getUserData() instanceof Enemy) {

				UserDataWrapper pa = (UserDataWrapper) fb.getUserData();
				if (pa.getObject() instanceof player) {
					player p = (player) pa.getObject();
					Enemy ba = (Enemy) fa.getUserData();
					if (p.getWeapon().equals("crowbar") || p.getWeapon().equals("knife") && !ba.isKnocked()) {
						ba.knockOut("melee", new Vector2(0, 0));
					} else {
						ba.knockOut(p.getWeapon(), new Vector2(10, 10));
					}
				}
			} else if (fb.getUserData() instanceof Enemy) {

				UserDataWrapper pa = (UserDataWrapper) fa.getUserData();
				if (pa.getObject() instanceof player) {
					player p = (player) pa.getObject();
					Enemy bb = (Enemy) fb.getUserData();
					if (p.getWeapon().equals("crowbar") || p.getWeapon().equals("knife") && !bb.isKnocked()) {
						bb.knockOut("melee", new Vector2(0, 0));
					} else {
						bb.knockOut(p.getWeapon(), new Vector2(10, 10));
					}
				}
			} else if (fa.getUserData() instanceof player) {

				UserDataWrapper pa = (UserDataWrapper) fb.getUserData();
				if (pa.getObject() instanceof Enemy) {
					Enemy p = (Enemy) pa.getObject();
					player ba = (player) fa.getUserData();
					if (p != null && !p.isKnocked() && !p.isDeath() && !ba.isKnocked() && !ba.isDeath()) {
						p.melee(true);
						if ((p.getWeapon().equals("crowbar") || p.getWeapon().equals("knife") && !ba.isKnocked())
								&& p.isMeleeActivated()) {
							ba.knockOut("melee", new Vector2(0, 0));
						} else {
							if (p.isMeleeActivated()) {
								ba.knockOut(p.getWeapon(), new Vector2(10, 10));
							}
						}
					}
				}

			} else if (fb.getUserData() instanceof player) {

				UserDataWrapper pa = (UserDataWrapper) fa.getUserData();
				if (pa.getObject() instanceof Enemy) {
					Enemy p = (Enemy) pa.getObject();
					player bb = (player) fb.getUserData();
					if (p != null && !p.isKnocked() && !p.isDeath() && !bb.isKnocked() && !bb.isDeath()) {
						p.melee(true);
						if ((p.getWeapon().equals("crowbar") || p.getWeapon().equals("knife") && !bb.isKnocked())
								&& p.isMeleeActivated()) {
							bb.knockOut("melee", new Vector2(0, 0));
						} else {
							if (p.isMeleeActivated()) {
								bb.knockOut(p.getWeapon(), new Vector2(10, 10));
							}
						}
					}
				}
			}
		}

		if (playerGoalContact(fa, fb)) {

			if (fa.getUserData() instanceof UserDataWrapper) {
				UserDataWrapper aux1 = (UserDataWrapper) fa.getUserData();
				if (aux1.getObject() instanceof Limit) {

					Limit ba = (Limit) aux1.getObject();
					if (fb.getUserData() instanceof UserDataWrapper) {
						UserDataWrapper aux = (UserDataWrapper) fb.getUserData();
						if (aux.getID().equals("itemdetector") || aux.getID().equals("PLAYER")) {
							ba.setTouchingGoal();
						}
					} else if (fb.getUserData() instanceof player) {
						ba.setTouchingGoal();
					}
				}
				if (fb.getUserData() instanceof UserDataWrapper) {
					UserDataWrapper aux2 = (UserDataWrapper) fb.getUserData();
					if (aux2.getObject() instanceof Limit) {

						Limit bb = (Limit) aux2.getObject();
						if (fa.getUserData() instanceof UserDataWrapper) {
							UserDataWrapper aux = (UserDataWrapper) fa.getUserData();
							if (aux.getID().equals("itemdetector") || aux.getID().equals("PLAYER")) {
								bb.setTouchingGoal();
							}
						} else if (fb.getUserData() instanceof player) {
							bb.setTouchingGoal();
						}
					}
				}
			}
		}

		if (collisionEnemyMaterial(fa, fb)) {

			if (fa.getUserData() instanceof Materials) {
				Materials aux = (Materials) fa.getUserData();
				Enemy bod = (Enemy) fb.getUserData();
				if (!aux.isStatic) {
					aux.collisionWithEnemy(bod.getBody());
				}
			} else if (fb.getUserData() instanceof Materials) {
				Materials aux = (Materials) fb.getUserData();
				Enemy bod = (Enemy) fa.getUserData();
				if (!aux.isStatic) {
					aux.collisionWithEnemy(bod.getBody());
				}
			}
		}
		if (fa.getUserData() instanceof UserDataWrapper && fb.getUserData() instanceof UserDataWrapper) {
			UserDataWrapper lol = (UserDataWrapper) fa.getUserData();
			UserDataWrapper lol1 = (UserDataWrapper) fb.getUserData();
			if (lol.getObject() instanceof Limit && lol1.getObject() instanceof player) {
				player p = (player) lol1.getObject();
				if (p.isMeleeActivated()) {
					Limit lim = (Limit) lol.getObject();
					lim.punched();
				}
			} else if (lol.getObject() instanceof Limit && lol1.getObject() instanceof Enemy) {
				Enemy p = (Enemy) lol1.getObject();
				if (p.isMeleeActivated()) {
					Limit lim = (Limit) lol.getObject();
					lim.punched();
				}
			}
		} else if (fb.getUserData() instanceof UserDataWrapper && fa.getUserData() instanceof UserDataWrapper) {
			UserDataWrapper lol = (UserDataWrapper) fb.getUserData();
			UserDataWrapper lol1 = (UserDataWrapper) fa.getUserData();
			if (lol.getObject() instanceof Limit && lol1.getObject() instanceof player) {
				player p = (player) lol1.getObject();
				if (p.isMeleeActivated()) {
					Limit lim = (Limit) lol.getObject();
					lim.punched();
				}
			} else if (lol.getObject() instanceof Limit && lol1.getObject() instanceof Enemy) {
				Enemy p = (Enemy) lol1.getObject();
				if (p.isMeleeActivated()) {
					Limit lim = (Limit) lol.getObject();
					lim.punched();
				}
			}
		}
	}

	public void endContact(Contact contact) {

		if ((contact.getFixtureA() != null) && (contact.getFixtureB() != null)) {
			if (contact.getFixtureA().getUserData() instanceof Item
					&& (contact.getFixtureB().getUserData() instanceof player)) {

				Item item = (Item) contact.getFixtureA().getUserData();
				item.endContact();
			} else if (contact.getFixtureB().getUserData() instanceof Item
					&& !(contact.getFixtureA().getUserData() instanceof player)) {

				Item item = (Item) contact.getFixtureB().getUserData();
				item.endContact();
			} else if (contact.getFixtureB().getUserData() instanceof Weapon
					&& (contact.getFixtureA().getUserData() instanceof player)) {

				Weapon weapon = (Weapon) contact.getFixtureB().getUserData();
				weapon.endContact();
			} else if (contact.getFixtureA().getUserData() instanceof Weapon
					&& (contact.getFixtureB().getUserData() instanceof player)) {

				Weapon weapon = (Weapon) contact.getFixtureA().getUserData();
				weapon.endContact();
			} else if (contact.getFixtureA().getUserData() instanceof Item
					&& (contact.getFixtureB().getUserData() instanceof UserDataWrapper)) {

				UserDataWrapper aux = (UserDataWrapper) contact.getFixtureB().getUserData();
				if (aux.getID().equals("itemdetector")) {
					Item item = (Item) contact.getFixtureA().getUserData();
					item.endContact();
				}
			} else if (contact.getFixtureB().getUserData() instanceof Item
					&& (contact.getFixtureA().getUserData() instanceof UserDataWrapper)) {

				UserDataWrapper aux = (UserDataWrapper) contact.getFixtureA().getUserData();
				if (aux.getID().equals("itemdetector")) {
					Item item = (Item) contact.getFixtureB().getUserData();
					item.endContact();
				}
			} else if (contact.getFixtureB().getUserData() instanceof Weapon
					&& (contact.getFixtureA().getUserData() instanceof UserDataWrapper)) {

				UserDataWrapper aux = (UserDataWrapper) contact.getFixtureA().getUserData();
				if (aux.getID().equals("itemdetector")) {
					Weapon weapon = (Weapon) contact.getFixtureB().getUserData();
					weapon.endContact();
				}
			} else if (contact.getFixtureA().getUserData() instanceof Weapon
					&& (contact.getFixtureB().getUserData() instanceof UserDataWrapper)) {

				UserDataWrapper aux = (UserDataWrapper) contact.getFixtureB().getUserData();
				if (aux.getID().equals("itemdetector")) {
					Weapon weapon = (Weapon) contact.getFixtureA().getUserData();
					weapon.endContact();
				}
			}
			short firstBit = contact.getFixtureA().getFilterData().categoryBits;
			short secondBit = contact.getFixtureB().getFilterData().categoryBits;
			if ((firstBit == Constants.BIT_TRASPASABLE && secondBit == Constants.BIT_ITEM)
					|| (firstBit == Constants.BIT_ITEM && secondBit == Constants.BIT_TRASPASABLE)) {
				contact.setEnabled(false);
			} else if (contact.getFixtureB().getUserData() instanceof player
					&& (contact.getFixtureA().getUserData() instanceof UserDataWrapper)) {
				UserDataWrapper aux = (UserDataWrapper) contact.getFixtureA().getUserData();
				if (aux.getObject() instanceof Enemy && aux.getID().equals("melee")) {
					Enemy enemy = (Enemy) aux.getObject();
					enemy.collisionWithPlayer();
				}
			} else if (contact.getFixtureA().getUserData() instanceof player
					&& (contact.getFixtureB().getUserData() instanceof UserDataWrapper)) {
				UserDataWrapper aux = (UserDataWrapper) contact.getFixtureB().getUserData();
				if (aux.getObject() instanceof Enemy && aux.getID().equals("melee")) {
					Enemy enemy = (Enemy) aux.getObject();
					enemy.collisionWithPlayer();
				}
			}
		}
	}

	public void preSolve(Contact contact, Manifold mnfld) {

		short firstBit = contact.getFixtureA().getFilterData().categoryBits;
		short secondBit = contact.getFixtureB().getFilterData().categoryBits;
		if ((firstBit == Constants.BIT_TRASPASABLE && secondBit == Constants.BIT_ITEM)
				|| (firstBit == Constants.BIT_ITEM && secondBit == Constants.BIT_TRASPASABLE)) {

			contact.setEnabled(false);
		} else if (firstBit == Constants.BIT_ITEM && secondBit == Constants.BIT_ITEM) {

			if (contact.getFixtureA().getUserData() instanceof Weapon
					&& contact.getFixtureB().getUserData() instanceof Weapon) {
				contact.setEnabled(false);
			}
		}
		
		if (contact.getFixtureA().getUserData() instanceof Enemy) {
			Enemy a = (Enemy) contact.getFixtureA().getUserData();
			if(a.isKnocked()) {
				contact.setEnabled(false);
			}
		} else if (contact.getFixtureB().getUserData() instanceof Enemy) {
			Enemy a = (Enemy) contact.getFixtureB().getUserData();
			if(a.isKnocked()) {
				contact.setEnabled(false);
			}
		} else if (contact.getFixtureA().getUserData() instanceof player) {
			player a = (player) contact.getFixtureA().getUserData();
			if(a.isKnocked()) {
				contact.setEnabled(false);
			}
		} else if (contact.getFixtureB().getUserData() instanceof player) {
			player a = (player) contact.getFixtureB().getUserData();
			if(a.isKnocked()) {
				contact.setEnabled(false);
			}
		}
		if (contact.getFixtureA().getUserData() instanceof Weapon
				&& !(contact.getFixtureB().getUserData() instanceof player)
				&& !(contact.getFixtureB().getUserData() instanceof Weapon)) {

			Weapon weapon = (Weapon) contact.getFixtureA().getUserData();
			if (weapon.getWeapon().equals("knife") && secondBit != Constants.BIT_TRASPASABLE) {
				weapon.stopImpulse();
			}
		} else if (contact.getFixtureB().getUserData() instanceof Weapon
				&& !(contact.getFixtureA().getUserData() instanceof player)
				&& !(contact.getFixtureA().getUserData() instanceof Weapon)) {

			Weapon weapon = (Weapon) contact.getFixtureB().getUserData();
			if (weapon.getWeapon().equals("knife") && firstBit != Constants.BIT_TRASPASABLE) {
				weapon.stopImpulse();
			}
		} else if (contact.getFixtureA().getUserData() instanceof Enemy
				&& (contact.getFixtureB().getUserData() instanceof Enemy)) {
			contact.setEnabled(false);
			
		} else if (contact.getFixtureA().getUserData() instanceof Enemy
				&& (contact.getFixtureB().getUserData() instanceof player)
				|| contact.getFixtureB().getUserData() instanceof Enemy
						&& (contact.getFixtureA().getUserData() instanceof player)) {
			if (contact.getFixtureA().getUserData() instanceof Enemy) {
				Enemy enemy = (Enemy) contact.getFixtureA().getUserData();
				player Player = (player) contact.getFixtureB().getUserData();
				if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && enemy.isKnocked()) {
					if (!Player.isRekilling()) {
						boolean boo = Player.randomBoolean();
						Player.reKill(enemy.getPosition(), enemy.getGrados(), boo);
						enemy.rekilled(boo);
					}
				}
				if (Player.isKnocked() && !enemy.isKnocked()) {
					/*if (!enemy.isRekilling()) {
						boolean boo = enemy.randomBoolean();
						enemy.reKill(Player.getPosition(), Player.getGrados(), boo);
						Player.rekilled(boo);
					}*/
				}
			} else if (contact.getFixtureB().getUserData() instanceof Enemy) {
				Enemy enemy = (Enemy) contact.getFixtureB().getUserData();
				player Player = (player) contact.getFixtureA().getUserData();
				if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && enemy.isKnocked()) {
					if (!Player.isRekilling()) {
						boolean boo = Player.randomBoolean();
						Player.reKill(enemy.getPosition(), enemy.getGrados(), boo);
						enemy.rekilled(boo);
					}
				}
				if (Player.isKnocked() && !enemy.isKnocked()) {
					/* (!enemy.isRekilling()) {
						boolean boo = enemy.randomBoolean();
						enemy.reKill(Player.getPosition(), Player.getGrados(), boo);
						Player.rekilled(boo);
					}*/
				}
			}
			contact.setEnabled(false);
		} else if (contact.getFixtureB().getUserData() instanceof Enemy
				&& contact.getFixtureA().getUserData() instanceof Weapon) {
			Enemy enemy = (Enemy) contact.getFixtureB().getUserData();
			if (enemy.isKnocked()) {
				contact.setEnabled(false);
			}
		} else if (contact.getFixtureA().getUserData() instanceof Enemy
				&& contact.getFixtureB().getUserData() instanceof Weapon) {
			Enemy enemy = (Enemy) contact.getFixtureA().getUserData();
			if (enemy.isKnocked()) {
				contact.setEnabled(false);
			}
		}
	}

	public void postSolve(Contact contact, ContactImpulse ci) {

	}

	//Condiciones para la detección de contactos
	
	private boolean collisionPlayerWeapon(Fixture a, Fixture b) {

		return ((a.getUserData() instanceof player && b.getUserData() instanceof Weapon)
				|| (b.getUserData() instanceof player && a.getUserData() instanceof Weapon));
	}

	private boolean collisionEnemyWeapon(Fixture a, Fixture b) {

		return ((a.getUserData() instanceof Enemy && b.getUserData() instanceof Weapon)
				|| (b.getUserData() instanceof Enemy && a.getUserData() instanceof Weapon));
	}
	
	private boolean collisionEnemyMaterial(Fixture a, Fixture b) {

		return ((a.getUserData() instanceof Enemy && b.getUserData() instanceof Materials)
				|| (b.getUserData() instanceof Enemy && a.getUserData() instanceof Materials));
	}

	private boolean collisionPlayerItem(Fixture a, Fixture b) {

		return ((a.getUserData() instanceof player && b.getUserData() instanceof Item)
				|| (b.getUserData() instanceof player && a.getUserData() instanceof Item));
	}

	private boolean collisionBulletMaterial(Fixture a, Fixture b) {

		return ((a.getUserData() instanceof Bullet && b.getUserData() instanceof Materials)
				|| (b.getUserData() instanceof Bullet && a.getUserData() instanceof Materials));
	}

	private boolean collisionWeaponMaterial(Fixture a, Fixture b) {

		return ((a.getUserData() instanceof Weapon && b.getUserData() instanceof Materials)
				|| (b.getUserData() instanceof Weapon && a.getUserData() instanceof Materials));
	}

	private boolean collisionWeaponEnemy(Fixture a, Fixture b) {

		return ((a.getUserData() instanceof Weapon && b.getUserData() instanceof Enemy)
				|| (b.getUserData() instanceof Weapon && a.getUserData() instanceof Enemy));
	}

	private boolean collisionExplosionPlayer(Fixture a, Fixture b) {
		return ((a.getUserData() instanceof player && b.getUserData() instanceof Explosion)
				|| (b.getUserData() instanceof player && a.getUserData() instanceof Explosion));
	}

	private boolean collisionExplosionEnemy(Fixture a, Fixture b) {
		return ((a.getUserData() instanceof Enemy && b.getUserData() instanceof Explosion)
				|| (b.getUserData() instanceof Enemy && a.getUserData() instanceof Explosion));
	}

	private boolean collisionExplosionWeapon(Fixture a, Fixture b) {
		return ((a.getUserData() instanceof Weapon && b.getUserData() instanceof Explosion)
				|| (b.getUserData() instanceof Weapon && a.getUserData() instanceof Explosion));
	}

	private boolean collisionExplosionMaterial(Fixture a, Fixture b) {
		return ((a.getUserData() instanceof Materials && b.getUserData() instanceof Explosion)
				|| (b.getUserData() instanceof Materials && a.getUserData() instanceof Explosion));
	}

	public boolean isBulletContact(Fixture a, Fixture b) {

		return (a.getBody().getUserData() instanceof Bullet || b.getBody().getUserData() instanceof Bullet);
	}

	private boolean collisionExplosionParticle(Fixture a, Fixture b) {
		return ((a.getUserData() instanceof Particle && b.getUserData() instanceof Explosion)
				|| (b.getUserData() instanceof Particle && a.getUserData() instanceof Explosion));
	}

	private boolean collisionBulletPlayer(Fixture a, Fixture b) {
		return ((a.getUserData() instanceof player && b.getUserData() instanceof Bullet)
				|| (b.getUserData() instanceof player && a.getUserData() instanceof Bullet));
	}

	private boolean collisionBulletEnemy(Fixture a, Fixture b) {
		return ((a.getUserData() instanceof Enemy && b.getUserData() instanceof Bullet)
				|| (b.getUserData() instanceof Enemy && a.getUserData() instanceof Bullet));
	}

	private boolean isMeleeContact(Fixture a, Fixture b) {
		if (a.getUserData() instanceof UserDataWrapper) {
			UserDataWrapper aux = (UserDataWrapper) a.getUserData();
			if (aux.getID().equals("melee")) {
				return true;
			}
		} else if (b.getUserData() instanceof UserDataWrapper) {
			UserDataWrapper aux = (UserDataWrapper) b.getUserData();
			if (aux.getID().equals("melee")) {
				return true;
			}
		}
		return false;
	}

	private boolean playerGoalContact(Fixture a, Fixture b) {
		if (a.getUserData() instanceof UserDataWrapper) {
			UserDataWrapper aux = (UserDataWrapper) a.getUserData();
			if (aux.getID().equals("limit")) {
				UserDataWrapper ba = (UserDataWrapper) a.getUserData();
				Limit va = (Limit) ba.getObject();
				if (va.getgoalID() == 999) {
					return true;
				}
			}
		} else if (b.getUserData() instanceof UserDataWrapper) {
			UserDataWrapper aux = (UserDataWrapper) b.getUserData();
			if (aux.getID().equals("limit")) {
				UserDataWrapper bb = (UserDataWrapper) b.getUserData();
				Limit vb = (Limit) bb.getObject();
				if (vb.getgoalID() == 999) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean itemDetector(Fixture a, Fixture b) {
		if (a.getUserData() instanceof UserDataWrapper) {
			UserDataWrapper aux = (UserDataWrapper) a.getUserData();
			if (aux.getID().equals("itemdetector")) {
				return true;
			}
		} else if (b.getUserData() instanceof UserDataWrapper) {
			UserDataWrapper aux = (UserDataWrapper) b.getUserData();
			if (aux.getID().equals("itemdetector")) {
				return true;
			}
		}
		return false;
	}
	
	private boolean collisionForcedWeapon(Fixture a, Fixture b) {
		if (a.getUserData() instanceof Weapon || b.getUserData() instanceof Weapon) {
			return true;
		}
		return false;
	}
}
