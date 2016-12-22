# geometry-wars
Howest Geometry-wars project


Teammembers
-----------
Killian Meersman

Timo Vergauwen

Lowiek Lemay

Robin De Neef

## Code style
This project uses several design patterns.
SpriteActor is a child class of LibGDX's Actor class and has a Sprite that always has the same position, rotation and scale as the Actor itself. This class uses the **Strategy Pattern** to define four of its core behaviours.
Each of these behaviours is stored in a class field.

1. MovementBehavior   - Defines how the actor moves
2. AttackBehavior     - Defines how the actor attacks
3. HealthBehavior     - Defines how the actor responds to damage
4. CollisionBehavior  - Defines what the actor collides with, currently ony 1-way (if it does not collide with ex. EnemyActors an EnemyActor can still collide with it)


We do this by defining an abstract class (all behaviours need an owner field) and overriding this class as we need more behaviours.
If an actor does not have a certain bahevior (ex. It does not move), then it will have a behavior called No\<behavior name> (Ex. NoMovement)
