package howest.groep14.game.actor.movement;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.actor.EnemyActor;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.collision.DamagePlayerActor;

public class Snake extends MovementBehavior implements ISnake {
    private int length;
    private SpriteActor[] segments;
    private Sprite segmentSprite;
    private final float speed;
    private float lastX, lastY;
    private int sustainedDamage;

    public Snake(SpriteActor owner, int snakeLength, Sprite segmentSprite, float speed) {
        super(owner);
        this.length = snakeLength;
        this.segments = new SpriteActor[length];
        this.segmentSprite = segmentSprite;
        this.speed = speed;
        ISnake lastSegment = this;
        for (int i = 0; i < length; i++) {
            EnemyActor segment = new EnemyActor(owner.getStage(), segmentSprite, EnemyActor.ENEMY_TYPE.SNAKE_SEG);
            segment.setPosition(owner.getX() - (25 * i), owner.getY() - (25 * i));
            SnakeSegment mov = new SnakeSegment(segment, lastSegment);
            segment.setMovementBehavior(mov);
            DamagePlayerActor coll = new DamagePlayerActor(owner, 1, 0);
            segment.setCollisionBehavior(coll);
            segments[i] = segment;

            owner.getStage().addEnemy(segment);
            lastSegment = mov;
        }
    }

    @Override
    public MovementBehavior copy(SpriteActor newOwner) {
        return new Snake(newOwner, length, segmentSprite, speed);
    }

    @Override
    public void move(float delta) {
        float x = 0;
        float y = 0;

        if (!CustomUtils.outOfBoundsX(owner.getX(), owner.getWidth(), speed)) {
            x = speed;
        }
        if (!CustomUtils.outOfBoundsY(owner.getY(), owner.getHeight(), speed)) {
            y = speed;
        }
        owner.updatePositionAbsolute(x, y, false);
        lastX = x;
        lastY = y;
        if (sustainedDamage > 25) {
            for (SpriteActor segment : segments) {
                segment.remove();
            }
            owner.addAction(Actions.removeActor());
        }
    }

    public float getSpeed() {
        return speed;
    }

    public float getLastX() {
        return lastX;
    }

    public float getLastY() {
        return lastY;
    }

    public void damage(int damage) {
        this.sustainedDamage += 1;
    }
}
